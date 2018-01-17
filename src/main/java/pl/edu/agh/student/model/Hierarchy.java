package pl.edu.agh.student.model;

import java.util.ArrayList;
import java.util.List;

public class Hierarchy {

    HierarchyText text;
    List<Hierarchy> children = new ArrayList<>();

    public Hierarchy() {

    }

    public Hierarchy(HierarchyText text, List<Hierarchy> children) {
        this.text = text;
        this.children = children;
    }

    public HierarchyText getText() {
        return text;
    }

    public void setText(HierarchyText text) {
        this.text = text;
    }

    public List<Hierarchy> getChildren() {
        return children;
    }

    public void setChildren(List<Hierarchy> children) {
        this.children = children;
    }
}
