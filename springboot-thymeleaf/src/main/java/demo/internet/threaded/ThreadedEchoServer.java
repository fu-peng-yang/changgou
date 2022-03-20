package demo.internet.threaded;

import javax.swing.tree.TreeModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.internet.threaded
 * @Author: yang
 * @CreateTime: 2021-05-09 09:50
 * @Description:
 */
public class ThreadedEchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket s = new ServerSocket(8189)){
            int i = 1;
            while (true){
                Socket incoming = s.accept();
                System.out.println(" Spawning "+i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }
    }
}

