package ua.catalog.liki.repository.impl;

import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.dto.Filter;
import ua.catalog.liki.entity.MedicineTag;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.entity.TagVocabulary;
import ua.catalog.liki.repository.TagRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

public class TagRepositoryImpl implements TagRepositoryCustom {

    private EntityManager em;

    public TagRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Filter> count(Tag category, CatalogSearchFilter filter, List<TagVocabulary> vocabularies) {

        Map<Integer, List<Tag>> tags =
                filter.getKey() == null ? new HashMap<>() : filter.getKey().stream().collect(Collectors.groupingBy(Tag::getTagVocabularyId));

        StringBuilder query = new StringBuilder();
        List<List<Integer>> params = new ArrayList<>();
        for (var vocabulary : vocabularies) {

            StringBuilder join = new StringBuilder();
            StringBuilder where = new StringBuilder();

            join.append(" INNER JOIN medicine_tag m0_ ON m0_.tag_id = t0_.id ")
                    .append(" INNER JOIN medicine_tag sm0_ ON m0_.medicine_id = sm0_.medicine_id ");

            where.append(" WHERE sm0_.tag_id= ").append(category.getId()).append("  and t0_.tag_vocabulary_id = ").append(vocabulary.getId());

            for (Map.Entry<Integer, List<Tag>> entry : tags.entrySet()) {

                if (entry.getKey() == vocabulary.getId()) {
                    continue;
                }

                join.append(" INNER JOIN medicine_tag sm").append(entry.getKey()).append("_ ON m0_.medicine_id = sm").append(entry.getKey()).append("_.medicine_id ");
                params.add(entry.getValue().stream().map(Tag::getId).collect(Collectors.toList()));
                where.append(" AND sm").append(entry.getKey()).append("_.tag_id in (?").append(params.size()).append(") ");
            }

            if (query.length() > 0) {
                query.append(" UNION ");
            }

            query
                    .append("SELECT t0_.id, t0_.name, t0_.alias, t0_.tag_vocabulary_id, count(DISTINCT m0_.medicine_id) as count ")
                    .append(" FROM tag t0_ ")
                    .append(join)
                    .append(where)
                    .append(" GROUP BY t0_.id ");

        }

        Query q = em.createNativeQuery(query.toString(), "FilterValueMapping");

        for (int i = 0; i < params.size(); i++) {
            q.setParameter(i + 1, params.get(i));
        }

        return q.getResultList();
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
