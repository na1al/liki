package ua.catalog.liki.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.dto.Filter;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.service.CatalogService;
import ua.catalog.liki.service.TagService;
import ua.catalog.liki.service.TagVocabularyService;
import ua.catalog.liki.util.propertyEditor.TagPropertyEditor;
import ua.catalog.liki.view.MedicineView;
import ua.catalog.liki.view.TagView;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class CatalogController {

    public final static int CATEGORY_VOCABULARY_ID = 1;

    private final CatalogService catalogService;
    private final TagService tagService;

    public CatalogController(CatalogService catalogService, TagService tagService) {
        this.catalogService = catalogService;
        this.tagService = tagService;
    }

    @InitBinder("filter")
    public void customizeBinding(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "key", new TagPropertyEditor(tagService));
    }

    @JsonView({MedicineView.List.class})
    @GetMapping("/catalog")
    public Response<Page<Medicine>> list(@ModelAttribute(value = "filter") CatalogSearchFilter filter,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         Response<Page<Medicine>> model) {
        Pageable pageable = PageRequest.of(page, CatalogService.CATALOG_PER_PAGE);
        model.data = catalogService.catalogSearch(filter, pageable);
        return model;
    }

    @JsonView({MedicineView.List.class})
    @GetMapping("/catalog/category/{alias}")
    public Response<Page<Medicine>> list(@PathVariable("alias") String alias,
                                         @ModelAttribute(value = "filter") CatalogSearchFilter filter,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         Response<Page<Medicine>> model) {
        Pageable pageable = PageRequest.of(page, CatalogService.CATALOG_PER_PAGE);
        Tag tag = tagService.findByAlias(alias).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        model.data = catalogService.catalogSearch(tag, filter, pageable);
        return model;
    }

    @JsonView({TagView.Filter.class})
    @GetMapping("/catalog/categories")
    public Response<Set<Tag>> categories(Response<Set<Tag>> model) {
        model.data = tagService.findAllByTagVocabularyId(CATEGORY_VOCABULARY_ID);
        return model;
    }

    @JsonView({TagView.Filter.class})
    @GetMapping("/catalog/filter/{alias}")
    public Response<Map<String, Object>> filter(@PathVariable("alias") String alias,
                                                @ModelAttribute(value = "filter") CatalogSearchFilter filter,
                                                @RequestParam(value = "f", required = false) boolean full,
                                                Response<Map<String, Object>> model) {
        Tag tag = tagService.findByAlias(alias).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        model.data = new HashMap<>();
        model.data.put("category", tag);
//        model.data.put("counts", catalogService.filter(tag, filter));
        model.data.put("keys", filter.getKey());

        model.data.put("counts", this.getCounts(tag, filter));

        if (full)
            model.data.put("items", catalogService.filter(tag, new CatalogSearchFilter()));


        return model;
    }

    @Autowired
    private TagVocabularyService tagVocabularyService;

    @Autowired
    private EntityManager em;


    private List<Filter> getCounts(Tag category, CatalogSearchFilter filter) {

        var vocabularies = tagVocabularyService.findAll();

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

//                Integer[] ids = entry.getValue().stream().mapToInt(Tag::getId).boxed().toArray(Integer[]::new);
////                List<Integer> ids = new ArrayList<>();
////                ids.add(37);

                var in =
                        entry.getValue().stream().map(v -> String.valueOf(v.getId())).collect(Collectors.joining(","));


//
//                String in = Arrays.stream(ids)
//                        .map(String::valueOf)
//                        .collect(Collectors.joining(","));

                join.append(" INNER JOIN medicine_tag sm").append(vocabulary.getId()).append("_ ON m0_.medicine_id = sm").append(vocabulary.getId()).append("_.medicine_id ");
                where.append(" AND sm").append(vocabulary.getId()).append("_.tag_id in (").append(in).append(") ");

            }

            if (query.length() > 0) {
                query.append(" UNION ");
            }

            query
                    .append("SELECT t0_.id, t0_.name, t0_.alias, t0_.tag_vocabulary_id, count(m0_.medicine_id) as count ")
                    .append(" FROM tag t0_ ")
                    .append(join)
                    .append(where)
                    .append(" GROUP BY t0_.id ");

        }

        System.out.println("-----------------------------------");
        System.out.println(query.toString());
        System.out.println("-----------------------------------");

//        List<List<Integer>> params1 = new ArrayList<>();

//        List<Integer> ids1 = new ArrayList<>();
//        ids1.add(37);

//        params1.add(ids1);
//        params1.add(ids1);

        Query q = em.createNativeQuery(query.toString(), "FilterValueMapping");
//        q.setParameter(1, ids1);
//        q.setParameter(2, ids1);

//        AtomicInteger index = new AtomicInteger();
//        params.forEach(v -> {
//
//            System.out.println(v);
//
//            var i = index.incrementAndGet();
//
//            q.setParameter(i, v);
//        });

        return q.getResultList();
    }

}
