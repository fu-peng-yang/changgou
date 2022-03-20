package demo.InnerClass;

import javax.xml.ws.Service;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.InnerClass
 * @Author: yang
 * @CreateTime: 2021-02-12 18:41
 * @Description: 匿名内部类
 */
public class Anonymity {
    private void test(final int i){
        new Service(){
            public void method(){
                for (int j = 0;j<i;j++){
                    System.out.println("匿名内部类");
                }
            }
        }.method();
    }
    //匿名内部类必须继承或实现一个已有的接口
    interface Service{
        void method();
    }

    public static void main(String[] args) {
         Anonymity a = new Anonymity();

       a.test(1);
    }
}
