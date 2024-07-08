package com.marxicb.advancedclass.a05stream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("1.txt");
        String str="123\n";
        fos.write(str.getBytes());
        fos.close();
    }
}
