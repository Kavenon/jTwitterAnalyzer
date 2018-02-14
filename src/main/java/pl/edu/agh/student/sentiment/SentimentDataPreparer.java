package pl.edu.agh.student.sentiment;

import com.google.common.primitives.Ints;
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

    public String cleanText(String text) {
        List<String> collect = Arrays.stream(text.split(" "))
                .filter(item -> !item.startsWith("@"))
                .filter(item -> !item.startsWith("http://"))
                .filter(item -> !item.startsWith("https://"))
                .filter(item -> !item.startsWith("#"))
                .map(String::toLowerCase)
                .map(item -> removePunctuationMarks(item))
                .filter(item -> item.length() > 0)
                .map(item -> morfTool.mapWithBasicForms(item))
                .collect(Collectors.toList());

        return String.join(" ", collect);
    }



    private String removePunctuationMarks(String word) {

        int[] polishLetter = new int[]{261, 263, 281, 322, 324, 243, 347, 378, 380 };
        String newString = "";
        for(char letter : word.toCharArray()){
            if(((int)letter >= 97 && (int)letter <= 122) || Ints.contains(polishLetter, (int)letter)) {
                newString += letter;
            }
        }

        return newString;
    }
}
