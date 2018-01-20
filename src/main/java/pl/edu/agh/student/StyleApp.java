package pl.edu.agh.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import pl.edu.agh.student.model.Tweet;
import pl.edu.agh.student.style.*;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StyleApp {

    public static void main(String[] args) throws JsonProcessingException, FileNotFoundException {

        System.out.println("Start");

        TweetReader tweetReader = new TweetReader();

        List<Tweet> read = Stream.of("JaroslawKuzniar", "BoniekZibi", "robertbiedron", "sikorskiradek")
                .map(user -> tweetReader.read("tweets_" + user + "_train.csv"))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        StyleDataPreparer styleDataPreparer = new StyleDataPreparer(read);
        List<Tweet> tweets = styleDataPreparer.prepare();

        List<String> texts = tweets.stream().map(Tweet::getText).collect(Collectors.toList());
        List<String> users = tweets.stream().map(Tweet::getUser).collect(Collectors.toList());

        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());
//
//        TokenizerFactory t = new OwnNGramTokenizerFactory(new DefaultTokenizerFactory(), 1, 4);
//        t.setTokenPreProcessor(new CommonPreprocessor());

        System.out.println("Start DEFAULT");
        StyleAnalyzer styleAnalyzerDefault = new StyleAnalyzer(new DefaultModificator());
        ParagraphVectors vcDefault = styleAnalyzerDefault.run(users, texts, "tree_default.js", t);

        System.out.println("Start POS");
        StyleAnalyzer styleAnalyzerPos = new StyleAnalyzer(new PartOfSpeechModificator());
        ParagraphVectors vcPos = styleAnalyzerPos.run(users, texts, "tree_pos.js", t);


        // ============================================

        List<Tweet> readTest = Stream.of("JaroslawKuzniar", "BoniekZibi", "robertbiedron", "sikorskiradek")
                .map(user -> tweetReader.read("tweets_" + user + "_test.csv"))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        StyleDataPreparer styleDataPreparerTest = new StyleDataPreparer(readTest);
        List<Tweet> tweetsTest = styleDataPreparerTest.prepare();

        System.out.println("POS results");
        styleAnalyzerPos.test(tweetsTest, vcPos);

        System.out.println("Default results");
        styleAnalyzerDefault.test(tweetsTest, vcDefault);

    }

}
