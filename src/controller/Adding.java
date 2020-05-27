package controller;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.WindowCreator;

import java.time.LocalDate;

public class Adding implements SectionCreationAware {
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
        tuneLevelBox();
        tuneSectionBox();
    }

    private void tuneLevelBox() {
        ObservableList<Integer> levels = FXCollections.observableArrayList(1, 2, 3);
        levelBox.setItems(levels);
        levelBox.setValue(1);
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
        imageTextField.clear();
    }

    @Override
    public void inform() {
        tuneSectionBox();
    }
}
