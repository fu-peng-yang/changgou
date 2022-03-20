package demo.dom;

import javax.swing.*;
import java.awt.*;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.dom
 * @Author: yang
 * @CreateTime: 2021-05-08 19:18
 * @Description: XML
 */
public class Treeviewer {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{

            JFrame frame = new DOMTreeFrame();
            frame.setTitle("TreeViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
