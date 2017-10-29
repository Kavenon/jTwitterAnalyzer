package pl.edu.agh.student.downloader;

import pl.edu.agh.student.model.Tweet;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterDownloader {

    private ConfigProvider configProvider;

    public TwitterDownloader(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    public List<Tweet>getTweets(TweetRequest tweetRequest){

        Twitter twitter = new TwitterFactory(configProvider.getConfiguration()).getInstance();
        Query query = new Query(tweetRequest.getQuery());

        long lastID = Long.MAX_VALUE;
        ArrayList<Tweet> tweets = new ArrayList<>();
        while (tweets.size () < tweetRequest.getLimit()) {

            query.setLang(tweetRequest.getLang());

            if (tweetRequest.getLimit() - tweets.size() > tweetRequest.getLimit())
                query.setCount(tweetRequest.getLimit());
            else
                query.setCount(tweetRequest.getLimit() - tweets.size());
            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets().stream().map(x -> new Tweet(x.getId(), x.getText())).collect(Collectors.toList()));
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Tweet t: tweets)
                    if(t.getId() < lastID) lastID = t.getId();

            }

            catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            };
            query.setMaxId(lastID-1);
        }

        return tweets;

    }
}
