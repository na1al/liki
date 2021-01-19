package ua.catalog.liki.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.service.CatalogService;
import ua.catalog.liki.views.MedicineView;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class WidgetController {

    private final CatalogService catalogService;

    public WidgetController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @JsonView({MedicineView.List.class})
    @GetMapping("/widget/top")
    public Response<List<Medicine>> list(Response<List<Medicine>> model) {
        model.data = catalogService.topMedicines();
        return model;
    }

}
