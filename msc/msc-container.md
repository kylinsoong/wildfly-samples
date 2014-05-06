### Quick Link

[The Java® Language Specification](http://docs.oracle.com/javase/specs/jls/se7/html/)

### synchronized

Java 语言提供了很多种多线程之间通信的机制，但最基本的方式是通过 `monitors` 实现的 `synchronization` 机制。Java 中每一个对象都关联一个 `monitor`，任何线程可以对 `monitor` 加锁或解锁，Only one thread at a time may hold a lock on a monitor. Any other threads attempting to lock that monitor are blocked until they can obtain a lock on that monitor. 

#### synchronized 的方法

一个 synchronized 的方法在执行时获取一个 `monitor`。For a class (static) method, the monitor associated with the Class object for the method's class is used. For an instance method, the monitor associated with this (the object for which the method was invoked) is used.

如下为 synchronized 方法的示例

~~~
class Test {
    int count;
    synchronized void bump() {
        count++;
    }
    static int classCount;
    static synchronized void classBump() {
        classCount++;
    }
}
~~~

同样等效的方式如下：

~~~
class BumpTest {
    int count;
    void bump() {
        synchronized (this) { count++; }
    }
    static int classCount;
    static void classBump() {
        try {
            synchronized (Class.forName("BumpTest")) {
                classCount++;
            }
        } catch (ClassNotFoundException e) {}
    }
}
~~~
 
