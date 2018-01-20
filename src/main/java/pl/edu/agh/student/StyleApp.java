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

        List<Tweet> readTest = Stream.of("JaroslawKuzniar", "BoniekZibi", "robertbiedron", "sikorskiradek")
                .map(user -> tweetReader.read("tweets_" + user + "_test.csv"))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        StyleDataPreparer styleDataPreparerTest = new StyleDataPreparer(readTest);
        List<Tweet> tweetsTest = styleDataPreparerTest.prepare();

        // =============================================

        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        TokenizerFactory tngrm = new OwnNGramTokenizerFactory(new DefaultTokenizerFactory(), 1, 4);
        tngrm.setTokenPreProcessor(new CommonPreprocessor());

        VectorConfig vectorConfig = new VectorConfig();
        vectorConfig.setMinWordFrequency(1);
        vectorConfig.setIterations(1);
        vectorConfig.setEpochs(5);
        vectorConfig.setLayerSize(100);
        vectorConfig.setLearningRate(0.25);
        vectorConfig.setWindowSize(5);
        vectorConfig.setSampling(0);
        vectorConfig.setTrainWordVectors(false);

        VectorConfig vectorConfig2 = new VectorConfig();
        vectorConfig2.setMinWordFrequency(1);
        vectorConfig2.setIterations(1);
        vectorConfig2.setEpochs(4);
        vectorConfig2.setLayerSize(100);
        vectorConfig2.setLearningRate(0.12);
        vectorConfig2.setWindowSize(5);
        vectorConfig2.setSampling(0);
        vectorConfig2.setTrainWordVectors(false);

        VectorConfig vectorConfig5 = new VectorConfig();
        vectorConfig5.setMinWordFrequency(1);
        vectorConfig5.setIterations(1);
        vectorConfig5.setEpochs(7);
        vectorConfig5.setLayerSize(500);
        vectorConfig5.setLearningRate(0.1);
        vectorConfig5.setWindowSize(5);
        vectorConfig5.setSampling(0);
        vectorConfig5.setTrainWordVectors(false);

        VectorConfig vectorConfig4 = new VectorConfig();
        vectorConfig4.setMinWordFrequency(1);
        vectorConfig4.setIterations(5);
        vectorConfig4.setEpochs(5);
        vectorConfig4.setLayerSize(100);
        vectorConfig4.setLearningRate(0.25);
        vectorConfig4.setWindowSize(3);
        vectorConfig4.setSampling(0);
        vectorConfig4.setTrainWordVectors(false);


        VectorConfig[] configs = new VectorConfig[]{vectorConfig5};

        for (VectorConfig config : configs) {
            System.out.println("Default - Default");
            System.out.println("=========================================");
            runDefault(texts, users, tweetsTest, config, t);
            System.out.println("Default - NGram");
            System.out.println("=========================================");
            runDefault(texts, users, tweetsTest, config, tngrm);
            System.out.println("POS - Default");
            System.out.println("=========================================");
            runPOS(texts, users, tweetsTest, config, t);
            System.out.println("POS - NGram");
            System.out.println("=========================================");
            runPOS(texts, users, tweetsTest, config, tngrm);
        }

    }

    private static void runPOS(List<String> texts, List<String> users, List<Tweet> tweetsTest, VectorConfig c, TokenizerFactory tf) {

        ParagraphVectors.Builder vecBuilder = new ParagraphVectors.Builder()
                .minWordFrequency(c.getMinWordFrequency())
                .iterations(c.getIterations())
                .epochs(c.getEpochs())
                .layerSize(c.getLayerSize())
                .learningRate(c.getLearningRate())
                .windowSize(c.getWindowSize())
                .trainWordVectors(c.isTrainWordVectors())
                .tokenizerFactory(tf)
                .sampling(c.getSampling());

        System.out.println("Start POS");
        System.out.println(c);

        StyleAnalyzer styleAnalyzerPos = new StyleAnalyzer(new PartOfSpeechModificator());
        ParagraphVectors vcPos = styleAnalyzerPos.run(users, texts, "tree_pos.js", vecBuilder);

        System.out.println("POS results");
        styleAnalyzerPos.test(tweetsTest, vcPos);
    }

    private static void runDefault(List<String> texts, List<String> users, List<Tweet> tweetsTest, VectorConfig c, TokenizerFactory tf) {

        ParagraphVectors.Builder vecBuilder = new ParagraphVectors.Builder()
                .minWordFrequency(c.getMinWordFrequency())
                .iterations(c.getIterations())
                .epochs(c.getEpochs())
                .layerSize(c.getLayerSize())
                .learningRate(c.getLearningRate())
                .windowSize(c.getWindowSize())
                .trainWordVectors(c.isTrainWordVectors())
                .tokenizerFactory(tf)
                .sampling(c.getSampling());

        System.out.println("Start DEFAULT");
        System.out.println(c);
        StyleAnalyzer styleAnalyzerDefault = new StyleAnalyzer(new DefaultModificator());
        ParagraphVectors vcDefault = styleAnalyzerDefault.run(users, texts, "tree_default.js", vecBuilder);

        System.out.println("Default results");
        styleAnalyzerDefault.test(tweetsTest, vcDefault);
    }

}
