package demo.reflect;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.reflect
 * @Author: yang
 * @CreateTime: 2021-02-09 15:12
 * @Description:反射
 */
public class Get {
    /**
     * 获取反射的三种方法
     * 1.通过new对象实现反射机制 2.通过路径实现反射机制 3.通过类名实现反射机制
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {

        //方式一(通过建立对象)
        Student stu = new Student();
        Class aClass = stu.getClass();
        System.out.println(aClass.getName());
        //方式二(所在通过路径.相对路径)
        Class<?> aClass1 = Class.forName("demo.reflect.Student");
        System.out.println(aClass1.getName());
        //方式三(通过类名)
        Class aClass2=Student.class;
        System.out.println(aClass2.getName());

    }


}
