package ua.catalog.loader.entity;

import lombok.Data;

@Data
public class Source {

    private int id;

    private String url;

    private Boolean active;

    private Long lastModified;

    private Type type;

    public enum Type {
        MEDICINE, PARTNER_MEDICINE, PARTNER_PRICE, PHARMACY
    }
}
