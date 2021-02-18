package Io;

import java.io.File;
import java.io.IOException;

public class IODemo1 {
    public static void main(String[] args) throws IOException {
        File file=new File("d:/test.txt");
        file.createNewFile();
        System.out.println(file.exists());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        listAllFiles(file);

    }
    public static void listAllFiles(File f){
        if(f.isDirectory()){
            File[] files = f.listFiles();
            for (File file:files) {
                listAllFiles(file);
            }
        }else {
            System.out.println(f);
        }
    }
}
