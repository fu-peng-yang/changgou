package demo.internet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.internet
 * @Author: yang
 * @CreateTime: 2021-05-09 08:37
 * @Description:
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
        /*try (Socket s = new Socket("time-a.nist.gov",13);//打开一个套接字,负责启动程序内部和外部之间的通信
        Scanner in = new Scanner(s.getInputStream(),"UTF-8")){
            while (in.hasNextLine()){
                String line = in.nextLine();
                System.out.println("成功"+line);
            }
        }*/
        if (args.length >0){
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for(InetAddress a :addresses){
                System.out.println("成功"+a);
            }
        }else {
            InetAddress localHostAddress = InetAddress.getLocalHost();
            System.out.println("主机地址"+localHostAddress);
        }
    }
}

