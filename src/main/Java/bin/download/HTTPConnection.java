package bin.download;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Josue on 2/1/2017.
 */
public class HTTPConnection {
    Connection connection = null;
    private Map<String, String> urlParameters = null;
    private String url = null;
    private Map<String, String> headerProperties = null;
    private boolean useCache = false;
    private Method requestMethod = null;
    private String responseText = null;
    private Map<String, String> headerResponse = null;
    private Map<String, String> cookies = null;
    private boolean ignoreContentType = false;

    public HTTPConnection(String url, Method requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.urlParameters = new HashMap<>();
        this.headerResponse = new HashMap<>();
        this.headerProperties = new HashMap<>();
        this.cookies = new HashMap<>();
        this.responseText = "";
    }


    public String getURL() {
        return this.url;
    }

    public void setHeaderProperties(HashMap<String, String> headerProperties) {
        this.headerProperties = headerProperties;
    }

    public void addParameter(String key, String value) {
        this.urlParameters.put(key, value);
    }

    public void addHeaderProperty(String key, String value) {
        this.headerProperties.put(key, value);
    }

    public void setParameters(HashMap<String, String> urlParameters) {
        this.urlParameters = urlParameters;
    }

    private String parametersToString() {
        String urlParams = "";
        int index = 0;
        for (String key : this.urlParameters.keySet()) {
            if (index != 0)
                urlParams += "&";

            urlParams += key + "=" + this.urlParameters.get(key);

            index++;
        }

        return urlParams;
    }

    public void setRequestMethod(Method requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponseText() {
        return this.responseText;
    }

    public Map<String, String> getHeaderResponse() {
        return this.headerResponse;
    }

    public String getHeaderResponse(String key) {
        return this.headerResponse.get(key);
    }

    public void displayHeader() {
        for (String key : this.headerResponse.keySet())
            System.out.println(key + ": " + this.headerResponse.get(key));
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public void setCookies(Map<String, String> cookies) throws Exception {
        if (!cookies.isEmpty())
            this.cookies = cookies;
        else
            throw new Exception("Cookie doesn't exist");
    }

    public Response connect() throws Exception {
        this.connection = Jsoup.connect(this.url);
        if (!this.cookies.isEmpty())
            this.connection.cookies(this.cookies);

        this.connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
        this.connection.header("Content-Type", "application/x-www-form-urlencoded");
        this.connection.header("Content-Length",
                Integer.toString(this.parametersToString().getBytes().length));

        this.connection.header("Content-Language", "en-US");

//        System.out.println("size: " + this.urlParameters.size());

        for (String key : this.urlParameters.keySet()) {
            this.connection.data(key, this.urlParameters.get(key));
//            System.out.println(key + ": " + this.urlParameters.get(key));
        }

        for (String key : this.headerProperties.keySet())
            this.connection.header(key, this.headerProperties.get(key));

        this.connection.method(this.requestMethod);

        this.connection.ignoreContentType(this.ignoreContentType);
        Response response = this.connection.execute();
        this.responseText = response.body();
        this.headerResponse = response.headers();

        return response;
    }

    public void flush() {
        // Resetting
        this.urlParameters = new HashMap<>();
        this.headerResponse = new HashMap<>();
        this.headerProperties = new HashMap<>();
        this.responseText = "";
    }

    public void ignoreContentType(boolean ignoreContentType) {
        this.ignoreContentType = ignoreContentType;
    }
}
