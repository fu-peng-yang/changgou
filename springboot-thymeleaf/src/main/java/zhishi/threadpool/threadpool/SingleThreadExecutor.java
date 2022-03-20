package zhishi.threadpool.threadpool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * 不推荐
 * SingleThreadExecutor 使用无界队列 LinkedBlockingQueue 作为线程池的工作队列（队列的容量
 * 为 Intger.MAX_VALUE）。 SingleThreadExecutor 使用无界队列作为线程池的工作队列会对线程池
 * 带来的影响与 FixedThreadPool 相同。说简单点就是可能会导致 OOM，
 *不推荐使用 CachedThreadPool
 *  CachedThreadPool 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致
 * OOM。
 */

 public class SingleThreadExecutor {
    /***返回只有一个线程的线程池 */
    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory));
    }

    private static class FinalizableDelegatedExecutorService implements ExecutorService {
        public FinalizableDelegatedExecutorService(ThreadPoolExecutor threadPoolExecutor) {
        }

        @Override
        public void shutdown() {

        }

        @Override
        public List<Runnable> shutdownNow() {
            return null;
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return null;
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return null;
        }

        @Override
        public Future<?> submit(Runnable task) {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }

        @Override
        public void execute(Runnable command) {

        }
    }
}
