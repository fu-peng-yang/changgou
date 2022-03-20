package demo.suanfa.algorithm;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.suanfa.algorithm
 * @Author: yang
 * @CreateTime: 2021-08-16 08:15
 * @Description:
 */
public class Solution {
    /**
     * 给个32位的有符号整数 x,返回将 x中的数字部分反转后的结果
     * @param x
     * @return
     */
    public static int reverse(int x){
        int result = 0;
        while (x != 0){
            int tmp =result;//保存计算之前的结果
            result = (result * 10) + (x%10);
            x /= 10;//将计算之后的结果 /10,判断是否与计算之前相同,如果butong,证明发生溢出,返回0
            if (result / 10 != tmp){
                return 0;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int reverse = reverse(-234);
        System.out.println(reverse);
    }
}
