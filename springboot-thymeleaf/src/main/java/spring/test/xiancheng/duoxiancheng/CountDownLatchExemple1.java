package spring.test.xiancheng.duoxiancheng;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

public class CountDownLatchExemple1 {
    //请求的数量
    private static final int threadCount = 550;

    public static void main(String[] args) throws InterruptedException {

        //创建一个具有固定线程池的线程数量
        ExecutorService threadPool = newFixedThreadPool(300);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadnum=i;
            threadPool.execute(()->{
                try {
                    test(threadnum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();//表示一个请求已被完成
                }
            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("finish");
    }
    public static void test(int threadnum) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("threadnum:"+threadnum);
        Thread.sleep(1000);
    }
}
