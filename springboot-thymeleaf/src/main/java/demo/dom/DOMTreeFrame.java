package demo.dom;

import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.dom
 * @Author: yang
 * @CreateTime: 2021-05-08 19:18
 * @Description:
 */
 class DOMTreeFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    private DocumentBuilder builder;

    /**
     * THis frame contains a tree that displays the contents of an XML document
     */
    public DOMTreeFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(event -> openFile());
        fileMenu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Open a file and load the document
     */
    public void openFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("dom"));
        chooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int r = chooser.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION){
            return;
        }
        final File file = chooser.getSelectedFile();

        new SwingWorker<org.w3c.dom.Document, Void>(){
            protected org.w3c.dom.Document doInBackground() throws Exception{
                if (builder == null){
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    builder = factory.newDocumentBuilder();
                }
                return  builder.parse(file);
            }
            protected void done(){
                try {
                    Document document = get();
                    JTree tree = new JTree(new DOMTreeModel(document));
                } catch (Exception e){
                    JOptionPane.showMessageDialog(DOMTreeFrame.this,e);
                }


            }
        }.execute();
    }

}
