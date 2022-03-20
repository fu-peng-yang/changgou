package demo;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo
 * @Author: yang
 * @CreateTime: 2021-08-19 10:42
 * @Description:
 */
public class Foo {
    public static int i = print("i");
    public static int n = 99;
    public int j = print("j");
    {
        print("Construct");
        ++n;
    }
    static {
        print("Static");
    }
    public Foo(String str){
        System.out.println(str + "i=" +i+"n="+n);
    }
    public static int print(String str){
        System.out.println(str+"i="+i+"n="+n);
        return ++i;
    }

    public static void main(String[] args) {
        Foo f = new Foo("Init");
    }

}
