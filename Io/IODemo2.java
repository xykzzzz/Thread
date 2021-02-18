package Io;

import java.io.*;


public class IODemo2 {
    public static void main(String[] args) {

    }
    private static void copyFile() throws IOException {
        //创建实例BufferInputStream 和 BufferOutputStream需要先创建
        //FileInputStream 和 FileOutputStream
        FileInputStream fileInputStream=new FileInputStream("D:/suibian/1.jpg");
        FileOutputStream fileOutputStream=new FileOutputStream("D:/suibian/2.jpg");
        BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
        byte[]buffer=new byte[1024];
        int len=-1;
        while ((len=bufferedInputStream.read(buffer))!=-1){
            bufferedOutputStream.write(buffer,0,len);
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }
    private static void copyFile2(){
        try(BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream("D:/suibian/1.jpg"));
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream("D:/suibian/2.jpg"))){
            byte[]buffer=new byte[1024];
            int len=-1;
            while ((len=bufferedInputStream.read(buffer))!=-1){
                bufferedOutputStream.write(buffer,0,len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
