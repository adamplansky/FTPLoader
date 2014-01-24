/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftploader;

import it.sauronsoftware.ftp4j.FTPFile;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author impz
 */
public class FTPComponent extends JScrollPane {

    private FTPFile f;
    private JTable tableList;
    private FTPTableModel ftm;
    //private Object[][] fileRow;
    private final FileComponent fc;

    public FTPComponent(FileComponent fc) {
        this.fc = fc;
        ShowDir(f);

    }

    private void ShowDir(FTPFile f) {
        tableList = new JTable();
        ftm = new FTPTableModel("56403.w3.wedos.net", "w56403", "uvjFgvVX", "www", fc, tableList);
        tableList.setModel(ftm);
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
                    try {
                        String dir = ftm.ChangeDir(row);
                        if (dir != null) {
                            ftm = new FTPTableModel("56403.w3.wedos.net", "w56403", "uvjFgvVX", dir, fc, tableList);
                            tableList.setModel(ftm);//invoking our custom model
                        }
//                       
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JScrollPane jp = new JScrollPane(tableList);

        this.viewport.add(tableList);
    }

    public void compare() {
        ftm.compare();
        //tableList.setModel(ftm);
        tableList.revalidate();
        tableList.repaint();
        
        
        

    }
}
