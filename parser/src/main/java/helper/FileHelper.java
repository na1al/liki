package helper;

import java.io.File;

public class FileHelper {

    public static boolean createDirectory(String path){
        File dir = new File(path);

        if(dir.isDirectory()){
            return true;
        }
        return dir.mkdir();
    }

    public static boolean canWrite(String path){
        File dir = new File(path);

        if(!dir.isDirectory()){
            return false;
        }

        if(!dir.canWrite()){
            return false;
        }

        return true;

    }

}
