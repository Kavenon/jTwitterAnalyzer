package pl.edu.agh.student.style;

import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.util.List;

public class PartOfSpeechModificator implements TextModificator {

    private PolishStemmer polishStemmer = new PolishStemmer();

    @Override
    public String modify(String text) {
        List<WordData> result = polishStemmer.lookup(text);
        return String.valueOf(result.get(0).getTag()).split(":")[0];
    }
}
