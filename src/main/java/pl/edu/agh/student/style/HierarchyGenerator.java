package pl.edu.agh.student.style;

import com.apporiented.algorithm.clustering.Cluster;
import pl.edu.agh.student.StyleApp;
import pl.edu.agh.student.model.Hierarchy;
import pl.edu.agh.student.model.HierarchyText;

import java.util.stream.Collectors;

public class HierarchyGenerator {

    private Cluster cluster;

    public HierarchyGenerator(Cluster cluster) {
        this.cluster = cluster;
    }

    public Hierarchy generate(){
        return toHierarchy(this.cluster);
    }

    private Hierarchy toHierarchy(Cluster cluster) {

        Hierarchy h = new Hierarchy();
        h.setText(new HierarchyText(cluster.getName()));

        h.setChildren(
                cluster.getChildren()
                        .stream()
                        .map(StyleApp::toHierarchy)
                        .collect(Collectors.toList()));

        return h;

    }

}
