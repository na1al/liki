package entity;

import lombok.Data;

@Data
public class MedicinePrice {

    private int externalMedicineId;

    private int integrationId;

    private int externalPharmacyId;

    private int price;

    private float quantity;

}
