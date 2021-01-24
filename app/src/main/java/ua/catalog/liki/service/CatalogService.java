package ua.catalog.liki.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.catalog.liki.dto.CatalogSearchFilter;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.entity.Tag;
import ua.catalog.liki.repository.MedicineRepository;
import ua.catalog.liki.repository.MedicineViewsRepository;
import ua.catalog.liki.repository.TagRepository;
import ua.catalog.liki.repository.specification.CatalogMedicineSpecification;
import ua.catalog.liki.repository.specification.CatalogFilterSpecification;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {

    public static final int CATALOG_PER_PAGE = 20;

    private MedicineRepository medicineRepository;
    private MedicineViewsRepository medicineViewsRepository;
    private final TagRepository tagRepository;

    public CatalogService(MedicineRepository medicineRepository, MedicineViewsRepository medicineViewsRepository, TagRepository tagRepository) {
        this.medicineRepository = medicineRepository;
        this.medicineViewsRepository = medicineViewsRepository;
        this.tagRepository = tagRepository;
    }

    public List<Medicine> topMedicines() {
        return medicineRepository.findTop10ByOrderByPriorityDesc();
    }

    public Page<Medicine> catalogSearch(CatalogSearchFilter filter, Pageable pageable) {
        return medicineRepository.findAll(new CatalogMedicineSpecification(filter), pageable);
    }

    public Page<Medicine> catalogSearch(Tag category, CatalogSearchFilter filter, Pageable pageable) {
        filter.setCategory(category);
        return medicineRepository.findAll(new CatalogMedicineSpecification(filter), pageable);
    }

    public List<Tag> filter(Tag category, CatalogSearchFilter filter) {
        filter.setCategory(category);
        return tagRepository.findAll(new CatalogFilterSpecification(filter));
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
