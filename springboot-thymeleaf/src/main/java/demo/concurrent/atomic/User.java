package demo.concurrent.atomic;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class User {
    private String name;
    public volatile int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
