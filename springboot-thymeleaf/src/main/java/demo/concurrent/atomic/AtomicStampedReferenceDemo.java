package demo.concurrent.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Administrator
 * 原子更新带有版本号的引用类型。该类将整数值与引用关联起来，
 * 可用于解决原子的更新数据和数据的版本号，可以解决使用 CAS 进行原子更新时可能出现的 ABA
 * 问题。
 */
public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        //实例化、取当前值和stamp值
        final  Integer initialRef = 0,initalStamp = 0;
        final AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(initialRef, initalStamp);
        System.out.printf("currentValue=" + asr.getReference() + "currentStamp=" + asr.getStamp());

        //compare and set
        final  Integer newReference = 666,newStamp = 999;
        final Boolean casResult = asr.compareAndSet(initialRef, newReference, initalStamp, newStamp);
        System.out.println("currentValue="+ asr.getReference() + ",currentStamp=" + asr.getStamp() + "casResult=" + casResult);

        //获取当前的值和当前的stamp值
        int[] arr = new int[1];
        final Integer currentValue =asr.get(arr);
        System.out.println("currentvalue="+ asr.getReference()+
                ",currentstamp="+asr.getStamp());

        //单独设置stamp值
        final  boolean attempStampResult = asr.attemptStamp(newReference,37);
        System.out.println("currtentValue=" + asr.getReference() +
                ",currentStamp=" + asr.getStamp() +
                ",attempStampResult=" + attempStampResult);
        //重新设置当前值和stamp值
        asr.set(initialRef, initalStamp);
        System.out.println("currentValue="+asr.getReference()+
                "currentStamp="+asr.getStamp());
        // [不推荐使用，除非搞清楚注释的意思了] weak compare and set
        // 困惑！weakCompareAndSet 这个方法最终还是调用 compareAndSet 方法。[版本: jdk-8u191]
        // 但是注释上写着 "May fail spuriously and does not provide ordering guarantees,
        // so is only rarely an appropriate alternative to compareAndSet."
        // todo 感觉有可能是 jvm 通过方法名在 native 方法里面做了转发
        final boolean wCasResult = asr.weakCompareAndSet(initialRef,newReference,initalStamp,newStamp);
        System.out.println("currentValue="+asr.getReference()+
                ",currentStamp="+asr.getStamp()+
                ",wCasResult="+wCasResult);
    }

}
