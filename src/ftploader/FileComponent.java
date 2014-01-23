package ftploader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class FileComponent extends JScrollPane implements MouseListener, ActionListener {

    private File f;
    private JList fileList;
    private Boolean vertical;
    private JLabel back;
    private JButton btn;

    public FileComponent() {
        f = new File(System.getProperty("user.home"));
        ShowDir(f);
    }

    public FileComponent(String path) {
        f = new File(path);
        ShowDir(f);
    }

    private void ShowDir(File f) {
        try {

            fileList = new JList(f.listFiles());
            vertical = false;
            fileList.setCellRenderer(new FileRenderer(vertical));
            fileList.addMouseListener(this);

            btn = new JButton("back");
            btn.addActionListener(this);
            btn.setActionCommand("b");

            JViewport jv2 = new JViewport();
            jv2.setView(btn);
            this.setColumnHeader(jv2);

            if (f.listFiles() != null) {
                this.viewport.add(fileList);
            } else {
                this.viewport.add(new JLabel("Ve složce není žádný obsah"));
                this.viewport.setLocation(0, 0);
                
            }
        } catch (Exception ex) {
            System.out.println("prazdny seznam pico");
        }

    }

    private void ChangeDir(String path) {
        System.out.println(path);
        f = new File(path);
        ShowDir(f);
    }

    private void ParentDir() {
        try {
            f = f.getParentFile();
            ShowDir(f);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /* listeneres */

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (e.getActionCommand().equals("b")) {
            System.out.println("Button pressed!");
            ParentDir();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (((File) fileList.getSelectedValue()).isDirectory()) //if clicked file is directory
            {
                ChangeDir(fileList.getSelectedValue().toString());//change directory
            } else //clicked file is file
            {
                //open it
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
