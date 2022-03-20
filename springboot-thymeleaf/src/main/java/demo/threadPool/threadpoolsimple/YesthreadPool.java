package demo.threadPool.threadpoolsimple;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.threadPool.threadpoolsimple
 * @Author: yang
 * @CreateTime: 2021-08-15 19:37
 * @Description:
 */
@Slf4j
public class YesthreadPool {

    BlockingQueue<Runnable> taskQueue; //存放任务的阻塞队列
    List<YesThread> threads; //线程列表

    YesthreadPool(BlockingQueue<Runnable> taskQueue,int threadSize){
        this.taskQueue = taskQueue;
        threads = new ArrayList<>(threadSize);
        //初始化线程,并定义名称
        IntStream.rangeClosed(1,threadSize).forEach((i)->{
            YesThread thread = new YesThread("yes-task-thread-"+i);
            thread.start();
            threads.add(thread);
        });
    }
    //提交任务只是往任务队列里面塞任务
    public void execute(Runnable task) throws InterruptedException {
        taskQueue.put(task);
    }

    class YesThread extends Thread{ //自定义一个线程
        public YesThread(String name){
            super(name);
        }
        public void run(){
            while (true){
                Runnable task = null;
                try {
                    task = taskQueue.take(); //不断从任务队列获取任务
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();//执行
            }
        }
    }

    public static void main(String[] args) {
        YesthreadPool pool = new YesthreadPool(new LinkedBlockingQueue<>(10),3);
        IntStream.rangeClosed(1,5).forEach((i)->{
            try {
                pool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"线程池");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
