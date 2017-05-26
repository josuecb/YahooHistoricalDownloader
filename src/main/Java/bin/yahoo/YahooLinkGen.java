package bin.yahoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Josue on 5/13/2017.
 */
public class YahooLinkGen {
    protected String companyName;
    protected String interval;
    protected String type;
    protected String scrumb;

    public YahooLinkGen(String companyName) {
        if (Yahoo.CURR_VERSION.equals(Yahoo.VERSION1))
            this.companyName = companyName;
        else
            this.companyName = companyName + "?";

        this.interval = "";
        this.setType(OutputType.DAILY);
    }

    public void setScrumb(String scrumb) {
        this.scrumb = scrumb;
    }

    public void setIntervalDate(int mm, int dd, int yyyy, boolean from) {

        if (Yahoo.CURR_VERSION.equals(Yahoo.VERSION1))
            this.setIntervalV1(mm, dd, yyyy, from);
        else if (Yahoo.CURR_VERSION.equals(Yahoo.VERSION2)) {
            try {
                this.setIntervalV2(mm, dd, yyyy, from);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }



    private void setIntervalV2(int mm, int dd, int yyyy, boolean from) throws ParseException {
        String someDate = mm + "-" + dd + "-" + yyyy;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = sdf.parse(someDate);
//        System.out.println(date.getTime());

        long milliseconds = date.getTime() / 1000;
        if (from) {
            if (this.interval.isEmpty())
                this.interval = "&period1=" + milliseconds;
            else
                this.interval += "&period1=" + milliseconds;
        } else {
            if (this.interval.isEmpty())
                this.interval = "&period2=" + milliseconds;
            else
                this.interval += "&period2=" + milliseconds;
        }
//        c.setUrl("https://query1.finance.yahoo.com/v7/finance/download/AAPL?&period1=1274158800&period2=1495059477&interval=1d&events=history&crumb=" + res.body());


    }

    private void setIntervalV1(int mm, int dd, int yyyy, boolean from) {
        if (from) {
            if (this.interval.isEmpty())
                this.interval = "&a=" + mm + "&b=" + dd + "&c=" + yyyy;
            else
                this.interval += "&a=" + mm + "&b=" + dd + "&c=" + yyyy;
        } else {
            if (this.interval.isEmpty())
                this.interval = "&d=" + mm + "&e=" + dd + "&f=" + yyyy;
            else
                this.interval += "&d=" + mm + "&e=" + dd + "&f=" + yyyy;
        }
    }

    public void setType(int flag) {
        if (Yahoo.CURR_VERSION.equals(Yahoo.VERSION1)) {
            if (flag == OutputType.DAILY)
                this.type = "&g=d";
            else if (flag == OutputType.MONTHLY)
                this.type = "&g=m";
        } else {
            this.type = "&interval=1d";
        }

    }

    public String getLink() throws Exception {

        if (Yahoo.CURR_VERSION.equals(Yahoo.VERSION2) && (this.scrumb == null || this.scrumb.isEmpty()))
            throw new Exception("Missing scrumb.");

        if (this.interval.isEmpty())
            throw new Exception("You must an interval.");

        if (this.type.isEmpty())
            this.type += "&g=d";
        if (Yahoo.CURR_VERSION.equals(Yahoo.VERSION1))
            return Yahoo.YAHOO_DOWNLOAD_LINK + this.companyName + interval + this.type + "&ignore=.csv";
        else
            return Yahoo.YAHOO_DOWNLOAD_LINK2 + this.companyName + interval + this.type + "&events=history&crumb=" + this.scrumb;
    }
}
