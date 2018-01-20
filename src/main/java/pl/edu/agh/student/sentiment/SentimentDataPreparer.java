package pl.edu.agh.student.sentiment;

import pl.edu.agh.student.model.TweetSentiment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SentimentDataPreparer {

    private List<TweetSentiment> tweets;
    private MorfologikSentimentTool morfTool = new MorfologikSentimentTool();

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
                .map(item -> removePunctuationMark(item))
                .map(String::toLowerCase)
                .map(item -> morfTool.mapWithBasicForms(item))
                .collect(Collectors.toList());

        return String.join(" ", collect);
    }

    private String removePunctuationMark(String word) {
        String marksList = ".,?!@#$%^&*\\()-_=+[{]};:'|<>/`~\"\'";

        for(String letter : marksList.split("")){
            word = word.replace(letter, "");
        }

        return word;
    }
}
