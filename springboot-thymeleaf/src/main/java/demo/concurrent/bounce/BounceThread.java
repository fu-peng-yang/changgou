package demo.concurrent.bounce;

import javax.swing.*;
import java.awt.*;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.concurrent.bounce
 * @Author: yang
 * @CreateTime: 2021-04-25 15:41
 * @Description:Shows animated bouncing balls.
 */
public class BounceThread {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new BounceFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });
    }
}
