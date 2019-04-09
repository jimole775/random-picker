package com.lottery;

/**
 * Hello world!
 *
 */
import com.lottery.experiment.*;
// import com.lottery.product.RewardRecords;
// import com.lottery.product.Product;
// import com.lottery.product.SeriesRecords;
import com.common.utils.FileWriter;
// import com.common.utils.JSArray;

public class App
{
    public static void main( String[] args )
    {
        // RecordCoupleAppearedTimes rcat = new RecordCoupleAppearedTimes();
        // rcat.record();
        CreateRollRate crr = new CreateRollRate();
        crr.run();
    }

    private static void record(FileWriter fw, Integer level, int runTimes){
        String a = "购买了" + runTimes + "期，每期100注，终于中得" + level + "等奖";
        fw.writeLine(a);
        String b = "消耗资金" + runTimes*100*2 + "元";
        fw.writeLine(b);
    }

    private static void pro(){


    }
}


