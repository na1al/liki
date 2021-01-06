package helper;

import entity.MedicineImageSource;
import org.apache.commons.io.FilenameUtils;

public class MediaHelper {

    public static String getNewName(MedicineImageSource source){
        return source.getId() + "_" + FilenameUtils.getName(source.getUrl());
    }

}
