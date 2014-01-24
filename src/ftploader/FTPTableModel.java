/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftploader;

import static com.sun.corba.se.impl.util.RepositoryId.cache;
import it.sauronsoftware.ftp4j.*;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.listparsers.DOSListParser;
import java.io.File;
import java.io.IOException;
import static java.lang.Boolean.FALSE;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author impz
 */
public class FTPTableModel extends javax.swing.table.DefaultTableModel {

    Object[] col = {"Jméno", "Přípona", "Velikost", "SUM", "OK"};
    Object[][] row;
    FileComponent fc;
    FTPFile[] files;
    private Boolean isDir = false;
    FTPClient client;
    JTable tableList;

    public FTPTableModel(String host, String user, String password, String dir, FileComponent fc, JTable tableList) {
        this.fc = fc;
        this.tableList = tableList;
        client = new FTPClient();
        try {
            client.connect(host);
            client.login(user, password);
            client.changeDirectory(dir);
            int arrLen = 0;
            if (client.list() != null) {
                arrLen = client.list().length + 1;//+1 = back 
            }
            row = new Object[arrLen][col.length];
            files = client.list();
            int counter = 0;
            JLabel l = new JLabel();
            l.setIcon(new ImageIcon("icons/back.png"));
            l.setText("[..]");
            row[counter][0] = l;
            row[counter++][4] = 0;
            for (FTPFile file : files) {

                DOSListParser dlp = new DOSListParser();
                // iconutils
                l = new JLabel();
                String ext = getExtension(file.getName());
                if (file.getType() == FTPFile.TYPE_DIRECTORY) {
                    isDir = true;
                } else {
                    isDir = false;
                }
                if (ext.length() > 0) {
                    l.setText(file.getName());
                    l.setIcon(getIconByExtension(ext));
                } else {
                    l.setIcon(getFolderIcon());
                    l.setText(setFolderName(file.getName()));
                }

                //row[counter][0] = l;
                row[counter][0] = l;//getIconByExtension(ext);
                row[counter][1] = ext;
                row[counter][2] = isDir ? "<DIR>" : file.getSize();
                row[counter][4] = 0;
                counter++;
                System.out.println(file.getName() + "  " + file.getSize() + "b");
            }

            //Adding columns
            for (Object c : col) {
                this.addColumn(c);
            }

            //Adding rows
            for (Object[] r : row) {
                addRow(r);
            }

        } catch (Exception e) {

        }
//      
    }

    public void Disconnect() throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException {
        client.disconnect(true);
    }

    public String ChangeDir(int r) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException {

        if (r == 0) {
            client.changeDirectoryUp();
        } else {
            if (files[r - 1].getType() == FTPFile.TYPE_FILE) {
                return null;
            }
            client.changeDirectory(files[r - 1].getName());
        }
        return client.currentDirectory();
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

    public static Icon getIconByExtension(String extension) {

        extension = extension.toLowerCase();

        try {
            File file = File.createTempFile("icon", "." + extension);
            FileSystemView view = FileSystemView.getFileSystemView();
            Icon smallIcon = view.getSystemIcon(file);
            // ShellFolder shellFolder = ShellFolder.getShellFolder(file);
            // Icon bigIcon = new ImageIcon(shellFolder.getIcon(true));
            file.delete();

            //cache.put(extension, smallIcon);
            return smallIcon;
        } catch (Exception e) {
            e.printStackTrace();
            // TD£º·µ»ØÍ¨ÓÃµÄÍ¼±ê
            return new ImageIcon("");

        }

    }

    public static Icon getFolderIcon() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
        file.delete();
        return icon;
    }

    public FTPTableModel compare() {
        ArrayList<String> filenames = new ArrayList<>();
        ArrayList<String> ftpnames = new ArrayList<>();

        ArrayList<Long> filesize = new ArrayList<>();
        ArrayList<Long> ftpsize = new ArrayList<>();
        Object[][] fileRow = fc.getMtm().getRow();
        for (int i = 0; i < fileRow.length; i++) {
            filenames.add(((JLabel) fileRow[i][0]).getText());
            if (fileRow[i][2] instanceof String || fileRow[i][2] == null) {
                filesize.add(0l);
            } else {
                filesize.add((Long) fileRow[i][2]);
            }
        }
        for (int i = 0; i < row.length; i++) {
            ftpnames.add(((JLabel) row[i][0]).getText());
            if (row[i][2] instanceof String || row[i][2] == null) {
                ftpsize.add(0l);
            } else {
                ftpsize.add((Long) row[i][2]);
            }
        }
//        System.out.println("done");
//        System.out.println(filenames.get(1));
//        System.out.println(ftpnames.get(1));

        //nula je vzdy back
        int ftpCounter = 1;
        int fileCounter = 1;

        while (true) {
            if (fileRow.length <= fileCounter) {
                for (int j = ftpCounter; j < row.length; j++) {
                    setValueAt(1, j, 4);
                    System.out.println(ftpnames.get(j));
                }
                break;
            }
            if (ftpCounter >= row.length) {
                while (ftpCounter < row.length) {
                    setValueAt(1, ftpCounter, 4);
                    System.out.println(ftpnames.get(ftpCounter));
                    ftpCounter++;
                }
                break;
            }
            if (filenames.get(fileCounter).compareTo(ftpnames.get(ftpCounter)) == 0) {
                //System.out.println("soubory se rovnaji");
                //System.out.println(filenames.get(ftpCounter));
                if (!(filesize.get(fileCounter).equals(ftpsize.get(ftpCounter)))) //soubory se nerovnaji
                {
                    setValueAt(1, ftpCounter, 4);
                    System.out.println(ftpnames.get(ftpCounter));
                } else {
                    setValueAt(0, ftpCounter, 4);
                }
            } else if (filenames.get(fileCounter).compareTo(ftpnames.get(ftpCounter)) > 0) {

                setValueAt(1, ftpCounter, 4);
                System.out.println(row[ftpCounter][4]);
                System.out.println(ftpnames.get(ftpCounter));
                ftpCounter++;
                continue;
            } else {
                fileCounter++;
                continue;
            }
            fileCounter++;
            ftpCounter++;
        }
        tableList.setModel(this);
        System.out.println("done");

        return this;

    }
}
