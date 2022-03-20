package demo.forkJoin;

import java.util.concurrent.ForkJoinPool;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.forkJoin
 * @Author: yang
 * @CreateTime: 2021-04-30 18:39
 * @Description:
 */
public class ForkJoinTest {
    public static void main(String[] args) {
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i = 0;i<SIZE;i++){
            numbers[i] = Math.random();
            Counter counter = new Counter(numbers,0,numbers.length,x -> x > 0.5);
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(counter);
            System.out.println(counter.join());
        }
    }
}
