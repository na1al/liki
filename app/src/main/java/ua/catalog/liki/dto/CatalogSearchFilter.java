package ua.catalog.liki.dto;

import lombok.Data;
import ua.catalog.liki.entity.Tag;

@Data
public class CatalogSearchFilter {
    private Tag category;

}
