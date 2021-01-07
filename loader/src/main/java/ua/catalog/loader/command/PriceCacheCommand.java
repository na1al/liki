package ua.catalog.loader.command;

import lombok.SneakyThrows;
import ua.catalog.loader.service.MedicineService;

public class PriceCacheCommand implements Runnable {

    MedicineService medicineService;

    public PriceCacheCommand(){
        medicineService = new MedicineService();
    }

    @SneakyThrows
    public void run() {
        medicineService.priceCacheUpdate();
    }

}
