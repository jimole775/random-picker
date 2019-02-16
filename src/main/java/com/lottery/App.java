package com.lottery;

/**
 * Hello world!
 *
 */
import com.lottery.product.RewardRecords;
import com.lottery.product.Roll;
import com.lottery.product.SeriesRecords;
import com.lottery.utils.FileReader;
import com.lottery.utils.JSArray;
import com.lottery.product.CreateRateFoundation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class App
{
    public static void main( String[] args )
    {
        readFile();
    }
    private static void readFile(){

        Integer[] awardTarget = {5,11,16,28,35,6,9};
        RewardRecords rr = new RewardRecords(awardTarget);
        SeriesRecords sr = new SeriesRecords();
        Roll r = new Roll();
        int loopTimes = 100000000;

        while(loopTimes -- > 0){
            JSArray aTerm = r.productATerm();
            rr.record((Integer[]) aTerm.getDataSet());
            sr.record((Integer[]) aTerm.getDataSet());
        }

}
}


