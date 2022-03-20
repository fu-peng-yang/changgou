package demo.string;

import java.lang.reflect.Field;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.string
 * @Author: yang
 * @CreateTime: 2021-02-09 15:53
 * @Description: String通过反射可以修改所谓的“不可变”对象
 */
public class Strings {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //创建字符串"Hello World",并赋给引用
        String s = "Hello World";
        System.out.println("s = " + s);
        //获取String类中的vlaue字段
        Field valueFieldOfString = String.class.getDeclaredField("value");
        //改变value属性的访问权限
        valueFieldOfString.setAccessible(true);
        //获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);
        //改变value所引用的数组中的第5个字符
        value[5] = '_';
        System.out.println("s = " + s);


        /**
         *String s = new String(“xyz”);创建了几个字符串对象
         */
        String str1 = "hello"; //str1.指向静态区
        String str2 = new String("hello"); //str2指向堆上的对象
        String str3 = "hello";
        String str4 = new String("hello");
        System.out.println(str1.equals(str2)); //true
        System.out.println("......."+str1.equals(str2)); //true
        System.out.println(str2.equals(str4)); //true
        System.out.println(str1 == str3); //true
        System.out.println(str1 == str2); //false
        System.out.println(str2 == str4); //false
        System.out.println(str2 == "hello"); //false
        str2=str1;
        System.out.println(str2 == "hello"); //true


        /**
         * 字符串反转
         */
        //StringBuffer reverse
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("abcdefg");
        System.out.println(stringBuffer.reverse());
        //StringBuilder reverse
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("abcdefg");
        System.out.println(stringBuilder.reverse());

        /**
         * （1）如果要操作少量的数据用 String；
         * （2）多线程操作字符串缓冲区下操作大量数据 StringBuffer；
         * （3）单线程操作字符串缓冲区下操作大量数据 StringBuilder。
         */

        
    }


}
