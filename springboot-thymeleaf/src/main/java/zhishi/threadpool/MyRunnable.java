package zhishi.threadpool;

import java.util.Date;

/**
 * 这个一个简单的Runnable类，需要大约5秒钟来执行其任务
 */
public class MyRunnable implements Runnable {
    private String command;

    /*
    1.execute() 方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；
    2. submit() 方法用于提交需要返回值的任务。线程池会返回一个 Future 类型的对象，通过这个
    Future 对象可以判断任务是否执行成功，并且可以通过 Future 的 get() 方法来获取返回值，
    get() 方法会阻塞当前线程直到任务完成，而使用 get（long timeout，TimeUnit unit） 方法
    则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。
     */

    /**
     * public Future<?> submit(Runnable task)
     * { if (task == null)
     * throw new NullPointerException();
     * RunnableFuture<Void> ftask = newTaskFor(task, null);
     * execute(ftask);
     * return ftask;
     * }
     * @param s
     */
    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + "开始时间：" + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " 结束时间 = " + new Date());
    }
        private void processCommand() {
        try {Thread.sleep(5000);
        }
        catch (InterruptedException e) {
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
