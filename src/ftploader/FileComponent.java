package ftploader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableColumn;

public class FileComponent extends JScrollPane {

    private File f;
    private JTable tableList;
    private MyTableModel mtm;

    public FileComponent() {
        ShowDir(f);
    }

    public MyTableModel getMtm() {
        return mtm;
    }

 

    private void ShowDir(File f) {
        try {

            tableList = new JTable();
            mtm = new MyTableModel("C:\\");
            tableList.setModel(mtm);//invoking our custom model
            tableList.setDefaultRenderer(JLabel.class, new Renderer());// for the rendering of cell
            tableList.setShowGrid(false);
            //tableList.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableList.getColumnModel().getColumn(1).setPreferredWidth(10);
            tableList.getColumnModel().getColumn(2).setPreferredWidth(10);
            TableColumn column = tableList.getColumnModel().getColumn(4);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setPreferredWidth(0);

            tableList.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

            tableList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        JTable target = (JTable) e.getSource();
                        int row = target.getSelectedRow();
                        int column = target.getSelectedColumn();
                        String path = mtm.ChangeDir(row);
                        if (path != null) { //isDir
                            mtm = new MyTableModel(path);
                            tableList.setModel(mtm);//invoking our custom model
                        } else {
                            //isFile
                        }
                    }
                }
            });
            JScrollPane jp = new JScrollPane(tableList);

            this.viewport.add(tableList);

        } catch (Exception ex) {
            System.out.println("prazdny seznam");
        }

    }
}
