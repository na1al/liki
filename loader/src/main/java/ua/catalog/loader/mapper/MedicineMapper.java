package ua.catalog.loader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.catalog.loader.component.Transliterator;
import ua.catalog.loader.component.parser.dto.MedicineDto;
import ua.catalog.loader.entity.Medicine;
import ua.catalog.loader.entity.MedicineImageSource;
import ua.catalog.loader.entity.MedicineRegistration;


import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicineMapper {

    @Mapping(target = "imageSource", expression = "java(!dto.getImage().isEmpty() ? toImageSource(dto) : null)")
    @Mapping(target = "medicineRegistrations", expression = "java(toRegistrations(dto))")
    @Mapping(target = "alias", expression = "java(toAlias(dto))")
    Medicine toEntity(MedicineDto dto);

    @Mapping(target = "medicineId", source = "id")
    @Mapping(target = "url", source = "image")
    MedicineImageSource toImageSource(MedicineDto dto);


    default List<MedicineRegistration> toRegistrations(MedicineDto dto) {
        List<MedicineRegistration> list = new ArrayList<>();

        if (dto.getCodeUa() != null) {
            MedicineRegistration registration = new MedicineRegistration();
            registration.setMedicineId(dto.getId());
            registration.setType(MedicineRegistration.Type.UA);
            registration.setCode(dto.getCodeUa());
            list.add(registration);
        }

        return list;
    }

    default String toAlias(MedicineDto dto){
        return Transliterator.urlNormalize(dto.getName()) +"-"+ dto.getId();
    }

}
