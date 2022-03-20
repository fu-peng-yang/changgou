package zhishi.threadpool.aqs;

import java.util.concurrent.*;

/**
 * CyclicBarrier(循环栅栏)
 * CyclicBarrier 和 CountDownLatch 非常类似，它也可以实现线程间的技术等待，但是它的功能比
 * CountDownLatch 更加复杂和强大。主要应用场景和 CountDownLatch 类似。
 * CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。它要做的事情是，让一组线程到
 * 达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障
 * 拦截的线程才会继续干活。CyclicBarrier 默认的构造方法是 CyclicBarrier(int parties) ，其参数
 * 表示屏障拦截的线程数量，每个线程调用 await 方法告诉 CyclicBarrier 我已经到达了屏障，然后当前
 * 线程被阻塞。
 */
/*
CyclicBarrier 的应用场景
CyclicBarrier 可以用于多线程计算数据，最后合并计算结果的应用场景。比如我们用一个 Excel 保存了
用户所有银行流水，每个 Sheet 保存一个帐户近一年的每笔银行流水，现在需要统计用户的日均银行流
水，先用多线程处理每个 sheet 里的银行流水，都执行完之后，得到每个 sheet 的日均银行流水，最
后，再用 barrierAction 用这些线程的计算结果，计算出整个 Excel 的日均银行流水。
 */
/*
CyclicBarrier 内部通过一个 count 变量作为计数器，cout 的初始值为 parties 属性的初始化
值，每当一个线程到了栅栏这里了，那么就将计数器减一。如果 count 值为 0 了，表示这是这一代最后
一个线程到达栅栏，就尝试执行我们构造方法中输入的任务。
 */
public class CylicBarrierExamplel {
    // 请求的数量
    private static final int threadCount = 550; // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

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
        try { /**等待60秒，保证子线程完全执行结束*/
            cyclicBarrier.await(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("-----CyclicBarrierException------");
        }
        System.out.println("threadnum:" + threadnum + "is finish");
    }
}
