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
    private Integer[] insertRangeList = {};

    public Product(){

    }

    public void defineBlackList(Integer[] _blackList){
        blackList = _blackList;
    }
    public void defineInsertRangeList(Integer[] _insertRangeList){
        insertRangeList = _insertRangeList;
    }

    public JSArray productATerm(){
        JSArray aTerm_front = new JSArray<Integer>(Integer.class);
        JSArray aTerm_behind = new JSArray<Integer>(Integer.class);
        picksNum(aTerm_front,4);

        if(insertRangeList.length > 0){
            picksNum_custom(aTerm_front,1);
        }else{
            picksNum(aTerm_front,1);
        }
        picksNum(aTerm_behind,2);
        JSArray aTerm = aTerm_front.concat(aTerm_behind);
        return aTerm;
    }
    private JSArray picksNum_custom(JSArray tempTerm,int times){
        JSArray<Integer> useBlackList = new JSArray<Integer>(blackList);
        while(times-- > 0){
            Integer customOne = customPicksOne(insertRangeList);
            if(tempTerm.include(customOne) || useBlackList.include(customOne)){
                times ++;
            }
            else{
                tempTerm.push(customOne);
            }
        }
        return tempTerm.sort();
    }

    private JSArray picksNum(JSArray tempTerm,int times){
        JSArray<Integer> foundation = times == 2 ? behind_foundation : front_foundation;
        JSArray<Integer> useBlackList = new JSArray<Integer>(blackList);
        while(times-- > 0){
            Integer randomOne = foundation.get(randomPicks(10000) - 1);
            if(tempTerm.include(randomOne) || useBlackList.include(randomOne)){
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
