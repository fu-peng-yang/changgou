package zhishi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TryWithResource {
    public static void tryWithResource(){
        try (Scanner scanner = new Scanner(new File("test.txt"));
             Scanner scanner1 = new Scanner(new File("test.txt"));){
            while (scanner.hasNext()){
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
