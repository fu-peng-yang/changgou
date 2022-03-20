package zhishi.threadpool.threadpool;

import java.util.concurrent.*;

/**
 * 不推荐
 *
 *
 * FixedThreadPool 使用无界队列 LinkedBlockingQueue （队列的容量为 Intger.MAX_VALUE）作为线程池的工作队列会对线程池带来如下影响 ：
 * 1. 当线程池中的线程数达到 corePoolSize 后，新任务将在无界队列中等待，因此线程池中的线程
 * 数不会超过 corePoolSize；
 * 2. 由于使用无界队列时 maximumPoolSize 将是一个无效参数，因为不可能存在任务队列满的情况。
 * 所以，通过创建 FixedThreadPool 的源码可以看出创建的 FixedThreadPool 的 corePoolSize 和 maximumPoolSize 被设置为同一个值。
 * 3. 由于 1 和 2，使用无界队列时 keepAliveTime 将是一个无效参数；
 * 4. 运行中的 FixedThreadPool （未执行 shutdown() 或 shutdownNow() ）不会拒绝任务，在任务
 * 比较多的时候会导致 OOM（内存溢出）。
 */
public class FixedThreadPool {
    /*** 创建一个可重用固定数量线程的线程池 */
    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
    }
}
