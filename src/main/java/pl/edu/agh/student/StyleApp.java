package pl.edu.agh.student;

import com.apporiented.algorithm.clustering.AverageLinkageStrategy;
import com.apporiented.algorithm.clustering.Cluster;
import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;
import com.apporiented.algorithm.clustering.visualization.DendrogramPanel;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import pl.edu.agh.student.model.Tweet;
import pl.edu.agh.student.style.StyleDataPreparer;

import java.util.List;
import java.util.stream.Collectors;

public class StyleApp {

    public static void main(String[] args) {

        TweetReader tweetReader = new TweetReader();
        List<Tweet> read = tweetReader.read("tweets_oneline.csv");

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

        double[][] multi = new double[users.size()][];

        for (int i = 0; i < users.size(); i++) {
            multi[i] = vec.getWordVector(users.get(i));
        }


        String[] stockArr = new String[users.size()];
        stockArr = users.toArray(stockArr);

        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.performClustering(multi, stockArr,
                new AverageLinkageStrategy());

        DendrogramPanel dp = new DendrogramPanel();
        dp.setModel(cluster);
        System.out.println("exit");

        //
//        System.out.println(vec.similarity("HenrykSatora", "DominikaWalus"));
//
//
//        int maxIterationCount = 5;
//        int clusterCount = 10;
//        String distanceFunction = "cosinesimilarity";
//        KMeansClustering kmc = KMeansClustering.setup(clusterCount, maxIterationCount, distanceFunction);
//
//        //2. iterate over rows in the paragraphvector and create a List of paragraph vectors
//        List<INDArray> vectors = new ArrayList<>();
//        for (String word : vec.vocab().words()) {
//            vectors.add(vec.getWordVectorMatrix(word));
//        }
//        List<Point> pointsLst = Point.toPoints(vectors);
//
//        ClusterSet cs = kmc.applyTo(pointsLst);
//        vectors = null;
//        pointsLst = null;
//
//        List<Cluster> clsterLst = cs.getClusters();
//
//        System.out.println("\nCluster Centers:");
//        for(Cluster c: clsterLst) {
//            Point center = c.getCenter();
//            System.out.println(center.getLabel());
//            System.out.println(center.getLabel());
//        }
//
//        double[] nesVec = vec.getWordVector("HenrykSatora");
//        Point newpoint = new Point("myid", "mylabel", nesVec);
//        PointClassification pc = cs.classifyPoint(newpoint);
//        System.out.println(pc.getCluster().getCenter().getId());
//
//        System.out.println("\nEnd Test");

            // ========================================================================
//
//        Word2Vec vec = new Word2Vec.Builder()
//                .minWordFrequency(5)
//                .iterations(1)
//                .layerSize(100)
//                .seed(42)
//                .windowSize(5)
//                .iterate(iter)
//                .tokenizerFactory(t)
//                .build();
//
//        vec.fit();
//
//        System.out.println("here");
//        System.out.println(Arrays.toString(vec.getWordVector("sejm"))
//        );

    }

}
