package zhishi;

import java.util.ArrayList;
import java.util.Iterator;


public class ArrayListDemo {
    public static void main(String[] args) {

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        System.out.println("长度：" + arrayList.size());

        Integer[] integers = new Integer[arrayList.size()];
        integers = (Integer[]) arrayList.toArray();
        System.out.println("通过迭代器遍历");
        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next()+" ");;
        }

        System.out.println("通过索引遍历");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }

        System.out.println("增强for循环遍历");
        for (Integer integer : arrayList) {
            System.out.println(integer);
        }


        //Integer[] integers = arrayList.toArray(new Integer[0]);
        System.out.println();
        arrayList.add(2, 9);
        arrayList.remove(2);
        arrayList.remove((Object) 5);
        System.out.println(arrayList.contains(9));

        arrayList.clear();
        for (Integer integer : arrayList) {
            System.out.println("遍历"+integer);
        }
        System.out.printf("", arrayList.isEmpty());
    }
}
