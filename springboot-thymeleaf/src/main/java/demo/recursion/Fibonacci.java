package demo.recursion;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.recursion
 * @Author: yang
 * @CreateTime: 2021-08-17 05:33
 * @Description: 递归
 */
public class Fibonacci {
    public static long fibonacci(long index){
        if (index <= 1){
            return index;
        }else {
            return fibonacci(index - 1)+fibonacci(index -2);
        }
    }

    public static long fibonacciTailRacursion(long index){
        return fibonacciTailRacursion(index,0,1);
    }
    public static long fibonacciTailRacursion(long index,int curr,int next){
        if (index == 0){
            return curr;
        }else {
            return fibonacciTailRacursion(index -1,next,curr+next);
        }
    }
}
