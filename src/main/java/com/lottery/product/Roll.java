package com.lottery.product;

import com.lottery.utils.JSArray;

/**
 * Created by Andy-Super on 2019/2/12.
 */
public class Roll {
    private CreateRateFoundation crf = new CreateRateFoundation();
    private JSArray<Integer> front_foundation = crf.produce(35);
    private JSArray<Integer> behind_foundation = crf.produce(12);
    public int rollTimes = 0;
    private VerifyInvalidTerm verifier = new VerifyInvalidTerm();
    public Roll(){

    }

    public JSArray productATerm(){
        JSArray aTerm_front = picksNum(5);
        JSArray aTerm_behind = picksNum(2);
        JSArray aTerm = aTerm_front.concat(aTerm_behind);
        rollTimes++;
        if(verifier.isInValid(aTerm)){
            return productATerm();
        }
        return aTerm;
    }

    private JSArray picksNum(int times){
        JSArray tempTerm = new JSArray<Integer>(Integer.class,times);
        JSArray<Integer> foundation = times == 5 ? front_foundation : behind_foundation;
        while(times-- > 0){
            Integer randomOne = foundation.get(suffixPick(10000) - 1);
            if(tempTerm.include(randomOne)){
                times ++;
            }
            else{
                tempTerm.set(times,randomOne);
            }
        }
        return tempTerm.sort();
    }

    private int suffixPick(int bit){
        return (int)Math.ceil(Math.random()*bit);
    }
}
