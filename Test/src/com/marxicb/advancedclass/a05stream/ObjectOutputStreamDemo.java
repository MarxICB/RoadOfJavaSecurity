package com.marxicb.advancedclass.a05stream;
import com.marxicb.advancedclass.a01staticdemo1.Student;

import java.io.*;

public class ObjectOutputStreamDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student stu=new Student("zs",20,"男");
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("1.txt"));
        oos.writeObject(stu);
        oos.close();
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("1.txt"));
        Student stu2=(Student)ois.readObject();//默认返回Object,故需要进行强制转换。
        ois.close();
    }
}
