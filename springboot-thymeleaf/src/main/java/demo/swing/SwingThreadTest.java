package demo.swing;

import javax.swing.*;
import java.awt.*;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.swing
 * @Author: yang
 * @CreateTime: 2021-05-01 09:22
 * @Description:
 */
public class SwingThreadTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new SwingThreadFrame();
            frame.setTitle("SwingThreadTest");
            frame.setVisible(true);
        });
    }
}
