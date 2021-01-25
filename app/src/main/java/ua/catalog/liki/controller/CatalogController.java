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
import ua.catalog.liki.entity.TagVocabulary;
import ua.catalog.liki.service.CatalogService;
import ua.catalog.liki.service.TagService;
import ua.catalog.liki.service.TagVocabularyService;
import ua.catalog.liki.util.propertyEditor.TagPropertyEditor;
import ua.catalog.liki.view.MedicineView;
import ua.catalog.liki.view.TagView;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class CatalogController {

    public final static int CATEGORY_VOCABULARY_ID = 1;

    private final CatalogService catalogService;
    private final TagService tagService;
    private final TagVocabularyService tagVocabularyService;

    public CatalogController(CatalogService catalogService, TagService tagService,TagVocabularyService tagVocabularyService) {
        this.catalogService = catalogService;
        this.tagService = tagService;
        this.tagVocabularyService = tagVocabularyService;
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
    public Response<Set<Tag>> categories(Response<Set<Tag>> model){
        model.data = tagService.findAllByTagVocabularyId(CATEGORY_VOCABULARY_ID);
        return model;
    }

    @JsonView({TagView.Filter.class})
    @GetMapping("/catalog/vocabularies")
    public Response<List<TagVocabulary>> vocabularies(Response<List<TagVocabulary>> model){
        model.data = tagVocabularyService.findAll();
        return model;
    }

    @JsonView({TagView.Filter.class})
    @GetMapping("/catalog/filter/{alias}")
    public Response<List<Tag>> filter(@PathVariable("alias") String alias,
                                      @ModelAttribute(value = "filter") CatalogSearchFilter filter,
                                      Response<List<Tag>> model){
        Tag tag = tagService.findByAlias(alias).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        model.data = catalogService.filter(tag, filter);
        return model;
    }

}
