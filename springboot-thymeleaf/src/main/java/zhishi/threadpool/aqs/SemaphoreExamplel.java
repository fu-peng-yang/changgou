package zhishi.threadpool.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static java.util.concurrent.Executors.*;


/*
    Semaphore(信号量)-允许多个线程同时访问
    synchronized 和 ReentrantLock 都是一次只允许一个线程访问某个资源，Semaphore(信号量)可以
    指定多个线程同时访问某个资源。
    Semaphore 经常用于限制获取某种资源的线程数
    量。
 */
public class SemaphoreExamplel {
    //请求的数量
    private static final int threadCount = 550;

    public static void main(String[] args) throws InterruptedException {
        //创建一个具有固定线程数量的线程池对象(如果这里线程池的线程数量给太少的话会发现执行的很慢)
        ExecutorService threadPool = newFixedThreadPool(300);
        //一次只能允许执行的线程数量
        final Semaphore semaphore = new Semaphore(20);
        for (int i = 0; i < threadCount; i++) {
            final int threadnum = i;
            threadPool.execute(()->{//Lambda表达式的应用
                try {
                    semaphore.acquire();
                    test(threadnum);
                    semaphore.release();//释放一个许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }

        threadPool.shutdown();
        System.out.println("完成");
    }
    public static void test(int threadnum){
        try {
            Thread.sleep(1000);//模拟骑牛的耗时操作
            System.out.println("threadnum:" + threadnum);
            Thread.sleep(1000);//模拟请求的耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}