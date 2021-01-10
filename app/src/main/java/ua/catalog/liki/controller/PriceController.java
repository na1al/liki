package ua.catalog.liki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.catalog.liki.dto.Response;
import ua.catalog.liki.entity.City;
import ua.catalog.liki.entity.PartnerPrice;
import ua.catalog.liki.repository.PartnerPriceRepository;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class PriceController {

    private final PartnerPriceRepository partnerPriceRepository;

    PriceController(PartnerPriceRepository partnerPriceRepository) {
        this.partnerPriceRepository = partnerPriceRepository;
    }

    @GetMapping("price/medicine/{id}")
    public Response<List<PartnerPrice>> medicinePharmacies(@PathVariable int id, Response<List<PartnerPrice>> model) {
        model.data = partnerPriceRepository.findAllMedicinePrices(id, City.DEFAULT_CITY_ID);
        return model;
    }

}
