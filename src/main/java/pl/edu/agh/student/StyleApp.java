package pl.edu.agh.student;

import com.apporiented.algorithm.clustering.Cluster;
import com.fasterxml.jackson.core.JsonProcessingException;
import pl.edu.agh.student.model.Hierarchy;
import pl.edu.agh.student.model.HierarchyText;
import pl.edu.agh.student.model.Tweet;
import pl.edu.agh.student.style.DefaultModificator;
import pl.edu.agh.student.style.PartOfSpeechModificator;
import pl.edu.agh.student.style.StyleAnalyzer;
import pl.edu.agh.student.style.StyleDataPreparer;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class StyleApp {

    public static void main(String[] args) throws JsonProcessingException, FileNotFoundException {

        TweetReader tweetReader = new TweetReader();
        List<Tweet> read = tweetReader.read("tweets_oneline_100.csv");

        StyleDataPreparer styleDataPreparer = new StyleDataPreparer(read);
        List<Tweet> tweets = styleDataPreparer.prepare();

        List<String> texts = tweets.stream().map(Tweet::getText).collect(Collectors.toList());
        List<String> users = tweets.stream().map(Tweet::getUser).collect(Collectors.toList());

        new StyleAnalyzer(new DefaultModificator()).run(users, texts, "tree_default.js");
        new StyleAnalyzer(new PartOfSpeechModificator()).run(users, texts, "tree_pos.js");

    }

    public static Hierarchy toHierarchy(Cluster cluster) {

        Hierarchy h = new Hierarchy();
        h.setText(new HierarchyText(cluster.getName()));

        h.setChildren(
                cluster.getChildren()
                .stream()
                .map(StyleApp::toHierarchy)
                .collect(Collectors.toList()));

        return h;

    }

}
