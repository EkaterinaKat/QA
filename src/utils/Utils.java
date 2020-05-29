package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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

}
