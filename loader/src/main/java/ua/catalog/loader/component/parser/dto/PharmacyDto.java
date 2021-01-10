package ua.catalog.loader.component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PharmacyDto {

    @CsvBindByName(column = "external_pharmacy_id")
    private int partnerPharmacyId;

    @CsvBindByName(column = "integration_id")
    private int integrationId;

    @CsvBindByName(column = "city_id")
    private int cityId;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "phone")
    private String phone;

    @CsvBindByName(column = "schedule")
    private String schedule;

    @CsvBindByName(column = "address")
    private String address;

    @CsvBindByName(column = "lat")
    private Double lat;

    @CsvBindByName(column = "lng")
    private Double lng;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

}
