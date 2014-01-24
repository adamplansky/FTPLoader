package ftploader;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                mf.setLayout(new BorderLayout());
                mf.setSize(1000, 600);
                mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                FileComponent fc = new FileComponent();

                mf.add(fc, BorderLayout.WEST);
                //Object [][] filerows = fc.getMtm().getRow();
                final FTPComponent ftpc = new FTPComponent(fc);
                mf.add(ftpc);

                Button compare = new Button("compare");
                compare.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ftpc.compare();
                    }
                });
                mf.add(compare, BorderLayout.NORTH);
                
                mf.setVisible(true);
            }
        });
    }

}
