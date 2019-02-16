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

    public FileWriter write(String bufferString){
        try {
            fop.write(bufferString.getBytes());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return this;
    }

    public FileWriter writeLine(String bufferString){
        try {
            fop.write((bufferString + compatibleWrapString()).getBytes());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return this;
    }

    public void end(){
        try {
            fop.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void end(Callback endCb){
        try {
            fop.close();
            endCb.entries((byte)-1);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String osName = System.getProperties().getProperty("os.name");

    private boolean isWindows(){
        return osName.contains("Windows");
    }

    private String compatibleWrapString(){
        byte[] result = new byte[2];
        if(isWindows()) {
            result[0] = (byte)0x0A;
        }
        else {
            result[0] = (byte)0x0D;
            result[1] = (byte)0x0A;
        }
        return new String(result);
    }
}
