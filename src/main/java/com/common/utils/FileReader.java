package com.common.utils;
/**
 * Created by Andy-Super on 2018/12/21.
 */
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import com.common.callbacks.*;

public class FileReader implements Pipe{
    private InputStream ins;
    private int insByteIndex = 0;
    private ArrayList<IOCallback> pipeCbRegister = new ArrayList<IOCallback>();
    private String osName = System.getProperties().getProperty("os.name");
    public FileReader(String path, String fileName) {
        File direction = new File(path);
        String completeFilePath = direction.getAbsolutePath() + direction.separatorChar + fileName;
        try {
            ins = new FileInputStream(completeFilePath);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

  /*public byte[] read() {
        return readHandle();
    }

    public FileReader read(IOCallback cb) {
        byte[] data = readByteHandle();
        cb.entries(data);
        return this;
    }

    public FileReader readLine(IOCallback cb){
        byte[] data = readLineHandle();
        cb.entries(data);
        return this;
    }*/

    // pipe需要存储所有回调，每读取一字节，就循环调用所有回调
    @Override
    public FileReader pipe(IOCallback pipeCb){
        pipeCbRegister.add(pipeCb);
        return this;
    }

    // end算是pipe触发器
    @Override
    public void end(IOCallback pipeEndCb){
        emit();
        pipeEndCb.entries((byte)-1);
    }

    @Override
    public void emit(){        
        int data = readByte();

        // 文件读完了，回调最后的方法
        if(data == -1){ 
            return;
        }

        int cbLen = pipeCbRegister.size();
        for (IOCallback pipeCb : pipeCbRegister) {

            pipeCb.entries((byte)data);

            cbLen--;
            // pipe组跑完了，来个递归
            if(cbLen == 0){
                emit();
                break;
            }
        }
    }

    public JSArray<Byte> readLine(){
        JSArray<Byte> aline = new JSArray<Byte>(Byte.class);
        while(true){
            byte data = readByte();

            if(data == -1){
                break;
            }

            aline.push(data);
            // 如果遇到换行符或者结果是-1，就结束循环
            if(isWindows() && data == 0x0A // 区别各系统之间的换行符
                    || !isWindows() && (data == 0x0D || data == 0x0A)){
                break;
            }
        }
        return aline;
    }

    public JSArray<Byte> readFile(){
        JSArray<Byte> fileData = new JSArray<Byte>(Byte.class);
        while(true){
            byte data = readByte();

            if(data == -1){
                break;
            }

            fileData.push(data);
        }
        return fileData;
    }

    private byte readByte(){
        int data = -1;
        try {
            data = ins.read();
            this.insByteIndex ++;
            if(data == -1){
                ins.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return (byte)data;
    }

    public boolean hasNextLine(){
        int leftSize = 0;
        try {
            leftSize = ins.available();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return leftSize != 0;
    }

    private boolean isWindows(){
        return osName.contains("Windows");
    }

    private char[] compatibleWrapString(){
        char[] result;
        if(isWindows()) {
            result = new char[1];
            result[0] = 0x0A;
        }
        else {
            result = new char[2];
            result[0] = 0x0D;
            result[1] = 0x0A;
        }
        return result;
    }

}