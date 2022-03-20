package demo.file.regex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.file.regex
 * @Author: yang
 * @CreateTime: 2021-05-02 19:56
 * @Description:
 */
public class HrefMatch {
    public static void main(String[] args) {
        try {
            //get URL string from command line or use default
            String urlString;
            if (args.length>0){
                urlString = args[0];
            }else {
                urlString = "http://java.sun.com";
            }

            //open reader for URL
            InputStreamReader in = new InputStreamReader(new URL(urlString).openStream(), StandardCharsets.UTF_8);

            //read contents into string builder
            StringBuilder input = new StringBuilder();
            int ch;
            while ((ch= in.read())!= -1){
                input.append((char) ch);
            }

            //search for all occurrences of pattern
            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^s>]*)\\s*>";
            Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()){
                String match = matcher.group();
                System.out.println(match);
            }
        }catch (IOException | PatternSyntaxException e){
            e.printStackTrace();
        }
    }
}
