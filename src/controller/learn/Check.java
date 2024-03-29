package controller.learn;

import controller.Catalogue.Catalogue;
import database.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.QA;
import model.SubQuestion;
import utils.Utils;
import utils.WindowCreator;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CHECK_WIDTH;
import static utils.Constants.IMAGES_PATH;
import static utils.Utils.*;

public class Check {
    private static List<QA> qas;
    private static LearnMode mode;
    private List<Group> groups = new ArrayList<>();
    @FXML
    private Button applyButton;
    @FXML
    private VBox mainVBox;

    static void check(List<QA> list, LearnMode learnMode) {
        qas = list;
        mode = learnMode;
        WindowCreator.getInstance().createCheckWindow();
    }

    @FXML
    private void initialize() {
        for (QA qa : qas) {
            mainVBox.getChildren().add(getVBoxWithQA(qa));
        }
    }

    private VBox getVBoxWithQA(QA qa) {
        VBox vBox = new VBox();
        Group group = new Group(qa);
        groups.add(group);
        vBox.getChildren().addAll(group.getVBoxWithQuestions(), getAnswerPane(qa), getSeparationPane());
        return vBox;
    }

    private ScrollPane getAnswerPane(QA qa) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMaxHeight(400);
        VBox vBox = new VBox();
        Label answerLabel = new Label(qa.getAnswer());
        answerLabel.setMaxWidth(CHECK_WIDTH - 160);
        scrollPane.setMaxWidth(CHECK_WIDTH - 140);
        scrollPane.setMinWidth(CHECK_WIDTH - 140);
        answerLabel.wrapTextProperty().setValue(true);
        ImageView imageView = new ImageView(new Image(IMAGES_PATH + qa.getImage()));
        vBox.getChildren().addAll(answerLabel, imageView);
        scrollPane.setContent(vBox);
        return scrollPane;
    }

    @FXML
    private void applyButtonListener() {
        for (Group group : groups) {
            group.applyResults();
            group.disableCheckBoxes();
            group.updateLabels();
        }
        applyButton.setDisable(true);
        Catalogue.getInstance().updateCatalogue();
    }

    @FXML
    private void okButtonListener() {
        Utils.closeWindowThatContains(mainVBox);
    }

    class Group {
        QA qa;
        List<SubGroup> subGroups = new ArrayList<>();
        CheckBox checkBox;
        Label label;

        Group(QA qa) {
            this.qa = qa;
            checkBox = new CheckBox();
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> setQuestionStyle(label, newValue));
            label = new Label(String.format(" %s (%d)", qa.getQuestion(), qa.getLevel()));
            setQuestionStyle(label, false);
            List<SubQuestion> subQuestions = JDBC.getInstance().getSubQuestions(qa);
            for (SubQuestion subQuestion : subQuestions) {
                subGroups.add(new SubGroup(subQuestion));
            }
            mode.tuneGroup(this);
        }

        void setQuestionStyle(Label label, boolean b) {
            if (b) {
                label.setStyle(getGreenTextStyle() + getMainQuestionStyle());
            } else {
                label.setStyle(getBlackTextStyle() + getMainQuestionStyle());
            }
        }

        VBox getVBoxWithQuestions() {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            hBox.getChildren().addAll(checkBox, label);
            vBox.getChildren().add(hBox);
            for (SubGroup subGroup : subGroups) {
                vBox.getChildren().add(subGroup.getHBox());
            }
            return vBox;
        }

        void applyResults() {
            /* Записываем результаты локально в объекты */
            mode.setAndSynchronizeNewLevels(this);
            /* Записываем результаты в БД, доставая их прямо из объектов */
            saveResultsToDB();
        }

        private void saveResultsToDB() {
            JDBC.getInstance().setQANewLevel(qa);
            JDBC.getInstance().setNewDate(qa);
            for (SubGroup subGroup : subGroups) {
                JDBC.getInstance().setSubQNewLevel(subGroup.getSubQuestion());
            }
        }

        void disableCheckBoxes() {
            checkBox.setDisable(true);
            for (SubGroup subGroup : subGroups) {
                subGroup.checkBox.setDisable(true);
            }
        }

        void updateLabels() {
            label.setText(String.format(" %s (%d)", qa.getQuestion(), JDBC.getInstance().getQuestionLevel(qa)));
            for (SubGroup subGroup : subGroups) {
                subGroup.updateLabel();
            }
        }

        class SubGroup {
            SubQuestion subQuestion;
            CheckBox checkBox;
            Label label;

            SubGroup(SubQuestion subQuestion) {
                this.subQuestion = subQuestion;
                checkBox = new CheckBox();
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> setSubQuestionStyle(label, newValue));
                label = new Label(String.format("   * %s (%d)", subQuestion.getQuestion(), subQuestion.getLevel()));
            }

            HBox getHBox() {
                HBox hBox = new HBox();
                hBox.getChildren().addAll(checkBox, label);
                return hBox;
            }

            void updateLabel() {
                label.setText(String.format("   * %s (%d)", subQuestion.getQuestion(), JDBC.getInstance().getSubQLevel(subQuestion)));
            }

            void setSubQuestionStyle(Label label, boolean b) {
                if (b) {
                    label.setStyle(getGreenTextStyle());
                } else {
                    label.setStyle(getBlackTextStyle());
                }
            }

            void setDisableAndTurnGray() {
                checkBox.setDisable(true);
                label.setStyle(getGrayTextStyle());
            }

            int getLevel() {
                return subQuestion.getLevel();
            }

            CheckBox getCheckBox() {
                return checkBox;
            }

            SubQuestion getSubQuestion() {
                return subQuestion;
            }
        }

        List<SubGroup> getSubGroups() {
            return subGroups;
        }

        CheckBox getCheckBox() {
            return checkBox;
        }

        QA getQa() {
            return qa;
        }
    }
}
