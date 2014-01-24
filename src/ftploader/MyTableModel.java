package ftploader;

import java.awt.Component;
import java.io.File;
import java.lang.reflect.Array;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableCellRenderer;

class MyTableModel extends javax.swing.table.DefaultTableModel {

    Object[] col = {"Jméno", "Přípona", "Velikost", "SUM", "OK"};
    Object[][] row;
    File[] files;
    private Boolean isDir = false;

    public Object[][] getRow() {
        return row;
    }

    public MyTableModel(String path) {
        //File f = new File(System.getProperty("user.home"));
        File f = new File(path);
        int arrLen = 1;
        if (f.listFiles() != null) {
            arrLen = f.listFiles().length + 1;//+1 = back 
        }
        row = new Object[arrLen][col.length];
        files = new File[arrLen];
        files[0] = f;

        int counter = 0;
        JLabel l = new JLabel();
        l.setIcon(new ImageIcon("icons/back.png"));
        l.setText("[..]");
        row[counter][0] = l;
        row[counter++][4] = 0;
        if (f.listFiles() != null) {
            System.arraycopy(f.listFiles(), 0, files, 1, arrLen - 1);
        }
        Boolean isDir = false;
        for (int i = 1; i < arrLen; i++) {
//            if (files[i].isHidden()) {
//                continue;
//            }
            l = new JLabel();
            l.setIcon(FileSystemView.getFileSystemView().getSystemIcon(files[i]));
            isDir = files[i].isDirectory();
            if (isDir) {
                l.setText(setFolderName(files[i].getName()));

            } else {
                l.setText(files[i].getName());
                //row[counter][3] = Hash.checksum(files[i]);
            }

            row[counter][0] = l;
            row[counter][1] = getExtension(files[i].getName());
            row[counter][2] = isDir ? "<DIR>" : files[i].length();
            row[counter][4] = 0;
            files[counter] = files[i];
            counter++;
        }

        //Adding columns
        for (Object c : col) {
            this.addColumn(c);
        }

        //Adding rows
        for (Object[] r : row) {
            addRow(r);
        }

    }

    public String ChangeDir(int row) {
        System.out.println("mtm");

        System.out.println(files[row].getAbsoluteFile());
        if (row == 0) {
            System.out.println(files[row].getParent());
            return files[row].getParent();
        }
        if (files[row].isDirectory()) {
            return files[row].getAbsoluteFile().toString();
        }
        return null;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return getValueAt(0, columnIndex).getClass();
        } else {
            return super.getColumnClass(columnIndex);
        }

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private String setFolderName(String name) {
        return "[ " + name + " ]";
    }

    private String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}
