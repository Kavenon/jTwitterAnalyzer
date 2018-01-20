package pl.edu.agh.student.sentiment;

import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.util.*;

public class MorfologikSentimentTool {

    private PolishStemmer polishStemmer = new PolishStemmer();

    String mapWithBasicForms(String wordToTransform) {
        List<WordData> result = polishStemmer.lookup(wordToTransform);

        if(result.size() == 0)
            return wordToTransform;
        else {
            List<String> stemList = new ArrayList<>();

            for(WordData data : result) {
                stemList.add(data.getStem().toString());
            }

            Collections.sort(stemList);
            return String.join("", stemList);
        }
    }
}
