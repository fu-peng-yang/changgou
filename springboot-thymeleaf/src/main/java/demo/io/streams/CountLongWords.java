package demo.io.streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.io.streams
 * @Author: yang
 * @CreateTime: 2021-05-01 13:46
 * @Description: 流
 */
public class CountLongWords {
    public static void main(String[] args) throws IOException {
        String fileName="D:/log/sfdsdfsdf.txt";
        String contents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("!"));

        long count=0;
        for (String w:words){
            if (w.length()>10){
                count++;
            }
        }
        System.out.println(count);

        count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println(count);

        count = words.parallelStream().filter(w -> w.length()>12).count();
        System.out.println(count);
    }
}
