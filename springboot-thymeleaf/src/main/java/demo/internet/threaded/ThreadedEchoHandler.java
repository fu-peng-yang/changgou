package demo.internet.threaded;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.internet.threaded
 * @Author: yang
 * @CreateTime: 2021-05-09 09:51
 * @Description:
 */
public class ThreadedEchoHandler implements Runnable{

    private Socket incoming;

    public ThreadedEchoHandler(Socket incomingSocket) {
        this.incoming = incomingSocket;
    }

    @Override
    public void run() {
       try (InputStream inputStream = incoming.getInputStream();
            OutputStream outputStream = incoming.getOutputStream()){
           Scanner in = new Scanner(inputStream,"UTF-8");
           PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);
                   out.println("Hello!输入 BYE 退出");
                   boolean done = false;
                   while (!done && in.hasNextLine()){
                       String line = in.nextLine();
                       out.println("Echo: "+line);
                       if (line.trim().equals("BYE")){
                           done = true;
                       }
                   }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
