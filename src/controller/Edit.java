package controller;

import database.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.QA;
import utils.Utils;
import utils.WindowCreator;

public class Edit {
    private static QA qa;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextArea questionTextField;
    @FXML
    private TextArea answerTextField;

    static void editQA(QA qa1) {
        qa = qa1;
        WindowCreator.getInstance().createEditWindow();
    }

    @FXML
    private void initialize() {
        setValues();
    }

    private void setValues() {
        imageTextField.setText(qa.getImage());
        questionTextField.setText(qa.getQuestion());
        answerTextField.setText(qa.getAnswer());
    }

    @FXML
    private void doneButtonListener() {
        String question = questionTextField.getText();
        String answer = answerTextField.getText();
        String image = imageTextField.getText();
        JDBC.getInstance().editQA(qa, question, answer, image);
        Catalogue.getInstance().updateCatalogue();
        Utils.closeWindowThatContains(questionTextField);
    }
}
