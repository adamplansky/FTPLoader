package ftploader;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.listparsers.DOSListParser;

/**
 *
 * @author Adam Plánský FIT username:plansada email: plansada@fit.cvut.cz
 *
 */
public class FTPLoader {

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        try {
            client.connect("56403.w3.wedos.net");
            client.login("w56403", "uvjFgvVX");
            client.changeDirectory("www");
            System.out.println(client.currentDirectory());
            FTPFile[] files = client.list();
            for(FTPFile file : files)
            {
                DOSListParser dlp = new DOSListParser();
                
                System.out.println(file.getName() +   "  " + file.getSize() + "b");
            }

            client.disconnect(true);

        } catch (Exception e) {

        }

    }
}
