package controller;

import database.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.QA;
import model.SubQuestion;
import utils.WindowCreator;

import java.util.List;

import static utils.Constants.IMAGES_PATH;

public class ShowQA {
    private static QA qa;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox questionPane;
    @FXML
    private Label answerLabel;
    @FXML
    private Label sectionLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label dateLabel;

    static void show(String question) {
        qa = JDBC.getInstance().getQAbyQuestion(question);
        WindowCreator.getInstance().createShowWindow();
    }

    @FXML
    private void initialize() {
        fillQuestionPane();
        answerLabel.setText(qa.getAnswer());
        sectionLabel.setText("Section: " + qa.getSection());
        levelLabel.setText("Level: " + qa.getLevel());
        dateLabel.setText(qa.getDate());
        if (!qa.getImage().equals("")) {
            imageView.setImage(new Image(IMAGES_PATH + qa.getImage()));
        }
    }

    private void fillQuestionPane() {
        questionPane.getChildren().add(new Label(qa.getQuestion()));
        List<SubQuestion> subQuestions = JDBC.getInstance().getSubQuestions(qa);
        for (SubQuestion subQuestion : subQuestions) {
            String labelText = String.format("   * %s (%d)", subQuestion.getQuestion(), subQuestion.getLevel());
            questionPane.getChildren().add(new Label(labelText));
        }
    }

    @FXML
    private void editButtonListener() {
        Edit.editQA(qa);
    }
}
