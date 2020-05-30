package controller;

import controller.learn.Learn;
import controller.learn.LearnMode012;
import controller.learn.LearnMode23;
import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.WindowCreator;

public class Main extends Application {
    @FXML
    private Button level_01_button;
    @FXML
    private Button level_2_button;
    @FXML
    private Button level_3_button;
    @FXML
    private TextField searchField;
    @FXML
    private GridPane gridPane;

    @Override
    public void start(Stage primaryStage) {
        WindowCreator.getInstance().createMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JDBC.getInstance().disconnect();
    }

    @FXML
    private void initialize() {
        Catalogue.create(gridPane);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                Catalogue.getInstance().updateCatalogue(searchField.getText()));
        makeButtonActive(level_01_button);
    }

    @FXML
    private void addButtonListener() {
        Adding.addQA();
    }

    public void learn012ButtonListener() {
        Learn.start(new LearnMode012());
    }

    public void learn23ButtonListener() {
        Learn.start(new LearnMode23());
    }

    @FXML
    private void level01ButtonListener() {
        makeButtonActive(level_01_button);
        makeButtonInactive(level_2_button);
        makeButtonInactive(level_3_button);
        Catalogue.getInstance().show_01_level();
    }

    @FXML
    private void level2ButtonListener() {
        makeButtonActive(level_2_button);
        makeButtonInactive(level_01_button);
        makeButtonInactive(level_3_button);
        Catalogue.getInstance().show_2_level();
    }

    @FXML
    private void level3ButtonListener() {
        makeButtonActive(level_3_button);
        makeButtonInactive(level_2_button);
        makeButtonInactive(level_01_button);
        Catalogue.getInstance().show_3_level();
    }

    private void makeButtonActive(Button button) {
        button.setDisable(true);
    }

    private void makeButtonInactive(Button button) {
        button.setDisable(false);
    }

}
