package model;

public class QA {
    private int id;
    private String question;
    private String answer;
    private int level;
    private String section;
    private String date;
    private String image;

    public QA(int id, String question, String answer, int level, String section, String date, String image) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.section = section;
        this.date = date;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getLevel() {
        return level;
    }

    public String getSection() {
        return section;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
