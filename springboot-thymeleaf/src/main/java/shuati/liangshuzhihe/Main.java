package shuati.liangshuzhihe;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: shuati.liangshuzhihe
 * @Author: yang
 * @CreateTime: 2021-08-20 09:22
 * @Description:给定一个整数数组nums和一个整数目标值target,在该数组中找出和为目标值target的那两个整数,
 * 并返回它们的数组下标
 * 可以按任意顺序返回答案
 */
public class Main {
    public static void main(String[] args) {
        int[] num={1, 3, 4, 9,5,7};
        int[] ints = twoSum(num, 8);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }
    public static int[] twoSum(int[] nums,int target){
        int[] res = new int[2];
        if (nums == null || nums.length == 0){
            return res;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp)){
                res[1] =i;
                res[0] =map.get(temp);
            }
            map.put(nums[i],i);
        }
        return res;
    }
}
