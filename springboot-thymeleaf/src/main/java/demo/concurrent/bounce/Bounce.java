package demo.concurrent.bounce;

import javax.swing.*;
import java.awt.*;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.bounce
 * @Author: yang
 * @CreateTime: 2021-04-25 14:32
 * @Description: 并发
 */
public class Bounce {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new BounceFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });
    }
}
