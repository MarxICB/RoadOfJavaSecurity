package com.marxicb.advancedclass.a06proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    Object realObj;
    public MyInvocationHandler(final Object realObj) {
        this.realObj=realObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行前...");
        method.invoke(realObj);
        System.out.println("执行后...");
        return null;
    }
}
