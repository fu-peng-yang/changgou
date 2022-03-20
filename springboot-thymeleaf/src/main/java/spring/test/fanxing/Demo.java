package spring.test.fanxing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Demo {
    public static <E> void printArray(E[] inputArray){
        for (E element: inputArray){
            System.out.printf("%S ",element);
        }

    }
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Integer> list = new ArrayList<>();
        list.add(12);
        Class<? extends List> classzz=list.getClass();
        Method add = classzz.getDeclaredMethod("add", Object.class);
        add.invoke(list, "kl");
        System.out.println(list);
        Generic<Integer> generic = new Generic<>(133);
        Integer[] intArray = {1,2,3};
        String[] stringArray = {"Hello","World"};
        printArray(intArray);
        printArray(stringArray);

    }
}
