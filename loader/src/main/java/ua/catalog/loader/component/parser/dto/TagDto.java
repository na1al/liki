package ua.catalog.loader.component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class TagDto {

    @CsvBindByName(column = "id")
    private int id;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "alias")
    private String alias;

    @CsvBindByName(column = "tag_vocabulary_id")
    private Integer tagVocabularyId;

    @CsvBindByName(column = "external_id")
    private Integer externalId;

    public void setName(String name) {
        this.name = name.trim();
    }
    public void setAlias(String alias) {
        this.alias = alias.trim();
    }

}
