package ua.catalog.liki.repository.specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.MedicineTag;
import ua.catalog.liki.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CatalogFilterSpecification implements Specification<Tag> {

    public CatalogSearchFilter filter;

    @Override
    public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Set<Predicate> predicates = new HashSet<>();

        Subquery<Tag> sq = query.subquery(Tag.class);
        sq.distinct(true);
        Root<MedicineTag> medicineTag = sq.from(MedicineTag.class);
        sq.select(medicineTag.get("tag").get("id"));

        if (filter.getCategory() != null) {
            Join<MedicineTag, MedicineTag> selfMedicineTag = medicineTag.join("selfMedicineTag");
            predicates.add(cb.equal(selfMedicineTag.get("tag"), filter.getCategory().getId()));
        }

        if (filter.getKey() != null) {

            Map<Integer, List<Tag>> tags =
                    filter.getKey().stream().collect(Collectors.groupingBy(Tag::getTagVocabularyId));

            for (Map.Entry<Integer, List<Tag>> entry : tags.entrySet()) {
                Integer[] id = entry.getValue().stream().mapToInt(Tag::getId).boxed().toArray(Integer[]::new);
                Join<MedicineTag, MedicineTag> selfMedicineTag = medicineTag.join("selfMedicineTag");
                predicates.add(selfMedicineTag.get("tag").in(id));
            }
        }

        sq.select(medicineTag.get("tag").get("id")).where(predicates.toArray(new Predicate[0]));
        return cb.in(root).value(sq);
    }
}
