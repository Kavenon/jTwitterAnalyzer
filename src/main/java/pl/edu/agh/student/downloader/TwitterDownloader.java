package pl.edu.agh.student.downloader;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class TwitterDownloader {

    private ConfigProvider configProvider;

    public TwitterDownloader(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    public List<Status>getTweets(TweetRequest tweetRequest){

        Twitter twitter = new TwitterFactory(configProvider.getConfiguration()).getInstance();
        Query query = new Query(tweetRequest.getQuery());

        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<>();
        while (tweets.size () < tweetRequest.getLimit()) {

            query.setLang(tweetRequest.getLang());

            if (tweetRequest.getLimit() - tweets.size() > tweetRequest.getLimit())
                query.setCount(tweetRequest.getLimit());
            else
                query.setCount(tweetRequest.getLimit() - tweets.size());
            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t: tweets)
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
