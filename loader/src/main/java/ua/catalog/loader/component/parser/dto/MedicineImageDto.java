package ua.catalog.loader.component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class MedicineImageDto {

    @CsvBindByName(column = "id")
    private int id;

    @CsvBindByName(column = "image")
    private String image;

}
