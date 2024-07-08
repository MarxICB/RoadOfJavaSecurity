package com.marxicb.advancedclass.a06proxy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Mapper mapper=new MapperImp();
        MyInvocationHandler invocationHandler=new MyInvocationHandler(mapper);
        Mapper proxyInstance=(Mapper) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), new Class[]{Mapper.class}, invocationHandler);
        proxyInstance.insert();

//        Class<?> proxyClass = proxyInstance.getClass();
//        String className = proxyClass.getName();
//        String classFileName = className.replace('.', '/') + ".class";
//        System.out.println(classFileName);
//        InputStream classInputStream = proxyClass.getClassLoader().getResourceAsStream(classFileName);
//        try (OutputStream outputStream = new FileOutputStream("ProxyClass.class")) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = classInputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
