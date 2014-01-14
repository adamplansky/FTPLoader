
package ftploader;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Adam Plánský
 * FIT username:plansada
 * email: plansada@fit.cvut.cz
 * 
 */
public class Window extends JFrame{

    public Window() throws HeadlessException {
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.add(new JButton("abcd"));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
