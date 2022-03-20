package demo.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 */
public class AtomicIntegerTest {
    public static void main(String[] args) {
        int temvalue = 0;
        AtomicInteger i = new AtomicInteger(0);
        temvalue = i.getAndSet(3);
        System.out.println(temvalue +"; i:"+ i);
         temvalue = i.getAndIncrement();
        System.out.println(temvalue + "; i:"+i);
        temvalue = i.getAndAdd(5);
        System.out.println(temvalue+"; + i:"+i);
    }
}
