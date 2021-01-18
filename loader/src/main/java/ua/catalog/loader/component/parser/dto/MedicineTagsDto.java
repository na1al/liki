package ua.catalog.loader.component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class MedicineTagsDto {

    @CsvBindByName(column = "medicine_id")
    private int medicineId;

    @CsvBindByName(column = "tag_vocabulary_id")
    private Integer tagVocabularyId;

    @CsvBindByName(column = "external_id")
    private Integer externalId;

}
