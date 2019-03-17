package com.lottery.product;

import com.lottery.utils.JSArray;

/**
 * Created by Andy-Super on 2019/2/12.
 */
public class Product {
    private CreateRateFoundation crf = new CreateRateFoundation();
    private JSArray<Integer> front_foundation = crf.produce(35);
    private JSArray<Integer> behind_foundation = crf.produce(12);
    private Integer[] blackList = {};

    public Product(){
    }

//    public void defineBlackList(Integer[] _blackList){
//        blackList = _blackList;
//    }

    public JSArray productATerm(){
        JSArray aTerm_front = new JSArray<Integer>(Integer.class);
        JSArray aTerm_behind = new JSArray<Integer>(Integer.class);
        picksNum(aTerm_front,5);
//        picksNum_special(aTerm_front,1);
        picksNum(aTerm_behind,2);
        JSArray aTerm = aTerm_front.concat(aTerm_behind);
        return aTerm;
    }
//    private JSArray picksNum_special(JSArray tempTerm,int times){
//        JSArray<Integer> useBlackList = new JSArray<Integer>(blackList);
//        while(times-- > 0){
//            // 每组必选29-35中的一个
//            Integer randomOne = suffixPick(7) + 28;
//            if(tempTerm.include(randomOne) || useBlackList.include(randomOne)){
//                times ++;
//            }
//            else{
//                tempTerm.push(randomOne);
//            }
//        }
//        return tempTerm.sort();
//    }

    private JSArray picksNum(JSArray tempTerm,int times){
        JSArray<Integer> foundation = times == 2 ? behind_foundation : front_foundation;
//        boolean isFrontArea = times != 2;   //如果是前区号码，就匹配黑名单
//        JSArray<Integer> useBlackList = new JSArray<Integer>(blackList);
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
