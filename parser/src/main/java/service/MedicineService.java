package service;

import repository.MedicinePriceRepository;
import repository.MedicineRepository;
import repository.SearchDataRepository;

import java.sql.SQLException;

public class MedicineService {

    MedicineRepository medicineRepository;
    MedicinePriceRepository medicinePriceRepository;
    SearchDataRepository searchDataRepository;

    public MedicineService() {
        medicineRepository = new MedicineRepository();
        medicinePriceRepository = new MedicinePriceRepository();
        searchDataRepository = new SearchDataRepository();
    }

    public void priceCacheUpdate() throws SQLException {
        medicinePriceRepository.cache();
    }

    public void searchDataUpdate() throws SQLException {
        searchDataRepository.indexSearchData();
    }

}
