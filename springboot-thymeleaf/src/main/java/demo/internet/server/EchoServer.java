package demo.internet.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.internet.server
 * @Author: yang
 * @CreateTime: 2021-05-09 09:12
 * @Description:
 */
public class EchoServer {
    public static void main(String[] args) throws IOException {
        //establish server socket
        try (ServerSocket s = new ServerSocket(8139)){
            //wait for client connection
            try (Socket incoming = s.accept()){
                InputStream inputStream = incoming.getInputStream();
                OutputStream outputStream = incoming.getOutputStream();
                try (Scanner in = new Scanner(inputStream,"UTF-8")){
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);
                    out.println("Hello! 成功 Enter BYE to exit.");
                    boolean done = false;
                    while (!done && in.hasNextLine()){
                        String line = in.nextLine();
                        out.println(" Echo: " + line);
                        if (line.trim().equals("BYE")){
                            done = true;
                        }
                    }

                }
            }
        }
    }

}
