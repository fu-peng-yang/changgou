package zhishi.threadpool.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger 类主要利用 CAS (compare and swap) + volatile 和 native 方法来保证原子操作，从而
 * 避免 synchronized 的高开销，执行效率大为提升。
 * CAS的原理是拿期望的值和原本的一个值作比较，如果相同则更新成新的值。UnSafe 类的
 * objectFieldOffset() 方法是一个本地方法，这个方法是用来拿到“原来的值”的内存地址。另外 value 是
 * 一个volatile变量，在内存中可见，因此 JVM 可以保证任何时刻任何线程总能拿到该变量的最新值。
 */
public class AtomicIntegerDefectDemo {
    public static void main(String[] args) {
        defectOfABA();
    }


    static void defectOfABA() {
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        Thread coreThread = new Thread(() -> {
            final int currentValue = atomicInteger.get();
            System.out.println(Thread.currentThread().getName() + " ---- -- currentValue=" + currentValue);
            // 这段目的：模拟处理其他业务花费的时间
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean casResult = atomicInteger.compareAndSet(1, 2);
            System.out.println(Thread.currentThread().getName() + " ------ currentValue=" + currentValue + ", finalValue=" + atomicInteger.get() + ", compareAndSet Result=" + casResult);
        });
        coreThread.start();
        // 这段目的：为了让 coreThread 线程先跑起来
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread amateurThread = new Thread(() -> {
            int currentValue = atomicInteger.get();
            boolean casResult = atomicInteger.compareAndSet(1, 2);
            System.out.println(Thread.currentThread().getName() + " ------ currentValue=" + currentValue + ", finalValue=" + atomicInteger.get() + ", compareAndSet Result=" + casResult);
            currentValue = atomicInteger.get();
            casResult = atomicInteger.compareAndSet(2, 1);
            System.out.println(Thread.currentThread().getName() + " ------ currentValue=" + currentValue + ", finalValue=" + atomicInteger.get() + ", compareAndSet Result=" + casResult);
        });
        amateurThread.start();
    }

}