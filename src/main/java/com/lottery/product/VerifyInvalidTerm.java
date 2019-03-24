package com.lottery.product;

import com.lottery.utils.JSArray;
import com.lottery.utils.JSArrayCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy-Super on 2019/2/11.
 * 验证是否超过3连号(包括梯级连号)，如果超过了就废弃
 * 验证是否出现4个同位数(比如4个个位数，或者4个十位数)，如果是就废弃
 * 验证后去是否是前区的映射，如果是就废弃
 */
public class VerifyInvalidTerm {
    private Integer[] blackList = {};
    public boolean isInValid(Integer[] aTerm){
        boolean result = false;
        if(hasSeriesDistance(aTerm)
                || hasSameFigure(aTerm)
                || hasMapping(aTerm)
                || hasMatchedBlackList(aTerm)){
            result = true;
        }
        return result;
    }

    public void definedBlackList(Integer[] _blackList){
        blackList = _blackList;
    }

    //如果超过3个以上的连号，就废弃
    public boolean hasSeriesDistance(Integer[] aTerm){
        boolean result = false;
        int arrLen = 7;
        Map distanceMap = new HashMap<Integer,Integer>();
        while(arrLen -- > 1){
            Object curItem = aTerm[arrLen];
            Object prevItem = aTerm[arrLen - 1];
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
    public boolean hasSameFigure(Integer[] aTerm){
        int loopTimes = 0;
        boolean result = false;
        int single = 0;
        int ten = 0;
        int twenty = 0;
        while(loopTimes < 5){
            int curItem = aTerm[loopTimes];
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

    // 后面的两个号码是前面的映射，废弃
    public boolean hasMapping(Integer[] aTerm){

        boolean frontResult = false;
        boolean behindResult = false;

        int firstNum = aTerm[5];
        int secondNum = aTerm[6];
        for(int i = 0; i<aTerm.length;i++){
            if(aTerm[i] == firstNum && i != 5){
                frontResult = true;
            }
            if(aTerm[i] == secondNum && i != 6){
                behindResult = true;
            }
        }

        return frontResult && behindResult;
    }

    public boolean hasMatchedBlackList(Integer[] aTerm){
        JSArray useBlackList = new JSArray(blackList);
        boolean result = false;
        for(int i = 0; i<5;i++){
            if(useBlackList.include(aTerm[i])){
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean hasDesignatedOne(){

        return true;
    }
}
