package ua.catalog.loader.mapper;

import org.mapstruct.Mapper;
import ua.catalog.loader.component.parser.dto.PartnerMedicineDto;
import ua.catalog.loader.component.parser.dto.PartnerPriceDto;
import ua.catalog.loader.entity.PartnerMedicine;
import ua.catalog.loader.entity.PartnerPrice;

@Mapper
public interface PartnerMedicineMapper {

    PartnerMedicine toEntity(PartnerMedicineDto dto);

    PartnerPrice toEntity(PartnerPriceDto dto);

}
