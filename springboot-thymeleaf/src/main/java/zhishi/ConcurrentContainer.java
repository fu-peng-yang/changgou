package zhishi;

import java.util.concurrent.ArrayBlockingQueue;

public class ConcurrentContainer {
    /**ConcurrentHashMap
     *
     * ConcurrentHashMap中，无论是读操作还是写操作都能保证很高的性能：
     * 在进行读操作时(几乎)不需要加锁，而在写操作时
     * 通过锁分段技术只对所操作的段加锁而不影响客户端对其它段的访问。
     */
    /*CopyOnWriteArrayList
     *
     * CopyOnWriteArrayList 读取是完全不用加锁的，
     * 并且更厉害的是：写入也不会阻塞读取操作。
     * 只有写入和写入之间需要进行同步等待。这样一来，读操作的性能就会大幅度提升。
     */
    /**ConcurrentLinkedQueue高性能的非阻塞队列
     *
     *ConcurrentLinkedQueue 主要使用CAS 非阻塞算法来实现线程安全就好了
     * ConcurrentLinkedQueue 适合在对性能要求相对较高，
     * 同时对队列的读写存在多个线程同时进行的场景，
     * 即如果对队列加锁的成本较高则适合使用无锁的 ConcurrentLinkedQueue 来替代
     */
    /*BlockingQueue

      阻塞队列（BlockingQueue）被广泛使用在“生产者-消费者”问题中，其原因是
       BlockingQueue 提供了可阻塞的插入和移除的方法。当队列容器已满，生产者线程会被阻塞，
       直到队列未满；当队列容器为空时，消费者线程会被阻塞，直至队列非空时为止。
     */
    /**ArrayBlockingQueue
     *
     * ArrayBlockingQueue 是 BlockingQueue 接口的有界队列实现类，底层采用数组来实现。
     * ArrayBlockingQueue 一旦创建，容量不能改变。其并发控制采用可重入锁来控制，不管是插入操作还
     * 是读取操作，都需要获取到锁才能进行操作。当队列容量满时，尝试将元素放入队列将导致操作阻塞;
     * 尝试从一个空队列中取一个元素也会同样阻塞。
     * ArrayBlockingQueue 默认情况下不能保证线程访问队列的公平性，所谓公平性是指严格按照线程等待
     * 的绝对时间顺序，即最先等待的线程能够最先访问到 ArrayBlockingQueue。而非公平性则是指访问
     * ArrayBlockingQueue 的顺序不是遵守严格的时间顺序，有可能存在，当 ArrayBlockingQueue 可以被
     * 访问时，长时间阻塞的线程依然无法访问到 ArrayBlockingQueue。如果保证公平性，通常会降低吞吐
     * 量。如果需要获得公平性的 ArrayBlockingQueue，可采用如下代码：
     */
    private static ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10,true);
    /*LinkedBlockingQueue底层基于单向链表实现的阻塞队列，可以当做无界队列也可以当做有界队列来
    使用

    同样满足 FIFO 的特性，与 ArrayBlockingQueue 相比起来具有更高的吞吐量，为了防止
    LinkedBlockingQueue 容量迅速增，损耗大量内存。通常在创建 LinkedBlockingQueue 对象时，会指
    定其大小，如果未指定，容量等于 Integer.MAX_VALUE
     */
    /**PriorityBlockingQueue 是一个支持优先级的无界阻塞队列
     *
     * 默认情况下元素采用自然顺序进行排
     * 序，也可以通过自定义类实现 compareTo() 方法来指定元素排序规则，或者初始化时通过构造器参数
     * Comparator 来指定排序规则。
     * 简单地说，它就是 PriorityQueue 的线程安全版本。不可以插入 null 值，同时，插入队列的对象必须是
     * 可比较大小的（comparable），否则报 ClassCastException 异常。它的插入操作 put 方法不会
     * block，因为它是无界队列（take 方法在队列为空的时候会阻塞）。
     */
    /*ConcurrentSkipListMap

    为了引出 ConcurrentSkipListMap，先带着大家简单理解一下跳表。
    对于一个单链表，即使链表是有序的，如果我们想要在其中查找某个数据，也只能从头到尾遍历链表，
    这样效率自然就会很低，跳表就不一样了。跳表是一种可以用来快速查找的数据结构，有点类似于平衡
    树。它们都可以对元素进行快速的查找。但一个重要的区别是：对平衡树的插入和删除往往很可能导致
    平衡树进行一次全局的调整。而对跳表的插入和删除只需要对整个数据结构的局部进行操作即可。这样
    带来的好处是：在高并发的情况下，你会需要一个全局锁来保证整个平衡树的线程安全。而对于跳表，
    你只需要部分锁即可。这样，在高并发环境下，你就可以拥有更好的性能。而就查询的性能而言，跳表
    的时间复杂度也是 O(logn) 所以在并发数据结构中，JDK 使用跳表来实现一个 Map。
    跳表的本质是同时维护了多个链表，并且链表是分层的，
    跳表是一种利用空间换时间的算法。
    使用跳表实现 Map 和使用哈希算法实现 Map 的另外一个不同之处是：哈希并不会保存元素的顺序，而
    跳表内所有的元素都是排序的。因此在对跳表进行遍历时，你会得到一个有序的结果。所以，如果你的
    应用需要有序性，那么跳表就是你不二的选择。JDK 中实现这一数据结构的类是
    ConcurrentSkipListMap。
     */


}
