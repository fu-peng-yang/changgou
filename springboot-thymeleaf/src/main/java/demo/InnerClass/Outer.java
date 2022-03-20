package demo.InnerClass;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.InnerClass
 * @Author: yang
 * @CreateTime: 2021-02-12 18:01
 * @Description: 成员内部类
 */
public class Outer {
    private static int radius = 1;
    private int count =2;
    class Inner{
        public void visit(){
            System.out.println("visit outer static  variable:" + radius);
            System.out.println("visit outer   variable:" + count);
        }

    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.visit();
    }
}
