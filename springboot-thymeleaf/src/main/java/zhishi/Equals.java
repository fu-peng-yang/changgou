package zhishi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Equals implements Serializable {
    private transient String s;//transient 声明 不想被序列化的变量

    /**
     * length 属性针对数组     length（）方法针对String字符   size（）方法针对泛型集合
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //键入方法
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        String a = "aa";
        String b = "aa";
        String c = new String("aa");
        String d = new String("aa");
        if (a.equals(b)) {
            System.out.println("a equals b");
        }
        if (a == b) {
            System.out.println("a==b");
        }
        if (a == c) {
            System.out.println("a==c");
        }
        if (a.equals(c)) {
            System.out.println("a equals c");
        }
        if (c == d) {
            System.out.println("c == d");
        }
        if (c.equals(d)) {
            System.out.println("c equals d");
        }
    }
}
