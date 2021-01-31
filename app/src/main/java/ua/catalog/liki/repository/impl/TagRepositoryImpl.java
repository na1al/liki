package ua.catalog.liki.repository.impl;

import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.dto.Filter;
import ua.catalog.liki.entity.MedicineTag;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.repository.TagRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TagRepositoryImpl implements TagRepositoryCustom {

    private EntityManager em;

    public TagRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<Filter> filter(Tag category, CatalogSearchFilter filter) {

        var cb = em.getCriteriaBuilder();
        var cq = em.getCriteriaBuilder().createQuery(Filter.class);


        Root<Tag> tag = cq.from(Tag.class);
        Root<MedicineTag> medicineTag = cq.from(MedicineTag.class);
        Join<MedicineTag, MedicineTag> selfMedicineTag = medicineTag.join("selfMedicineTag");


        Set<Predicate> predicates = new HashSet<>();
        predicates.add(cb.equal(tag.get("id"), medicineTag.get("tag")));
        predicates.add(cb.equal(selfMedicineTag.get("tag"), category));

        if (filter.getKey() != null) {

            Map<Integer, List<Tag>> tags =
                    filter.getKey().stream().collect(Collectors.groupingBy(Tag::getTagVocabularyId));

            for (Map.Entry<Integer, List<Tag>> entry : tags.entrySet()) {
                Integer[] id = entry.getValue().stream().mapToInt(Tag::getId).boxed().toArray(Integer[]::new);
                Join<MedicineTag, MedicineTag> selfMedicineTag2 = medicineTag.join("selfMedicineTag");
                predicates.add(selfMedicineTag2.get("tag").in(id));
            }
        }

        cq.multiselect(
                tag.get("id"),
                tag.get("name"),
                tag.get("alias"),
                tag.get("tagVocabularyId"),
                cb.countDistinct(medicineTag.get("medicineId"))
        )
                .where(predicates.toArray(new Predicate[0]))
                .groupBy(tag.get("id"))
                .orderBy(cb.asc(tag.get("name")));

        return em.createQuery(cq).getResultList();
    }
}
