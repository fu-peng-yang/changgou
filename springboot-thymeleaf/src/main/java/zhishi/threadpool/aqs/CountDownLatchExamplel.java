package zhishi.threadpool.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

/**
 * CountDownLatch （倒计时器）
 * CountDownLatch允许 count 个线程阻塞在一个地方，直至所有线程的任务都执行完毕。在 Java 并发
 * 中，countdownlatch 的概念是一个常见的面试题，所以一定要确保你很好的理解了它。
 * CountDownLatch是共享锁的一种实现,它默认构造 AQS 的 state 值为 count。当线程使用countDown
 * 方法时,其实使用了 tryReleaseShared 方法以CAS的操作来减少state,直至state为0就代表所有的线程
 * 都调用了countDown方法。当调用await方法的时候，如果state不为0，就代表仍然有线程没有调用
 * countDown方法，那么就把已经调用过countDown的线程都放入阻塞队列Park,并自旋CAS判断state
 * == 0，直至最后一个线程调用了countDown，使得state == 0，于是阻塞的线程便判断成功，全部往下
 * 执行。
 */
/*
CountDownLatch 的两种典型用法
1. 某一线程在开始运行前等待n个线程执行完毕。将 CountDownLatch 的计数器初始化为 n ： new CountDownLatch(n) ，每当一个任务线程执行完毕，就将计数器减 1
countdownlatch.countDown() ，当计数器的值变为 0 时，在 CountDownLatch上 await() 的
线程就会被唤醒。一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之
后再继续执行。
2. 实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，强调的是多个线程在某一时
刻同时开始执行。类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。做法是初始
化一个共享的 CountDownLatch 对象，将其计数器初始化为 1 ： new CountDownLatch(1) ，多
个线程在开始执行任务前首先 coundownlatch.await() ，当主线程调用 countDown() 时，计数
器变为 0，多个线程同时被唤醒。
 */
public class CountDownLatchExamplel {
    //请求的数量
    private static final int threadCount = 550;

    public static void main(String[] args) throws InterruptedException {
        //创建一个具有固定线程数量的线程池对象(如果这里线程池的线程数量给太少的话会发现执行的线程很慢)
        ExecutorService threadPool = newFixedThreadPool(300);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadnum = i;
            threadPool.execute(()->{

                try {
                    test(threadnum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();//表示一个请求已经被完成
                }

            });
            countDownLatch.await();
            threadPool.shutdown();
            System.out.println("finish");
        }
    }
    public static void test(int threadnum) throws InterruptedException {

            Thread.sleep(1000);//模拟请求的耗时操作
            System.out.println("threadnum:" + threadnum);
            Thread.sleep(1000);//模拟请求的耗时操作

    }
}
