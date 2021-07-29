package chapter02;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by weiyupeng on 2021/7/29 21:27
 */
public class AtomTest {
    private static AtomicInteger atomicI = new AtomicInteger(0);
    private static int i = 0;

    public static void main(String[] args) {
//        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<>();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    count();
                    safeCount();
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                //
            }
        }

        System.out.println(i);
        System.out.println(atomicI);
    }

    /**
     * CAS 实现安全计数
     */
    private static void safeCount() {
        // 一顿自旋
        while (true) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }

        // 以上代码用这一句话也可以
        // atomicI.incrementAndGet();
    }

    private static void count() {
        i++;
    }
}
