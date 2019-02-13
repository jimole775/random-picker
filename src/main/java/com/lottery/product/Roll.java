package com.lottery.product;

import com.lottery.utils.JSArray;

/**
 * Created by Andy-Super on 2019/2/12.
 */
public class Roll {
    private CreateRateFoundation crf = new CreateRateFoundation();
    JSArray<Short> front_foundation = crf.produce(35);
    JSArray<Short> behind_foundation = crf.produce(12);
    SeriesRecords sr = new SeriesRecords();
    public JSArray<Short> productATerm(){
        JSArray<Short> aTerm = new JSArray<Short>(Short.class,7);

        picksNum(5,aTerm);
        picksNum(2,aTerm);

        return aTerm;
    }

    private void picksNum(int times,JSArray aTerm){
        int loop = times;
        JSArray<Short> foundation = loop == 5 ? front_foundation : behind_foundation;
        while(loop-- > 0){
            short randomOne = foundation.get(suffixPick(10000));
            if(aTerm.include(randomOne)){
                loop ++;
            }
            else{
                aTerm.push(randomOne);
            }
        }
    }

    private int suffixPick(int bit){
        return (int)Math.ceil(Math.random()*bit);
    }
}
