package pl.edu.agh.student.downloader;

public class TweetRequest {

    private String query;
    private String lang;
    private int limit;
    private int requestedQuantity;

    public TweetRequest(String query, String lang, int limit, int requestedQuantity) {
        this.query = query;
        this.lang = lang;
        this.limit = limit;
        this.requestedQuantity = requestedQuantity;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}
