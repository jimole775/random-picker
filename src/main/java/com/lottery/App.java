package com.lottery;

/**
 * Hello world!
 *
 */
import com.lottery.experiment.RollFromAvg;
import com.lottery.product.RewardRecords;
import com.lottery.product.Roll;
import com.lottery.product.SeriesRecords;
import com.lottery.utils.FileWriter;
import com.lottery.utils.JSArray;

public class App
{
    public static void main( String[] args )
    {

//        pro();01,23,24,28,33,#04,#05
        Integer[] simp = {1,23,24,28,33,4,5};
        RollFromAvg mfa = new RollFromAvg(simp);
        FileWriter fw = new FileWriter("src/main/java/com/lottery/db/experiment/awardTimes.log");
        int runTimes = 0;
        while(runTimes < 10000){
            runTimes ++;
            Integer level = mfa.run();
            if(level > 0 && level <=3){
                record(fw,level,runTimes);
            }
        }

        fw.end();
    }

    private static void record(FileWriter fw, Integer level, int runTimes){
        String a = "购买了" + runTimes + "期，每期100注，终于中得" + level + "等奖";
        fw.writeLine(a);
        String b = "消耗资金" + runTimes*100*2 + "元";
        fw.writeLine(b);
    }

    private static void pro(){

        RewardRecords rr = new RewardRecords();
        SeriesRecords sr = new SeriesRecords();
        Roll r = new Roll();
        int loopTimes = 1000000000;

        while(loopTimes -- > 0){
            JSArray aTerm = r.productATerm();
            rr.record(aTerm);
            sr.record(aTerm);
        }
        rr.end();
        sr.end();

}
}


