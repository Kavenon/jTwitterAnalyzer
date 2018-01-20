package pl.edu.agh.student.style;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;

import java.util.List;

public class VectorGenerator {

    public ParagraphVectors generate(List<String> labels, List<String> texts, ParagraphVectors.Builder builder){
        SentenceIterator iter = new CollectionSentenceIterator(texts);

        AbstractCache<VocabWord> cache = new AbstractCache<>();
        LabelsSource source = new LabelsSource(labels);

        ParagraphVectors vec = builder.iterate(iter).vocabCache(cache).labelsSource(source).build();

        vec.fit();
        return vec;
    }
}
