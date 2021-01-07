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
import java.util.List;

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
                case MEDICINE -> {
                    (new MedicineCommand(source)).run();
                    (new MedicineImageCommand(source)).run();
                }
                case PARTNER_MEDICINE -> (new PartnerMedicineCommand(source)).run();
                case PARTNER_PRICE -> (new PartnerPriceCommand(source)).run();
                case PHARMACY -> (new PharmacyCommand(source)).run();
            }

            source.setLastModified(file.lastModified());
            sourceRepository.update(source);
        }
    }

    private final static class MedicineCommand extends AbstractImporter<MedicineDto, Medicine> {

        protected MedicineMapper mapper;

        public MedicineCommand(Source source) throws FileNotFoundException {
            super(new MedicineRepository(), new Parser<>(MedicineDto.class, source.getUrl()));
            mapper = new MedicineMapperImpl();
        }

        @Override
        public Medicine cast(MedicineDto dto) {
            return mapper.toEntity(dto);
        }
    }

    private final static class MedicineImageCommand extends AbstractImporter<MedicineImageDto, MedicineImageSource> {

        protected MedicineMapper mapper;

        public MedicineImageCommand(Source source) throws FileNotFoundException {
            super(new MedicineImageSourceRepository(), (new Parser<>(MedicineImageDto.class, source.getUrl())).setFilter(entity -> !entity.getImage().isEmpty()));
            mapper = new MedicineMapperImpl();
        }

        @Override
        public MedicineImageSource cast(MedicineImageDto dto) {
            return mapper.toEntity(dto);
        }
    }

    private final static class PartnerMedicineCommand extends AbstractImporter<PartnerMedicineDto, PartnerMedicine> {

        protected PartnerMedicineMapper mapper;

        public PartnerMedicineCommand(Source source) throws FileNotFoundException {
            super(new PartnerMedicineRepository(), new Parser<>(PartnerMedicineDto.class, source.getUrl()));
            mapper = new PartnerMedicineMapperImpl();
        }

        @Override
        public PartnerMedicine cast(PartnerMedicineDto dto) {
            return mapper.toEntity(dto);
        }
    }

    private final static class PartnerPriceCommand extends AbstractImporter<PartnerPriceDto, PartnerPrice> {

        protected PartnerMedicineMapper mapper;

        public PartnerPriceCommand(Source source) throws FileNotFoundException {
            super(new PartnerPriceRepository(), new Parser<>(PartnerPriceDto.class, source.getUrl()));
            mapper = new PartnerMedicineMapperImpl();
        }

        @Override
        public PartnerPrice cast(PartnerPriceDto dto) {
            return mapper.toEntity(dto);
        }
    }

    private final static class PharmacyCommand extends AbstractImporter<PharmacyDto, Pharmacy> {

        protected PharmacyMapper mapper;

        public PharmacyCommand(Source source) throws FileNotFoundException {
            super(new PharmacyRepository(), new Parser<>(PharmacyDto.class, source.getUrl()));
            mapper = new PharmacyMapperImpl();
        }

        @Override
        public Pharmacy cast(PharmacyDto dto) {
            return mapper.toEntity(dto);
        }
    }

}
