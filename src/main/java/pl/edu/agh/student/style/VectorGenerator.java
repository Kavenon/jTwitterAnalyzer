package pl.edu.agh.student.style;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.util.List;

public class VectorGenerator {

    public ParagraphVectors generate(List<String> labels, List<String> texts, TokenizerFactory tokenizerFactory){
        SentenceIterator iter = new CollectionSentenceIterator(texts);

        AbstractCache<VocabWord> cache = new AbstractCache<>();
        LabelsSource source = new LabelsSource(labels);

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
                .tokenizerFactory(tokenizerFactory)
                .allowParallelTokenization(false) //
                .sampling(0)
                .build();

        vec.fit();
        return vec;
    }
}
