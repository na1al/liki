package ua.catalog.liki.repository.specification;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.entity.Medicine;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@NoArgsConstructor
@AllArgsConstructor
public class CatalogSearchSpecification implements Specification<Medicine> {

    public CatalogSearchFilter filter;

    @Override
    public Predicate toPredicate(Root<Medicine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
       return criteriaBuilder.and(
                criteriaBuilder.equal(
                        root.get("alias"), "urinakejr-nutranekst-tabletki-no30-16169"),
                criteriaBuilder.equal(
                        root.get("alias"), "urinakejr-nutranekst-tabletki-no30-16169"));
    }
}
