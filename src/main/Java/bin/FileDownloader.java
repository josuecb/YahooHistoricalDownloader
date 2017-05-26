package bin;

import java.io.*;
import java.net.URL;

/**
 * Created by Josue on 5/13/2017.
 */
public class FileDownloader {
    protected String webURL;

    public FileDownloader(String webURL) {
        this.webURL = webURL;
    }

    public void start(String cvsFileName) throws IOException {
        System.out.println("Starting to download...");
        URL link = new URL(this.webURL); //The file that you want to download

        //Code to download
        InputStream in = new BufferedInputStream(link.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        FileOutputStream fos = new FileOutputStream(cvsFileName);
        fos.write(response);
        fos.flush();
        fos.close();
        System.out.println("Finished downloading");
    }
}
