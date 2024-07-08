# JAVA基础04

## 反射

Java 的**反射机制**是指在运行状态中，对于任意一个类都能够知道这个类所有的属性和方法； 并且对于任意一个对象，都能够调用它的任意一个方法；这种动态获取信息以及动态调用对象方法的功能成为 Java 语言的反射机制。

首先先理清几个概念

类和对象,这里的的对象就是平常说的类的实例

类对象，指的是类Class的对象，这个对象描述了其他类。Class是对所有类的一种抽象(不是指抽象类)。

类似于上面的做法，又可以对构造器，属性，方法抽象出类，比如一个Field的对象表示一个类的属性。

反射机制相关操作一般位于java.lang.reflect包中。

而java反射机制组成需要重点注意以下的类：

java.lang.Class：类对象;

java.lang.reflect.Constructor：类的构造器对象;

java.lang.reflect.Field：类的属性对象;

java.lang.reflect.Method：类的方法对象;

我们都知道java运行会把每一个类编译成.class，而Class的对象就可以指代这个.class

### 一些方法

#### 获取类对象

1.利用实例化对象反射获得其类

```java
TestReflection testReflection = new TestReflection();
Class class3 = testReflection.getClass();
```

2.使用类的.class方法

```java
Class class2 = TestReflection.class;
```

3.使用Class.forName

```java
Class class1 = Class.forName("reflection.TestReflection");
```

#### 获取成员变量Filed

获取成员变量Field位于 `java.lang.reflect.Field` 包中

```java
Field[] getFields() ：获取所有 public 修饰的成员变量

Field[] getDeclaredFields() 获取所有的成员变量，不考虑修饰符

Field getField(String name) 获取指定名称的 public 修饰的成员变量

Field getDeclaredField(String name) 获取指定的成员变量
```

#### 获取成员方法Method

```java
Method getMethod(String name, 类<?>... parameterTypes) //返回该类所声明的public方法

Method getDeclaredMethod(String name, 类<?>... parameterTypes) //返回该类所声明的所有方法

//第一个参数获取该方法的名字，第二个参数获取标识该方法的参数类型

Method[] getMethods() //获取所有的public方法，包括类自身声明的public方法，父类中的public方法、实现的接口方法

Method[] getDeclaredMethods() // 获取该类中的所有方法
```

#### 获取构造函数Constructor

```java
Constructor<?>[] getConstructors() ：只返回public构造函数

Constructor<?>[] getDeclaredConstructors() ：返回所有构造函数

Constructor<> getConstructor(类<?>... parameterTypes) : 匹配和参数配型相符的public构造函数

Constructor<> getDeclaredConstructor(类<?>... parameterTypes) ： 匹配和参数配型相符的构造函数
```

#### 反射创建对象

```java
Class c = Class.forName("类的名称"); // 创建Class对象
Object m1 =  c.newInstance(); // 创建类对象
```

#### 反射执行方法

```java
public Object invoke(Object obj, Object... args)
```

第一个参数为类的实例，第二个参数为相应函数中的参数

obj：从中调用底层方法的对象，必须是实例化对象

args： 用于方法的调用，是一个 object 的数组，参数有可能是多个

但需要注意的是，invoke 方法第一个参数并不是固定的：

**如果调用这个方法是普通方法，第一个参数就是类对象；**

**如果调用这个方法是静态方法，第一个参数就是类；**

```java
package src;  
  
import java.lang.reflect.Method;  
  
public class ReflectionTest04 {  
    public static void main(String[] args) throws Exception{  
        Class c1 = Class.forName("src.Person");  
 Object m = c1.newInstance();  
 Method method = c1.getMethod("reflect");  
 method.invoke(m);  
 }  
}
```

## 动态代理

例子

```java
package com.marxicb.advancedclass.a06proxy;

public interface Mapper {
    void insert();
}
```

```java
package com.marxicb.advancedclass.a06proxy;

public class MapperImp implements Mapper{
    public void insert(){
        System.out.println("真实类执行逻辑");

    }
}
```

```java
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
```

```java
package com.marxicb.advancedclass.a06proxy;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Mapper mapper=new MapperImp();
        MyInvocationHandler invocationHandler=new MyInvocationHandler(mapper);
        Mapper proxyInstance=(Mapper) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), new Class[]{Mapper.class}, invocationHandler);
        proxyInstance.insert();
    }
}
```

Mapper是被代理的接口，MapperImp是被代理类，MyInvocationHandler实现了InvocationHandler接口的invoke方法

首先是invoke方法做了什么

proxy接收了代理类实例，method是传入的被代理的方法，args调用时的参数

method.invoke(realObj);使用反射来执行被代理类对象的方法



Proxy.newProxyInstance返回代理类对象，分别传入类加载器，被代理接口，invocationHandler实现对象

```java
    @CallerSensitive
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h) {
        Objects.requireNonNull(h);

        @SuppressWarnings("removal")
        final Class<?> caller = System.getSecurityManager() == null
                                    ? null
                                    : Reflection.getCallerClass();

        /*
         * Look up or generate the designated proxy class and its constructor.
         */
        Constructor<?> cons = getProxyConstructor(caller, loader, interfaces);

        return newProxyInstance(caller, cons, h);
    }
```

其中重要的有两个重要函数

getProxyConstructor生成代理类的构造方法，然后newProxyInstance把构造方法传入生成一个代理类实例。

我们可以看到getProxyConstructor传入了类加载器和接口，通过该方法动态加载一个具有上述接口的类

只有实例才会绑定这个InvocationHandler



这里举个其他地方找到生成代理类的例子

```java
import com.gwf.jdkproxy.ProductServiceImpl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

// 继承了Proxy类
public final class $Proxy0 extends Proxy implements ProductServiceImpl {
    private static Method m1;
    private static Method m8;
    private static Method m2;
    private static Method m3;
    private static Method m5;
    private static Method m4;
    private static Method m7;
    private static Method m9;
    private static Method m0;
    private static Method m6;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

....
....

/**
* 这里是代理类实现的被代理对象的接口的相同方法
*/
    public final void addProduct(String var1) throws  {
        try {
            // super.h 对应的是父类的h变量，他就是Proxy.nexInstance方法中的InvocationHandler参数
           // 所以这里实际上就是使用了我们自己写的InvocationHandler实现类的invoke方法
            super.h.invoke(this, m3, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

   

    public final Class getClass() throws  {
        try {
            return (Class)super.h.invoke(this, m7, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

....
....
// 在静态构造块中，代理类通过反射获取了被代理类的详细信息，比如各种方法
    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m8 = Class.forName("com.gwf.jdkproxy.ProductServiceImpl").getMethod("notify");
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m3 = Class.forName("com.gwf.jdkproxy.ProductServiceImpl").getMethod("addProduct", Class.forName("java.lang.String"));
            m5 = Class.forName("com.gwf.jdkproxy.ProductServiceImpl").getMethod("wait", Long.TYPE);
            m4 = Class.forName("com.gwf.jdkproxy.ProductServiceImpl").getMethod("wait", Long.TYPE, Integer.TYPE);
            m7 = Class.forName("com.gwf.jdkproxy.ProductServiceImpl").getMethod("getClass");
            m9 = Class.forName("com.gwf.jdkproxy.ProductServiceImpl").getMethod("notifyAll");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
            m6 = Class.forName("com.gwf.jdkproxy.ProductServiceImpl").getMethod("wait");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
```



类加载器就是加载类的对象，其负责加载各种类

http://t.csdnimg.cn/9NaoV
