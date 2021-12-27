package com.koreait.basic;

import java.io.File;

public class FileUtils {
    public static void delFolder(String path){
        delFolderFiles(path, true);
    }
    public static void delFolderFiles(String path, boolean isDelFolder){
        File folder = new File(path);
        if(folder.exists()){
            File[] fileList = folder.listFiles();
            for(File f : fileList){
                if(f.isDirectory()){
                    delFolderFiles(f.getPath(),true);
                }else {
                    f.delete();
                }
            }
        }
        if(isDelFolder){
            folder.delete();
        }
    }
}
