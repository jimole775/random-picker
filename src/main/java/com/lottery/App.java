package com.lottery;

/**
 * Hello world!
 *
 */
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
        CreateRateFoundation crf = new CreateRateFoundation();

        JSArray<Short> front = crf.produce(35);
        JSArray<Short> behind = crf.produce(12);

        System.out.println(behind.getSize());
}
}


