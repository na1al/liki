package command;

import entity.Media;
import entity.Medicine;
import entity.MedicineImageSource;
import helper.FileHelper;
import helper.MediaHelper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import repository.MediaRepository;
import repository.MedicineRepository;
import service.MedicineImageSourceService;

@Log4j2
public class ImageCommand implements Runnable {


    private MedicineImageSourceService imageSourceService;
    private MediaRepository mediaRepository;
    private MedicineRepository medicineRepository;

    public ImageCommand() {
        imageSourceService = new MedicineImageSourceService();
        mediaRepository = new MediaRepository();
        medicineRepository = new MedicineRepository();
    }

    @SneakyThrows
    public void run() {

        if (!FileHelper.canWrite(MedicineImageSourceService.IMAGE_PATH)) {
            log.error("command.ImageCommand directory not configured");
            return;
        }

        MedicineImageSource source = null;
        while ((source = imageSourceService.getNext()) != null) {

            try {
                imageSourceService.importSource(source);

                Media media = new Media();
                media.setName(MediaHelper.getNewName(source));
                mediaRepository.save(media);

                Medicine medicine = medicineRepository.findById(source.getId());

                if(medicine != null){
                    medicine.setMediaId(media.getId());
                    medicineRepository.save(medicine);
                }
            } catch (Exception e) {
                log.error("File(" + source.getId() + "): Fail to download. " + e.getMessage());
            }


        }

    }

}
