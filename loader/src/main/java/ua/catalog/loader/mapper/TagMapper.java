package ua.catalog.loader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.catalog.loader.component.Transliterator;
import ua.catalog.loader.component.parser.dto.MedicineTagDto;
import ua.catalog.loader.component.parser.dto.TagDto;
import ua.catalog.loader.entity.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "alias", expression = "java(toAlias(dto))")
    Tag toEntity(TagDto dto);

    default String toAlias(TagDto dto) {
        return Transliterator.urlNormalize(dto.getName());
    }

}
