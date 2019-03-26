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
    private Integer[] fixedList = {};
    public boolean isInValid(Integer[] aTerm){
        boolean result = false;
        if(hasSeriesDistance(aTerm)
                || hasSameFigure(aTerm)
                || hasMapping(aTerm)
                || hasMatchedBlackList(aTerm)
                || !hasDesignatedOne(aTerm)){
            result = true;
        }
        return result;
    }

    public void definedBlackList(Integer[] _blackList){
        blackList = _blackList;
    }
    public void definedFixedItem(Integer[] _fixedList){
        fixedList = _fixedList;
    }

    //如果超过3个以上的连号，就废弃
    private boolean hasSeriesDistance(Integer[] aTerm){
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
    private boolean hasSameFigure(Integer[] aTerm){
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
    private boolean hasMapping(Integer[] aTerm){

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

    //判断这组号码是否有元素被标记在黑名单
    private boolean hasMatchedBlackList(Integer[] aTerm){
        boolean result = false;
        for(int i = 0; i<5;i++){
            for(int j = 0;j<blackList.length;j++){
                if(aTerm[i].equals(blackList[j])){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    //判断这组号码是否拥有限定的号码，现情况只支持限定一个
    private boolean hasDesignatedOne(Integer[] aTerm){
        boolean result = false;
        for(int i = 0; i<5;i++){
            for(int j = 0;j<fixedList.length;j++){
                if(aTerm[i].equals(fixedList[j])){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
