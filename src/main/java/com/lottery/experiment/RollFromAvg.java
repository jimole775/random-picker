package com.lottery.experiment;

import com.lottery.product.Product;
import com.common.utils.AwardLevel;
import com.common.utils.MyFileWriter;
import com.common.utils.JSArray;
import com.common.callbacks.*;

/**
 * Created by Andy-Super on 2019/2/19.
 *
 */
public class RollFromAvg {

    private Integer[] simp;
    private Integer awardLevel = 0;
    public RollFromAvg(Integer[] _simp){
        simp = _simp;
    }

    public Integer run(){
        String filePath = "src/main/db/experiment/";
        MyFileWriter fw = new MyFileWriter(filePath,"product.log");
        JSArray<String> terms = new JSArray<String>(String.class);
        awardLevel = 0;
        Product r = new Product();
        for(int i = 0;i<=600000;i++){
            JSArray<Integer> aTerm = new JSArray<Integer>(r.productATerm());

            if(i>=53310 && i<= 53320 || i>=222210 && i<=222260 || i>=523900 && i<=523940){
                terms.push(aTerm.join("-"));

                fw.writeLine(aTerm.join("-"));
            }

        }
        fw.end();


        terms.forEach(new JSArrayCallback() {
            @Override
            public void entries(Object item, Integer index) {
                AwardLevel al = new AwardLevel(simp);
                String[] targetStr = item.toString().split("-");
                JSArray<Integer> targetArr = new JSArray<Integer>(Integer.class);
                for (String str:targetStr) {
                    targetArr.push(Integer.valueOf(str));
                }
                if(al.match(targetArr) > 0 && al.match(targetArr) <= 3){
                    awardLevel = al.match(targetArr);
//                    System.out.println("中奖等级：" + awardLevel);
//                    System.out.println("中奖号码：" + targetArr.join("-"));
                }
            }
        });

        return awardLevel;
    }
}
