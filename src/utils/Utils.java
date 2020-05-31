package utils;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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

    public static String getFullDescriptionOfQuestion(QA qa) {
        StringBuilder desc = new StringBuilder(String.format(" %s (%d)", qa.getQuestion(), qa.getLevel()));
        List<SubQuestion> subQuestions = JDBC.getInstance().getSubQuestions(qa);
        for (SubQuestion subQuestion : subQuestions) {
            desc.append(String.format("\n        * %s (%d)", subQuestion.getQuestion(), subQuestion.getLevel()));
        }
        return desc.toString();
    }

}
