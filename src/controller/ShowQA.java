package controller;

import database.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.QA;
import utils.Utils;
import utils.WindowCreator;

import static utils.Constants.IMAGES_PATH;

public class ShowQA {
    private static QA qa;
    @FXML
    private ImageView imageView;
    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;
    @FXML
    private Label sectionLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label dateLabel;

    static void show(QA qaToShow) {
        qa = qaToShow;
        WindowCreator.getInstance().createShowWindow();
    }

    @FXML
    private void initialize() {
        questionLabel.setText(Utils.getFullDescriptionOfQuestion(qa));
        answerLabel.setText(qa.getAnswer());
        sectionLabel.setText("Section: " + qa.getSection());
        levelLabel.setText("Level: " + qa.getLevel());
        dateLabel.setText(qa.getDate());
        if (!qa.getImage().equals("")) {
            imageView.setImage(new Image(IMAGES_PATH + qa.getImage()));
        }
    }

    @FXML
    private void editButtonListener() {
        Edit.editQA(qa);
    }
}
