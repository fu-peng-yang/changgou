package demo.InnerClass;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo
 * @Author: yang
 * @CreateTime: 2020-12-13 17:16
 * @Description: 静态内部类
 */
public class InnerClass {

    public static void in(){

    }

    public void instance(){

    }
    private static int radius=1;
    static class OutInner{
        public void visit(){
            System.out.println("visit outer static  variable:" + radius);
        }

        public static void main(String[] args) {
            InnerClass.OutInner out = new InnerClass.OutInner();
            out.visit();
            InnerClass.in();
        }


    }

    public static void main(String[] args) {
        InnerClass.OutInner outInner = new InnerClass.OutInner();
        outInner.visit();
    }
}
