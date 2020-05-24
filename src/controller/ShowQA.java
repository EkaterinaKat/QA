package controller;

import database.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.QA;
import utils.WindowCreator;

public class ShowQA {
    private static QA qa;
    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;
    @FXML
    private Label sectionLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label dateLabel;

    static void show(String question){
        qa = JDBC.getInstance().getQA(question);
        WindowCreator.getInstance().createShowWindow();
    }

    @FXML
    private void initialize(){
        questionLabel.setText(qa.getQuestion());
        answerLabel.setText(qa.getAnswer());
        sectionLabel.setText("Section: "+qa.getSection());
        levelLabel.setText("Level: "+qa.getLevel());
        dateLabel.setText(String.format("%d.%d.%d", qa.getDay(), qa.getMonth(), qa.getYear()));
    }

}
