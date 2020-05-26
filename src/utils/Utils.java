package utils;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Utils {

    private Utils() {
    }

    public static void closeWindowThatContains(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
