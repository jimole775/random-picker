package com.lottery.product;

import com.lottery.utils.FileWriter;
import com.lottery.utils.JSArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy-Super on 2019/2/12.
 * 记录中奖次数
 */
public class RewardRecords {
    private Integer[] simp = {5,11,16,28,35,6,9};
    private JSArray awardTarget = new JSArray(simp);
    private int rollTimes = 0;
    private JSArray no1 = new JSArray(Integer.class);
    private JSArray no2 = new JSArray(Integer.class);
    private JSArray no3 = new JSArray(Integer.class);
    private JSArray no4 = new JSArray(Integer.class);
    private String filePath = "src/main/java/com/lottery/db/product/";
    private FileWriter fw1 = new FileWriter( filePath + awardTarget.join("-") + "_no1.log");
    private FileWriter fw2 = new FileWriter(filePath + awardTarget.join("-") + "_no2.log");
    private FileWriter fw3 = new FileWriter(filePath + awardTarget.join("-") + "_no3.log");
    private FileWriter fw4 = new FileWriter(filePath + awardTarget.join("-") + "_no4.log");

    public RewardRecords(){
    }
    //14,19,23,27,34,#06,#12
    private void write(JSArray collection,FileWriter writer){
        collection.push(rollTimes);
        if(collection.getSize() > 1){
            int latest = (int)collection.get(collection.getSize() - 1);
            int prevLatest = (int)collection.get(collection.getSize() - 2);
            int distance = latest-prevLatest;
            writer.writeLine(distance + "");
        }else{
            writer.writeLine(rollTimes + "");
        }

    }
    public void end(){
        fw1.end();
        fw2.end();
        fw3.end();
        fw4.end();
    }
    public void record(JSArray rollTerm){
        rollTimes ++;
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
        if(frontMatchedTimes == 4 && behindMatchedTimes == 1
            || frontMatchedTimes == 3 && behindMatchedTimes == 2){
            write(no4,fw4);
        }
        return 0;
    }

    private int matchFront(JSArray rollTerm){
        int matchTimes = 0;
        for(int i = 0; i< 5;i++){
            if((int)rollTerm.get(i) == (int)awardTarget.get(i)){
                matchTimes ++;
            }
        }
        return matchTimes;
    }

    private int matchBehind(JSArray rollTerm){
        int matchTimes = 0;
        for(int i = 4; i < 6;i++){

            if((int)rollTerm.get(i) == (int)awardTarget.get(i)){
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
