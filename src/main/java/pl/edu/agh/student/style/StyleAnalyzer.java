package pl.edu.agh.student.style;

import com.apporiented.algorithm.clustering.Cluster;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import pl.edu.agh.student.model.Hierarchy;

import java.util.List;
import java.util.stream.Collectors;

public class StyleAnalyzer {

    private TextModificator modificator;

    public StyleAnalyzer(TextModificator modificator) {
        this.modificator = modificator;
    }

    public void run(List<String> labels, List<String> texts, String filename){

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

    }
}
