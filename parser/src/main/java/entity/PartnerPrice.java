package entity;

import lombok.Data;

@Data
public class PartnerPrice {

    private int externalMedicineId;

    private int integrationId;

    private int externalPharmacyId;

    private int price;

    private float quantity;

}
