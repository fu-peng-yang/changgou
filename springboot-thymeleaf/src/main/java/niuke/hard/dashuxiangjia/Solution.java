package niuke.hard.dashuxiangjia;

import java.math.BigInteger;

/**
 * @author Administrator
 * 以字符串的形式读入
 * 计算两个数之和
 */
public class Solution {
    /**
     * 算法思想  栈
     * 在将每一位的相加结果入栈存储,当循环完之后再将栈中数据依次出栈形成字符串
     * @param s
     * @param t
     * @return
     */
    public static String solve(String s,String t){

        BigInteger bigInteger1 = new BigInteger(s);
        BigInteger bigInteger2 = new BigInteger(t);
        return bigInteger1.add(bigInteger2).toString();
    }

    public static void main(String[] args) {
        String solve = solve("4545454545", "9898989898");
        System.out.println(solve.toString());
    }
}
