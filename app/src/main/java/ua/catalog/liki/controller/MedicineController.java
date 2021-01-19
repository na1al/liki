package ua.catalog.liki.controller;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.service.CatalogService;

@RestController
@RequestMapping("/v1")
public class MedicineController {

    private final CatalogService catalogService;

    public MedicineController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/medicine/alias/{alias}")
    public Response<Medicine> view(@PathVariable("alias") String alias, Response<Medicine> model) {
        model.data = catalogService.viewByAlias(alias).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        return model;
    }

    @GetMapping("/medicine/id/{id}")
    public Response<Medicine> view(@PathVariable("id") int id, Response<Medicine> model) {
        model.data = catalogService.viewById(id).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        return model;
    }

}
