package demo.suanfa.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.suanfa.algorithm
 * @Author: yang
 * @CreateTime: 2021-08-16 07:30
 * @Description: 算法
 */
public class Algorithm {
    /**
     * 给定一个整数数组nums和一个整数目标值target,请在该数组中找出和为目标值target的那两个整数,
     * 并返回它们的数组下标
     */
    public static int[] twoSum(int[] nums,int target){
        int[] res = new int[2];
        if (nums == null || nums.length == 0){
            return res;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for (int i =0;i<nums.length;i++){
            int temp = target - nums[i];
            if (map.containsKey(temp)){
                res[1] = i;
                res[0] = map.get(temp);
            }
            map.put(nums[i],i);
        }
        return res;
    }
    public static void main(String[] args) {
        int[] ints = twoSum(new int[]{2, 3, 8, 4, 9, 5}, 7);
        for (int anInt : ints) {

            System.out.println(anInt);
        }

    }
}
