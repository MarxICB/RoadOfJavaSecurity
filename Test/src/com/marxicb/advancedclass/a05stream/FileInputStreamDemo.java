package com.marxicb.advancedclass.a05stream;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputStreamDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("1.txt");
        int b;
        while((b=fis.read())!=-1){
            System.out.println((char)b);
        }
        fis.close();
    }
}
