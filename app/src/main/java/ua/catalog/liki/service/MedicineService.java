package ua.catalog.liki.service;

import org.springframework.stereotype.Service;
import ua.catalog.liki.repository.MedicineRepository;

@Service
public class MedicineService {

    private MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }
}
