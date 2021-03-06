## 哪些内存需要回收？

> 程序计数器、虚拟机栈、本地方法栈3个区域随线程而生，随线程而灭，不需要回收
>
> 堆、方法区需要回收，其中方法区回收常量、类，常量回收类似堆回收，类回收限制条件比较多。

## 什么时候回收？

> 堆分为新生代和老生代，用标记-复制算法填充新生代，下一次填充的时候内存不够开始回收。新生代中无法放下则放到老生代中，还有一种新生代中回收15次以上的可以换到老生代。老生代如果内存不够，用标记-整理算法。

## 如何回收？

##### 可达性分析算法

> 通过“GC Roots”作为起点，进行搜索。可作为GC Roots对象：

- （栈帧中本地变量表）中的引用对象
- 方法区中类静态属性引用的对象
- 方法区中常量引用的对象

##### 引用定义

- 强引用：Object obj = new Object()永不回收
- 软引用：系统发生内存溢出之前，列入回收范围进行第二次回收
- 弱引用：生存到下一次垃圾收集发生之前

### 垃圾收集算法

##### 标记-清除算法

思想：首先标记需要回收的对象，然后统一回收。

缺点：

- 效率不高
- 空间不连续

##### 复制算法

思想：内存按容量划分，使用完一块，进行回收，复制存活对象到另外一块

优点：

- 运行高效，不需要访问所有空间
- 存储连续

缺点：

- 内存浪费

使用场景：新生代垃圾回收，一般一块较大Eden空间，两块小的Survivor，比例8:1，浪费10%的空间实现空间连续高效利用。

##### 标记-整理算法

思想：首先标记需要回收的对象，然后回收的时候所有存活对象向一端移动，然后清理掉其他内存

使用场景：老生代长期复活

##### 分代收集算法

思想：`新生代`用复制算法，`老生代`标记-清理/整理算法。



