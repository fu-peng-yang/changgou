package zhishi.threadpool.aqs;

/**
 * @author Administrator
 * AQS 使用一个 int 成员变量来表示同步状态，通过内置的 FIFO 队列来完成获取资源线程的排队工作。
 * AQS 使用 CAS 对该同步状态进行原子操作实现对其值的修改。
 * FIFO 是先入先出
 */
public class Aqs {
    private volatile int state;//共享变量，使用volatile修饰保证线程可见性
    /*
        AQS 定义两种资源共享方式
    1)Exclusive（独占）
    只有一个线程能执行，如 ReentrantLock。又可分为公平锁和非公平锁,ReentrantLock 同时支持两种
    锁,下面以 ReentrantLock 对这两种锁的定义做介绍：
    公平锁：按照线程在队列中的排队顺序，先到者先拿到锁
    非公平锁：当线程要获取锁时，先通过两次 CAS 操作去抢锁，如果没抢到，当前线程再加入到队
    列中等待唤醒。
    总结：公平锁和非公平锁只有两处不同：
    1. 非公平锁在调用 lock 后，首先就会调用 CAS 进行一次抢锁，如果这个时候恰巧锁没有被占用，那
    么直接就获取到锁返回了。
    2. 非公平锁在 CAS 失败后，和公平锁一样都会进入到 tryAcquire 方法，在 tryAcquire 方法中，如果
    发现锁这个时候被释放了（state == 0），非公平锁会直接 CAS 抢锁，但是公平锁会判断等待队列
    是否有线程处于等待状态，如果有则不去抢锁，乖乖排到后面。
    相对来说，非公平锁会有更好的性能，因为它的吞吐量比较大。当然，非公平锁让获取锁的时间变得更
    加不确定，可能会导致在阻塞队列中的线程长期处于饥饿状态。
    */
    /*
    2)Share（共享）
    多个线程可同时执行，如 Semaphore/CountDownLatch。Semaphore、CountDownLatCh、
    CyclicBarrier、ReadWriteLock 我们都会在后面讲到。
    ReentrantReadWriteLock 可以看成是组合式，因为 ReentrantReadWriteLock 也就是读写锁允许多个
    线程同时对某一资源进行读。
    不同的自定义同步器争用共享资源的方式也不同。自定义同步器在实现时只需要实现共享资源 state 的
    获取与释放方式即可，至于具体线程等待队列的维护（如获取资源失败入队/唤醒出队等），AQS 已经
    在上层已经帮我们实现好了。
     */
}
