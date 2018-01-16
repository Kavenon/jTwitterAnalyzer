package pl.edu.agh.student.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tweet {

    private String text;
    private String user;
    private Date createdAt;
    private int retweetCount;
    private int favoriteCount;
    private int followersCount;
    private List<String> tags = new ArrayList<>();

    public Tweet() {
    }

    public Tweet(String text, String user, Date createdAt, int retweetCount, int favoriteCount, int followersCount, List<String> tags) {
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
        this.retweetCount = retweetCount;
        this.favoriteCount = favoriteCount;
        this.followersCount = followersCount;
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "text='" + text + '\'' +
                ", user='" + user + '\'' +
                ", createdAt=" + createdAt +
                ", retweetCount=" + retweetCount +
                ", favoriteCount=" + favoriteCount +
                ", followersCount=" + followersCount +
                ", tags=" + tags +
                '}';
    }
}
