/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftploader;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author impz
 */
public class Renderer extends DefaultTableCellRenderer {

    public void fillColor(JTable t, JLabel l, boolean isSelected) {
        
        if (isSelected) {
            l.setBackground(t.getSelectionBackground());
            l.setForeground(t.getSelectionForeground());
        } else {
            l.setBackground(t.getBackground());
            l.setForeground(t.getForeground());
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof JLabel) {
            JLabel label = (JLabel) value;
            label.setOpaque(true);
            fillColor(table, label, isSelected);

            System.out.println(column);
            
            if ((int) (table.getModel().getValueAt(row, 4)) == 1) { 
                label.setBackground(Color.red);
            }

            System.out.println(table.getModel().getValueAt(row, 4).toString());
            return label;
        } else {
            final Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            comp.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
            return comp;

        }
    }

}
