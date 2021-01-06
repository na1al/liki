package command;

import lombok.SneakyThrows;
import service.MedicineService;

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
