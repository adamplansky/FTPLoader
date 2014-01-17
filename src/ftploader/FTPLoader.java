package ftploader;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.listparsers.DOSListParser;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileFilter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Adam Plánský FIT username:plansada email: plansada@fit.cvut.cz
 * OMGFG
 * asdfasdfasdf
 */
public class FTPLoader {

   public Component getGui(File[] all, boolean vertical) {
        // put File objects in the list..
        JList fileList = new JList(all);
        // ..then use a renderer
        fileList.setCellRenderer(new FileRenderer(!vertical));

        if (!vertical) {
            fileList.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
            fileList.setVisibleRowCount(-1);
        } else {
            fileList.setVisibleRowCount(9);
        }
        return new JScrollPane(fileList);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                File f = new File(System.getProperty("user.home"));
                FTPLoader fl = new FTPLoader();
                Component c1 = fl.getGui(f.listFiles(new TextFileFilter()),true);

                //f = new File(System.getProperty("user.home"));
                Component c2 = fl.getGui(f.listFiles(new TextFileFilter()),false);

                JFrame frame = new JFrame("File List");
                JPanel gui = new JPanel(new BorderLayout());
                gui.add(c1,BorderLayout.WEST);
                gui.add(c2,BorderLayout.CENTER);
                c2.setPreferredSize(new Dimension(375,100));
                gui.setBorder(new EmptyBorder(3,3,3,3));

                frame.setContentPane(gui);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class TextFileFilter implements FileFilter {

    public boolean accept(File file) {
        // implement the logic to select files here..
        String name = file.getName().toLowerCase();
        //return name.endsWith(".java") || name.endsWith(".class");
        return name.length()<20;
    }
}

class FileRenderer extends DefaultListCellRenderer {

    private boolean pad;
    private Border padBorder = new EmptyBorder(3,3,3,3);

    FileRenderer(boolean pad) {
        this.pad = pad;
    }

    @Override
    public Component getListCellRendererComponent(
        JList list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(
            list,value,index,isSelected,cellHasFocus);
        JLabel l = (JLabel)c;
        File f = (File)value;
        l.setText(f.getName());
        l.setIcon(FileSystemView.getFileSystemView().getSystemIcon(f));
        if (pad) {
            l.setBorder(padBorder);
        }

        return l;
    }
}
//        FTPClient client = new FTPClient();
//        try {
//            client.connect("56403.w3.wedos.net");
//            client.login("w56403", "uvjFgvVX");
//            client.changeDirectory("www");
//            System.out.println(client.currentDirectory());
//            FTPFile[] files = client.list();
//            for(FTPFile file : files)
//            {
//                DOSListParser dlp = new DOSListParser();
//                
//                System.out.println(file.getName() +   "  " + file.getSize() + "b");
//            }
//
//            client.disconnect(true);
//
//        } catch (Exception e) {
//
//        }

//    }
//}
