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
 * 创建随机数组，以10000的长度为基准，用以实现概率取数
 */
public class CreateRateFoundation {

    private JSONArray getJson(String path,String fileName){
        FileReader frontReader = new FileReader(path,fileName);
        JSArray<Byte> jsonData = frontReader.readFile();
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

    private JSArray<Integer> foundationStorage;
    private String fileName_front = "sum_front.json";
    private String fileName_behind = "sum_behind.json";

    public JSArray<Integer> produce(int baseType){
        int i = 10000;
        foundationStorage = new JSArray<Integer>(Integer.class,10000);
        String fileName = baseType == 35 ? fileName_front : fileName_behind;
        JSONArray json_info = getJson("src/main/java/com/lottery/db/base/", fileName);
        JSArray<Integer> timesRecord = new JSArray<Integer>(Integer.class,35);
        try{

            while(i-- > 0){
                int suffixNum = (int)suffixPick(baseType);
                JSONObject curItem = new JSONObject(json_info.get(suffixNum - 1).toString());
                double curNumRate = (double) curItem.get("rate"); // 几率
                int curNumLimit = (int)Math.round(curNumRate * 10000); // 把原几率换成当前数组的出现频率
                Object _curItemRollTimes = timesRecord.get(suffixNum - 1);
                int curItemRollTimes = _curItemRollTimes == null ? 0 : (int)_curItemRollTimes;
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
