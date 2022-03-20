package demo.dom.read;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.dom.read
 * @Author: yang
 * @CreateTime: 2021-05-08 22:52
 * @Description:
 */
public class GridBagTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFileChooser chooser = new JFileChooser(".");
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            JFrame frame = new FontFrame(file);
            frame.setTitle("GridBagTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
