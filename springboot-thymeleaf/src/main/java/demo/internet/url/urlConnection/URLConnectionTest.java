package demo.internet.url.urlConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.internet.url.urlConnection
 * @Author: yang
 * @CreateTime: 2021-05-09 13:05
 * @Description:
 */
public class URLConnectionTest {
    public static void main(String[] args) {

        try {
            String urlName;
            if (args.length > 0){
                urlName = args[0];
            }else {
                urlName = "http://horstmann.com";
            }
            URL url = new URL(urlName);
            URLConnection urlConnection = url.openConnection();
            //set username,password if sepcified on command line

            if (args.length > 2){
                String username = args[1];
                String password = args[2];
                String input = username +":"+password;
                Base64.Encoder encoder = Base64.getEncoder();
                encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
            }
            urlConnection.connect();

            //print header fields
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            for(Map.Entry<String,List<String>> entry:headers.entrySet()){
                String key = entry.getKey();
                for (String value : entry.getValue()){
                    System.out.println(key+":"+value);
                }
            }
            System.out.println("---------------------");
            System.out.println("getContentType: "+urlConnection.getContentType());
            System.out.println("getContentLength: "+urlConnection.getContentLength());
            System.out.println("getContentEncoding: "+urlConnection.getContentEncoding());
            System.out.println("getDate: "+urlConnection.getDate());
            System.out.println("getExpiration: "+urlConnection.getExpiration());
            System.out.println("getLastModified: "+urlConnection.getLastModified());
            System.out.println("-----------------------");

            String contentEncoding = urlConnection.getContentEncoding();
            if(contentEncoding == null){
                contentEncoding = "UTF-8";
            }
            try (Scanner in = new Scanner(urlConnection.getInputStream(),contentEncoding)){
                //print first ten lines of contents
                for (int n = 1;in.hasNextLine() && n <= 10;n++){
                    System.out.println(in.nextLine());
                    if (in.hasNextLine()){
                        System.out.println(". . .");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
