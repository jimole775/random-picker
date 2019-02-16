package com.lottery.product;

import com.lottery.utils.JSArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy-Super on 2019/2/12.
 * 记录中奖次数
 */
public class RewardRecords {
    private Map<String,Integer> map = new HashMap<String, Integer>();
    private Integer[] awardTarget = new Integer[7];
    private int rollTimes = 0;
    public RewardRecords(Integer[] simp){
        awardTarget = simp;
    }
    //14,19,23,27,34,#06,#12
    private void write(){

    }

    public void record(Integer[] rollTerm){
        rollTimes ++;
        matchTarget(rollTerm);
    }

    private int matchTarget(Integer[] rollTerm){
        int frontMatchedTimes = matchFront(rollTerm);
        int behindMatchedTimes = matchBehind(rollTerm);
        if(frontMatchedTimes == 5 && behindMatchedTimes == 2){
            award_No1();
        }
        if(frontMatchedTimes == 5 && behindMatchedTimes == 1){
            award_No2();
        }
        if(frontMatchedTimes == 5 && behindMatchedTimes == 0
            || frontMatchedTimes == 4 && behindMatchedTimes == 2){
            award_No3();
        }
        if(frontMatchedTimes == 4 && behindMatchedTimes == 1
            || frontMatchedTimes == 3 && behindMatchedTimes == 2){
            award_No4();
        }
        return 0;
    }

    private int matchFront(Integer[] rollTerm){
        int matchTimes = 0;
        for(int i = 0; i< 5;i++){
            if((int)rollTerm[i] == awardTarget[i]){
                matchTimes ++;
            }
        }
        return matchTimes;
    }

    private int matchBehind(Integer[] rollTerm){
        int matchTimes = 0;
        for(int i = 4; i < 6;i++){

            if((int)rollTerm[i] == awardTarget[i]){
                matchTimes ++;
            }

        }
        return matchTimes;
    }

    private boolean award_No1(){
        return true;
    }

    private boolean award_No2(){
        return true;
    }
    private boolean award_No3(){
        return true;
    }
    private boolean award_No4(){
        return true;
    }
// if(frontMatchedTimes === 5 && behindMatchedTimes === 2){
//        afterAwarded(awardState,1,awardState.rollTimeSum+awardState.rollTimeCalc);
//    }
//                if(frontMatchedTimes === 5 && behindMatchedTimes === 1){
//        afterAwarded(awardState,2,awardState.rollTimeSum+awardState.rollTimeCalc);
//    }
//                if(frontMatchedTimes === 5 && behindMatchedTimes === 0
//            || frontMatchedTimes === 4 && behindMatchedTimes === 2){
//        afterAwarded(awardState,3,awardState.rollTimeSum+awardState.rollTimeCalc);
//    }
//                if(frontMatchedTimes === 4 && behindMatchedTimes === 1
//            || frontMatchedTimes === 3 && behindMatchedTimes === 2){
//    }
    public static void main(String[] args){
        Map<String,Integer> map = new HashMap<String, Integer>();
        int curSeriesTimes = map.get("asd");

        System.out.println(curSeriesTimes);
    }

}
