package com.lottery.product;
import com.lottery.utils.FileReader;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonIOException;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonSyntaxException;
import com.lottery.utils.JSArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by Administrator on 2019/1/22.
 */
public class CreateRateFoundation {

    private JSONArray getJson(String path){
        FileReader frontReader = new FileReader(path);
        JSArray jsonData = frontReader.readFile();
        JSONArray front_info = new JSONArray();
        try {
            front_info = new JSONArray(jsonData.byteToString());
        }catch (JSONException e){
            System.out.print(e.getMessage());
        }

        return front_info;
    }

    private int suffixPick(int bit){
        return (int)Math.ceil(Math.random()*bit);
    }

    private JSArray<Short> foundationStorage;
    private String filePath_front = "src/main/java/com/lottery/db/base/front_sum.json";
    private String filePath_behind = "src/main/java/com/lottery/db/base/behind_sum.json";
    public JSArray<Short> produce(int baseType){
        int i = 10000;
        foundationStorage = new JSArray(Short.class,10000);
        String filePath = baseType == 35 ? filePath_front : filePath_behind;
        JSONArray json_info = getJson(filePath);
        JSArray timesRecord = new JSArray(Integer.class,35);
        timesRecord.fill(0);
        try{

            while(i-- > 0){
                short suffixNum = (short)suffixPick(baseType);
                JSONObject curItem = new JSONObject(json_info.get(suffixNum - 1).toString());
                double curNumRate = (double) curItem.get("rate"); // 几率
                int curNumLimit = (int)Math.round(curNumRate * 10000); // 把原几率换成当前数组的出现频率
                int curItemRollTimes = (int) timesRecord.get(suffixNum - 1);
                int position = suffixPick(10000) - 1;
                if(curItemRollTimes <= curNumLimit){
                    if(foundationStorage.get(position) == null){
                        foundationStorage.set(position,suffixNum);
                        // 记录roll次数
                        timesRecord.set(suffixNum - 1,++curItemRollTimes);
                    }else{
                        i ++;
                    }
                }else{
                    i ++;
                }
            }

        }catch(JSONException e){
            System.out.print(e.getMessage());
        }

        return foundationStorage;
    }

}
