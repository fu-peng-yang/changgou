package demo.blockingQueue;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.blockingQueue
 * @Author: yang
 * @CreateTime: 2021-04-30 14:59
 * @Description:阻塞队列
 */

public class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS =100;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);


    public static void main(String[] args) {
        try(Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory(e.g. /opt/jdk1.8.0/src):");
            String directory = in.nextLine();
            System.out.print("Enter base directory(e.g. volatile):");
            String keyword = in.nextLine();
            Runnable enumerator = () ->{
                try {
                    enumrate(new File(directory));
                }catch (InterruptedException e){

                }

            };

            new Thread(enumerator).start();
            for (int i = 1;i<=SEARCH_THREADS;i++){
                Runnable searcher = ()->{
                    try {
                        boolean done = false;
                        while (!done){
                            File file = queue.take();
                            if (file == DUMMY){
                                queue.put(file);
                                done = true;
                            }else {
                                search(file,keyword);
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }catch (InterruptedException e){

                    }



                };
                new Thread(searcher).start();
            }
        }
    }

    /**
     * Recursively enumerates all files in a given directory and its subdirectories.
     * @param directory the directory in which to start
     * @throws InterruptedException
     */
    public static void enumrate(File directory) throws InterruptedException{
        File[] files = directory.listFiles();
        for (File file:files){
            if (file.isDirectory()){
                enumrate(file);
            }else {
                queue.put(file);
            }
        }
    }

    /**
     * Searches a file for a given keyword and prints all matching lines.
     * @param file the file to search
     * @param keyword the keyword to search for
     * @throws IOException
     */
    public static void search(File file,String keyword) throws IOException {
        try(Scanner in = new Scanner(file,"UTF-8")) {
            int lineNumber = 0;
            while (in.hasNext()){
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword)){
                    System.out.printf("%s:%d:%s%n",file.getPath(),lineNumber,line);
                }
            }
        }
    }
}
