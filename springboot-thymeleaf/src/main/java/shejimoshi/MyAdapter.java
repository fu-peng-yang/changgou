package shejimoshi;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: shejimoshi
 * @Author: yang
 * @CreateTime: 2021-08-18 06:23
 * @Description: 适配器模式
 */
public class MyAdapter {

    private MyAdapterImpl adapterImpl;

    public MyAdapter(MyAdapterImpl myAdapterImpl) {

        this.adapterImpl = myAdapterImpl;

    }

    public void doSomething() {

        adapterImpl.doSomething();

    }

    public static void main(String args[]) {

        new MyAdapter(new MyAdapterImpl()).doSomething();

    }

}

class MyAdapterImpl {

    public void doSomething() {

    }

}