package ua.catalog.loader.component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PartnerPriceDto {

    @CsvBindByName(column = "external_medicine_id")
    private int externalMedicineId;

    @CsvBindByName(column = "integration_id")
    private int integrationId;

    @CsvBindByName(column = "external_pharmacy_id")
    private int externalPharmacyId;

    @CsvBindByName(column = "price")
    private int price;

    @CsvBindByName(column = "quantity")
    private float quantity;

}
