package controller;

import controller.Catalogue.Catalogue;
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

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private List<Button> catalogueModeButtons = new ArrayList<>();
    @FXML
    private Button sectionsButton;
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
                Catalogue.getInstance().search(searchField.getText()));
        level01ButtonListener();

        catalogueModeButtons.add(level_01_button);
        catalogueModeButtons.add(level_2_button);
        catalogueModeButtons.add(level_3_button);
        catalogueModeButtons.add(sectionsButton);
    }

    @FXML
    private void addButtonListener() {
        Adding.addQA();
    }

    @FXML
    private void learn012ButtonListener() {
        Learn.start(new LearnMode012());
    }

    @FXML
    private void learn23ButtonListener() {
        Learn.start(new LearnMode23());
    }

    @FXML
    private void level01ButtonListener() {
        activateButton(level_01_button);
        Catalogue.getInstance().show_01_level();
    }

    @FXML
    private void level2ButtonListener() {
        activateButton(level_2_button);
        Catalogue.getInstance().show_2_level();
    }

    @FXML
    private void level3ButtonListener() {
        activateButton(level_3_button);
        Catalogue.getInstance().show_3_level();
    }

    @FXML
    private void sectionsButtonListener() {
        activateButton(sectionsButton);
        Catalogue.getInstance().showSections();
    }

    private void activateButton(Button buttonToActivate) {
        for (Button button : catalogueModeButtons) {
            button.setDisable(false);
        }
        buttonToActivate.setDisable(true);
    }
}
