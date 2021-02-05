package ua.catalog.liki.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.service.CatalogService;
import ua.catalog.liki.service.TagService;
import ua.catalog.liki.util.propertyEditor.TagPropertyEditor;
import ua.catalog.liki.view.MedicineView;
import ua.catalog.liki.view.TagView;

import java.util.*;

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
        model.data.put("counts", catalogService.count(tag, filter));
        model.data.put("keys", filter.getKey());

        if (full)
            model.data.put("items", catalogService.filter(tag, new CatalogSearchFilter()));

        return model;
    }

}
