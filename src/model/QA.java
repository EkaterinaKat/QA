package model;

import java.util.Date;

public class QA {
    private int id;
    private String question;
    private String answer;
    private int level;
    private String section;
    private int day;
    private int month;
    private int year;

    public QA(int id, String question, String answer, int level, String section, int day, int month, int year) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.section = section;
        this.day = day;
        this.month = month;
        this.year = year;
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

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
