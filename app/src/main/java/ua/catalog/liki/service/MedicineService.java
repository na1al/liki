package ua.catalog.liki.service;

import org.springframework.stereotype.Service;
import ua.catalog.liki.entity.Medicine;
import ua.catalog.liki.repository.MedicineRepository;
import java.util.Optional;

@Service
public class MedicineService {

    private MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Optional<Medicine> findById(int id) {
        return medicineRepository.findOneById(id);
    }

    public Optional<Medicine> findByAlias(String alias) {
        return medicineRepository.findOneByAlias(alias);
    }
}
