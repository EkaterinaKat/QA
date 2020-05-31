package controller.Catalogue;

import controller.ShowQA;
import database.JDBC;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.QA;
import utils.DateManager;

import java.util.List;

public class CatalogueMode2 implements CatalogueMode {
    private GridPane table;
    private DateManager dateManager= new DateManager();

    CatalogueMode2(GridPane table) {
        this.table = table;
    }

    @Override
    public List<QA> getQAsFromDB() {
        List<QA> qas = JDBC.getInstance().getQALevel_2();
        dateManager.sortByDate(qas);
        return qas;
    }

    @Override
    public void addQuestionToCatalogue(QA qa, int row) {
        int numOfQinList = row+1;
        Label label = new Label(numOfQinList + "   " + qa.getDate() + "   " + qa.getQuestion());
        label.setOnMouseClicked(event -> ShowQA.show(qa));
        table.add(label, 0, row);
    }
}