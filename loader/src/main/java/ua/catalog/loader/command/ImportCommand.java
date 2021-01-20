package ua.catalog.loader.command;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ua.catalog.loader.classes.AbstractImporter;
import ua.catalog.loader.component.parser.Parser;
import ua.catalog.loader.component.parser.dto.*;
import ua.catalog.loader.entity.*;
import ua.catalog.loader.mapper.*;
import ua.catalog.loader.repository.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class ImportCommand implements Runnable {

    SourceRepository sourceRepository;

    public ImportCommand() {
        sourceRepository = new SourceRepository();
    }

    @SneakyThrows
    public void run() {
        List<Source> sources = sourceRepository.findActive();

        for (Source source : sources) {

            File file = new File(source.getUrl());

            if (!file.exists()) {
                log.error("Source " + source.getId() + " file: " + source.getUrl() + " not found.");
                continue;
            }

            if (file.lastModified() == source.getLastModified()) {
                log.info("Source " + source.getId() + ": Hasn't modified.");
                continue;
            }

            switch (source.getType()) {
                case MEDICINE -> (new MedicineCommand(source)).run();
                case PARTNER_MEDICINE -> (new PartnerMedicineCommand(source)).run();
                case PARTNER_PRICE -> (new PartnerPriceCommand(source)).run();
                case PHARMACY -> (new PharmacyCommand(source)).run();
                case TAG -> (new TagCommand(source)).run();
                case MEDICINE_TAGS -> (new MedicineTagsCommand(source)).run();
            }

            source.setLastModified(file.lastModified());
            sourceRepository.update(source);
        }
    }

    private final static class MedicineCommand extends AbstractImporter<MedicineDto, Medicine> {

        private final MedicineMapper mapper;
        private BatchInsert<Medicine> medicineRepository;
        private BatchInsert<MedicineImageSource> imageSourceRepository;
        private BatchInsert<MedicineRegistration> registrationRepository;


        public MedicineCommand(Source source) throws FileNotFoundException {
            super(new Parser<>(MedicineDto.class, source.getUrl()));
            mapper = new MedicineMapperImpl();
            medicineRepository = new MedicineRepository();
            imageSourceRepository = new MedicineImageSourceRepository();
            registrationRepository = new MedicineRegistrationRepository();
        }

        @Override
        public Medicine cast(MedicineDto dto) {
            return mapper.toEntity(dto);
        }

        @Override
        public void batchInsert(List<Medicine> entities) throws SQLException {
            medicineRepository.batchInsert(entities);

            List<MedicineImageSource> imageSources = entities.stream().map(Medicine::getImageSource).filter(Objects::nonNull).collect(Collectors.toList());
            imageSourceRepository.batchInsert(imageSources);
            imageSources.clear();

            List<MedicineRegistration> registrations = entities.stream().flatMap(e -> e.getMedicineRegistrations().stream().filter(z -> !z.getCode().isEmpty())).collect(Collectors.toList());
            registrationRepository.batchInsert(registrations);
        }
    }

    private final static class TagCommand extends AbstractImporter<TagDto, Tag> {

        protected TagMapper mapper;
        private BatchInsert<Tag> tagRepository;
        private TagVocabularyRepository tagVocabularyRepository;
        private Map<TagVocabulary.Type, Integer> tagVocabularyIndex = null;

        public TagCommand(Source source) throws FileNotFoundException {
            super(new Parser<>(TagDto.class, source.getUrl()));
            mapper = new TagMapperImpl();
            tagRepository = new TagRepository();
            tagVocabularyRepository = new TagVocabularyRepository();
        }

        @Override
        public Tag cast(TagDto dto) throws SQLException {
            if (tagVocabularyIndex == null) {
                tagVocabularyIndex = tagVocabularyRepository.getTagVocabularyIndex();
            }
            return mapper.toEntity(dto, tagVocabularyIndex);
        }

        @Override
        public void batchInsert(List<Tag> entities) throws SQLException {
            tagRepository.batchInsert(entities);
        }
    }

    private final static class MedicineTagsCommand extends AbstractImporter<MedicineTagDto, MedicineTag> {

        protected TagMapper mapper;
        private BatchInsert<MedicineTag> repository;
        private TagRepository tagRepository;
        private Map<TagVocabulary.Type, Map<Integer, Integer>> tagCascadeIndex = null;

        public MedicineTagsCommand(Source source) throws FileNotFoundException {
            super(new Parser<>(MedicineTagDto.class, source.getUrl()));
            mapper = new TagMapperImpl();
            repository = new MedicineTagRepository();
            tagRepository = new TagRepository();
        }

        @Override
        public MedicineTag cast(MedicineTagDto dto) throws SQLException {

            if (tagCascadeIndex == null) {
                tagCascadeIndex = tagRepository.getTagCascadeIndex();
            }

            MedicineTag tag = mapper.toEntity(dto, tagCascadeIndex);

            if(tag.getTagId() == null){
                return null;
            }

            return tag;
        }

        @Override
        public void batchInsert(List<MedicineTag> entities) throws SQLException {
            repository.batchInsert(entities);
        }
    }

    private final static class PartnerMedicineCommand extends AbstractImporter<PartnerMedicineDto, PartnerMedicine> {

        protected PartnerMedicineMapper mapper;
        private BatchInsert<PartnerMedicine> repository;

        public PartnerMedicineCommand(Source source) throws FileNotFoundException {
            super(new Parser<>(PartnerMedicineDto.class, source.getUrl()));
            mapper = new PartnerMedicineMapperImpl();
            repository = new PartnerMedicineRepository();
        }

        @Override
        public PartnerMedicine cast(PartnerMedicineDto dto) {
            return mapper.toEntity(dto);
        }

        @Override
        public void batchInsert(List<PartnerMedicine> entities) throws SQLException {
            repository.batchInsert(entities);
        }
    }

    private final static class PartnerPriceCommand extends AbstractImporter<PartnerPriceDto, PartnerPrice> {

        protected PartnerMedicineMapper mapper;
        private BatchInsert<PartnerPrice> repository;

        public PartnerPriceCommand(Source source) throws FileNotFoundException {
            super(new Parser<>(PartnerPriceDto.class, source.getUrl()));
            mapper = new PartnerMedicineMapperImpl();
            repository = new PartnerPriceRepository();
        }

        @Override
        public PartnerPrice cast(PartnerPriceDto dto) {
            return mapper.toEntity(dto);
        }

        @Override
        public void batchInsert(List<PartnerPrice> entities) throws SQLException {
            repository.batchInsert(entities);
        }
    }

    private final static class PharmacyCommand extends AbstractImporter<PharmacyDto, Pharmacy> {

        protected PharmacyMapper mapper;
        private BatchInsert<Pharmacy> repository;

        public PharmacyCommand(Source source) throws FileNotFoundException {
            super(new Parser<>(PharmacyDto.class, source.getUrl()));
            mapper = new PharmacyMapperImpl();
            repository = new PharmacyRepository();
        }

        @Override
        public Pharmacy cast(PharmacyDto dto) {
            return mapper.toEntity(dto);
        }

        @Override
        public void batchInsert(List<Pharmacy> entities) throws SQLException {
            repository.batchInsert(entities);
        }
    }

}
