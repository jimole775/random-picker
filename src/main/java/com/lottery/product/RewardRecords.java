package com.lottery.product;

import com.lottery.utils.FileWriter;
import com.lottery.utils.JSArray;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy-Super on 2019/2/12.
 * 记录中奖次数
 */
public class RewardRecords {
    private int rollTimes = 0;

    private Integer[] awardTarget;
    private int[] no1,no2,no3;
    private FileWriter fw1,fw2,fw3;
    private String fileName = "";
    private String filePath = "";
    public RewardRecords(){
    }

    private void initStorage(){
        no1 = new int[2];
        no2 = new int[2];
        no3 = new int[2];
    }

    private void initWriter(){
        fw1 = new FileWriter(filePath + fileName + "_no1.log");
        fw2 = new FileWriter(filePath + fileName + "_no2.log");
        fw3 = new FileWriter(filePath + fileName + "_no3.log");
    }

    public void defineAwardTarget(Integer[] designated){
        awardTarget = designated;
        initStorage();
    }

    public void openInputStream(String _filePath){
        fileName = "";
        for (Integer num:awardTarget) {
            fileName += num + ",";
        }
        filePath = _filePath;
        fileName = fileName.substring(0,fileName.length() - 1);
        initWriter();
    }

    //14,19,23,27,34,#06,#12
    private void write(int[] rollTimesStorage,FileWriter writer){
        if(rollTimesStorage[0] == 0){
            rollTimesStorage[0] = rollTimes;
            writer.writeLine("sum:" + rollTimes + ",dvd:" + 0);
        }else{
            rollTimesStorage[1] = rollTimes;
            int prev = rollTimesStorage[0];
            int latest = rollTimesStorage[1];
            writer.writeLine("sum:" + rollTimes + ",dvd:" + (latest - prev));

            // 用完后替换
            rollTimesStorage[0] = rollTimesStorage[1];
            rollTimesStorage[1] = 0;
        }
    }

    private void rollTimeGrowUp(){
        rollTimes ++;
//        encodeRollTimes();
    }

    public void end(){
        fw1.end();
        fw2.end();
        fw3.end();
        reset();
    }

    private void reset(){
        rollTimes = 0;
        fileName = "";
        filePath = "";
    }

    public void record(JSArray rollTerm){
        rollTimeGrowUp();
        matchTarget(rollTerm);
    }

    private int matchTarget(JSArray rollTerm){
        int frontMatchedTimes = matchFront(rollTerm);
        int behindMatchedTimes = matchBehind(rollTerm);
        if(frontMatchedTimes == 5 && behindMatchedTimes == 2){
            write(no1,fw1);
        }
        if(frontMatchedTimes == 5 && behindMatchedTimes == 1){
            write(no2,fw2);
        }
        if(frontMatchedTimes == 5 && behindMatchedTimes == 0
            || frontMatchedTimes == 4 && behindMatchedTimes == 2){
            write(no3,fw3);
        }
//        if(frontMatchedTimes == 4 && behindMatchedTimes == 1
//            || frontMatchedTimes == 3 && behindMatchedTimes == 2){
//            write(no4,fw4);
//        }
        return 0;
    }

    private int matchFront(JSArray rollTerm){
        int matchTimes = 0;
        for(int i = 0; i< 5;i++){
            if((int)rollTerm.get(i) == (int)awardTarget[i]){
                matchTimes ++;
            }
        }
        return matchTimes;
    }

    private int matchBehind(JSArray rollTerm){
        int matchTimes = 0;
        for(int i = 4; i < 6;i++){

            if((int)rollTerm.get(i) == (int)awardTarget[i]){
                matchTimes ++;
            }

        }
        return matchTimes;
    }

    public static void main(String[] args){
        Map<String,Integer> map = new HashMap<String, Integer>();
        int curSeriesTimes = map.get("asd");

        System.out.println(curSeriesTimes);
    }

}
