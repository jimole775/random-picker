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
        JSArray aTerm_front = new JSArray<Integer>(Integer.class);
        JSArray aTerm_behind = new JSArray<Integer>(Integer.class);
        picksNum(aTerm_front,4);
        picksNum_special(aTerm_front);
        picksNum(aTerm_behind,2);
        JSArray aTerm = aTerm_front.concat(aTerm_behind);
        rollTimes++;
        if(verifier.isInValid(aTerm)){
            return productATerm();
        }
        return aTerm;
    }
    private JSArray picksNum_special(JSArray tempTerm){
        int times = 1;
        while(times-- > 0){
            Integer randomOne = suffixPick(5) + 30;
            if(tempTerm.include(randomOne)){
                times ++;
            }
            else{
                tempTerm.push(randomOne);
            }
        }
        return tempTerm;
    }

    private JSArray picksNum(JSArray tempTerm,int times){
//        JSArray tempTerm = new JSArray<Integer>(Integer.class,times);
        JSArray<Integer> foundation = times == 5 ? front_foundation : behind_foundation;
        while(times-- > 0){
            Integer randomOne = foundation.get(suffixPick(10000) - 1);
            if(tempTerm.include(randomOne)){
                times ++;
            }
            else{
                tempTerm.push(randomOne);
            }
        }
        return tempTerm.sort();
    }

    private int suffixPick(int bit){
        return (int)Math.ceil(Math.random()*bit);
    }
}
