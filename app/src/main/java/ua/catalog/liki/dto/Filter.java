package ua.catalog.liki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Filter {

    private int id;
    private String  name;
    private String alias;
    private int tagVocabularyId;
    private Long count;

}
