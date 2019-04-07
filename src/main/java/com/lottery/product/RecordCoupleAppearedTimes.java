package com.lottery.product;

import java.util.HashMap;
import java.util.Map;

import com.lottery.utils.FileReader;
import com.lottery.utils.FileWriter;

public class RecordCoupleAppearedTimes {
    private Map frontCouples = new HashMap<String,Integer>();
    private Map behindCouples = new HashMap<String,Integer>();
    public void record(){
        FileReader fr = new FileReader("src/main/java/com/lottery/db/base/","amount.txt");
        int lines = 0;

        while(fr.hasNextLine()) {
            lines++;
            String aLine = fr.readLine().byteToString();
            Integer[] aTerm = analysisATerm(aLine);
            recordFrontCouple(aTerm);
            recordBehindCouple(aTerm);
        }
        System.out.println("读取行数：" + lines);
        write();
    }

    public void write(){
        FileWriter fw = new FileWriter("src/main/java/com/lottery/db/base/", "couple_front.log");
        FileWriter fw1 = new FileWriter("src/main/java/com/lottery/db/base/", "couple_behind.log");
        int reseverCount = 0;
        for (Object key : frontCouples.keySet()){
            int values = (int)frontCouples.get(key);
            if(values > 5)reseverCount += values;
            fw.writeLine(key + ":" + values);
        }
        for (Object key : behindCouples.keySet()){
            int values = (int)behindCouples.get(key);
            fw1.writeLine(key + ":" + values);
        }
        
        System.out.println("总数：" + reseverCount);
        fw.end();
        fw1.end();
    }



    private Integer[] analysisATerm(String aLine){
        aLine = aLine.replace("#","");
        aLine = aLine.replace("\n","");
        aLine = aLine.replace("\r","");
        String[] temp = aLine.split(",");
        Integer[] designatedTerm = new Integer[7];
        for (int i = 0; i<temp.length;i++) {
            designatedTerm[i] = Integer.parseInt(temp[i]);
        }
        return designatedTerm;
    }

    private void recordFrontCouple(Integer[] aTerm){
        for(int i=0;i<4;i++){
            Integer prev = aTerm[i];
            Integer next = aTerm[i + 1];
            String key = prev + "," + next;
            if(frontCouples.get(key) == null){
                frontCouples.put(key,1);    
            }else{
                int times = (int)frontCouples.get(key);
                frontCouples.put(key,++times);   
            }
        }
    }

    private void recordBehindCouple(Integer[] aTerm){
            Integer prev = aTerm[5];
            Integer next = aTerm[6];
            String key = prev + "," + next;
            if(behindCouples.get(key) == null){
                behindCouples.put(key,1);    
            }else{
                int times = (int)behindCouples.get(key);
                behindCouples.put(key,++times);   
            }
    }

    public static void main(String[] arg){
        
    }

}