package ua.catalog.liki.dto;

import lombok.Data;
import ua.catalog.liki.entity.Tag;

import java.util.Set;

@Data
public class CatalogSearchFilter {

    private Tag category;

    private Set<Tag> key;

}
