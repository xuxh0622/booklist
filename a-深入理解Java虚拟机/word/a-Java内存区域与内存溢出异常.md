## JVM内存



```java
/**
 * 内存介绍
 * @author xuxhm
 */
public class JavaVMIntro {
	public static String staticObjVar;
	
	/**
	 * 运行时变量线程共享：字符串（"staticObjVal"）、字面量（下面的3）、类名（JavaVMIntro）、方法名：
	 * 方法区共享：类信息、常量、静态变量（staticObjVar、staticObjVal、objVal）、类方法（main）
	 */
	public static final String staticObjVal = "staticObjVal";

	public String objVar;

	public final int objVal = 3;
	
	/**
	 * 栈内存线程独有：虚拟机栈、程序计数器、本地方法栈
	 * 一个程序计数器（当前执行字节码地址）
	 * 一个线程有一个虚拟机栈
	 *    一个虚拟机栈有多个栈帧，一个栈帧对应一个方法
	 *       一个方法含：当前对象(this)、参数(i)、成员变量(a)、操作数栈（寄存器）、方法返回地址等
	 * @param i
	 * @return
	 */
	public int add(int i){
		int a = 0;
		return a + i;
	}
	
	public static void main(String[] args) {
		/**
		 * 堆线程共享：对象实例对象（intro）、实例变量（objVar）、数组
		 */
		JavaVMIntro intro = new JavaVMIntro();
    }
}
```

## 栈、堆、方法区内存溢出

```java
package com.alice.inaction.bbehapara;
import java.util.ArrayList;
import java.util.List;

/**
 * 栈、堆、方法区内存溢出
 * @author xuxhm
 */
public class JavaVMOverFlow {
	
	private void dontStop() {
        while (true) {
        }
    }

	/**
	 * -Xss10m 配置栈内存
	 * java.lang.OutOfMemoryError
	 * 虚拟机栈溢出：一个线程一个虚拟机栈，包含多个栈帧，每个栈帧对应一个方法
	 * 栈帧包含：参数、局部变量、操作数栈等，需要栈溢出可以递归方法调用，使得栈帧很深，也可以循环创建线程
	 * @param args
	 */
    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }
	
	/**
	 * -Xss配置栈内存
	 * java.lang.StackOverflowError
	 * 虚拟机栈溢出：一个线程一个虚拟机栈，包含多个栈帧，每个栈帧对应一个方法
	 * 栈帧包含：参数、局部变量、操作数栈等，需要栈溢出可以递归方法调用，使得栈帧很深，也可以循环创建线程
	 * @param args
	 */
	public void stackLeakByParameter(){
		int a = 0;
		while(true){
			stackLeakByParameter();
		}
	}
	
	/**
	 * -XX:PermSize=10M -XX:MaxPermSize=10M 方法去配置
	 * java.lang.OutOfMemoryError: Java heap space
	 * 方法区溢出：类信息、常量、静态变量、类方法
	 * 运行时常量池：字面量（2）、符号引用、变量、类名、方法名
	 * 溢出方案：字符串多个
	 * @param args
	 */
	public void RuntimeConstantPool(){
		List<String> list = new ArrayList<String>();
		int i = 0;
		while(true){
			//intern()查到对象，然后如果运行常量池没有存放第一次引用地址
			list.add(String.valueOf(i++).intern());
		}
	}
	
	/**
	 * -Xms10M -Xmx10M 堆内存配置
	 * java.lang.OutOfMemoryError: Java heap space
	 * 方法区溢出：对象实例、数组
	 * 溢出方案：创建多个对象
	 * @param args
	 */
	public void HeapSpace(){
		List<JavaVMOverFlow> list = new ArrayList<JavaVMOverFlow>();
		int i = 0;
		while(true){
			list.add(new JavaVMOverFlow());
		}
	}
	
	public static void main(String[] args) {
		JavaVMOverFlow test = new JavaVMOverFlow();
		test.stackLeakByThread();
    }
}

```

