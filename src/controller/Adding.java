package controller;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Utils;
import utils.WindowCreator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Adding implements SectionCreationAware {
    private Map<TextField, ComboBox> subQuestionMap;
    @FXML
    private VBox subQuestionPane;
    @FXML
    private TextField imageTextField;
    @FXML
    private ComboBox<String> sectionBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> levelBox;
    @FXML
    private TextArea questionTextField;
    @FXML
    private TextArea answerTextField;

    static void addQA() {
        WindowCreator.getInstance().createAddingWindow();
    }

    @FXML
    private void initialize() {
        subQuestionMap = new HashMap<>();
        Utils.tuneLevelComboBox(levelBox);
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
        int level = levelBox.getValue();
        String section = sectionBox.getValue();
        LocalDate date = datePicker.getValue();
        String image = imageTextField.getText();
        return JDBC.getInstance().addQA(question, answer, level, section, date, image);
    }

    private void saveSubQuestionsToDB(int qa_id) {
        for (Map.Entry entry : subQuestionMap.entrySet()) {
            String question = ((TextField) entry.getKey()).getText().trim();
            int level = ((ComboBox<Integer>) entry.getValue()).getValue();
            JDBC.getInstance().createSubQuestion(question, level, qa_id);
        }
    }

    private void clearFields() {
        questionTextField.clear();
        answerTextField.clear();
        imageTextField.clear();
        subQuestionPane.getChildren().clear();
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
        ComboBox<Integer> comboBox = new ComboBox<>();
        Utils.tuneLevelComboBox(comboBox);
        hBox.getChildren().addAll(textField, comboBox);
        subQuestionPane.getChildren().add(hBox);
        subQuestionMap.put(textField, comboBox);
    }
}
