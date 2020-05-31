package controller.Catalogue;

import controller.ShowQA;
import database.JDBC;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.QA;
import utils.DateManager;

import java.util.List;

public class CatalogueMode3 implements CatalogueMode {
    private GridPane table;

    CatalogueMode3(GridPane table) {
        this.table = table;
    }

    @Override
    public List<QA> getQAsFromDB() {
        List<QA> qas = JDBC.getInstance().getQALevel_3();
        DateManager.getInstance().sortByDate(qas);
        return qas;
    }

    @Override
    public void addQuestionToCatalogue(QA qa, int row) {
        int numOfQinList = row + 1;
        Label label = new Label(numOfQinList + "   " + qa.getQuestion());
        label.setOnMouseClicked(event -> ShowQA.show(qa));
        table.add(label, 0, row);
    }
}
