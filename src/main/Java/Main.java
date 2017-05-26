import bin.download.YahooFileDownloader;
import bin.yahoo.YahooLinkGen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Josue on 5/13/2017.
 */
public class Main {
    public static void main(String[] args) {
//        String testFileAbsolutePath = System.getProperty("user.dir") + File.separator + "test.csv";
        String company = "JMP";

        // Download data from company
        System.out.println("Downloading " + company + " table...");

        // Generate the link to download
        YahooLinkGen y = new YahooLinkGen(company);
////        from interval
        y.setIntervalDate(3, 1, 2017, true);
////        to interval
        y.setIntervalDate(5, 1, 2017, false);
////
        YahooFileDownloader yd = new YahooFileDownloader(y);
//        yd.getString();
//            Files.write(Paths.get(testFileAbsolutePath), res.getBytes(), StandardOpenOption.CREATE);
        System.out.println(yd.getString());
    }
}
