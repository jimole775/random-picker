package com.lottery.experiment;

import com.lottery.product.*;
import com.lottery.utils.*;
/**
 * Created by Andy-Super on 2019/3/14.
 */
public class CreateRollRate {

       public void run(){
//           natureRollRate();
           simulateRollRate();
       }

       public void natureRollRate(){
           FileReader fr = new FileReader("src/main/db/base/amount.txt");
           Product pdt = new Product();
           RewardRecords rr = new RewardRecords();
           while(fr.hasNextLine()){
               String aLine = fr.readLine().byteToString();
               Integer[] designatedTerm = analysisATerm(aLine);
               rr.defineAwardTarget(designatedTerm);
               rr.openInputStream("src/main/db/natureRoll/");
               int peerTermRollTimes = 100000000;
               while(peerTermRollTimes-- >=0){
                   JSArray aTerm = pdt.productATerm();
                   rr.record(aTerm);
               }
               rr.end();
           }
       }
       public void simulateRollRate(){
           FileReader fr = new FileReader("src/main/db/base/amount.txt");
           Product pdt = new Product();
           RewardRecords rr = new RewardRecords();
           VerifyInvalidTerm vit = new VerifyInvalidTerm();
           Integer[] blackList = {4,8,12,15,16,26};
           vit.definedBlackList(blackList);
           pdt.defineBlackList(blackList);
           Integer[] insertRange = {29,30,31,32,33,34,35};
           pdt.defineInsertRangeList(insertRange);
           while(fr.hasNextLine()){
               String aLine = fr.readLine().byteToString();
               Integer[] designatedTerm = analysisATerm(aLine);
               if(vit.isInValid(designatedTerm)){
                   continue;
               }
               rr.defineAwardTarget(designatedTerm);
               rr.openInputStream("src/main/db/simulateRoll/");
               int peerTermRollTimes = 100000000;
               while(peerTermRollTimes-- >=0){
                   JSArray aTerm = pdt.productATerm();
                   if(vit.isInValid((Integer[]) aTerm.getDataSet())){
                       continue;
                   }
                   rr.record(aTerm);
               }
               rr.end();
           }
       }

       private Integer[] analysisATerm(String aLine){
           aLine = aLine.replace("#","");
           aLine = aLine.replace("\n","");
           aLine = aLine.replace("\r","");
           String[] temp = aLine.split(",");
           Integer[] designatedTerm = new Integer[7];
           for (int i = 0; i<temp.length;i++) {
               designatedTerm[i] = Integer.parseInt(temp[i]);
           }
           return designatedTerm;
       }
}
