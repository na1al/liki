package command;

import lombok.SneakyThrows;
import service.MedicineService;

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
