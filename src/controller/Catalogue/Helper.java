package controller.Catalogue;

import controller.ShowQA;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.QA;
import utils.DateManager;
import utils.Utils;

import java.util.List;

class Helper {
    private Helper() {
    }

    static void fillGridPaneWithPlainQAs(List<QA> qas, GridPane table) {
        table.getChildren().clear();
        DateManager.getInstance().sortByDate(qas);
        int row = 0;
        for (QA qa : qas) {
            int numOfQinList = row + 1;
            Label label = new Label(numOfQinList + "   " + qa.getQuestion());
            label.setOnMouseClicked(event -> ShowQA.show(qa));
            table.add(label, 0, row);
            row++;
        }
    }

    static void fillGridPaneWithQAsAndDates(List<QA> qas, GridPane table) {
        table.getChildren().clear();
        DateManager.getInstance().sortByDate(qas);
        int row = 0;
        for (QA qa : qas) {
            int numOfQinList = row + 1;
            Label label = new Label(numOfQinList + "   " + qa.getDate() + "   " + qa.getQuestion());
            label.setOnMouseClicked(event -> ShowQA.show(qa));
            table.add(label, 0, row);
            row++;
        }
    }

    static void fillGridPainWithQAsAndSubQAs(List<QA> qas, GridPane table) {
        table.getChildren().clear();
        DateManager.getInstance().sortByDate(qas);
        int row = 0;
        for (QA qa : qas) {
            int numOfQinList = row + 1;
            HBox hBox = new HBox();
            hBox.getChildren().addAll(new Label(numOfQinList + " "), Utils.getVBoxDescOfQuestion(qa));
            hBox.setOnMouseClicked(event -> ShowQA.show(qa));
            table.add(hBox, 0, row);
            row++;
        }
    }
}
