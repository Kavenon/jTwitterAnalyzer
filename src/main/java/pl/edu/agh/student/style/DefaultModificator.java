package pl.edu.agh.student.style;

public class DefaultModificator implements TextModificator {
    @Override
    public String modify(String text) {
        return text;
    }
}
