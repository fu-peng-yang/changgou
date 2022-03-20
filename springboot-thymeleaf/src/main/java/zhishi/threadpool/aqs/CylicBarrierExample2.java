package zhishi.threadpool.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier 还提供一个更高级的构造函数 CyclicBarrier(int parties, Runnable barrierAction) ，用于在线程到达屏障时，优先执行 barrierAction ，方便处理更复杂的业务场
 * 景。
 * CyclicBarrier 内部通过一个 count 变量作为计数器，cout 的初始值为 parties 属性的初始化
 * 值，每当一个线程到了栅栏这里了，那么就将计数器减一。如果 count 值为 0 了，表示这是这一代最后
 * 一个线程到达栅栏，就尝试执行我们构造方法中输入的任务。
 */
/*
CountDownLatch和CyclicBarrier的区别
CountDownLatch 是计数器，只能使用一次，而 CyclicBarrier 的计数器提供 reset 功能，可以多次使
用。
对于 CountDownLatch 来说，重点是“一个线程（多个线程）等待”，而其他的 N 个线程在完成“某件事
情”之后，可以终止，也可以等待。而对于 CyclicBarrier，重点是多个线程，在任意一个线程没有完
成，所有的线程都必须等待。
CountDownLatch 是计数器，线程完成一个记录一个，只不过计数不是递增而是递减，而
CyclicBarrier 更像是一个阀门，需要所有线程都到达，阀门才能打开，然后继续执行。
 */

/**
 * ReentrantLock 和 ReentrantReadWriteLock
 * 读写锁 ReentrantReadWriteLock 可以保证多个线程可以同时读，所以在读操作远大于写操作的时候，
 * 读写锁就非常有用了
 */
public class CylicBarrierExample2 {
    // 请求的数量
    private static final int threadCount = 550; // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
        System.out.println("------当线程数达到之后，优先执行------");
    });

    public static void main(String[] args) throws InterruptedException { // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                try {
                    test(threadNum);
                } catch (InterruptedException e) { // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (BrokenBarrierException e) { // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }

    public static void test(int threadnum) throws InterruptedException, BrokenBarrierException {
        System.out.println("threadnum:" + threadnum + "is ready");
        cyclicBarrier.await();
        System.out.println("threadnum:" + threadnum + "is finish");
    }
}
