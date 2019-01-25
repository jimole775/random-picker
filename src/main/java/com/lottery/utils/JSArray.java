package com.lottery.utils;

import java.lang.reflect.Array;
/**
 * Created by Administrator on 2019/1/8.
 */
public class JSArray<T>{
    T[] ts;
    public JSArray(Class type,int size){
        ts = (T[]) Array.newInstance(type,size);
    }
    public T get(int index){
        return ts[index];
    }

    public T[] rep(){
        return ts;
    }

    public void set(int index,T t){
        ts[index] = t;
    }
    public static void main(String[] args){
        JSArray aog2 = new JSArray(Integer.class,10);
        Object[] objs = aog2.rep();
        for(int i = 0; i<10;i++){
            aog2.set(i,i);
            System.out.println(aog2.get(i));
            try{
                Integer[] strs = (Integer[])aog2.rep();
                System.out.println("user Array.newInstance to create generic of array was successful!!!!" + strs[0]);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }


}
