package com.lottery;

/**
 * Hello world!
 *
 */
import com.lottery.experiment.RollFromAvg;
import com.lottery.product.RewardRecords;
import com.lottery.product.Roll;
import com.lottery.product.SeriesRecords;
import com.lottery.utils.JSArray;

public class App
{
    public static void main( String[] args )
    {

//        pro();
        Integer[] simp = {5,11,16,28,35,6,9};
        RollFromAvg mfa = new RollFromAvg(simp);
        mfa.run();
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


