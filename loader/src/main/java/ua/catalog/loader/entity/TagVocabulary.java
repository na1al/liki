package ua.catalog.loader.entity;

import lombok.Data;

@Data
public class TagVocabulary {

    private int id;

    private String name;

    private String type;

    public enum Type {
        CATEGORY
    }

}
