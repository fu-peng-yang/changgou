package demo.io.optional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.io.optional
 * @Author: yang
 * @CreateTime: 2021-05-01 16:16
 * @Description: Optional API的使用方式
 */
public class OptionalTest {

    public static Optional<Double> inverse(Double x){
        return x== 0? Optional.empty() :Optional.of(1/x);
    }

    public static Optional<Double> squareRoot(Double x){
        return x < 0 ? Optional.empty() :Optional.of(Math.sqrt(x));
    }
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("D:/log/sfdsdfsdf.txt")), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("!"));

        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("fred")).findFirst();
        System.out.println(optionalValue.orElse("NO word")+" contains fred");

        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("result: "+result);
        result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("result11111: "+result);
        try {
            result = optionalString.orElseThrow(IllegalStateException::new);
            System.out.println("result: "+result);
        }catch (Throwable t){
            t.printStackTrace();
        }
        optionalValue = wordList.stream().filter(s -> s.contains("red")).findFirst();
        optionalValue.ifPresent(s -> System.out.println(s + " contains red"));

        Set<String> results = new HashSet<>();
        optionalValue.ifPresent(results::add);
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println(added);

        System.out.println(inverse(4.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(-1.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(0.0).flatMap(OptionalTest::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
        System.out.println(result2);
    }
}
