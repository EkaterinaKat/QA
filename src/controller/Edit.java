package controller;

import controller.Catalogue.Catalogue;
import database.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.QA;
import model.SubQuestion;
import utils.Utils;
import utils.WindowCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Edit {
    private Map<TextField, ComboBox<Integer>> subQuestionElementsMap = new HashMap<>();
    private static QA qa;
    private static ShowQA showWindow;
    @FXML
    private VBox subQuestionPane;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextArea questionTextField;
    @FXML
    private TextArea answerTextField;

    static void editQA(QA qa1, ShowQA showQA) {
        qa = qa1;
        showWindow = showQA;
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
        List<SubQuestion> subQuestions = JDBC.getInstance().getSubQuestions(qa);
        for (SubQuestion subQuestion : subQuestions) {
            addElementsForSubQuestion(subQuestion);
        }
    }

    @FXML
    private void doneButtonListener() {
        updateQAtableInDb();
        updateSubQtableInDb();
        Catalogue.getInstance().updateCatalogue();
        showWindow.update();
        Utils.closeWindowThatContains(questionTextField);
    }

    private void updateQAtableInDb() {
        String question = questionTextField.getText().trim();
        String answer = answerTextField.getText().trim();
        String image = imageTextField.getText().trim();
        JDBC.getInstance().editQA(qa, question, answer, image);
    }

    private void updateSubQtableInDb() {
        List<SubQuestion> subQuestions = collectedEnteredSubQ();
        JDBC.getInstance().updateSubQuestions(qa, subQuestions);
    }

    private List<SubQuestion> collectedEnteredSubQ() {
        List<SubQuestion> subQuestions = new ArrayList<>();
        for (Map.Entry<TextField, ComboBox<Integer>> entry : subQuestionElementsMap.entrySet()) {
            String question = entry.getKey().getText().trim();
            int level = entry.getValue().getValue();
            subQuestions.add(new SubQuestion(qa.getId(), question, level));
        }
        return subQuestions;
    }

    @FXML
    private void addSubQuestionListener() {
        addElementsForSubQuestion();
    }

    /* Добавление полей для нового подвопроса */
    private void addElementsForSubQuestion() {
        HBox hBox = new HBox();
        TextField textField = new TextField();
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setDisable(true);
        comboBox.setValue(0);
        hBox.getChildren().addAll(textField, comboBox);
        subQuestionPane.getChildren().add(hBox);
        subQuestionElementsMap.put(textField, comboBox);
    }

    /* Добавление полей для уже существующего подвопроса */
    private void addElementsForSubQuestion(SubQuestion subQuestion) {
        HBox hBox = new HBox();
        TextField textField = new TextField();
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setDisable(true);
        comboBox.setValue(subQuestion.getLevel());
        textField.setText(subQuestion.getQuestion());
        hBox.getChildren().addAll(textField, comboBox);
        subQuestionPane.getChildren().add(hBox);
        subQuestionElementsMap.put(textField, comboBox);
    }
}
