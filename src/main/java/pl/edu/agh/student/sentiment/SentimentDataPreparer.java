package pl.edu.agh.student.sentiment;

import pl.edu.agh.student.model.TweetSentiment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SentimentDataPreparer {

    private List<TweetSentiment> tweets;

    public SentimentDataPreparer(List<TweetSentiment> tweets) {
        this.tweets = tweets;
    }

    public List<TweetSentiment> prepare(){

        return this.tweets
                .stream()
                .map(tweet -> {
                    TweetSentiment newTweet = new TweetSentiment();
                    newTweet.setText(this.cleanText(tweet.getText()));
                    newTweet.setSentiment(tweet.getSentiment());
                    return newTweet;
                })
                .collect(Collectors.toList());

    }

    private String cleanText(String text) {

        List<String> collect = Arrays.stream(text.split(" "))
                .filter(item -> !item.startsWith("@"))
                .filter(item -> !item.startsWith("http://"))
                .filter(item -> !item.startsWith("https://"))
                .filter(item -> !item.startsWith("#"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return String.join(" ", collect);

    }

}
