package demo.concurrent.atomic;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import shuati.Main;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author Administrator
 * 对象属性修改类型原子类
 * 要想原子地更新对象的属性需要两步。第一步，因为对象的属性修改类型原子类都是抽象类，所以每次
 * 使用都必须使用静态方法 newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。第二
 * 步，更新的对象属性必须使用 public volatile 修饰符。
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
        User user = new User("JAVA",22);
        System.out.println(a.getAndIncrement(user));//22
        System.out.println(a.get(user));//23
    }
}
