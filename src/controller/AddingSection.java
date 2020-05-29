package controller;

import database.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utils.Utils;
import utils.WindowCreator;

public class AddingSection {
    private static SectionCreationAware sectionCreationAware;
    @FXML
    private Button okButton;
    @FXML
    private TextField nameInputField;

    static void addSection(SectionCreationAware reciver) {
        sectionCreationAware = reciver;
        WindowCreator.getInstance().createAddingSectionWindow();
    }

    @FXML
    private void initialize() {
        okButton.setDisable(true);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) ->
                okButton.setDisable(nameInputFieldIsEmpty()));
    }

    private boolean nameInputFieldIsEmpty() {
        return nameInputField.getText().trim().equals("") || nameInputField.getText() == null;
    }

    @FXML
    private void okButtonListener() {
        JDBC.getInstance().addSection(nameInputField.getText());
        sectionCreationAware.inform();
        Utils.closeWindowThatContains(okButton);
    }
}
