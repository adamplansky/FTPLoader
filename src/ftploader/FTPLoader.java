package ftploader;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Adam Plánský FIT username:plansada email: plansada@fit.cvut.cz OMGFG
 * asdfasdfasdf
 */
public class FTPLoader {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mf = new JFrame("ftploader");
                mf.setLayout(new GridLayout());
                mf.setSize(800, 600);
                mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mf.add(new FileComponent());
                mf.add(new FileComponent());
                mf.setVisible(true);
            }
        });
    }

}
