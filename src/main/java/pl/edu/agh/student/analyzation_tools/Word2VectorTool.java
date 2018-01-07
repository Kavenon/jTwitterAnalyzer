package pl.edu.agh.student.analyzation_tools;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import pl.edu.agh.student.model.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Word2VectorTool {

    public static void transformTweetsToVectors(List<Tweet> tweets){
        List<String> tweetSentences = new ArrayList<>();
        for(Tweet tweet : tweets)
        {
            tweetSentences.add(tweet.getText());
        }

        SentenceIterator iter = new CollectionSentenceIterator(tweetSentences);

        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        vec.fit();
        Collection<String> vecResult = vec.wordsNearest("za", 5);
    }
}
