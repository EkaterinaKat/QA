package utils;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.QA;
import model.SubQuestion;

import java.util.List;

public class Utils {

    private Utils() {
    }

    public static void closeWindowThatContains(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void tuneLevelComboBox(ComboBox<Integer> comboBox) {
        ObservableList<Integer> levels = FXCollections.observableArrayList(0, 1, 2, 3);
        comboBox.setItems(levels);
    }

    public static String getStringDescOfQuestion(QA qa) {
        StringBuilder desc = new StringBuilder(String.format(" %s (%d)", qa.getQuestion(), qa.getLevel()));
        List<SubQuestion> subQuestions = JDBC.getInstance().getSubQuestions(qa);
        for (SubQuestion subQuestion : subQuestions) {
            desc.append(String.format("\n        * %s (%d)", subQuestion.getQuestion(), subQuestion.getLevel()));
        }
        return desc.toString();
    }

    public static VBox getVBoxDescOfQuestion(QA qa) {
        VBox vBox = new VBox();
        Label qLabel = new Label(String.format(" %s (%d)", qa.getQuestion(), qa.getLevel()));
        qLabel.setWrapText(true);
        qLabel.setMaxWidth(1000);
        qLabel.setStyle(getMainQuestionStyle());
        vBox.getChildren().add(qLabel);
        List<SubQuestion> subQuestions = JDBC.getInstance().getSubQuestions(qa);
        for (SubQuestion subQuestion : subQuestions) {
            Label subLabel = new Label(String.format(
                    "\n        * %s (%d)", subQuestion.getQuestion(), subQuestion.getLevel()));
            vBox.getChildren().add(subLabel);
        }
//        vBox.getChildren().add(getSeparationPane());
        return vBox;
    }

    public static String getMainQuestionStyle() {
        return "-fx-font-weight: bold; -fx-font-size: 18; ";
    }

    public static String getGreenTextStyle() {
        return "-fx-text-fill: #0ADB00; ";
    }

    public static String getBlackTextStyle() {
        return "-fx-text-fill: #000000; ";
    }

    public static String getGrayTextStyle() {
        return "-fx-text-fill: #939393; ";
    }

    public static Pane getSeparationPane() {
        Pane pane = new Pane();
        pane.setMinHeight(60);
        return pane;
    }

}
