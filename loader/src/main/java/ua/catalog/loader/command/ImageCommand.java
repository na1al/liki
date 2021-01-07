package ua.catalog.loader.command;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ua.catalog.loader.entity.Media;
import ua.catalog.loader.entity.Medicine;
import ua.catalog.loader.entity.MedicineImageSource;
import ua.catalog.loader.helper.FileHelper;
import ua.catalog.loader.helper.MediaHelper;
import ua.catalog.loader.repository.MediaRepository;
import ua.catalog.loader.repository.MedicineRepository;
import ua.catalog.loader.service.MedicineImageSourceService;

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
