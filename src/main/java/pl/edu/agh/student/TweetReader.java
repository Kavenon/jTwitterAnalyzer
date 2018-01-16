package pl.edu.agh.student;

import au.com.bytecode.opencsv.CSVReader;
import pl.edu.agh.student.model.Tweet;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TweetReader {

    public List<Tweet> read(String file){
        List<Tweet> inputList = new ArrayList<>();
        try{

            CSVReader reader = new CSVReader(new FileReader(file), ';');

            return reader.readAll()
                    .stream()
                    .map(this::mapToTweet)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Could not read tweets: " + e.getMessage());
        }
        return inputList ;
    }

    private Tweet mapToTweet(String[] row) {
        Tweet tweet = new Tweet();
        tweet.setUser(unwrap(row[0]));
        tweet.setText(unwrap(row[1]));
        tweet.setRetweetCount(Integer.valueOf(unwrap(row[3])));
        tweet.setFavoriteCount(Integer.valueOf(unwrap(row[4])));
        tweet.setFollowersCount(Integer.valueOf(unwrap(row[5])));

        String tagString = unwrap(row[6]);
        if(tagString != null && tagString.length() > 0){
            List<String> tags = Arrays.asList(tagString.split(":"));
            tweet.setTags(tags);
        }
        return tweet;
    }

    private String unwrap(String input){
        return input.replaceAll("^\"|\"$", "");
    }

    public static void main(String[] args) throws FileNotFoundException {

        TweetReader tweetReader = new TweetReader();
        List<Tweet> read = tweetReader.read("tweets_oneline.csv");


    }
}
