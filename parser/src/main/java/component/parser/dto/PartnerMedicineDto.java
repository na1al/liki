package component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PartnerMedicineDto {

    @CsvBindByName(column = "external_medicine_id")
    private int externalMedicineId;

    @CsvBindByName(column = "integration_id")
    private int integrationId;

    @CsvBindByName(column = "medicine_id")
    private int medicineId;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "description")
    private String description;

}
