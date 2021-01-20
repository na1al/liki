package ua.catalog.loader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.catalog.loader.component.Transliterator;
import ua.catalog.loader.component.parser.dto.MedicineTagDto;
import ua.catalog.loader.component.parser.dto.TagDto;
import ua.catalog.loader.entity.*;

import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "alias", expression = "java(toAlias(dto))")
    @Mapping(target = "tagVocabularyId", expression = "java(getVocabularyId(dto,index))")
    Tag toEntity(TagDto dto, Map<TagVocabulary.Type, Integer> index);

    default String toAlias(TagDto dto) {
        if(!dto.getType().equals(TagVocabulary.Type.CATEGORY.toString())){
            return null;
        }
        return Transliterator.urlNormalize(dto.getName());
    }

    default int getVocabularyId(TagDto dto, Map<TagVocabulary.Type, Integer> index){
        return index.get(TagVocabulary.Type.valueOf(dto.getType()));
    }

    @Mapping(target = "tagId", expression = "java(getTagId(dto,index))")
    MedicineTag toEntity(MedicineTagDto dto, Map<TagVocabulary.Type, Map<Integer, Integer>> index);

    default Integer getTagId(MedicineTagDto dto, Map<TagVocabulary.Type, Map<Integer, Integer>> index){
        Map<Integer, Integer> map = index.get(TagVocabulary.Type.valueOf(dto.getType()));

        if (map == null) {
            return null;
        }

        return map.get(dto.getExternalTagId());
    }

}
