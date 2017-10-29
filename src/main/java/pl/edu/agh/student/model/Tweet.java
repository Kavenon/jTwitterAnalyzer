package pl.edu.agh.student.model;

public class Tweet {

    private long id;
    private String text;

    public Tweet() {

    }

    public Tweet(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
