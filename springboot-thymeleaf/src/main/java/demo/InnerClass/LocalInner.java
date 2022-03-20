package demo.InnerClass;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.InnerClass
 * @Author: yang
 * @CreateTime: 2021-02-12 18:07
 * @Description: 局部内部类
 */
public class LocalInner {
    private int out_a = 1;
    private static int STATIC_b=2;

    public void testFunctionClass(){
        int inner_c = 3;
        class Inner{
            private void fun(){
                System.out.println(out_a);
                System.out.println(STATIC_b);
                System.out.println(inner_c);
            }
        }
        Inner inner = new Inner();
        inner.fun();
    }

    /**
     * 定义在实例方法中的局部类可以访问外部类的所有变量和方法，
     * 定义在静态方法中的局部类只能访问外部类的静态变量和方法。
     * 局部内部类的创建方式，在对应方法内，new 内部类()
     */
    public static void testStaticFunctionClass(){
        int d = 4;
        class Inner{
            private void fun(){
                // System.out.println(out_a); 编译错误，定义在静态方法中的局部类不可以访问外部类的实例变量
                System.out.println(STATIC_b);
                System.out.println(d);
            }
        }
        Inner inner = new Inner();
        inner.fun();
    }
}
