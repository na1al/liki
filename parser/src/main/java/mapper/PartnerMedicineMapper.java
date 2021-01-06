package mapper;

import component.parser.dto.PartnerMedicineDto;
import component.parser.dto.PartnerPriceDto;
import entity.PartnerMedicine;
import entity.PartnerPrice;
import org.mapstruct.Mapper;

@Mapper
public interface PartnerMedicineMapper {

    PartnerMedicine toEntity(PartnerMedicineDto dto);

    PartnerPrice toEntity(PartnerPriceDto dto);

}
