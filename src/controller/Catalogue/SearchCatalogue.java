package controller.Catalogue;

import database.JDBC;
import javafx.scene.layout.GridPane;
import model.QA;

import java.util.ArrayList;
import java.util.List;

class SearchCatalogue implements CatalogueMode {
    private GridPane table;
    private String lastSearch;

    SearchCatalogue(GridPane table) {
        this.table = table;
    }

    void searchAndShow(String string) {
        table.getChildren().clear();
        List<QA> qas = JDBC.getInstance().getAllQA();
        List<QA> suitableQAs = new ArrayList<>();
        for (QA qa : qas) {
            if (qa.getQuestion().contains(string)) {
                suitableQAs.add(qa);
            }
        }
        Helper.fillGridPaneWithPlainQAs(suitableQAs, table);
    }

    @Override
    public void updateCatalogue() {
        searchAndShow(lastSearch);
    }
}
