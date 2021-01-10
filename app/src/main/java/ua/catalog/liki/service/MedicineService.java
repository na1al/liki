package ua.catalog.liki.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.catalog.liki.entity.City;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.repository.MedicineRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    private static final int CATALOG_PER_PAGE = 20;

    private MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> topMedicines(){
        return medicineRepository.findTop10ByOrderByPriorityDescMediaDesc();
    }

    public Page<Medicine> catalogSearch(Pageable pageable){
        return medicineRepository.findAllCatalog(pageable);
    }

    public Optional<Medicine> findById(int id){
        return medicineRepository.findOneById(id);
    }

    public Optional<Medicine> findByAlias(String alias){
        return medicineRepository.findOneByAlias(alias);
    }
}
