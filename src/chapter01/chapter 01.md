## chapter 01

+ 并发执行有线程切换时间，不一定比串行快，在ConcurrentTest中，执行次数越多，并发的执行速度越快

+ 减少上下文切换时间的方式
    1. 无锁并发编程：任务分段给不同的线程，任务明确，不用来回上锁了
    2. CAS
    3. 使用最少线程
    4. 协程
    
+ 查看死锁
```bash
Z:\IDEA Project\concurrent>jps
7924 DeadLockTest
15308
17772 Jps
2860 Launcher

Z:\IDEA Project\concurrent>jstack 7924
Found one Java-level deadlock:
=============================
"Thread-1":
  waiting to lock monitor 0x000000001cc905e8 (object 0x000000076b174968, a java.lang.String),
  which is held by "Thread-0"
"Thread-0":
  waiting to lock monitor 0x000000001cc907f8 (object 0x000000076b174998, a java.lang.String),
  which is held by "Thread-1"

Java stack information for the threads listed above:
===================================================
"Thread-1":
        at chapter01.DeadLockTest$1.run(DeadLockTest.java:36)
        - waiting to lock <0x000000076b174968> (a java.lang.String)
        - locked <0x000000076b174998> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:748)
"Thread-0":
        at chapter01.DeadLockTest.lambda$deadLock$0(DeadLockTest.java:21)
        - waiting to lock <0x000000076b174998> (a java.lang.String)
        - locked <0x000000076b174968> (a java.lang.String)
        at chapter01.DeadLockTest$$Lambda$1/990368553.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.
``` 

+ 避免死锁
  1. 避免一个线程同时获得多个锁
  2. 避免一个锁同时占用多个资源
  3. 使用 `lock.tryLock(timeout)`
  4. 对于数据库锁，加锁和解锁必须在一个数据库里
  
