package com.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import com.common.utils.*;

/**
 * Created by Andy-Super on 2019/4/9.
 */
public class HTTP {
    public static void main(String[] arg){
        new HTTP(8080);
    }

    public HTTP(int port){

        try {
            ServerSocket ss = new ServerSocket(port);
            while(true){
               Socket s = ss.accept();
    
               // s.getInputStream 获取流数据
               // new InputStreamReader 把流数据封装到输入读取器
               // new BufferedReader buffer读取器,将buffer转成字符串 
               BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
               String requestHeader;
               int contentLength = 0;
               while((requestHeader = br.readLine()) != null && !requestHeader.isEmpty()){
                    System.out.println(requestHeader);
                     /**
                     * 获得GET参数
                     */
                    if(requestHeader.startsWith("GET")){
                        int begin = requestHeader.indexOf("/?");
                        int end = requestHeader.indexOf("HTTP/");
                        String condition = "";
                        if(begin > -1){
                            begin = requestHeader.indexOf("/?") + 2;
                            condition = requestHeader.substring(begin,end);
                        }
                        System.out.println("GET参数：" + condition);                    
                    };
                   /**
                     * 获得POST参数
                     * 1.获取请求内容长度
                     */
                    if(requestHeader.startsWith("Content-Length")){
                        int begin = "Content-Length:".length();
                        
                    }
               }


               PrintWriter pw = new PrintWriter(s.getOutputStream());
               pw.println("HTTP/1.1 200 OK");
               pw.println("Content-type:text/html;charset=UTF-8");
               pw.println();    //这个空行必须有，否则客户端无法接收后面的文本
               pw.println(getIndexPage());
               pw.flush();  //刷新流
               s.close();
            }
    
    
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    private String getIndexPage(){
        String htmlString = "";
        try{
            MyFileReader fr = new MyFileReader("src/webContent/","index.html");
            while(fr.hasNextLine()){
                htmlString += fr.readLine().byteToString();
            }
            System.out.println(htmlString);
        }catch(Exception e){
            e.printStackTrace();
        }
        return htmlString;
    }

}
