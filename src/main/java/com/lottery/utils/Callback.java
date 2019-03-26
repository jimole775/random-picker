package com.lottery.utils;

/**
 * Created by Andy-Super on 2018/12/21.
 */
public interface Callback{
    public void entries(byte[] data); // 主要是为了兼容readLine方法
    public void entries(byte data);
    public void entries();
}
