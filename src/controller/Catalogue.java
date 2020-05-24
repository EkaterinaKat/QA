package controller;

import database.JDBC;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

class Catalogue {
    private static Catalogue instance;
    private GridPane table;

    static Catalogue getInstance() {
        return instance;
    }

    static void create(GridPane table) {
        instance = new Catalogue(table);
    }

    private Catalogue(GridPane table) {
        this.table = table;
        updateCatalogue();
    }

    void updateCatalogue() {
        List<String> questions = JDBC.getInstance().getQuestions();
        int row = 0;
        for (String question : questions) {
            Label label = new Label(question);
            label.setOnMouseClicked(event -> ShowQA.show(question));
            table.add(label, 0, row);
            row++;
        }
    }
}
