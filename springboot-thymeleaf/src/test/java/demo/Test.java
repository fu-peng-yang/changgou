package demo;

public class Test {
    public static void swap(int a,int b){
        int temp = a;
        a = b;
        b = temp;
        System.out.println("a="+a);
        System.out.println("b="+b);
    }
    public static void change(int[] array){
        array[0] = 9;
    }
    public static void main(String[] args) {
        int num1 = 10;
        int num2 =80;

        swap(num1,num2);
        System.out.println("num1=" + num1);
        System.out.println("num2="+num2);
    }
}
