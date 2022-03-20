package spring.test.xiancheng.duoxiancheng;

import java.util.concurrent.*;

/**
 * @author Administrator
 * 测试CyclicBarrier类中带参数的await()方法
 */
public class CyclicBarrierExample2 {
    //请求的数量
    private static final int THREADCOUNT = 550;
    //需要同步的线程数量
    private static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(5,()->{
        System.out.println("---------当线程数达到之后优先执行--------");
    });

    public static void main(String[] args) throws InterruptedException {

        //创建线程池
        ExecutorService threadpool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < THREADCOUNT; i++) {
            final int threadnum = i;
            Thread.sleep(1000);
            threadpool.execute(()->{
                test(threadnum);
            });
        }
        threadpool.shutdown();
    }
    public static void test(int threadnum){
        System.out.println("threadnum: "+threadnum+" is ready");
        try {
            CYCLIC_BARRIER.await(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("CycliBarrierException");
        }
        System.out.println("threadnum: "+threadnum+" is finish");
    }
}
