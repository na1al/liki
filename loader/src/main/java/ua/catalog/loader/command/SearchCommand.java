package ua.catalog.loader.command;

import lombok.SneakyThrows;
import ua.catalog.loader.service.MedicineService;

public class SearchCommand implements Runnable {

    MedicineService medicineService;

    public SearchCommand(){
        medicineService = new MedicineService();
    }

    @SneakyThrows
    public void run() {
        medicineService.searchDataUpdate();
    }

}
