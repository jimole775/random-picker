package com.common.utils;

/**
 * Created by Andy-Super on 2018/12/21.
 */
import java.util.ArrayList;
import com.common.callbacks.*;
public interface Pipe{

    // 注册管理pipe的所有回调，等到emit或者end调用的时候，统一调用
    public ArrayList<IOCallback> pipeCbRegisted = new ArrayList<IOCallback>();

    // pipe需要存储所有回调，每读取一字节，就循环调用所有回调
    // 这种情况，就要求，每个回调都要实现Stream接口
    public <T>T pipe(IOCallback pipeCb);

    // end算是pipe触发器
    public void end(IOCallback endCb);

    // 也可以直接手动触发
    public void emit();
}