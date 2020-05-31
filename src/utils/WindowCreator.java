package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static utils.Constants.*;

public class WindowCreator {
    private static final WindowCreator instance = new WindowCreator();

    public static WindowCreator getInstance() {
        return instance;
    }

    private WindowCreator() {
    }

    public void createMainWindow() {
        Stage stage = getStage("main.fxml", MAIN_WIDTH, MAIN_HEIGHT);
        stage.show();
    }

    public void createAddingWindow() {
        createModalWindow("adding.fxml", ADDING_WIDTH, ADDING_HEIGHT);
    }

    public void createEditWindow() {
        createModalWindow("edit.fxml", ADDING_WIDTH, ADDING_HEIGHT);
    }

    public void createAddingSectionWindow() {
        createModalWindow("adding_section.fxml", ADDING_SECTION_WIDTH, ADDING_SECTION_HEIGHT);
    }

    public void createShowWindow() {
        createModalWindow("show.fxml", SHOW_WIDTH, SHOW_HEIGHT);
    }

    public void createLearnWindow() {
        createModalWindow("learn.fxml", MAIN_WIDTH, MAIN_HEIGHT);
    }

    public void createCheckWindow() {
        createModalWindow("learn.fxml", MAIN_WIDTH, MAIN_HEIGHT);
    }

    private void createModalWindow(String fxmlName, int width, int height) {
        Stage stage = getStage(fxmlName, width, height);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private Stage getStage(String fxmlName, int width, int height) {
        Stage stage = new Stage();
        stage.setTitle(TITLE);
        stage.setMinHeight(height);
        stage.setMaxHeight(height);
        stage.setMinWidth(width);
        stage.setMaxWidth(width);
        stage.setScene(new Scene(getParent(fxmlName), width, height));
        setClosingWithEscape(stage);
        return stage;
    }

    private Parent getParent(String fxmlName) {
        String fullFxmlName = FXML_PATH + fxmlName;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fullFxmlName));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

    private void setClosingWithEscape(Stage stage) {
        stage.getScene().setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });
    }

}
