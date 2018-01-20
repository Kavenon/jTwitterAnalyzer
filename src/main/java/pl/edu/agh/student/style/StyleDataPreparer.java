package pl.edu.agh.student.style;

import com.vdurmont.emoji.EmojiParser;
import pl.edu.agh.student.model.Tweet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StyleDataPreparer {

    private List<Tweet> tweets;

    public StyleDataPreparer(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> prepare(){

        return this.tweets
                .stream()
                .map(tweet -> {
                    Tweet newTweet = new Tweet();
                    newTweet.setUser(tweet.getUser());
                    newTweet.setText(this.cleanText(tweet.getText()));
                    newTweet.setRetweetCount(tweet.getRetweetCount());
                    newTweet.setFavoriteCount(tweet.getFavoriteCount());
                    newTweet.setFollowersCount(tweet.getFollowersCount());
                    newTweet.setTags(new ArrayList<>(tweet.getTags()));
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
                .map(EmojiParser::removeAllEmojis)
                .map(String::toLowerCase)
                .filter(item -> item != null && item.length() > 0)
                .collect(Collectors.toList());

        return String.join(" ", collect);

    }

}
