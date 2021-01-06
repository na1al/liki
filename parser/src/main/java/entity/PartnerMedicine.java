package entity;

import lombok.Data;

@Data
public class PartnerMedicine {

    private int externalMedicineId;

    private int integrationId;

    private int medicineId;

    private String name;

    private String description;

}
