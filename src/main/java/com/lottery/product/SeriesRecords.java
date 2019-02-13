package com.lottery.product;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.lottery.utils.FileWriter;
import com.lottery.utils.JSArray;

/**
 * Created by Andy-Super on 2019/2/13.
 */
public class SeriesRecords {

    private Map<String,Integer> map = new HashMap<String, Integer>();
    private String seriesRecordFilePath = "src/main/java/com/lottery/db/pro/seriesRecord.log";
    private FileWriter fw = new FileWriter(seriesRecordFilePath);
    public SeriesRecords(){
        String title = "pile-1次，代表roll-100000次";
        fw.writeLine(title);
    }


    private void analyze(JSArray aTerm){
        aTerm.sort();
        int arrLen = 7;
//        while(arrLen -- > 0){
//            Object curItem = aTerm.get(arrLen);
//            Object prevItem = aTerm.get(arrLen - 1);
//            if((Integer)curItem - (Integer) prevItem == 1){
//                seriesLen ++;
//            }
//        }

    }

    public Map record(String series){
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

    private void write(String bufferString){

    }

    public void end(){
//        fw.writeLine(bufferString);
    }
}
