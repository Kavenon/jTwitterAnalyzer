package pl.edu.agh.student.downloader;

public class TweetRequest {

    private String query;
    private String lang;
    private int limit;

    public TweetRequest(String query, String lang, int limit) {
        this.query = query;
        this.lang = lang;
        this.limit = limit;
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
}
