package command;

import classes.AbstractImporter;
import component.parser.Parser;
import component.parser.dto.*;
import entity.*;
import lombok.SneakyThrows;
import mapper.*;
import repository.*;

import java.io.FileNotFoundException;
import java.util.List;

public class ImportCommand implements Runnable {

    SourceRepository sourceRepository;

    public ImportCommand() {
        sourceRepository = new SourceRepository();
    }

    @SneakyThrows
    public void run() {
        List<Source> sources = sourceRepository.findActive();

        for (Source source : sources) {
            switch (source.getType()) {
                case MEDICINE -> {
                    (new MedicineCommand(source.getUrl())).run();
                    (new MedicineImageCommand(source.getUrl())).run();
                }
                case PARTNER_MEDICINE -> (new PartnerMedicineCommand(source.getUrl())).run();
                case PARTNER_PRICE -> (new PartnerPriceCommand(source.getUrl())).run();
                case PHARMACY -> (new PharmacyCommand(source.getUrl())).run();
            }
        }
    }

    private final static class MedicineCommand extends AbstractImporter<MedicineDto, Medicine> {

        protected MedicineMapper mapper;

        public MedicineCommand(String url) throws FileNotFoundException {
            super(new MedicineRepository(), new Parser<>(MedicineDto.class, url));
            mapper = new MedicineMapperImpl();
        }

        @Override
        public Medicine cast(MedicineDto dto) {
            return mapper.toEntity(dto);
        }
    }

    private final static class MedicineImageCommand extends AbstractImporter<MedicineImageDto, MedicineImageSource> {

        protected MedicineMapper mapper;

        public MedicineImageCommand(String url) throws FileNotFoundException {
            super(new MedicineImageSourceRepository(), (new Parser<>(MedicineImageDto.class, url)).setFilter(entity -> !entity.getImage().isEmpty()));
            mapper = new MedicineMapperImpl();
        }

        @Override
        public MedicineImageSource cast(MedicineImageDto dto) {
            return mapper.toEntity(dto);
        }
    }

    private final static class PartnerMedicineCommand extends AbstractImporter<PartnerMedicineDto, PartnerMedicine> {

        protected PartnerMedicineMapper mapper;

        public PartnerMedicineCommand(String url) throws FileNotFoundException {
            super(new PartnerMedicineRepository(), new Parser<>(PartnerMedicineDto.class, url));
            mapper = new PartnerMedicineMapperImpl();
        }

        @Override
        public PartnerMedicine cast(PartnerMedicineDto dto)  {
            return mapper.toEntity(dto);
        }
    }

    private final static class PartnerPriceCommand extends AbstractImporter<PartnerPriceDto, PartnerPrice> {

        protected PartnerMedicineMapper mapper;

        public PartnerPriceCommand(String url) throws FileNotFoundException {
            super(new PartnerPriceRepository(), new Parser<>(PartnerPriceDto.class, url));
            mapper = new PartnerMedicineMapperImpl();
        }

        @Override
        public PartnerPrice cast(PartnerPriceDto dto)  {
            return mapper.toEntity(dto);
        }
    }

    private final static class PharmacyCommand extends AbstractImporter<PharmacyDto, Pharmacy> {

        protected PharmacyMapper mapper;

        public PharmacyCommand(String url) throws FileNotFoundException {
            super(new PharmacyRepository(), new Parser<>(PharmacyDto.class, url));
            mapper = new PharmacyMapperImpl();
        }

        @Override
        public Pharmacy cast(PharmacyDto dto) {
            return mapper.toEntity(dto);
        }
    }

}
