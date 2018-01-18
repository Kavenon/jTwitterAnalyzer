package pl.edu.agh.student.model;

public enum Sentiment {
    POS(0), NEG(1), NEU(2);

    private int val;
    Sentiment(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
