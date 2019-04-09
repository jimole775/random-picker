package com.common.utils;

import java.lang.reflect.Array;
import com.common.callbacks.*;
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

        extendSize = extendSize > 0 ? extendSize : 1;

        T[] temp = createNewArr(arrInstance.length + extendSize);

        System.arraycopy(arrInstance,0,temp,extendSize,arrInstance.length);

        arrInstance = temp;

        frontEmpty += extendSize;

        insCapacity += extendSize;
    }

    private void extendBehindCapacity(){
        int extendSize = arrInstance.length>>1;
        extendSize = extendSize > 0 ? extendSize : 1;
        T[] temp = createNewArr(insCapacity + extendSize);

        System.arraycopy(arrInstance,0,temp,0,insCapacity);

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
        insCount = insCapacity = size;
        frontEmpty = 0;
        behindEmpty = 0;
    }

    public JSArray(Class type){
        curInsType = type;
        arrInstance = createNewArr(0);
        insCapacity = 0;
        frontEmpty = 0;
        behindEmpty = 0;
        insCount = 0;
    }

    public JSArray(Object[] simple){
        arrInstance = clearArrayEmpty((T[])simple);
        frontEmpty = 0;
        behindEmpty = 0;
        insCapacity = insCount = arrInstance.length;
        curInsType = simple.getClass();
    }

    private T[] clearArrayEmpty(T[] irregularArr){
        int loopTimes = irregularArr.length;
        int _totalSum = 0;
        Class _curInsType = null; // 这里主要为了兼容 public JSArray(Object[] simple) 方法；
        while(loopTimes -- > 0){
            if(irregularArr[loopTimes] != null){
                _totalSum ++;
                if(_curInsType == null) _curInsType = irregularArr[loopTimes].getClass();
            }
        }

        if(_curInsType == null) {
            return (T[]) Array.newInstance(Object.class,0);
        }
        int emptyCount = 0;
        T[] newArr = (T[]) Array.newInstance(_curInsType,_totalSum);
        for(int i=0;i<_totalSum;i++){
            int irregularIndex = i + emptyCount;
            if(irregularArr[irregularIndex] == null) {
                emptyCount ++;
                i--;
            }else{
                newArr[i] = irregularArr[irregularIndex];
            }
        }
        return newArr;
    }

    public void clearEmpty(){
        T[] newArr = createNewArr(insCount);
        for(int i=0;i<insCount;i++){
            int insIndex = i + frontEmpty;
            if(arrInstance[insIndex] != null) {
                newArr[i] = arrInstance[insIndex];
            }else{
                i--;
            }
        }
        arrInstance = newArr;
        frontEmpty = 0;
        behindEmpty = 0;
        insCapacity = insCount;
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

    // 复制指定位置的一串连续的元素
    public T[] slice(int delStartIndex,int count){

        if(delStartIndex + count > insCapacity){
            return createNewArr(count);
        }

        T[] delItem = createNewArr(count);
        System.arraycopy(arrInstance,frontEmpty + delStartIndex,delItem,0,count);
        return delItem;
    }

    // 修改原数组，返回被剔除的数组
    public T[] splice(int delStartIndex,int count){
        T[] delItem = slice(delStartIndex,count);
        insCapacity -= count;
        T[] temp = createNewArr(insCapacity);

        // 以delStartIndex为中心，把原数组切为两段，最后再拼接起来
        System.arraycopy(arrInstance,frontEmpty,temp,frontEmpty,delStartIndex);
        System.arraycopy(arrInstance,frontEmpty + (delStartIndex + count),temp,frontEmpty + delStartIndex,arrInstance.length - frontEmpty - delStartIndex - count);

        insCount -= count;
        arrInstance = temp;
        return delItem;
    }

    private T[] splice(T[] arr,int delStartIndex,int count){
        T[] delItem = slice(delStartIndex,count);
        T[] temp = createNewArr(arr.length - 1);
        arr = clearArrayEmpty(arr);
        // 以delStartIndex为中心，把原数组切为两段，最后再拼接起来
        System.arraycopy(arr,0,temp,0,delStartIndex);
        System.arraycopy(arr,delStartIndex + count,temp,delStartIndex,arr.length - delStartIndex - count);
        arr = temp;
        return delItem;
    }

    public JSArray sort(){
        clearEmpty();
        arrInstance = quickSort(arrInstance,0,insCount - 1);
        return this;
    }

    private T[] quickSort(T[] arrIns,int left,int right){
        if(left > right){
            return arrIns;
        }
        int partitionIndex = partition(arrIns, left, right);
        quickSort(arrIns,0,partitionIndex - 1);
        quickSort(arrIns,partitionIndex + 1,right);

        return arrIns;
    }

    private int partition(T[] arr, int left, int right){
        T partitionItem = arr[left];
        while(left < right){

            while(right > left && (Integer)arr[right] >= (Integer)partitionItem){
                right --;
            }
            arr[left] = arr[right];
            while(left < right && (Integer)arr[left] <= (Integer)partitionItem){
                left ++;
            }
            arr[right] = arr[left];

        }
        arr[right] = partitionItem;

        return right;
    }
    private int popSort(T[] arr, int left, int right){
        T partitionItem = arr[left];
        while(left < right){
            T temp = partitionItem;
            if((Integer)arr[right] <= (Integer)partitionItem){
                temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
            right --;
            left ++;
            if((Integer)arr[left] >= (Integer)partitionItem){
                temp = arr[right];
                arr[right] = arr[left];
                arr[left] = temp;
            }
        }

        return right;
    }

    private void insertSort(){

    }

    private T[] merge(T[] ...arr){
        int argsLen = arr.length;
        int finalCapacity = 0;
        for(int i = 0; i<argsLen;i++){
            finalCapacity += arr[i].length;
        }
        T[] result = createNewArr(finalCapacity);
        int capacityStep = 0;
        for (T[] _arr:arr) {
            System.arraycopy(_arr,0,result,capacityStep,_arr.length);
            capacityStep += _arr.length;
        }

        return result;
    }


    public JSArray concat(JSArray otherObj){
        otherObj.clearEmpty();
        if(otherObj.getSize() > 0 && otherObj.get(0).getClass() == curInsType){
            int extendSize = otherObj.getSize();
            for(int i = 0;i<extendSize;i++){
                if(otherObj.get(i) != null){
                    push((T)otherObj.get(i));
                }
            }
        }
        return new JSArray(arrInstance);
    }

    public T[] concat(T[] otherArr){
        otherArr = clearArrayEmpty(otherArr);
        int extendSize = otherArr.length;
        for(int i = 0;i<extendSize;i++){
            if(otherArr[i] != null){
                push(otherArr[i]);
            }
        }
        return arrInstance;
    }

    public void forEach(JSArrayCallback cb){
        for(int i = 0;i < insCount;i ++){
            T item = get(i);
            cb.entries(item,i);
        }
    }

    public boolean include(T target){
        int index = getIndex(target);
        return index != -1;
    }

    public int getIndex(T target){
        int index = -1;
        for(int i=0;i<insCount;i++){
            int insIndex = i + frontEmpty;
            if(arrInstance[insIndex] != null && arrInstance[insIndex] == target && target.getClass() == curInsType) {
                index = i;
                break;
            }
        }
        return index;
    }

    public T get(int index){
        int finalIndex = frontEmpty + index;
        return arrInstance[finalIndex];
    }

    public void set(int index,T target){
        if(frontEmpty + index >= insCapacity){
            extendBehindCapacity();
        }

        if(index > insCount){
            behindEmpty --;
        }

        if(frontEmpty + index > insCount){
            insCount ++;
        }

        arrInstance[frontEmpty + index] = target;
    }

    public T[] getDataSet(){
        return arrInstance;
    }

    public String byteToString(){

        byte[] data = new byte[insCount];

        for(int i=0;i<insCount;i++){
            if(arrInstance[i + frontEmpty] != null)data[i] = (Byte) arrInstance[i + frontEmpty];
        }

        return new String(data);
    }

    public int getSize(){
        return insCount;
    }

    public String toString(){
        return join("");
    }

    public void supplement(T simple){
        for(int i = 0;i < insCapacity;i ++){
            if(arrInstance[i] == null){

                insCount++;
                arrInstance[i] = simple;

            }
        }

        frontEmpty = 0;
        behindEmpty = 0;
    }
    public void fullFill(T simple){
        for(int i = 0;i < insCapacity;i ++){
            arrInstance[i] = simple;
            insCount++;
        }
        frontEmpty = 0;
        behindEmpty = 0;
    }

    public void reset(){
        arrInstance = createNewArr(0);
        insCount = insCapacity = frontEmpty = behindEmpty = 0;
    }

    public String join(String markSign){
        String spillInOne = "";
        for(int i = frontEmpty;i < insCapacity - behindEmpty;i ++){
            if(arrInstance[i] != null)spillInOne += (arrInstance[i].toString() + markSign);
        }
        if(!markSign.equals("") && !spillInOne.equals("")){
            spillInOne = spillInOne.substring(0,spillInOne.length() - 1);
        }
        return spillInOne;
    }

    public JSArray clone(){
        clearEmpty();
        return new JSArray(arrInstance);
    }

    public static void main(String[] args){

        JSArray arr =  new JSArray(Integer.class);
        Integer[] base = {1,2,3,4,5};
        JSArray arr1 =  new JSArray(base);
        arr.push(10);
        arr.push(11);
        arr.push(13);
        arr.shift(15);
        arr.shift(17);
        arr.shift(19);
        arr.concat(arr1);

        arr.sort();
        JSArray arr2 = arr.clone();
//        arr.set(0,0);

        /*arr.forEach(new JSArrayCallback(){
            @Override
            public void entries(Object item, Integer index) {
                System.out.println(item + " in " + index);
            }
        });*/

        System.out.println(arr2.join("-"));
    }

}
