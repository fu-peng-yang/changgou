package spring.test.xiancheng;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

public class MyCallable implements Callable<String> {

     @Override
     public String call() throws Exception {
         Thread.sleep(1000 );
         //返回当前执行callable线程的名字
         return Thread.currentThread().getName()    ;
     }

 }
