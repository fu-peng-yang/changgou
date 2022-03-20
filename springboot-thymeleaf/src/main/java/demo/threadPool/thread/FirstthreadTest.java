package demo.threadPool.thread;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.threadPool.thread
 * @Author: yang
 * @CreateTime: 2021-08-17 06:33
 * @Description:
 */
public class FirstthreadTest extends Thread {
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(getName()+" 调用该方法的线程的名字 "+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+" 当前正在执行的线程对象 "+i);
            if (i==20){
                new FirstthreadTest().start();
                new FirstthreadTest().start();
            }
        }
    }
}
