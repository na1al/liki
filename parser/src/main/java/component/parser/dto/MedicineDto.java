package component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class MedicineDto {


    @CsvBindByName(column = "id")
    private int id;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "image")
    private String image;

    @CsvBindByName(column = "description")
    private String description;

}
