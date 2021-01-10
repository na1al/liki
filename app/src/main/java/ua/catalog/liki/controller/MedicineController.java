package ua.catalog.liki.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.service.MedicineService;
import ua.catalog.liki.views.MedicineView;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class MedicineController {

    private static final int CATALOG_PER_PAGE = 20;

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @JsonView({MedicineView.List.class})
    @GetMapping("/medicine")
    public Response<Page<Medicine>> list(@RequestParam(value = "page", defaultValue = "0") Integer page, Response<Page<Medicine>> model) {
        Pageable pageable = PageRequest.of(page, CATALOG_PER_PAGE);
        model.data = medicineService.catalogSearch(pageable);
        return model;
    }

    @JsonView({MedicineView.List.class})
    @GetMapping("/medicine/top")
    public Response<List<Medicine>> list(Response<List<Medicine>> model) {
        model.data = medicineService.topMedicines();
        return model;
    }

    @GetMapping("/medicine/alias/{alias}")
    public Response<Medicine> view(@PathVariable("alias") String alias, Response<Medicine> model) {
        model.data = medicineService.findByAlias(alias).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        return model;
    }

    @GetMapping("/medicine/{id}")
    public Response<Medicine> view(@PathVariable("id") int id, Response<Medicine> model) {
        model.data = medicineService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        return model;
    }

}
