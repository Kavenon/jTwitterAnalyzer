package pl.edu.agh.student.model;

public class TweetSentiment {

    private String text;
    private Sentiment sentiment;

    public TweetSentiment() {
    }

    public TweetSentiment(String text, Sentiment sentiment) {
        this.text = text;
        this.sentiment = sentiment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "TweetSentiment{" +
                "text='" + text + '\'' +
                ", sentiment=" + sentiment +
                '}';
    }
}
