package com.lottery.experiment;

import com.lottery.product.*;
import com.common.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.concurrent.*;
import com.common.callbacks.*;
/**
 * Created by Andy-Super on 2019/3/14.
 */
public class CreateRollRate {

    private ArrayList<RollThread> natureThreadArray = new ArrayList<RollThread>();
    private ArrayList<RollThread> simulateThreadArray = new ArrayList<RollThread>();
    private JSArray<String> allATerms = new JSArray<String>(String.class);
       public void run(){
           allATerms = getAllTerm();

           ExecutorService executor = Executors.newFixedThreadPool(allATerms.getSize() * 2);
           natureRollRate();
           for (RollThread threadItem:natureThreadArray) {
//               threadItem.start();
               executor.submit(threadItem);
           }
           simulateRollRate();
           for (RollThread threadItem:simulateThreadArray) {
//               threadItem.start();
               executor.submit(threadItem);
           }
           executor.shutdown();
       }


       public void natureRollRate(){

            int maxLen = allATerms.getSize();
            int i;
            for(i = 0;i<maxLen;i++){
                String aLine = allATerms.get(i);
                Integer[] designatedTerm = analysisATerm(aLine);
                RollThread rt = new RollThread(designatedTerm);
                natureThreadArray.add(rt);
            }

       }

       public void simulateRollRate(){

           VerifyInvalidTerm vit = new VerifyInvalidTerm();
           int maxLen = allATerms.getSize();
           int i;
           for(i = 0;i<maxLen;i++){
               String aLine = allATerms.get(i);
               Integer[] designatedTerm = analysisATerm(aLine);
               if(vit.isInValid(designatedTerm) || vit.coupleLess(designatedTerm)){
                   continue;
               }
               RollThread rt = new RollThread(designatedTerm);
               simulateThreadArray.add(rt);
           }
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
           MyFileReader fr = new MyFileReader("src/main/db/base/","amount.txt");
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

           // 0x类型的数字转成x
           aLine = aLine.replace(",0",",");
           if(aLine.indexOf("0") == 0){
               aLine = aLine.substring(1,aLine.length());
           }

           return aLine;
       }
}
