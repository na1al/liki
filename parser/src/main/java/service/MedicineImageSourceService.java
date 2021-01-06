package service;

import entity.MedicineImageSource;
import helper.MediaHelper;
import lombok.extern.log4j.Log4j2;
import repository.MedicineImageSourceRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

@Log4j2
public class MedicineImageSourceService {

    public static final String IMAGE_PATH = "src/main/resources/images";

    MedicineImageSourceRepository sourceRepository;

    public MedicineImageSourceService() {
        sourceRepository = new MedicineImageSourceRepository();
    }

    public MedicineImageSource getNext() throws SQLException {
        return sourceRepository.next();
    }

    public boolean importSource(MedicineImageSource source) throws IOException, SQLException {

        source.setNeedUpdate(false);
        sourceRepository.update(source);

        URL url = new URL(source.getUrl());
        InputStream in = url.openStream();
        long bytes = Files.copy(in, Paths.get(IMAGE_PATH + "/" + MediaHelper.getNewName(source)), StandardCopyOption.REPLACE_EXISTING);
        log.info("File(" + source.getId() + "): " + source.getUrl() + " Size: " + bytes + " bytes");
        return true;
    }

}
