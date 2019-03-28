package com.lottery.utils;

import com.lottery.callbacks.IOCallback;

/**
 * Created by Andy-Super on 2018/12/21.
 */
/**这里需要Stream的on方法有监听data的流的功能，和nodejs一样*/
public interface Stream{
    public void on(IOCallback onEvent);
    public void trigger();
}