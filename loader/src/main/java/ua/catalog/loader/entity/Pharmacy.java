package ua.catalog.loader.entity;

import lombok.Data;

@Data
public class Pharmacy {

    private int partnerPharmacyId;

    private int integrationId;

    private int cityId;

    private String name;

    private String phone;

    private String schedule;

    private String address;

    private Double lat;

    private Double lng;

}
