package com.lottery;

/**
 * Hello world!
 *
 */
import com.lottery.utils.FileReader;
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
//        FileReader fr = new FileReader("db/base/amount.txt");
//        fr.pipe(new Callback(){
//            public void entries(byte data){
//                System.out.println("is run success " + data);
//            }
//        }).end(new Callback(){
//            public void entries(byte data){
//                System.out.println("is run end " + data);
//            }
//        });
//        byte[] line = fr.readLine();
//        System.out.println(new String(line));
    CreateRateFoundation crf = new CreateRateFoundation();
//    System.out.println(crf.getJsonObject());

        String jsonObject = crf.getJsonObject();
        System.out.println(jsonObject);
//        try{
//            JSONArray dataArray = jsonObject.getJSONArray("aaa");
//            System.out.println(dataArray.toString());
//        }catch(JSONException e){
//            System.out.println(e.getMessage());
//        }
}
}


