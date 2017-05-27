# YahooHistoricalDownloader
This is a Java API you can use to download the historical data from interval 1 to interval 2.


## Requirements
- Jsoup (java) Library. 

# How to use it

## Generate link to download
First of all you need to generate the link by calling the class YahooLinkGen
```java
    YahooLinkGen link = new YahooLinkGen(company);
    // you set the from interval by adding true at the end
    link.setIntervalDate(3, 1, 2017, true);
    // you set the to interval by adding false at the end
    link.setIntervalDate(3, 2, 2017, false);
```

## Download Data
YahooLink Gen will generate the link according to the company and the interval you give
Then you must use the ```YahooFileDownloader``` where you must pass the YahooLinkGen instance 
as parameter to initialize ```YahooFileDownloader``` constructor
```java
    YahooFileDownloader yd = new YahooFileDownloader(link);
    System.out.println(yd.getString());
```

After you create an instance you can use ```getString()``` to get the string or you can use
```downloadFile(filename)``` method from ```YahooFileDownloader``` to download file file in a specific
directory location. if you use ```getString()``` you will get the following.

### Output
```
    Date,Open,High,Low,Close,Adj Close,Volume
    2017-03-01,6.500000,6.500000,6.420000,6.460000,6.460000,36900
    2017-03-02,6.430000,6.530000,6.420000,6.450000,6.450000,27300
```


#### NOTE
In case you don't have a compiler that doesn't support gradle you should manually add this library
[Download Jsoup](https://jsoup.org/).


# License & Credits

This software is published under the [MIT License](http://en.wikipedia.org/wiki/MIT_License).
