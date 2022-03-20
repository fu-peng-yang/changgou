package demo.threadPool.thread.runnable;

import java.util.Date;

/**
 * @author Administrator
 * 这是一个简单的Runnable类，需要大约5秒钟来执行任务
 */
public class MyRunnable implements Runnable{
    private String command;

    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + "开始时间" + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName()+"结束时间"+new Date());
    }

    private void processCommand(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MyRunnable{" +
                "command='" + command + '\'' +
                '}';
    }
}
