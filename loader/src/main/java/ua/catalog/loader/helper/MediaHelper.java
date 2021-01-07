package ua.catalog.loader.helper;

import org.apache.commons.io.FilenameUtils;
import ua.catalog.loader.entity.MedicineImageSource;

public class MediaHelper {

    public static String getNewName(MedicineImageSource source){
        return source.getId() + "_" + FilenameUtils.getName(source.getUrl());
    }

}
