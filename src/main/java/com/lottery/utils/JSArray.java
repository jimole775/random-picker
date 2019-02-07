package com.lottery.utils;

import java.lang.reflect.Array;

/**
 * Created by Administrator on 2019/1/8.
 */
public class JSArray<T>{

    private Class curInsType;
    private T[] arrInstance;
    private int insCount = 0;
    private int insCapacity = 0;
    private int frontEmpty = 0;
    private int behindEmpty = 0;

    private void extendFrontCapacity(){
        int extendSize = arrInstance.length>>1;

        T[] temp = createNewArr(arrInstance.length + extendSize);

        System.arraycopy(arrInstance,0,temp,extendSize,arrInstance.length);

        arrInstance = temp;

        frontEmpty += extendSize;

        insCapacity += extendSize;
    }

    private void extendBehindCapacity(){
        int extendSize = arrInstance.length>>1;

        T[] temp = createNewArr(arrInstance.length + extendSize);

        System.arraycopy(arrInstance,0,temp,0,arrInstance.length);

        arrInstance = temp;

        behindEmpty += extendSize;

        insCapacity += extendSize;
    }

    private T[] createNewArr(int size){
        return (T[]) Array.newInstance(curInsType,size);
    }

    public JSArray(Class type,int size){
        curInsType = type;
        arrInstance = createNewArr(size);
        insCapacity = size;
    }

    public JSArray(Class type){
        curInsType = type;
        arrInstance = createNewArr(10);
        insCapacity = 10;
    }

    public JSArray(Object[] simple){
        curInsType = simple[0].getClass();
        arrInstance = (T[])simple;
        insCount = insCapacity = arrInstance.length;
    }

    public void push(T newItem){
        if(behindEmpty == 0){
            extendBehindCapacity();
        }

        arrInstance[insCount + frontEmpty] = newItem;
        insCount ++;
        behindEmpty --;
    }

    // 从头部插入一位
    public void shift(T newItem){
        if(frontEmpty == 0){
            extendFrontCapacity();
        }
        arrInstance[frontEmpty - 1] = newItem;
        insCount ++;
        frontEmpty --;
    }

    // 从头部剔除一位
    public T unshift(){
        T[] delItem = createNewArr(1);
        System.arraycopy(arrInstance,frontEmpty,delItem,0,1);
        arrInstance[frontEmpty] = null;
        frontEmpty ++;
        insCount --;
        return delItem[0];
    }

    // 从后面剔除一位
    public T pop(){
        T[] delItem = createNewArr(1);
        System.arraycopy(arrInstance,frontEmpty + insCount,delItem,0,1);
        arrInstance[frontEmpty + insCount] = null;
        behindEmpty ++;
        insCount --;
        return delItem[0];
    }

    // 从指定位置剔除一个，返回指定得元素
    public T[] slice(int delStartIndex,int count){

        if(delStartIndex + count > insCapacity){
            return createNewArr(count);
        }

        T[] delItem = createNewArr(count);
        System.arraycopy(arrInstance,frontEmpty + delStartIndex,delItem,0,count);

        insCapacity -= count;
        insCount -= count;

        T[] temp = createNewArr(insCapacity);
        System.arraycopy(arrInstance,frontEmpty,temp,frontEmpty,delStartIndex);
        System.arraycopy(arrInstance,frontEmpty + delStartIndex + count,temp,frontEmpty + delStartIndex,insCount - delStartIndex - count);

        arrInstance = temp;
        return delItem;
    }

    // 和slice一样，不过这个是返回原数组
    public T[] splice(int delStartIndex,int count){
        slice(delStartIndex,count);
        return arrInstance;
    }

    public void concat(JSArray otherObj){
        if(otherObj.get(0).getClass() != curInsType){

        }else{
            Object[] dataSet = otherObj.getDataSet();
            int extendSize = dataSet.length;
            for(int i = 0;i<extendSize;i++){
                if(dataSet[i] != null){
                    push((T)dataSet[i]);
                }
            }
        }
    }

    public void concat(T[] otherArr){
        if(otherArr[0].getClass() != curInsType){

        }else{
            int extendSize = otherArr.length;
            for(int i = 0;i<extendSize;i++){
                if(otherArr[i] != null){
                    push(otherArr[i]);
                }
            }
        }
    }

    public void forEach(JSArrayCallback cb){
        for(int i = 0;i < insCount;i ++){
            T item = get(i);
            cb.entries(item,i);
        }
    }

    public int getIndex(){
        return 0;
    }

    public T get(int index){
        return arrInstance[frontEmpty + index];
    }

    public T[] getDataSet(){
        return arrInstance;
    }

    public int getSize(){
        return insCount;
    }

    public String toString(){
        return arrInstance.toString();
    }

    public static void main(String[] args){

        JSArray arr =  new JSArray(Integer.class);
        Object[] base = {1,2,3,4,5};
        JSArray arr1 =  new JSArray(base);
        arr.push(10);
        arr.push(11);
        arr.push(13);
        arr.shift(15);
        arr.shift(17);
        arr.shift(19);
        arr.concat(arr1);
        arr.forEach(new JSArrayCallback(){
            @Override
            public void entries(Object item, Integer index) {
                System.out.println(item + " in " + index);
            }
        });

    }

}
