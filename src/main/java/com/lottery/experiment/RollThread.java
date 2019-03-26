package com.lottery.experiment;

import com.lottery.utils.Callback;
/**
 * Created by Andy-Super on 2019/3/26.
 */
public class RollThread extends Thread{
    private Callback threadFn;
    public RollThread(Callback fn){
        threadFn = fn;
    }
    @Override
    public void run(){
        if(threadFn != null)threadFn.entries();
    }

}
