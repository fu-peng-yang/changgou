package demo.dom.read;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.dom.read
 * @Author: yang
 * @CreateTime: 2021-05-08 22:36
 * @Description:
 */
public class FontFrame extends JFrame {
    private GridBagPane gridbag;
    private JComboBox<String> face;
    private JComboBox<String> size;
    private JCheckBox bold;
    private JCheckBox italic;

    @SuppressWarnings(("unchecked"))
    public FontFrame(File file){
        gridbag = new GridBagPane(file);
        add(gridbag);

        face = (JComboBox<String>) gridbag.get("face");
        size = (JComboBox<String>) gridbag.get("size");
        bold = (JCheckBox) gridbag.get("bold");
        italic = (JCheckBox) gridbag.get("italic");

        face.setModel(new DefaultComboBoxModel<String>(new String[]{"Serif",
        "SansSerif","Monospaced","Dialog","DialogInput"}));
        size.setModel(new DefaultComboBoxModel<String>(new String[]{"8",
        "10","12","15","18","24","36","48"}));

        ActionListener listener = event -> setSample();

        face.addActionListener(listener);
        size.addActionListener(listener);
        bold.addActionListener(listener);
        italic.addActionListener(listener);
        setSample();
    }

    /**
     * This method sets the text sample to the selected font.
     */
    public void setSample(){
        String fontFace = face.getItemAt(face.getSelectedIndex());
        int fontSize = Integer.parseInt(size.getItemAt(size.getSelectedIndex()));
        JTextArea sample = (JTextArea) gridbag.get("sample");
        int fontStyle = (bold.isSelected() ? Font.BOLD :0)+(italic.isSelected() ? Font.ITALIC :0);

        sample.setFont(new Font(fontFace,fontStyle,fontSize));
        sample.repaint();
    }
}
