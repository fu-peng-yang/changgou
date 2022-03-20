package shuati;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: shuati
 * @Author: yang
 * @CreateTime: 2021-08-20 03:52
 * @Description:
 */
public class Main1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < num; ++i) {
            fun(sc.nextLine());
        }
    }
    public static void fun(String input){
        //记录各个连着的1的数量
        List<Integer> list = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i)=='1'){
                num++;
            }else {
                if (num !=0){
                    list.add(num);
                }
                num=0;
            }
        }
        if (num!=0){
            list.add(num);
        }
        Collections.sort(list);
        int score =0;
        int add = 1;
        for (int i = list.size()-1; i >=0 ; --i) {
            score += (add * list.get(i));
            add *= (-1);
        }
        if (score == 0){
            System.out.println("Draw");
        }else if(score <0){
            score *=(-1);
            System.out.println("Niuniu");
            System.out.println(score+"");
        }else {
            System.out.println("Niumei");
            System.out.println(score+"");
        }

    }
}
