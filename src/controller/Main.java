package controller;

import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.WindowCreator;

public class Main extends Application {
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
    }

    @FXML
    private void addButtonListener() {
        Adding.addQA();
    }

    public void learn12ButtonListener() {
        Learn.start(Learn.LearningMode.TO_2_LEVEL);
    }
}
