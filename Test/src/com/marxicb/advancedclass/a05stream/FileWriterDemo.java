package com.marxicb.advancedclass.a05stream;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterDemo {
    public static void main(String[] args) throws IOException {
        FileWriter fw= new FileWriter("1.txt",true);
        fw.write(1111);
        fw.write("你好");
        char[] chars={'a','b','我'};
        fw.write(chars);
        fw.close();
    }
}
