package com.lottery.product;

import com.common.utils.JSArray;

/**
 * Created by Andy-Super on 2019/2/12.
 */
public class Product {
    private CreateRateFoundation crf;
    private JSArray<Integer> front_foundation;
    private JSArray<Integer> behind_foundation;
//    private Integer[] blackList = {};
//    private Integer[] insertRangeList = {};
    private JSArray<Integer> aTerm_front;
    private JSArray<Integer> aTerm_behind;
    public Product(){
        crf = new CreateRateFoundation();
        front_foundation = crf.produce(35);
        behind_foundation = crf.produce(12);
        aTerm_front = new JSArray<Integer>(Integer.class);
        aTerm_behind = new JSArray<Integer>(Integer.class);
    }

//    public void defineBlackList(Integer[] _blackList){
//        blackList = _blackList;
//    }
//    public void defineInsertRangeList(Integer[] _insertRangeList){
//        insertRangeList = _insertRangeList;
//    }

    public Integer[] productATerm(){
        picksNum(aTerm_front,5);
//        if(insertRangeList.length > 0){
//            picksNum_custom(aTerm_front,1);
//        }else{
//            picksNum(aTerm_front,1);
//        }
        picksNum(aTerm_behind,2);
        Integer[] completeTerm = (Integer[])aTerm_front.concat(aTerm_behind).getDataSet();
        aTerm_front.reset();
        aTerm_behind.reset();
        return completeTerm;
    }
    private JSArray<Integer> picksNum_custom(JSArray<Integer> tempTerm,int times){
//        JSArray<Integer> useBlackList = new JSArray<Integer>(blackList);
//        while(times-- > 0){
//            Integer customOne = customPicksOne(insertRangeList);
//            if(tempTerm.include(customOne)){
//                times ++;
//            }
//            else{
//                tempTerm.push(customOne);
//            }
//        }
        return tempTerm.sort();
    }

    private JSArray<Integer> picksNum(JSArray<Integer> tempTerm,int times){
        JSArray<Integer> foundation = times == 2 ? behind_foundation : front_foundation;
//        JSArray<Integer> useBlackList = new JSArray<Integer>(blackList);
        while(times-- > 0){
            Integer randomOne = foundation.get(randomPicks(10000) - 1);
            if(tempTerm.include(randomOne)){
                times ++;
            }
            else{
                tempTerm.push(randomOne);
            }
        }

        return tempTerm.sort();
    }

    private int randomPicks(int bit){
        return (int)Math.ceil(Math.random()*bit);
    }
    private int customPicksOne(Integer[] rangeList){
        int  witchOne = (int)Math.ceil(Math.random()*rangeList.length) - 1;
        return rangeList[witchOne];
    }

}
