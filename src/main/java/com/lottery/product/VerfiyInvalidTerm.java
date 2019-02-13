package com.lottery.product;

import com.lottery.utils.JSArray;
import com.lottery.utils.JSArrayCallback;

/**
 * Created by Andy-Super on 2019/2/11.
 */
public class VerfiyInvalidTerm {

    public boolean isInValid(JSArray aTerm){
        //如果超过3个以上的连号，就废弃
        aTerm.sort();
        boolean result = false;
        int seriesLen = 0;
        int arrLen = 7;
        while(arrLen -- > 0){
            Object curItem = aTerm.get(arrLen);
            Object prevItem = aTerm.get(arrLen - 1);
            if((Integer)curItem - (Integer) prevItem == 1){
                seriesLen ++;
            }
        }

        if(seriesLen >= 3){
            result = true;
        }
        return result;
    }

}
