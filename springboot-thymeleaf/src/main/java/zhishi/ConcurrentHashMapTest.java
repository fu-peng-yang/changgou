package zhishi;

import javax.swing.text.Segment;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.7 底层一个Segments数组，存储一个Segments对象，一个Segments中储存一个Entry数组，存储的每个Entry对象又是一个链表头结点。
 * 1.8
 */
/*
ConcurrentHashMap: 线程安全的 HashMap
CopyOnWriteArrayList: 线程安全的 List，在读多写少的场合性能非常好，远远好于 Vector.
ConcurrentLinkedQueue: 高效的并发队列，使用链表实现。可以看做一个线程安全的
LinkedList，这是一个非阻塞队列。
BlockingQueue: 这是一个接口，JDK 内部通过链表、数组等方式实现了这个接口。表示阻塞队
列，非常适合用于作为数据共享的通道。
ConcurrentSkipListMap: 跳表的实现。这是一个 Map，使用跳表的数据结构进行快速查找。
 */

/**
 * ArrayBlockingQueue 是 BlockingQueue 接口的有界队列实现类，底层采用数组来实现。
 * ArrayBlockingQueue 一旦创建，容量不能改变。其并发控制采用可重入锁来控制，不管是插入操作还
 * 是读取操作，都需要获取到锁才能进行操作。当队列容量满时，尝试将元素放入队列将导致操作阻塞;尝
 * 试从一个空队列中取一个元素也会同样阻塞。
 * ArrayBlockingQueue 默认情况下不能保证线程访问队列的公平性
 *
 *  LinkedBlockingQueue
 * LinkedBlockingQueue 底层基于单向链表实现的阻塞队列，可以当做无界队列也可以当做有界队列来
 * 使用，同样满足 FIFO 的特性，与 ArrayBlockingQueue 相比起来具有更高的吞吐量，为了防止
 * LinkedBlockingQueue 容量迅速增，损耗大量内存。通常在创建 LinkedBlockingQueue 对象时，会指
 * 定其大小，如果未指定，容量等于 Integer.MAX_VALUE。
 *
 * PriorityBlockingQueue
 * PriorityBlockingQueue 是一个支持优先级的无界阻塞队列。默认情况下元素采用自然顺序进行排
 * 序，也可以通过自定义类实现 compareTo() 方法来指定元素排序规则，或者初始化时通过构造器参数
 * Comparator 来指定排序规则。
 * PriorityBlockingQueue 并发控制采用的是 ReentrantLock，队列为无界队列（ArrayBlockingQueue
 * 是有界队列，LinkedBlockingQueue 也可以通过在构造函数中传入 capacity 指定队列最大的容量，但
 * 是 PriorityBlockingQueue 只能指定初始的队列大小，后面插入元素的时候，如果空间不够的话会自动
 * 扩容）。
 * 简单地说，它就是 PriorityQueue 的线程安全版本。不可以插入 null 值，同时，插入队列的对象必须是
 * 可比较大小的（comparable），否则报 ClassCastException 异常。它的插入操作 put 方法不会
 * block，因为它是无界队列（take 方法在队列为空的时候会阻塞）。
 *
 *  ConcurrentSkipListMap
 * 跳表是一种可以用来快速查找的数据结构，有点类似于平衡
 * 树。它们都可以对元素进行快速的查找。但一个重要的区别是：对平衡树的插入和删除往往很可能导致
 * 平衡树进行一次全局的调整。而对跳表的插入和删除只需要对整个数据结构的局部进行操作即可。这样
 * 带来的好处是：在高并发的情况下，你会需要一个全局锁来保证整个平衡树的线程安全。而对于跳表，
 * 你只需要部分锁即可。这样，在高并发环境下，你就可以拥有更好的性能。而就查询的性能而言，跳表
 * 的时间复杂度也是 O(logn) 所以在并发数据结构中，JDK 使用跳表来实现一个 Map。
 * 跳表的本质是同时维护了多个链表，并且链表是分层的，
 */
public class ConcurrentHashMapTest {
    private static ConcurrentHashMap<String, String> cacheMap = new ConcurrentHashMap<String, String>();

    /**
     * 获取缓存的对象
     * @param account
     * @return
     */
    public static String getCache(String account){
        account = getCacheKey(account);
        //如果缓存中有该帐号，则返回value
        if (cacheMap.containsKey(account)){
            return cacheMap.get(account);
        }
        //如果缓存中没有该帐号，把该帐号对象缓存到concurrentHashMap中
        initCache(account);
        return cacheMap.get(account);
    }

    /**
     * 初始化缓存
     *
     * @param account
     */
    private static void initCache(String account) {
        //一般是进行数据库查询，将查询的结果进行缓存
        cacheMap.put(account, "18013093863");
    }
    /**
     * 拼接一个缓存key
     * @param account
     * @return
     */
    private static String getCacheKey(String account){
        return Thread.currentThread().getId()+"-"+account;
    }
    /**
     * 移除缓存信息
     * @param account
     */
    public static void removeCache(String account){
        cacheMap.remove(getCacheKey(account));
    }

}
