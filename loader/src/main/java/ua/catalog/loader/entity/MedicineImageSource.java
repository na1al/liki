package ua.catalog.loader.entity;

import lombok.Data;

@Data
public class MedicineImageSource {

    private int medicineId;

    private String url;

    private Boolean needUpdate;

    public int getId() {
        return medicineId;
    }

}
