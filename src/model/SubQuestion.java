package model;

public class SubQuestion implements HavingLevel{
    private int ID;
    private int QA_ID;
    private String question;
    private int level;

    /* Этот конструктор для транспортировки подвопросов от ввода в полях GI до БД */
    public SubQuestion(int QA_ID, String question, int level) {
        this.QA_ID = QA_ID;
        this.question = question;
        this.level = level;
    }

    /* Этот конструктор для того чтобы доставать подвопросы из БД */
    public SubQuestion(int ID, int QA_ID, String question, int level) {
        this.ID = ID;
        this.QA_ID = QA_ID;
        this.question = question;
        this.level = level;
    }

    public int getQA_ID() {
        return QA_ID;
    }

    public void setQA_ID(int QA_ID) {
        this.QA_ID = QA_ID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    public int getID() {
        return ID;
    }
}
