package demo.concurrent.atomic;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }


}
