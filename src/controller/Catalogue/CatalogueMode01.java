package controller.Catalogue;

import controller.ShowQA;
import database.JDBC;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.QA;
import utils.Utils;

import java.util.List;

public class CatalogueMode01 implements CatalogueMode {
    private GridPane table;

    CatalogueMode01(GridPane table) {
        this.table = table;
    }

    @Override
    public List<QA> getQAsFromDB() {
        return JDBC.getInstance().getQALevel_0_or_1();
    }

    @Override
    public void addQuestionToCatalogue(QA qa, int row) {
        int numOfQinList = row + 1;
        HBox hBox = new HBox();
        hBox.getChildren().addAll(new Label(numOfQinList + " "), Utils.getVBoxDescOfQuestion(qa));
        hBox.setOnMouseClicked(event -> ShowQA.show(qa));
        table.add(hBox, 0, row);
    }
}
