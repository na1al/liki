package component.parser.dto;

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

    @CsvBindByName(column = "address")
    private String address;

}
