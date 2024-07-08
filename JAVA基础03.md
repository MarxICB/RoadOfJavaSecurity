# JAVA基础03

## IO流

http://t.csdnimg.cn/9I31M

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200901170510351.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTE1Nzg3MzQ=,size_16,color_FFFFFF,t_70#pic_center)

### 字节流

#### FileOutputStream

```java
public class FileOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("aa\\1.txt",true);
        fos.write(1);
        String str="123\n";
        fos.write(str.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }
}
```

创建对象：可以使用字符串也可以使用File，文件若不存在则会创建（前提父目录存在），默认重写，所以若续写则需要( ,append=True)

写出数据：可以写整数和字节数组

末尾记得关闭通道

#### FileInputStream

```java
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
```

### 字符流

字符流自动处理编码问题，按字符读取并进行编码

字符流默认自带缓冲区，字节流没有

#### FileReader

```java
public class FileReaderDemo {
    public static void main(String[] args) throws IOException {
        FileReader fr=new FileReader("1.txt");
        char[] chars=new char[2];
        int len;
        while((len=fr.read(chars))!=-1){
            System.out.println(new String(chars,0,len));
        }
        fr.close();
    }
}
```

#### FileWriter

```java
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
```

### 高级流

比如FileInputStream，提供缓冲区。对上层基本流进行封装，提供更多的功能。

BufferedReader: readLine()

BufferedWriter: newLine()

#### 序列化流

类需要实现Seriaizable接口,这个接口内无东西，故称为标记型接口

标记trisient的不会序列化

该类是字节流的高级类

```java
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
```

类序列化存在版本号，这个版本号根据类属性等构成，因此若类修改则会报错，所以可以手动自己设定SerialVersionUID

除序列化流还有打印流和压缩流

## 多线程

### 三种实现方式

继承Thread类  编程简单，但是不能继承其他类，无返回

实现Runnable接口 拓展性强，无返回

实现Callable接口 拓展性强，有返回

### 同步代码块

```java
synchronized(锁){
	操作共享数据的代码
}
```

锁默许打开，同一时间只能有一个线程使用，执行完毕线程出来则锁自动打开

范例

```java
package com.marxicb.advancedclass.threaddemo;

public class Mythread extends Thread{
    static int ticket=0;
    public void run(){
        while(true){
            synchronized (Mythread.class){
                //同步代码块
                if(ticket<100){
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    ticket++;
                    System.out.println(getName()+"正在卖第"+ticket+"张票!!！");
                }else{
                    break;
                }
            }
        }
    }
}
```

```java
package com.marxicb.advancedclass.threaddemo;

public class ThreadTest1 {
    public static void main(String[] args) {
        Mythread t1=new Mythread();
        Mythread t2=new Mythread();
        Mythread t3=new Mythread();
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}

```

### 同步方法

就是把synchronized关键字加到方法上

格式:

```java
修饰符 synchronized 返回值类型 方法名(方法参数){...}
```

这种定义不能自己指定锁对象

非静态:this

静态:当前类的字节码文件对象



先举一个实现Runnable接口的例子

```java
package com.marxicb.advancedclass.threaddemo;

public class MyRunnable implements Runnable{
    int ticket=0; //实现runnable不像上面一样用static因为这个对象被复用到线程中了
    public void run(){
        while(true){
            synchronized (Mythread.class){
                //同步代码块
                if(ticket<100){
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    ticket++;
                    System.out.println(Thread.currentThread().getName()+"正在卖第"+ticket+"张票!!！");
                }else{
                    break;
                }
            }
        }
    }
}
```

```java
package com.marxicb.advancedclass.threaddemo;

public class ThreadTest2 {
    public static void main(String[] args) {
        MyRunnable mr=new MyRunnable();
        Thread t1=new Thread(mr);
        Thread t2=new Thread(mr);
        Thread t3=new Thread(mr);
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}
```

下面对其进行改造

```java
package com.marxicb.advancedclass.threaddemo;

public class MyRunnable2 implements Runnable{
    int ticket=0;
    public void run(){
        while(true){
            if(method()) break;
        }
    }
    private synchronized boolean method(){
        if(ticket==100){
            return true;
        }else{
            try {
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            ticket++;
            System.out.println(Thread.currentThread().getName()+"正在卖第"+ticket+"张票!!！");
            return false;
        }
    }
}
```

### lock锁

```java
package com.marxicb.advancedclass.threaddemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mythread2 extends Thread{
    static int ticket=0;
    static Lock lock=new ReentrantLock();
    public void run(){
        while(true){
            lock.lock();
                //同步代码块
            try {
                if(ticket<100){
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    ticket++;
                    System.out.println(getName()+"正在卖第"+ticket+"张票!!！");
                }else{
                    break;
                }
            } finally {
                lock.unlock();
            }

        }
    }
}

```

### 阻塞队列

```java
package com.marxicb.advancedclass.threaddemo;

import java.util.concurrent.ArrayBlockingQueue;

public class Cook extends Thread{
    ArrayBlockingQueue<String> queue;
    public Cook(ArrayBlockingQueue<String> queue){this.queue=queue};
    public void run(){
        while(true){
            try{
                queue.put("面条");
                System.out.println("厨师放了一碗面");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

```

```java
package com.marxicb.advancedclass.threaddemo;

import java.util.concurrent.ArrayBlockingQueue;

public class Foodie extends Thread{
    ArrayBlockingQueue<String> queue;
    public Foodie(ArrayBlockingQueue<String> queue){this.queue=queue};
    public void run(){
        while(true){
            try{
                String food=queue.take();
                System.out.println(food);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

```

```java
package com.marxicb.advancedclass.threaddemo;

import java.util.concurrent.ArrayBlockingQueue;

public class ThreadDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue=new ArrayBlockingQueue<>(1);
        Cook c=new Cook(queue);
        Foodie f= new Foodie(queue);
        c.start();
        f.start();
    }
}

```

### 线程池

提交任务时池子会创建新的线程对象，任务执行完毕，线程归还池子，下回再次提交任务时不需要创建新的进程，直接复用已有的线程即可，如果提交任务时池子里没有空闲线程则排队等待

```java
public static ExecutorService newCachedThreadPool()//创建一个没有上限的线程池
public static ExecutorService newFixedThreadPool()//创建一个有上限的池子，他们都是Executor工具类的方法
ExecutorService pool1=Executors.newCachedThreadPool();
pool1.submit(new MyRunnable());
pool1.shutdown();
```

