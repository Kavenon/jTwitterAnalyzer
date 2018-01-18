package pl.edu.agh.student;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import pl.edu.agh.student.model.Sentiment;
import pl.edu.agh.student.model.TweetSentiment;
import pl.edu.agh.student.sentiment.SentimentDataPreparer;
import pl.edu.agh.student.sentiment.TweetSentimentReader;
import pl.edu.agh.student.style.VectorGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SentimentApp {

    public static void main(String[] args) {

        int vectorSize = 300;
        final int seed = 0;

        Nd4j.getMemoryManager().setAutoGcWindow(10000);

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .updater(Updater.ADAM)  //To configure: .updater(Adam.builder().beta1(0.9).beta2(0.999).build())
                .regularization(true).l2(1e-5)
                .weightInit(WeightInit.XAVIER)
                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue).gradientNormalizationThreshold(1.0)
                .learningRate(2e-2)
                .list()
                .layer(0, new GravesLSTM.Builder().nIn(vectorSize).nOut(256)
                        .activation(Activation.TANH).build())
                .layer(1, new RnnOutputLayer.Builder().activation(Activation.SOFTMAX)
                        .lossFunction(LossFunctions.LossFunction.MCXENT).nIn(256).nOut(2).build())
                .pretrain(false).backprop(true).build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        TweetSentimentReader tweetReader = new TweetSentimentReader();
        List<TweetSentiment> read = tweetReader.read("tweets_oneline_100_train.csv");

        SentimentDataPreparer preparer = new SentimentDataPreparer(read);
        List<TweetSentiment> tweets = preparer.prepare();

        List<String> texts = tweets.stream().map(TweetSentiment::getText).collect(Collectors.toList());
        List<String> labels = tweets.stream().map(item -> item.getSentiment().name()).collect(Collectors.toList());

        VectorGenerator vectorGenerator = new VectorGenerator();
        ParagraphVectors vec = vectorGenerator.generate(labels, texts);

        double[][] values = new double[Sentiment.values().length][];

        for (int i = 0; i < Sentiment.values().length; i++) {
            values[i] = vec.getWordVector(Sentiment.values()[i].name());
        }

        int[] collect = Arrays.stream(Sentiment.values()).mapToInt(Sentiment::getVal).toArray();

        System.out.println("Starting training");

        // Todo: labels: n*[NEG POS NEU] -> [0 1 0]
        // values n*[sentence vector]
//        net.fit(Nd4j.create(values), Nd4j.create(collect));



//        INDArray output = net.output(Nd4j.create(values));
//        System.out.println(output);

    }
}
