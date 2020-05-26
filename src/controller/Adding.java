package controller;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.WindowCreator;

import java.time.LocalDate;

public class Adding {
    @FXML
    private TextField imageTextField;
    @FXML
    private Button addButton;
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
        tuneLevelBox();
        tuneSectionBox();
    }

    private void tuneLevelBox() {
        ObservableList<Integer> levels = FXCollections.observableArrayList(1, 2, 3);
        levelBox.setItems(levels);
        levelBox.setValue(1);
    }

    void tuneSectionBox() {
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
        String question = questionTextField.getText();
        String answer = answerTextField.getText();
        int level = levelBox.getValue();
        String section = sectionBox.getValue();
        LocalDate date = datePicker.getValue();
        String image = imageTextField.getText();
        JDBC.getInstance().addQA(question, answer, level, section, date, image);
        Catalogue.getInstance().updateCatalogue();
        clear();
    }

    private void clear() {
        questionTextField.clear();
        answerTextField.clear();
    }
}
