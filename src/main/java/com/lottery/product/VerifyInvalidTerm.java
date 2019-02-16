package com.lottery.product;

import com.lottery.utils.JSArray;
import com.lottery.utils.JSArrayCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy-Super on 2019/2/11.
 * 验证是否超过3连号，如果超过了就废弃
 */
public class VerifyInvalidTerm {

    public boolean isInValid(JSArray aTerm){
        boolean result = false;
        if(hasSeriesDistance(aTerm) || hasSameFigure(aTerm) || hasMapping(aTerm)){
            result = true;
        }
        return result;
    }

    //如果超过3个以上的连号，就废弃
    private boolean hasSeriesDistance(JSArray aTerm){
        boolean result = false;
        int arrLen = 7;
        Map distanceMap = new HashMap<Integer,Integer>();
        while(arrLen -- > 1){
            Object curItem = aTerm.get(arrLen);
            Object prevItem = aTerm.get(arrLen - 1);
            int distance = (int)curItem - (int) prevItem;
            if(distanceMap.get(distance) == null){
                distanceMap.put(distance,1);
            }else{
                int distanceTimes = (int)distanceMap.get(distance);
                distanceTimes = distanceTimes + 1;
                distanceMap.put(distance,distanceTimes);
            }
        }

        for (Object val:distanceMap.values()) {
            if((int)val >= 2){
                result = true;
            }
        }
        return result;
    }

    // 超过4个同位数，就废弃
    private boolean hasSameFigure(JSArray aTerm){
        int loopTimes = 0;
        boolean result = false;
        int single = 0;
        int ten = 0;
        int twenty = 0;
        while(loopTimes < 5){
            int curItem = (int)aTerm.get(loopTimes);
            if(curItem<10){
                single ++;
            }else if(curItem<20){
                ten ++;
            }else if(curItem<30){
                twenty++;
            }
            loopTimes ++;
        }
        if(single >= 4 || ten >= 4 || twenty >= 4){
            result = true;
        }

        return result;
    }

    // 后面是前面的映射，废弃
    private boolean hasMapping(JSArray aTerm){

        boolean result = false;

        int first = aTerm.getIndex(aTerm.get(5));
        int second = aTerm.getIndex(aTerm.get(6));
        if(first != 5 && first != -1 && second != 6 && second != -1){
            result = true;
        }

        return result;
    }
}
