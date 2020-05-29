package model;

public class SubQuestion {
    private int QAID;
    private String question;
    private int level;

    public SubQuestion(int QAID, String question, int level) {
        this.QAID = QAID;
        this.question = question;
        this.level = level;
    }

    public int getQAID() {
        return QAID;
    }

    public void setQAID(int QAID) {
        this.QAID = QAID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
