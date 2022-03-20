package demo.threadPool;

import java.io.File;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.threadPool
 * @Author: yang
 * @CreateTime: 2021-04-30 17:44
 * @Description:
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws  Exception{
        try (Scanner in = new Scanner(System.in)){
            System.out.print("Enter base directory(e.g. /usr/local/jdk5.0/src):");
            String directory = in.nextLine();
            System.out.println("Enter keyword(e.g. volatile):");
            String keyword = in.nextLine();

            ExecutorService pool = Executors.newCachedThreadPool();
            MatchCounter counter = new MatchCounter(new File(directory),keyword,pool);
            Future<Integer> result = pool.submit(counter);
            System.out.println(result.get()+" matching files");
            pool.shutdown();



        }
    }
}
