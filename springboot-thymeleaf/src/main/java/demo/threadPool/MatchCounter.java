package demo.threadPool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.threadPool
 * @Author: yang
 * @CreateTime: 2021-04-30 17:25
 * @Description:
 */
public class MatchCounter implements Callable<Integer> {
   private File directory;
   private String keyword;
   private int count;
   private ExecutorService pool;

    /**
     * This counts the files in a directory and its subdirectories the contain a given keyword
     * @param directory
     * @param keyword
     * @param count
     */
    public MatchCounter(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() {
        count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();
            for (File file:files){
                if (file.isDirectory()){
                    MatchCounter counter = new MatchCounter(file,keyword,pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                }else {
                    if (search(file)){
                        count++;
                    }
                }
                for (Future<Integer> result:results){
                    try {
                        count += result.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (InterruptedException e){

        }
        return count;
    }

    /**
     * Searches a file for a given keyword
     * @param file the file to search
     * @return true if the keyword is contained the file
     */
    public boolean search(File file){
        try {
            try(Scanner in = new Scanner(file,"UTF-8")){
                boolean found = false;
                while (!found && in.hasNextLine()){
                    String line = in.nextLine();
                    if (line.contains(keyword)){
                        found = true;
                    }
                }
                return found;

            }
        }catch (IOException e){
            return  false;
        }

    }
}
