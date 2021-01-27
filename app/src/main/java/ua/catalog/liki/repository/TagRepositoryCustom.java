package ua.catalog.liki.repository;

import org.springframework.stereotype.Repository;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.dto.Filter;
import ua.catalog.liki.entity.Tag;

import java.util.List;

@Repository
public interface TagRepositoryCustom {

    public List<Filter> filter(Tag category, CatalogSearchFilter filter);

}
