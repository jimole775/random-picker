package com.lottery.experiment;

import com.lottery.callbacks.*;
import com.lottery.product.Product;
import com.lottery.product.RewardRecords;

/**
 * Created by Andy-Super on 2019/3/26.
 */
public class RollThread extends Thread{
    private Integer[] designatedTerm;
    public void setParams(Integer[] designatedTerm){
        this.designatedTerm = designatedTerm;
//        this.rr = rr;
//        this.pdt = pdt;
    }

    public RollThread(Integer[] designatedTerm){
        setParams(designatedTerm);
    }
    @Override
    public void run(){
        Product pdt = new Product();
        RewardRecords rr = new RewardRecords();
        rr.defineAwardTarget(designatedTerm);
        rr.openInputStream("src/main/db/temp/natureRoll/");
            int peerTermRollTimes = 1000 * 1000;
            int j;
            for(j = 0;j < peerTermRollTimes;j ++){
                Integer[] aTerm = pdt.productATerm();
                rr.record(aTerm,j);
            }
        rr.end();
    }

}
