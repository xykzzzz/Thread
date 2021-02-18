package Io;

import java.io.*;
import java.util.Arrays;

public class ZIO {
    /**
     *文件输入字节流
     */
/*    public static void main(String[] args) throws IOException {
        File file=new File("D:\\suibian\\1.jpg");
        //文件输入字节流
        FileInputStream fileInputStream=new FileInputStream(file);
        int len=-1;
        byte[] buffer=new byte[1024];
       while ((len=fileInputStream.read(buffer))!=-1){
           String s = new String(buffer,0,len);
           System.out.println(s);
       }
       //输入输出流使用完要关闭,反向关闭
    }*/

    /**
     *文件输入字符流
     */
/*    public static void main(String[] args) throws IOException {
    File file=new File("D:\\test.txt");
    FileReader fileReader=new FileReader(file);
    char[] buffer=new char[1024];
    int len=-1;
    while ((len=fileReader.read(buffer))!=-1){
        String s = new String(buffer,0,len);
        System.out.println(s);
    }
}*/
    /**
     *文件输入缓冲流:缓冲字节输入，缓冲字符输入
     */
    public static void main(String[] args) throws IOException {
        File file=new File("D:\\test.txt");
        FileInputStream fileInputStream=new FileInputStream(file); //文件字节输入流
        //如果字节流转变为字符流，要使用转化类，并且可以定制编码
       InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
       BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String str;
        while((str=bufferedReader.readLine()) != null){
            System.out.println(str);
        }
        //释放资源：反向释放
        bufferedReader.close();
        fileInputStream.close();
        inputStreamReader.close();
    }
}
