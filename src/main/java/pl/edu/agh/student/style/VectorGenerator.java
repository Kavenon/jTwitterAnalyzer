package pl.edu.agh.student.style;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.NGramTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.util.ArrayList;
import java.util.List;

public class VectorGenerator {

    public ParagraphVectors generate(List<String> labels, List<String> texts, ParagraphVectors.Builder builder) {
        AbstractCache<VocabWord> cache = new AbstractCache<>();
        return generate(labels, texts, builder, cache);
    }

    public ParagraphVectors generate(List<String> labels, List<String> texts, ParagraphVectors.Builder builder, AbstractCache<VocabWord> cache){
        SentenceIterator iter = new CollectionSentenceIterator(texts);

        LabelsSource source = new LabelsSource(labels);

        TokenizerFactory t =new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        ParagraphVectors vec = builder
                .minWordFrequency(1)
                .iterations(1)
                .epochs(5)
                .vocabCache(cache)
                .layerSize(100)
                .learningRate(0.4)
                .trainWordVectors(false)
                //.stopWords(new ArrayList<String>())
                .windowSize(5)
                .labelsSource(source)
                .tokenizerFactory(t)
                .iterate(iter)
                .build();

        vec.fit();
        return vec;
    }
}
