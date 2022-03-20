package shuati;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: shuati
 * @Author: yang
 * @CreateTime: 2021-08-17 06:10
 * @Description:
 */
public class Main {

    public static int f(int value) {
        try {
            return value * value;
        } finally {
            if (value == 2) {
                return 0;
            }
        }
    }
    public static void main(String[] args) {
        /*long x = 0;
        try {
            Scanner sc = new Scanner(System.in);
            long a = sc.nextLong();
            long b = sc.nextLong();
            long c = sc.nextLong();
            x = (long) Math.max(Math.ceil(1.0*(3*a+b-c)/3),0L);
            System.out.println(x);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            x=454545;
            return ;
        }
*/
        System.out.println(f(2));
    }
}
