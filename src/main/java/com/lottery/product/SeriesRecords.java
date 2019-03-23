package com.lottery.product;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.lottery.utils.FileWriter;
import com.lottery.utils.JSArray;

/**
 * Created by Andy-Super on 2019/2/13.
 * 记录连号次数
 */
public class SeriesRecords {

    private Map<String,Integer> map_front = new HashMap<String, Integer>();
    private Map<String,Integer> map_behind = new HashMap<String, Integer>();
    private String seriesRecordFilePath_front = "src/main/java/com/lottery/db/product/seriesRecord_front.log";
    private String seriesRecordFilePath_behind = "src/main/java/com/lottery/db/product/seriesRecord_behind.log";

    private FileWriter fw_front = new FileWriter(seriesRecordFilePath_front);
    private FileWriter fw_behind = new FileWriter(seriesRecordFilePath_behind);

    public SeriesRecords(){
    }

    public void record(JSArray aTerm){
        analyze(map_front,(Integer[])aTerm.slice(0,5));
        analyze(map_behind,(Integer[])aTerm.slice(5,2));
    }

    private Map analyze(Map map,Integer[] aTerm){
        int loopTimes = 0;
        JSArray series = new JSArray(Integer.class);
        while(loopTimes < aTerm.length - 1){
            Object curItem = aTerm[loopTimes];
            Object nextItem = aTerm[loopTimes + 1];
            if((int)nextItem - (int)curItem == 1){
                if(!series.include(curItem))series.push(curItem);
                if(!series.include(nextItem))series.push(nextItem);
            }
            storage(map,series.join("-"));
            series.reset();
            loopTimes ++;
        }
        return map;
    }

    private Map storage(Map map,String series){
        if(!series.equals("")){
            Integer curSeriesTimes = (Integer)map.get(series);
            if(curSeriesTimes == null){
                curSeriesTimes = 0;
            }

            map.put(series,curSeriesTimes + 1);
        }
        return map;
    }

    private void write(){
        for (String key:map_front.keySet()) {
            fw_front.writeLine(key + ":" + map_front.get(key));
        }
        for (String key:map_behind.keySet()) {
            fw_behind.writeLine(key + ":" + map_behind.get(key));
        }
    }

    public void end(){
        write();
        fw_front.end();
        fw_behind.end();
    }
}
