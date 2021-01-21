package ua.catalog.liki.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.repository.MedicineRepository;
import ua.catalog.liki.repository.MedicineViewsRepository;
import ua.catalog.liki.repository.specification.CatalogSearchSpecification;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {

    private static final int CATALOG_PER_PAGE = 20;

    private MedicineRepository medicineRepository;
    private MedicineViewsRepository medicineViewsRepository;

    public CatalogService(MedicineRepository medicineRepository, MedicineViewsRepository medicineViewsRepository) {
        this.medicineRepository = medicineRepository;
        this.medicineViewsRepository = medicineViewsRepository;
    }

    public List<Medicine> topMedicines() {
        return medicineRepository.findTop10ByOrderByPriorityDescMediaDesc();
    }

    public Page<Medicine> catalogSearch(CatalogSearchFilter filter, Pageable pageable) {
        return medicineRepository.findAll(new CatalogSearchSpecification(), pageable);
    }

    public Optional<Medicine> viewByAlias(String alias) {
        Optional<Medicine> optional = medicineRepository.findOneByAlias(alias);
        optional.ifPresent(medicine -> medicineViewsRepository.updateCounter(medicine.getId()));
        return optional;
    }

    public Optional<Medicine> viewById(int id) {
        Optional<Medicine> optional = medicineRepository.findOneById(id);
        optional.ifPresent(medicine -> medicineViewsRepository.updateCounter(medicine.getId()));
        return optional;
    }
}
