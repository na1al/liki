package ua.catalog.liki.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.repository.TagRepository;
import ua.catalog.liki.service.CatalogService;
import ua.catalog.liki.service.MedicineService;
import ua.catalog.liki.service.TagService;
import ua.catalog.liki.views.MedicineView;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CatalogController {

    private static final int CATALOG_PER_PAGE = 20;

    private final CatalogService catalogService;
    private final TagService tagService;

    public CatalogController(CatalogService catalogService, TagService tagService) {
        this.catalogService = catalogService;
        this.tagService = tagService;
    }

    @JsonView({MedicineView.List.class})
    @GetMapping("/catalog")
    public Response<Page<Medicine>> list(@RequestParam Tag[] key,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         Response<Page<Medicine>> model) {
        Pageable pageable = PageRequest.of(page, CATALOG_PER_PAGE);
      //  model.data = catalogService.catalogSearch(pageable);
        return model;
    }

    @JsonView({MedicineView.List.class})
    @GetMapping("/catalog/category/{alias}")
    public Response<Page<Medicine>> list(@PathVariable("alias") String alias,
                                         @RequestParam(required = false) String key,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         Response<Page<Medicine>> model) {
        Pageable pageable = PageRequest.of(page, CATALOG_PER_PAGE);
        Tag tag = tagService.findByAlias(alias).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
       // model.data = catalogService.catalogSearch(tag, pageable);
        return model;
    }

}
