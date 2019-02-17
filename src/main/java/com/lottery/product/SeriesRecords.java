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

    private Map<String,Integer> map = new HashMap<String, Integer>();
    private String seriesRecordFilePath = "src/main/java/com/lottery/db/pro/seriesRecord.log";
    private FileWriter fw = new FileWriter(seriesRecordFilePath);
    public SeriesRecords(){
        String title = "1个pile代表roll了100000次";
        fw.writeLine(title);
    }

    public void record(Integer[] aTerm){
        int loopTimes = 0;
        JSArray series = new JSArray(Integer.class);
        while(loopTimes < 6){
            Integer curItem = aTerm[loopTimes];
            Integer nextItem = aTerm[loopTimes + 1];
            if(nextItem - curItem == 1){
                series.push(curItem);
            }else{
                storage(series.join("-"));
                series.reset();
            }
            loopTimes ++;
        }
    }

    private Map storage(String series){
        Integer curSeriesTimes = map.get(series + "_roll");
        Integer curSeriesTimesPile = map.get(series + "_pile");
        if(curSeriesTimes == null){
            curSeriesTimes = 0;
            curSeriesTimesPile = 0;
        }else{
            if(curSeriesTimes >= 100000){
                curSeriesTimes = 0;
                curSeriesTimesPile ++;
            }
        }

        map.put(series + "_roll",curSeriesTimes + 1);
        map.put(series + "_pile",curSeriesTimesPile);

        return map;
    }

    private void write(){
        for (String key:map.keySet()) {
            fw.writeLine(key + ":" + map.get(key));
        }
    }

    public void end(){
        write();
        fw.writeLine("=========>结束文件<=========");
        fw.end();
    }
}
