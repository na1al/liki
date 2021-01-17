package ua.catalog.loader.command;

import lombok.SneakyThrows;
import ua.catalog.loader.service.MedicineService;

public class PriorityCommand implements Runnable {

    MedicineService medicineService;

    public PriorityCommand(){
        medicineService = new MedicineService();
    }

    @SneakyThrows
    public void run() {
        medicineService.priorityUpdate();
    }

}
