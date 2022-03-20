package demo.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 * 引用类型原子类
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Person> ar = new AtomicReference<>();

        Person person = new Person("Daisy", 20);
        ar.set(person);
        Person updatePerson = new Person("Snailclib", 22);
        ar.compareAndSet(person,updatePerson);
        System.out.println(ar.get().getName());
        System.out.println(ar.get().getAge());
    }

}
