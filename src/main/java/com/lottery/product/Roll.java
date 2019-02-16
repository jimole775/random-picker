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
        JSArray aTerm_front = new JSArray<Integer>(Integer.class,5);
        JSArray aTerm_behind = new JSArray<Integer>(Integer.class,2);
        picksNum(aTerm_front,5);
        picksNum(aTerm_behind,2);
        JSArray aTerm = aTerm_front.concat(aTerm_behind);
        rollTimes++;
        if(verifier.isInValid(aTerm)){
            return productATerm();
        }
        System.out.println("aTerm:" + aTerm.join("-"));
        return aTerm.sort();
    }

    private void picksNum(JSArray aTerm,int times){
        JSArray<Integer> foundation = times == 5 ? front_foundation : behind_foundation;
        while(times-- > 0){
            Integer randomOne = foundation.get(suffixPick(10000) - 1);
            if(aTerm.include(randomOne)){
                times ++;
            }
            else{
                aTerm.set(times,randomOne);
            }
        }
        aTerm.sort();
    }

    private int suffixPick(int bit){
        return (int)Math.ceil(Math.random()*bit);
    }
}
