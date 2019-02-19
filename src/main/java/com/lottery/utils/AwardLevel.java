package com.lottery.utils;

/**
 * Created by Andy-Super on 2019/2/19.
 */
public class AwardLevel {
    private JSArray<Integer> awardTarget;
    public AwardLevel(Integer[] simp){
        awardTarget = new JSArray<Integer>(simp);
    }
    public int match(JSArray rollTerm){
        int level = 0;
        int frontMatchedTimes = matchFront(rollTerm);
        int behindMatchedTimes = matchBehind(rollTerm);
        if(frontMatchedTimes == 5 && behindMatchedTimes == 2){
            level = 1;
        }
        if(frontMatchedTimes == 5 && behindMatchedTimes == 1){
            level = 2;
        }
        if(frontMatchedTimes == 5 && behindMatchedTimes == 0
                || frontMatchedTimes == 4 && behindMatchedTimes == 2){
            level = 3;
        }
        if(frontMatchedTimes == 4 && behindMatchedTimes == 1
                || frontMatchedTimes == 3 && behindMatchedTimes == 2){
            level = 4;
        }
        if(frontMatchedTimes == 4 && behindMatchedTimes == 1
                || frontMatchedTimes == 3 && behindMatchedTimes == 2){
            level = 5;
        }
        return level;
    }

    private int matchFront(JSArray rollTerm){
        int matchTimes = 0;
        for(int i = 0; i< 5;i++){
            if((int)rollTerm.get(i) == (int)awardTarget.get(i)){
                matchTimes ++;
            }
        }
        return matchTimes;
    }

    private int matchBehind(JSArray rollTerm){
        int matchTimes = 0;
        for(int i = 4; i < 6;i++){

            if((int)rollTerm.get(i) == (int)awardTarget.get(i)){
                matchTimes ++;
            }

        }
        return matchTimes;
    }
}
