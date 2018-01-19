package pl.edu.agh.student.style;

import com.apporiented.algorithm.clustering.Cluster;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import pl.edu.agh.student.model.Hierarchy;
import pl.edu.agh.student.model.Tweet;

import java.util.List;
import java.util.stream.Collectors;

public class StyleAnalyzer {

    private TextModificator modificator;

    public StyleAnalyzer(TextModificator modificator) {
        this.modificator = modificator;
    }

    public ParagraphVectors run(List<String> labels, List<String> texts, String filename){

        texts = texts.stream()
                .map(this.modificator::modify)
                .collect(Collectors.toList());

        VectorGenerator vectorGenerator = new VectorGenerator();
        ParagraphVectors vec = vectorGenerator.generate(labels, texts);

        HierarchicalClutering hierarchicalClutering = new HierarchicalClutering(labels, vec);
        Cluster cluster = hierarchicalClutering.cluster();

        HierarchyGenerator hierarchyGenerator = new HierarchyGenerator(cluster);
        Hierarchy hierarchy = hierarchyGenerator.generate();

        HierarchyJavascriptSaver hierarchyJavascriptSaver = new HierarchyJavascriptSaver(hierarchy);
        hierarchyJavascriptSaver.save("visualise/" + filename);

        return vec;

    }

    public void test(List<Tweet> tweets, ParagraphVectors vc){

        int success = 0;
        int fail = 0;
        for (Tweet tweet : tweets) {
            List<String> strings = (List<String>) vc.nearestLabels(this.modificator.modify(tweet.getText()), 1);
//            System.out.println(tweet.getUser() + " -> " + strings);
            if(matchSuccess(tweet, strings)){
                success++;
            }
            else {
                fail++;
            }
        }

        System.out.println("Total: " + tweets.size());
        System.out.println("Failed: " + fail);
        System.out.println("Success: " + success);
        System.out.println("SuccessRatio: " + (double) success/tweets.size());
    }

    private boolean matchSuccess(Tweet tweet, List<String> strings) {
        return strings != null && strings.size() > 0 && strings.get(0) != null && tweet.getUser().equals(strings.get(0));
    }
}
