package Io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Copy {
    public static void main(String[] args) throws IOException {
        copyFile("D:/suibian/1.jpg","D:/suibian/3.jpg");
    }

    private static void copyFile(String srcPath, String destPath)  {
        FileInputStream fileInputStream=null;
        FileOutputStream fileOutputStream=null;
        try {
          fileInputStream=new FileInputStream(srcPath);
            fileOutputStream=new FileOutputStream(destPath);
         byte[] buffer=new byte[1024];
         int len = -1;
         while ((len=fileInputStream.read(buffer))!=-1){
             fileOutputStream.write(buffer,0,len);
         }
     }catch ( IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
               if(fileOutputStream!=null) {
                   fileOutputStream.close();
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void copyFile(){
        //try括号里面会自动关闭资源（调用close方法） 因为实现了Closable接口
        try(FileInputStream fileInputStream=new FileInputStream("D:/suibian/1.jpg");
            FileOutputStream fileOutputStream=new FileOutputStream("D:/suibian/2.jpg")){
            byte[]buff=new byte[1024];
            int len=-1;
            while ((len=fileInputStream.read(buff))!=-1){
                fileOutputStream.write(buff,0,len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
