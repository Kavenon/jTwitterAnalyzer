package pl.edu.agh.student.sentiment;

import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.util.List;

public class MorfologikSentimentTool {

    private PolishStemmer polishStemmer = new PolishStemmer();

    List<String> mapWithBasicForms(List<String> wordsToTransform) {
        for(String word : wordsToTransform) {
            List<WordData> result = polishStemmer.lookup(word);
        }
        return null;
    }

}
