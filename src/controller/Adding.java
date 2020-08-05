package controller;

import controller.Catalogue.Catalogue;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.WindowCreator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Adding implements SectionCreationAware {
    private List<TextField> subQueTextFields = new ArrayList<>();
    @FXML
    private VBox subQuestionPane;
    @FXML
    private TextField imageTextField;
    @FXML
    private ComboBox<String> sectionBox;
    @FXML
    private TextArea questionTextField;
    @FXML
    private TextArea answerTextField;

    static void addQA() {
        WindowCreator.getInstance().createAddingWindow();
    }

    @FXML
    private void initialize() {
        tuneSectionBox();
    }

    private void tuneSectionBox() {
        ObservableList<String> sections = FXCollections.observableArrayList();
        sections.addAll(JDBC.getInstance().getSections());
        sectionBox.setItems(sections);
    }

    @FXML
    private void addSectionButtonListener() {
        AddingSection.addSection(this);
    }

    @FXML
    private void addButtonListener() {
        int qa_id = saveQAtoDB();
        saveSubQuestionsToDB(qa_id);
        Catalogue.getInstance().updateCatalogue();
        clearFields();
    }

    private int saveQAtoDB() {
        String question = questionTextField.getText().trim();
        String answer = answerTextField.getText().trim();
        int level = 0;
        String section = sectionBox.getValue();
        LocalDate date = LocalDate.now();
        String image = imageTextField.getText();
        return JDBC.getInstance().addQA(question, answer, level, section, date, image);
    }

    private void saveSubQuestionsToDB(int qa_id) {
        for (TextField textField : subQueTextFields) {
            String question = textField.getText();
            int level = 0;
            JDBC.getInstance().createSubQuestion(question, level, qa_id);
        }
    }

    private void clearFields() {
        questionTextField.clear();
        answerTextField.clear();
        imageTextField.clear();
        subQuestionPane.getChildren().clear();
        subQueTextFields = new ArrayList<>();
    }

    @Override
    public void inform() {
        tuneSectionBox();
    }

    @FXML
    private void addSubQuestionListener() {
        addElementsForSubQuestion();
    }

    private void addElementsForSubQuestion() {
        HBox hBox = new HBox();
        TextField textField = new TextField();
        hBox.getChildren().addAll(textField);
        subQuestionPane.getChildren().add(hBox);
        subQueTextFields.add(textField);
    }
}
