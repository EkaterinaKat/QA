package controller.Catalogue;

import controller.ShowQA;
import database.JDBC;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.QA;
import utils.Utils;

import java.util.List;

public class CatalogueMode01 implements CatalogueMode{
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
        int numOfQinList = row+1;
        Label label = new Label(numOfQinList+"   "+Utils.getFullDescriptionOfQuestion(qa));
        label.setOnMouseClicked(event -> ShowQA.show(qa));
        table.add(label, 0, row);
    }
}
