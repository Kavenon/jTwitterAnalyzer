package pl.edu.agh.student.style;

import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PartOfSpeechModificator implements TextModificator {

    private PolishStemmer polishStemmer = new PolishStemmer();

    @Override
    public String modify(String text) {
        return Arrays.stream(text.split(" "))
                .map(item -> {
                    try {
                        List<WordData> result = polishStemmer.lookup(item);
                        return String.valueOf(result.get(0).getTag()).split(":")[0];
                    }
                    catch(Exception e){
                        System.out.println("Stem failed:" + item);
                        return null;
                    }
                })
                .filter(item -> item != null && item.length() > 0)
                .collect(Collectors.joining(" "));

    }
}
