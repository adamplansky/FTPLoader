package ftploader;

import java.awt.Button;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JList;

import javax.swing.JScrollPane;

public class FileComponent extends JScrollPane implements MouseListener {

    private File f;
    private JList fileList;
    private Boolean vertical;
    private Button back;

    public FileComponent() {
        f = new File(System.getProperty("user.home"));
        ShowDir(f);
    }

    public FileComponent(String path) {
        f = new File(path);
        ShowDir(f);
    }

    private void ShowDir(File f) {
        fileList = new JList(f.listFiles());
        vertical = true;
        fileList.setCellRenderer(new FileRenderer(vertical));
        fileList.addMouseListener(this);
        back = new Button("abcd");

        this.viewport.add(fileList);
        this.add(back);
    }

    private void ChangeDir(String path) {
        System.out.println(path);
        f = new File(path);
        ShowDir(f);
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
