package demo.io.collecting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.io.collecting
 * @Author: yang
 * @CreateTime: 2021-05-01 17:03
 * @Description: 从流中收集元素
 */
public class CollectingResults {
    public static Stream<String> noVowels() throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("D:/log/sfdsdfsdf.txt")), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("!"));
        Stream<String> words = wordList.stream();
        return words.map(s -> s.replaceAll("[aeiouAEIOU]",""));
    }

    public static <T> void show(String label, Set<T> set){
        System.out.print(label+": "+set.getClass().getName());
        System.out.println("["+set.stream().limit(10).map(Objects::toString).collect(Collectors.joining(", "))+"]");
    }

    public static void main(String[] args) throws IOException {
        Iterator<Integer> iter = Stream.iterate(0,n -> n+1).limit(10).iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
        Object[] numbers = Stream.iterate(0,n -> n+1).limit(10).toArray();
        System.out.println("Object array:"+numbers);//Note it's an Object[] array

        try{
            Integer number = (Integer) numbers[0]; //OK
            System.out.println("number:"+ number);
            System.out.println("The following statement throws an exception:");
            Integer[] numbers2 = (Integer[]) numbers;
        }catch (ClassCastException ex){
            System.out.println(ex);
        }


        Integer[] numbers3 = Stream.iterate(0,n -> n+1).limit(10).toArray(Integer[]::new);
        System.out.println("Integer array: "+numbers3);//Note it's an Integer[] array

        //将流收集到列表或集中  List同理
        Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
        show("noVowelSet",noVowelSet);

        //控制获得的集的种类
        TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet",noVowelTreeSet);

        //通过连接操作来收集流中的所有字符串
        String result = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("Joining" + result);
        //在元素之间增加分隔符
        result = noVowels().limit(10).collect(Collectors.joining(", "));
        System.out.println("Joining with comas: "+result);

        //将流的结果约简为总和 平均值 最大值或最小值
        IntSummaryStatistics summary =noVowels().collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average Word Length: " +averageWordLength);
        System.out.println("Max Word Length:"+maxWordLength);
        System.out.println("forEach:");
        noVowels().limit(10).forEach(System.out::println);
    }
}
