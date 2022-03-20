package demo;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo
 * @Author: yang
 * @CreateTime: 2021-08-18 09:57
 * @Description:
 */
public class Example extends Thread {
    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("run");
    }
static  int  a[] = new int[6];

    public static void main(String[] arg) {
        System.out.println(0/Math.random());
        /*System.out.println(a[0]);
        Example example = new Example();
        //example.start();
        example.run();
        System.out.println("main");*/
    }
}
