package spring.test.xiancheng.duoxiancheng;

import ch.qos.logback.core.helpers.CyclicBuffer;

import java.util.concurrent.*;

/**
 * 测试 CyclicBarrier 类中带参数的 await() 方法
 */
public class CyclicBarrierExample1 {
    //请求的数量
    private static final int threadcount=550;
    //需要同步的线程数量
    /**
     *
     */
    private static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {

        //创建线程池
        ExecutorService threadpool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < threadcount; i++) {

            final int threadnum = i;
            Thread.sleep(1000);
            threadpool.execute(()->{
                try {
                    test(threadnum);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        threadpool.shutdown();
    }
    public static void test(int threadnum) throws InterruptedException,  BrokenBarrierException {
        System.out.println("threadnum:" + threadnum + "  is ready");

        try {
            //等待6秒,保证子线程安全执行结束
            CYCLIC_BARRIER.await(60, TimeUnit.SECONDS);
        }catch (Exception e){
            System.out.println("----------CyclicBarrierException---------");
        }
        System.out.println("threadnum:"+threadnum+"  is finish");
    }
}
