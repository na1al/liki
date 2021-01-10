package ua.catalog.loader.entity;

import lombok.Data;

@Data
public class MedicineRegistration {

    private int medicineId;

    private String code;

    private Type type;

    public enum Type {
        UA, MORION, OTIMA, BADM
    }

}
