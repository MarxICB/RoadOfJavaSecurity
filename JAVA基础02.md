# JAVA基础02

## 泛型

泛型中不能写基本数据类型

指定泛型的具体类型后，传递数据时，可以传入该类型及其子类型

如果不指定则默认为object

java中的泛型是伪泛型，数据类型的统一在编译期间确定，因为object是所有的父类，所以可以理解为存储时按object存，取出时再强制转换，在.class文件时泛型字样已经消失。

```java
public class MyArrayList<E> {
    Object[] obj = new Object[10];
    int size = 0;

    public boolean add(E e) {
        obj[size++] = e;
        return true;
    }
    public E get(int index){
        if(index < 0 || index >= size) return null;
        return (E) obj[index];
    }
}
```

泛型方法

当参数类型不确定的时候

```java
修饰符 <类型> 返回值类型 方法名(类型 变量名){}
```

泛型接口

类implements时确定类型或者延续放置自己的泛型

```java
修饰符 interface 接口名<类型>{}
```

## 泛型的通配符

? extends E:表示可以传递E或者E的所有子类型

? super E:表示可以传递E或者E的所有父类型

```java
void method(ArrayList<? super Fu> list){}
```

## 增强for遍历

### 增强for遍历

增强for底层就是迭代器，为了简化代码而成

```java
for (String s : list){
}
```

### lambda

```java
public class Test {
    public static void main(String[] args) {
        Collection<String> coll=new ArrayList<>();
        coll.add("zs");
        coll.add("ls");
        //匿名内部类
        coll.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        //lambda表达式
        coll.forEach((String s)->{
            System.out.println(s);
        });
        coll.forEach(s->System.out.println(s));
        coll.forEach(System.out::println);
    }
}
```

## 数据结构

![image-20240704103246550](E:\note\java\java基础\JAVA基础02\image-20240704103246550.png)

## Stream

### 初始方法

单列集合 default Stream<E> stream() Collection中的默认方法

双列集合

​	需要间接创建

```java
hm.keySet().stream().forEach(S->sout);
hm.entrySet().stream().forEach(s->sout);
```

数组 public static <T> Stream<T> stream(T[] array) Arrays工具类中的静态方法

```java
Arrays.stream(arr).forEach()
```

零散数据 public static<T> Stream<T> of(T... values) Stream接口中的静态方法

```java
Stream.of(1,2,3).forEach()
```

### 中间方法

```java
Stream<T> filter(Predicate<? super T> predicate)//过滤
Stream<T> limit(long maxsize) //获取前几个元素
Stream<T> skip(long n)//跳过前几个元素
Stream<T> distinct()//元素去重（依赖hashCode和equals方法）
static<T> Stream<T> concat(Stream a,Stream b)//合并a和b两个流为1个流
Stream<R> map(Function<T,R> mapper)//转换流中的数据和类型
```

由于其返回都是Stream且Stream只能使用一次故可以构造链来处理

### 终结方法

```java
void forEach(Consumer action)//遍历
long count()//统计
toArray()//收集流中的数据，放到数组中
collect(Collector collector)//收集流中的数据，放到集合中
```

## 方法引用

把已存在的方法拿来用，当作函数式接口中抽象方法的方法体

### 引用静态方法

格式:类名::静态方法

范例:Integer::parseInt

### 引用成员方法

格式：对象::成员方法

本类：this::方法名

父类:  super::方法名

### 引用构造方法

格式: 类名::new

### 类名引用成员方法

前面的都是抽象方法的参数和引用的一样。这个有区别，在于这个类要是抽象方法的第一个参数，后面的一致。

## 异常

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200716171022101.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3BpcGl6aGVuXw==,size_16,color_FFFFFF,t_70)

Error:系统级别异常

Exception:异常（代表程序可能出现的问题）

Exception下的RuntimeException是运行时异常

其他异常是编译阶段的异常

捕获异常

```java
try{
	可能出现异常的代码;
}catch(异常类名 变量名){
	异常的处理代码;
}
```

当代码出现异常时让程序继续往下执行

1.如果try中没有遇到问题

​	会把try里面所有的代码执行，不执行catch中的

2.如果try中可能会遇到多个问题

​	会写多个catch与之对应

​	父类异常需要写到下面

3.如果try中遇到的问题没有被捕获

​	异常正常报错

4.如果try中遇到问题下面的代码不会再执行

throws:写在方法定义处，表示声明一个异常。告诉调用者，使用本方法可能会有那些异常

throw：写在方法内，结束方法。手动抛出异常对象，交给调用者。方法中下面的代码不会再执行。

throws抛出的都是运行时异常

## FILE

File对象表示路径，可以是文件也可以是文件夹。

这个路径可以是真实存在的也可以是不存在的

### 三种构造方法

```java
1.根据字符串表示路径
String str="C:\\Users\\marxicb\\Desktop\\a.txt";
File f1= new File(str);
2.根据父级路径和子级路径
String parent="C:\\Users\\marxicb\\Desktop";
String child="a.txt";
File f2= new File(parent,child);
3.把一个File表示的路径和String路径拼接起来
File parent2 = new File("C:\\Users\\marxicb\\Desktop");
File f3= new File(parent2,child);
```

![85cd92c50b95304fd57aada676c36c0](E:\note\java\java基础\java基础02\85cd92c50b95304fd57aada676c36c0.jpg)

![cc0e6a824dff8736617e91be7b166e5](./java基础02/cc0e6a824dff8736617e91be7b166e5.jpg)