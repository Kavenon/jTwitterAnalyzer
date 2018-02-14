package pl.edu.agh.student.sentiment;

import au.com.bytecode.opencsv.CSVReader;
import pl.edu.agh.student.model.Sentiment;
import pl.edu.agh.student.model.TweetSentiment;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TweetSentimentReader {

    public List<TweetSentiment> read(String file){
        List<TweetSentiment> inputList = new ArrayList<>();
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

    public List<String> readUnlabelled(String file){
        List<String> inputList = new ArrayList<>();
        try{

            CSVReader reader = new CSVReader(new FileReader(file), ';');

            return reader.readAll()
                    .stream()
                    .map(p -> unwrap(p[0]))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Could not read tweets: " + e.getMessage());
        }
        return inputList ;
    }

    private TweetSentiment mapToTweet(String[] row) {
        TweetSentiment tweet = new TweetSentiment();
        tweet.setText(unwrap(row[0]));
        tweet.setSentiment(Sentiment.valueOf(unwrap(row[1])));
        return tweet;
    }

    private String unwrap(String input){
        return input.replaceAll("^\"|\"$", "");
    }

}
