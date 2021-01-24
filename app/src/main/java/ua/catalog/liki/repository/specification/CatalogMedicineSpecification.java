package ua.catalog.liki.repository.specification;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.entity.Media;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.MedicineTag;
import ua.catalog.liki.entity.Tag;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class CatalogMedicineSpecification implements Specification<Medicine> {

    public CatalogSearchFilter filter;

    @Override
    public Predicate toPredicate(Root<Medicine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Class<?> clazz = query.getResultType();
        if (clazz.equals(Medicine.class)) {
            root.fetch("media", JoinType.LEFT);
        }

        Set<Predicate> predicates = new HashSet<>();

        if (filter.getCategory() != null) {
            Join<Medicine, MedicineTag> medicineTag = root.join("medicineTag");
            predicates.add(criteriaBuilder.equal(medicineTag.get("tag"), filter.getCategory().getId()));
        }


        if (filter.getKey() != null) {

            Map<Integer, List<Tag>> tags =
                    filter.getKey().stream().collect(Collectors.groupingBy(Tag::getTagVocabularyId));

            for (Map.Entry<Integer, List<Tag>> entry : tags.entrySet()) {

                if (entry.getValue().size() > 1) {
                    query.distinct(true);
                }

                Integer[] id = entry.getValue().stream().mapToInt(Tag::getId).boxed().toArray(Integer[]::new);
                Join<Medicine, MedicineTag> medicineTag = root.join("medicineTag");
                predicates.add(medicineTag.get("tag").get("id").in(id));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
