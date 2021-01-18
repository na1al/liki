package ua.catalog.loader.entity;

import lombok.Data;

@Data
public class Tag {

    private int id;

    private String name;

    private String alias;

    private Integer tagVocabularyId;

    private Integer externalId;

}
