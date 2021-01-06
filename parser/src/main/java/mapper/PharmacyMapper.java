package mapper;

import component.parser.dto.PharmacyDto;
import entity.Pharmacy;
import org.mapstruct.Mapper;

@Mapper
public interface PharmacyMapper {

    Pharmacy toEntity(PharmacyDto dto);

}
