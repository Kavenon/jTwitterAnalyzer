package pl.edu.agh.student;

import com.apporiented.algorithm.clustering.AverageLinkageStrategy;
import com.apporiented.algorithm.clustering.Cluster;
import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import pl.edu.agh.student.model.Hierarchy;
import pl.edu.agh.student.model.HierarchyText;
import pl.edu.agh.student.model.Tweet;
import pl.edu.agh.student.style.StyleDataPreparer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class StyleApp {

//    public static void main(String[] args) {
//
//
//        PolishStemmer wordData = new PolishStemmer();
//        List<WordData> test = wordData.lookup("test");
//
//        System.out.println(test.get(0).getStem());
//        System.out.println(String.valueOf((test.get(0).getTag())).split(":")[0]);
//
////
//    }

    public static void main(String[] args) throws JsonProcessingException, FileNotFoundException {

        TweetReader tweetReader = new TweetReader();
        List<Tweet> read = tweetReader.read("tweets_oneline_100.csv");

        StyleDataPreparer styleDataPreparer = new StyleDataPreparer(read);
        List<Tweet> tweets = styleDataPreparer.prepare();


//        PolishStemmer wordData = new PolishStemmer();
//        List<WordData> test = wordData.lookup("test");
//
//        System.out.println(test.get(0).getStem());
//        System.out.println(test.get(0).getTag());
//
//


        List<String> texts = tweets.stream().map(Tweet::getText).collect(Collectors.toList());
        List<String> users = tweets.stream().map(Tweet::getUser).collect(Collectors.toList());
//
        List<String> usersUnique = new ArrayList<>(new HashSet<>(users));


        SentenceIterator iter = new CollectionSentenceIterator(texts);
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        AbstractCache<VocabWord> cache = new AbstractCache<>();
        LabelsSource source = new LabelsSource(users);

        ParagraphVectors vec = new ParagraphVectors.Builder()
                .minWordFrequency(1)
                .iterations(5)
                .epochs(1)
                .layerSize(100)
                .learningRate(0.025)
                .labelsSource(source)
                .windowSize(5)
                .iterate(iter)
                .trainWordVectors(false)
                .vocabCache(cache)
                .tokenizerFactory(t)
                .sampling(0)
                .build();

        vec.fit();

        // ========================================================================

        System.out.println("begin");


        double[][] multi = new double[usersUnique.size()][usersUnique.size()];

        for (int i = 0; i < usersUnique.size(); i++) {
            for(int j = 0; j < usersUnique.size(); j++){
                if(multi[j][i] != 0){
                    multi[i][j] = multi[j][i];
                }
                else {
                    multi[i][j] = vec.similarity(usersUnique.get(i), usersUnique.get(j));
                }
            }
        }


        String[] stockArr = new String[usersUnique.size()];
        stockArr = usersUnique.toArray(stockArr);

        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.performClustering(multi, stockArr,
                new AverageLinkageStrategy());
        System.out.println("exit");


        cluster.toConsole(0);

        Hierarchy hierarchy = toHierarchy(cluster);

        ObjectMapper om = new ObjectMapper();

        String s = om.writeValueAsString(hierarchy);

        System.out.println(s);
        try(  PrintWriter out = new PrintWriter( "visualise/tree.js" )  ){
            out.println("data = '" + s + "';" );
        }




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
