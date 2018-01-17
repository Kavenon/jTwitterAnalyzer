package pl.edu.agh.student.style;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.student.model.Hierarchy;

import java.io.PrintWriter;

public class HierarchyJavascriptSaver {

    private ObjectMapper om = new ObjectMapper();
    private Hierarchy hierarchy;

    public HierarchyJavascriptSaver(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void save(String filename){
        try {
            String s = om.writeValueAsString(hierarchy);

            try(  PrintWriter out = new PrintWriter( filename )  ){
                out.println("data = '" + s + "';" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
