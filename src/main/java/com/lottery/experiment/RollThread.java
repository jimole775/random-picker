package com.lottery.experiment;

import com.lottery.callbacks.*;
import com.lottery.product.Product;
import com.lottery.product.RewardRecords;
import com.lottery.product.VerifyInvalidTerm;


/**
 * Created by Andy-Super on 2019/3/26.
 */
public class RollThread implements Runnable{
//    private Integer[] designatedTerm;

    private RewardRecords natureRR = new RewardRecords();
    private RewardRecords simulateRR = new RewardRecords();
    private VerifyInvalidTerm vit = new VerifyInvalidTerm();
    private Product pdt = new Product();

    public void injectParam(Integer[] _designatedTerm){
//        designatedTerm = _designatedTerm;
        natureRR.defineAwardTarget(_designatedTerm);
        simulateRR.defineAwardTarget(_designatedTerm);
        natureRR.openInputStream("src/main/db/temp/natureRoll/");
        simulateRR.openInputStream("src/main/db/temp/simulateRoll/");
    }

    public RollThread(Integer[] designatedTerm){
        injectParam(designatedTerm);
    }
    @Override
    public void run(){
        nature();
        simulate();
    }

    private void nature(){
        int peerTermRollTimes = 100000;
        int j;
        for(j = 0;j < peerTermRollTimes;j ++){
            Integer[] aTerm = pdt.productATerm();
            natureRR.record(aTerm,j);
        }
        natureRR.end();
    }

    private void simulate(){
        int peerTermRollTimes = 100000;
        int j;
        for(j = 0;j < peerTermRollTimes;j ++){
            Integer[] aTerm = pdt.productATerm();
            if(vit.isInValid(aTerm) || vit.coupleLess(aTerm)){
                j--;
                continue;
            }
            simulateRR.record(aTerm,j);
        }
        simulateRR.end();
    }

}
