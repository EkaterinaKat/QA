package controller.Catalogue;

import database.JDBC;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.QA;

import java.util.List;

class SectionsCatalogue implements CatalogueMode {
    private GridPane table;
    private String lastChosenSection;

    SectionsCatalogue(GridPane table) {
        this.table = table;
    }

    void showSections() {
        table.getChildren().clear();
        List<String> sections = JDBC.getInstance().getSections();
        int row = 0;
        for (String s : sections) {
            addSectionToCatalogue(s, row);
            row++;
        }
    }

    private void addSectionToCatalogue(String section, int row) {
        int numOfSec = row + 1;
        Label label = new Label(numOfSec + "   " + section);
        label.setOnMouseClicked(event -> showSectionQA(section));
        table.add(label, 0, row);
    }

    private void showSectionQA(String section) {
        lastChosenSection = section;
        table.getChildren().clear();
        List<QA> qas = JDBC.getInstance().getQAsBySection(section);
        Helper.fillGridPaneWithPlainQAs(qas, table);
    }

    @Override
    public void updateCatalogue() {
        showSectionQA(lastChosenSection);
    }
}
