package entity;

import lombok.Data;

@Data
public class Pharmacy {

    private int partnerPharmacyId;

    private int integrationId;

    private int cityId;

    private String name;

    private String address;

}
