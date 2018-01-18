package pl.edu.agh.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.edu.agh.student.model.Tweet;
import pl.edu.agh.student.style.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class StyleApp {

    public static void main(String[] args) throws JsonProcessingException, FileNotFoundException {

        TweetReader tweetReader = new TweetReader();
        List<Tweet> read = tweetReader.read("tweets_oneline_1000.csv");

        StyleDataPreparer styleDataPreparer = new StyleDataPreparer(read);
        List<Tweet> tweets = styleDataPreparer.prepare();

        List<String> texts = tweets.stream().map(Tweet::getText).collect(Collectors.toList());
        List<String> users = tweets.stream().map(Tweet::getUser).collect(Collectors.toList());

        new StyleAnalyzer(new PartOfSpeechModificator()).run(users, texts, "tree_pos.js");
        new StyleAnalyzer(new DefaultModificator()).run(users, texts, "tree_default.js");

    }

}
