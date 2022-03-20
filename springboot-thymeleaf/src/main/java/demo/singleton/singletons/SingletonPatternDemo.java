package demo.singleton.singletons;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.singleton.singletons
 * @Author: yang
 * @CreateTime: 2021-07-25 19:05
 * @Description:
 */
public class SingletonPatternDemo {
    public static void main(String[] args) {
        //不合法的构造函数
        //编译时错误:构造函数SingleObject()是不可见的
        //SingleObject object = new SingleObject();
        //获取唯一可用的对象
        SingleObject object = SingleObject.getInstance();
        //显示消息
        object.showMessage();
    }

}
