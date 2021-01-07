package ua.catalog.loader.mapper;

import org.mapstruct.Mapper;
import ua.catalog.loader.component.parser.dto.PharmacyDto;
import ua.catalog.loader.entity.Pharmacy;

@Mapper
public interface PharmacyMapper {

    Pharmacy toEntity(PharmacyDto dto);

}
