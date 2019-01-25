package com.lottery.product;
import com.lottery.utils.FileReader;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonIOException;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by Administrator on 2019/1/22.
 */
public class CreateRateFoundation {
    private short[] front;
    private short[] behind;
    private String filePath_front = "src/main/java/com/lottery/db/base/test.json";
    private String filePath_behind = "src/main/java/com/lottery/db/base/behind_sum.json";

    public String getJsonObject(){
        FileReader frontReader = new FileReader(filePath_front);
        byte[] jsonByte = frontReader.readFile();
        return new String(jsonByte);
    }

}
