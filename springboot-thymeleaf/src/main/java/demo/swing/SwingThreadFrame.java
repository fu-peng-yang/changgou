package demo.swing;

import javax.swing.*;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.swing
 * @Author: yang
 * @CreateTime: 2021-05-01 09:24
 * @Description:
 */
public class SwingThreadFrame extends JFrame {

    public SwingThreadFrame(){
        final JComboBox<Integer> combo = new JComboBox<>();
        combo.insertItemAt(Integer.MAX_VALUE,0);
        combo.setPrototypeDisplayValue(combo.getItemAt(0));
        combo.setSelectedIndex(0);

        JPanel panel = new JPanel();

        JButton goodButton = new JButton("Good");
        goodButton.addActionListener(event ->new Thread(new BadWorkerRunnable(combo)).start());
        panel.add(combo);
        add(panel);
        pack();
    }
}
