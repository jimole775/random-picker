package com.lottery.experiment;

import com.lottery.product.Roll;
import com.lottery.utils.AwardLevel;
import com.lottery.utils.FileWriter;
import com.lottery.utils.JSArray;
import com.lottery.utils.JSArrayCallback;

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
        String filePath = "src/main/java/com/lottery/db/experiment/";
        FileWriter fw = new FileWriter(filePath + "product.log");
        JSArray terms = new JSArray(String.class);

        Roll r = new Roll();
        for(int i = 0;i<=600000;i++){
            JSArray aTerm = r.productATerm();

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
                JSArray targetArr = new JSArray(Integer.class);
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
