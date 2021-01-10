package ua.catalog.loader.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Medicine {

    private int id;

    private String name;

    private String alias;

    private Integer mediaId;

    private String description;

    private MedicineImageSource imageSource;

    private List<MedicineRegistration> medicineRegistrations = new ArrayList<>();

}
