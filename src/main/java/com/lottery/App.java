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

        RewardRecords rr = new RewardRecords();
        SeriesRecords sr = new SeriesRecords();
        Roll r = new Roll();
        int loopTimes = 10000;

        while(loopTimes -- > 0){
            JSArray aTerm = r.productATerm();
            rr.record((Integer[]) aTerm.getDataSet());
            sr.record((Integer[]) aTerm.getDataSet());
        }
        rr.end();
        sr.end();

}
}


