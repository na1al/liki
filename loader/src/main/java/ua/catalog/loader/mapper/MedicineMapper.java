package ua.catalog.loader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.catalog.loader.component.parser.dto.MedicineDto;
import ua.catalog.loader.component.parser.dto.MedicineImageDto;
import ua.catalog.loader.entity.Medicine;
import ua.catalog.loader.entity.MedicineImageSource;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicineMapper {

    Medicine toEntity(MedicineDto dto);

    @Mapping(target = "medicineId", source = "id")
    @Mapping(target = "url", source = "image")
    MedicineImageSource toEntity(MedicineImageDto dto);

}
