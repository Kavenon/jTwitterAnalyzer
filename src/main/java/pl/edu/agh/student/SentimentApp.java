package pl.edu.agh.student;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import pl.edu.agh.student.model.TweetSentiment;
import pl.edu.agh.student.sentiment.SentimentDataPreparer;
import pl.edu.agh.student.sentiment.TweetSentimentReader;
import pl.edu.agh.student.style.VectorGenerator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SentimentApp {

    public static void main(String[] args) {
//
//        int vectorSize = 300;
//        final int seed = 0;

//        Nd4j.getMemoryManager().setAutoGcWindow(10000);
//
//        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
//                .seed(seed)
//                .updater(Updater.ADAM)  //To configure: .updater(Adam.builder().beta1(0.9).beta2(0.999).build())
//                .regularization(true).l2(1e-5)
//                .weightInit(WeightInit.XAVIER)
//                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue).gradientNormalizationThreshold(1.0)
//                .learningRate(2e-2)
//                .list()
//                .layer(0, new GravesLSTM.Builder().nIn(vectorSize).nOut(256)
//                        .activation(Activation.TANH).build())
//                .layer(1, new RnnOutputLayer.Builder().activation(Activation.SOFTMAX)
//                        .lossFunction(LossFunctions.LossFunction.MCXENT).nIn(256).nOut(2).build())
//                .pretrain(false).backprop(true).build();
//
//        MultiLayerNetwork net = new MultiLayerNetwork(conf);
//        net.init();
//        net.setListeners(new ScoreIterationListener(1));

        TweetSentimentReader tweetReader = new TweetSentimentReader();
        List<TweetSentiment> read = tweetReader.read("sentiment_train_data.csv");

        SentimentDataPreparer preparer = new SentimentDataPreparer(read);
        List<TweetSentiment> tweets = preparer.prepare();

        List<String> texts = tweets.stream().map(TweetSentiment::getText).collect(Collectors.toList());
        List<String> labels = tweets.stream().map(item -> item.getSentiment().name()).collect(Collectors.toList());

        AbstractCache<VocabWord> cache = new AbstractCache<>();

        VectorGenerator vectorGenerator = new VectorGenerator();
        ParagraphVectors.Builder builder = new ParagraphVectors.Builder();
        ParagraphVectors vec = vectorGenerator.generate(labels, texts, builder, cache);

        int right = 0;
        int wrong = 0;
        for(int i=0; i < texts.size(); i++)
        {
            String text = texts.get(i);
            String label = labels.get(i);
            //Collection<String> no_elo = vec.predictSeveral(text, 3);
            Collection<String> no_elo = vec.nearestLabels(text, 1);
            if(label == no_elo.toArray()[0])
                right++;
            else
                wrong++;
            String a = "aaa";
        }

        System.out.println("Skuteczność ucząca:" + ((right * 100)/(right + wrong)) + "%");

        TweetSentimentReader tweetReader2 = new TweetSentimentReader();
        List<TweetSentiment> read2 = tweetReader.read("sentiment_test_data.csv");

        for(TweetSentiment tweet : read2)
        {
            tweet.setText(preparer.cleanText(tweet.getText()));
        }

        int right2 = 0;
        int rightPos = 0;
        int rightNeu = 0;
        int rightNeg = 0;
        int wrong2 = 0;
        for(int i=0; i < read2.size(); i++)
        {
            String text = read2.get(i).getText();
            String label = read2.get(i).getSentiment().toString();
            //Collection<String> no_elo = vec.predictSeveral(text, 3);
            Collection<String> no_elo = vec.nearestLabels(text, 1);
            if(no_elo.toArray().length == 0)
                System.out.println("Brak przypisania: " + text);
            else {
                String predictedLabel = no_elo.toArray()[0].toString();
                if (label == predictedLabel) {
                    right2++;

                    if(predictedLabel.equals("POS"))
                        rightPos++;
                    else if(predictedLabel.equals("NEU"))
                        rightNeu++;
                    else if(predictedLabel.equals("NEG"))
                        rightNeg++;
                }
                else
                    wrong2++;
            }
            String a = "aaa";
        }

        System.out.println("Skuteczność testowa:" + ((right2 * 100)/(right2 + wrong2)) + "%");
        System.out.println("Poprawne POS:" + rightPos);
        System.out.println("Poprawne NEU:" + rightNeu);
        System.out.println("Poprawne NEG:" + rightNeg);
        // values n*[sentence vector]
//        net.fit(Nd4j.create(values), Nd4j.create(collect));

//        INDArray output = net.output(Nd4j.create(values));
//        System.out.println(output);

    }
}
