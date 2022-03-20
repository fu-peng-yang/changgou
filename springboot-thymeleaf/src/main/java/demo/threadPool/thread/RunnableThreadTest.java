package demo.threadPool.thread;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.threadPool.thread
 * @Author: yang
 * @CreateTime: 2021-08-17 06:39
 * @Description:
 */
public class RunnableThreadTest implements Runnable {
    private int i;

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+".........."+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+""+i);
            if (i==20){
                RunnableThreadTest rtt = new RunnableThreadTest();
                new Thread(rtt,"新线程1").start();
                new Thread(rtt,"新线程2").start();
            }
        }
    }
}
