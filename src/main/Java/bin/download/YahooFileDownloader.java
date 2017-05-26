package bin.download;

import bin.yahoo.Yahoo;
import bin.yahoo.YahooLinkGen;
import org.jsoup.Connection;

import java.io.FileOutputStream;

public class YahooFileDownloader {
    protected HTTPConnection c;
    protected String scrumb;
    protected YahooLinkGen yahooLinkGen;

    public YahooFileDownloader(YahooLinkGen yahooLinkGen) {
        this.yahooLinkGen = yahooLinkGen;

        c = new HTTPConnection(Yahoo.YAHOO_LINK, Connection.Method.GET);
        try {
            Connection.Response r = c.connect();
//            System.out.println(r.cookies().toString());
            c.setUrl(Yahoo.YAHOO_CRUMB_LINK);
            c.setCookies(r.cookies());
            Connection.Response res = c.connect();

            this.scrumb = res.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String fileName) throws Exception {
        this.yahooLinkGen.setScrumb(this.scrumb);

        c.setUrl(this.yahooLinkGen.getLink());
        c.ignoreContentType(true);
        Connection.Response res = c.connect();
        System.out.println(res.body());
        // output here
        FileOutputStream out = (new FileOutputStream(new java.io.File(fileName)));
        out.write(res.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
        out.close();
    }

    public String getString() {
        this.yahooLinkGen.setScrumb(this.scrumb);

        try {
            c.setUrl(this.yahooLinkGen.getLink());

            c.ignoreContentType(true);
            Connection.Response res = c.connect();
            return res.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
