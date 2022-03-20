package demo.io.streams;

import jdk.nashorn.internal.runtime.options.Option;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.io.streams
 * @Author: yang
 * @CreateTime: 2021-05-01 14:30
 * @Description:
 */
public class CreatingStreams {
    public static <T> void show(String title, Stream<T> stream){
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print(title +": ");
        for (int i = 0;i<firstElements.size();i++){
            if (i>0){
                System.out.print(".");
            }
            if (i<SIZE){
                System.out.println(firstElements.get(i));
            }else {
                System.out.print("...");
            }
        }

    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:/log/sfdsdfsdf.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        Stream<String> words = Stream.of(contents.split("!"));
        show("words",words);
        Stream<String> song = Stream.of("gently","down","the","stream");
        show("song",song);
        Stream<String> silence = Stream.empty();
        show("Silence",silence);

        Stream<String> echos = Stream.generate(() -> "Echo");
        show("echos",echos);

        Stream<Double> randoms = Stream.generate(Math::random);
        show("randoms",randoms);

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE,n->n.add(BigInteger.ONE));
        show("integers",integers);

        Stream<String> wordAnotherway = Pattern.compile("!").splitAsStream(contents);
        show("wordAnotherway",wordAnotherway);

        /*try (Stream<String > lines = Files.lines(path,StandardCharsets.UTF_8);){
            show("lines",lines);
        }*/

        //会产生100个随机数的流
        Stream<Double> randomss = Stream.generate(Math::random).limit(100);

        /**
         * 当实际访问一个元素时,就会打印出来一条消息,通过这种方式,可以验证iter
         * 返回的无线流失被惰性处理的
         * 对于调试,可以让peek调用一个设置了断点的方法
         */
        Object[] powers = Stream.iterate(1.0,p->p*2).peek(e-> System.out.println("Feching "+e)).limit(20).toArray();

        /**
         * 获取流中的最大值
         */
        Optional<String> largest = words.max(String::compareToIgnoreCase);
        System.out.println("largest: "+largest.orElse(""));
    }
}
