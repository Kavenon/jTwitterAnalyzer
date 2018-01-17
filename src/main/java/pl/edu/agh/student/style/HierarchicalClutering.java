package pl.edu.agh.student.style;

import com.apporiented.algorithm.clustering.AverageLinkageStrategy;
import com.apporiented.algorithm.clustering.Cluster;
import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HierarchicalClutering {

    private List<String> labels;
    private ParagraphVectors vec;

    public HierarchicalClutering(List<String> labels, ParagraphVectors vec) {
        this.labels = labels;
        this.vec = vec;
    }

    public Cluster cluster() {

        List<String> usersUnique = new ArrayList<>(new HashSet<>(this.labels));
        String[] labelsArray = new String[usersUnique.size()];
        labelsArray = usersUnique.toArray(labelsArray);

        double[][] distances = getDistances(usersUnique);

        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        return alg.performClustering(distances, labelsArray, new AverageLinkageStrategy());

    }

    private double[][] getDistances(List<String> usersUnique) {
        double[][] distances = new double[usersUnique.size()][usersUnique.size()];
        for (int i = 0; i < usersUnique.size(); i++) {
            for(int j = 0; j < usersUnique.size(); j++){
                if(distances[j][i] != 0){
                    distances[i][j] = distances[j][i];
                }
                else {
                    distances[i][j] = this.vec.similarity(usersUnique.get(i), usersUnique.get(j));
                }
            }
        }
        return distances;
    }
}
