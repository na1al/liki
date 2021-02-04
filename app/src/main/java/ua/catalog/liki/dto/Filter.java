package ua.catalog.liki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

@MappedSuperclass
@SqlResultSetMapping(
        name = "FilterValueMapping",
        classes = @ConstructorResult(
                targetClass = Filter.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "alias"),
                        @ColumnResult(name = "tag_vocabulary_id"),
                        @ColumnResult(name = "count", type = Long.class)}))
@AllArgsConstructor
@Data
public class Filter {

    private int id;
    private String name;
    private String alias;
    private int tagVocabularyId;
    private Long count;

}
