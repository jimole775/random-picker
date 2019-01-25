package com.lottery.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andy-Super on 2018/12/25.
 */
public class FileWriter {
    private FileOutputStream fop;
    public FileWriter(String path){
        File direction = new File("");
        String filePath = direction.getAbsolutePath() + File.separator + path;
        try {
            fop = new FileOutputStream(filePath);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public FileWriter write(String[] bufferString){
        return this;
    }

    public void end(Callback endCb){

    }

}
