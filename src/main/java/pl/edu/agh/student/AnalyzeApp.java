package pl.edu.agh.student;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import pl.edu.agh.student.model.Tweet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyzeApp {

    private static final ObjectMapper OM = new ObjectMapper();

    public static void main(String[] args) {

        try{

            List<Tweet> tweets = OM.readValue(new File("filewithtweets.json"), new TypeReference<List<Tweet>>() {
            });

            List<String> texts = tweets.stream().map(Tweet::getText).collect(Collectors.toList());

            SentenceIterator iter = new CollectionSentenceIterator(texts);

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


            Collection<String> lst = vec.wordsNearest("jest", 10);
            System.out.println("10 Words closest to 'jest': " + lst);

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
