package shuati.shanjinlieren;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: shuati.shanjinlieren
 * @Author: yang
 * @CreateTime: 2021-08-20 08:19
 * @Description:
 */
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        Hunter[] hunters = new Hunter[n];
        for (int i = 0; i < n; i++) {
            hunters[i] = new Hunter();
            hunters[i].attack = sc.nextInt();
            hunters[i].index = i;
        }
        for (int i = 0; i < n; i++) {
            hunters[i].money=sc.nextInt();
        }

        //按照赏金建立小项堆
        PriorityQueue<Hunter> pq = new PriorityQueue<>(new Comparator<Hunter>() {
            @Override
            public int compare(Hunter o1, Hunter o2) {
                return o1.money-o2.money;
            }
        });
        //首先按照攻击力进行排序
        Arrays.sort(hunters, new Comparator<Hunter>() {
            @Override
            public int compare(Hunter o1, Hunter o2) {
                return o1.attack-o2.attack;
            }
        });
        //计算结果
        int[] res = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int money = hunters[i].money;
            sum += money;
            //原本的位置添加
            res[hunters[i].index] = sum;
            pq.offer(hunters[i]);
            if (pq.size()>k){
                //减去其中的最小值并取出
                sum = pq.peek().money;
                pq.poll();
            }
        }
        //输出结果
        for (int re:res){
            System.out.println(re+" ");
        }
    }
}
