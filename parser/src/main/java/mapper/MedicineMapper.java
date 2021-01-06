package mapper;

import component.parser.dto.MedicineDto;
import component.parser.dto.MedicineImageDto;
import entity.Medicine;
import entity.MedicineImageSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicineMapper {

    Medicine toEntity(MedicineDto dto);

    @Mapping(target = "medicineId", source = "id")
    @Mapping(target = "url", source = "image")
    MedicineImageSource toEntity(MedicineImageDto dto);

}
