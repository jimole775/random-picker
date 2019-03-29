package com.lottery.experiment;

import com.lottery.product.*;
import com.lottery.utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import com.lottery.callbacks.*;
/**
 * Created by Andy-Super on 2019/3/14.
 */
public class CreateRollRate {

    private JSArray<String> allATerms = new JSArray<String>(String.class);
       public void run(){
           RollThread rt1 = new RollThread(
               new ThreadCallback() {
                   @Override
                   public void entries() {
                       natureRollRate();
                   }
               }
           );
           RollThread rt2 = new RollThread(
               new ThreadCallback() {
                   @Override
                   public void entries() {
                       simulateRollRate();
                   }
               }
           );
           allATerms = getAllTerm();
           rt1.start();
           rt2.start();
       }

       public void natureRollRate(){
           Product pdt = new Product();
           RewardRecords rr = new RewardRecords();
            long startTime = new Date().getTime();
            int maxLen = allATerms.getSize();
            int i;
            for(i = 0;i<maxLen;i++){
                String aLine = allATerms.get(i);
                Integer[] designatedTerm = analysisATerm(aLine);
                rr.defineAwardTarget(designatedTerm);
                rr.openInputStream("src/main/db/temp/natureRoll/");
                int peerTermRollTimes = 1000 * 1000 * 1000;
                int j;
                for(j = 0;j < peerTermRollTimes;j ++){
                    Integer[] aTerm = pdt.productATerm();
                    rr.record(aTerm,j);
                }
                rr.end();
            }

           System.out.println("自然数摇取消耗时长：" + (new Date().getTime() - startTime));
       }

       public void simulateRollRate(){
           Product pdt = new Product();
           RewardRecords rr = new RewardRecords();
           VerifyInvalidTerm vit = new VerifyInvalidTerm();
            long startTime = new Date().getTime();

           int maxLen = allATerms.getSize();
           int i;
           for(i = 0;i<maxLen;i++){
               String aLine = allATerms.get(i);
               Integer[] designatedTerm = analysisATerm(aLine);
               if(vit.isInValid(designatedTerm) || vit.coupleLess(designatedTerm)){
                   continue;
               }
               rr.defineAwardTarget(designatedTerm);
               rr.openInputStream("src/main/db/temp/simulateRoll/");
               int peerTermRollTimes = 1000 * 1000 * 1000;
               int j;
               for(j = 0;j < peerTermRollTimes;j ++){
                   Integer[] aTerm = pdt.productATerm();
                   if(vit.isInValid(aTerm) || vit.coupleLess(aTerm)){
                       continue;
                   }
                   rr.record(aTerm,j);
               }
               rr.end();
           }
           System.out.println("人工数消耗时长：" + (new Date().getTime() - startTime));
       }

       private Integer[] analysisATerm(String aLine){
           aLine = queryLineString(aLine);
           String[] temp = aLine.split(",");
           Integer[] designatedTerm = new Integer[7];
           for (int i = 0; i<temp.length;i++) {
               designatedTerm[i] = Integer.parseInt(temp[i]);
           }
           return designatedTerm;
       }

       private JSArray<String> getAllTerm(){
           FileReader fr = new FileReader("src/main/db/base/","amount.txt");
           JSArray<String> allATerms = new JSArray<String>(String.class);
            while(fr.hasNextLine()) {
               String aLine = fr.readLine().byteToString();
                allATerms.push(queryLineString(aLine));
           }
           return allATerms;
       }

       private String queryLineString(String aLine){
           aLine = aLine.replace("#","");
           aLine = aLine.replace("\n","");
           aLine = aLine.replace("\r","");
           aLine = aLine.replace(",0",",");
           if(aLine.indexOf("0") == 0){
               aLine = aLine.substring(1,aLine.length());
           }
           return aLine;
       }
}
