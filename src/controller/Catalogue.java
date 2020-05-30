package controller;

import database.JDBC;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.QA;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

class Catalogue {
    private static Catalogue instance;
    private Level level;
    private GridPane table;

    static Catalogue getInstance() {
        return instance;
    }

    static void create(GridPane table) {
        instance = new Catalogue(table);
    }

    private Catalogue(GridPane table) {
        this.table = table;
        level = Level._01;
        updateCatalogue();
    }

    enum Level {_01, _2, _3}

    void updateCatalogue() {
        table.getChildren().clear();
        List<QA> qas = getQAs();
        int row = 0;
        for (QA qa : qas) {
            addQuestionToCatalogue(qa, row);
            row++;
        }
    }

    private List<QA> getQAs() {
        List<QA> list = new ArrayList<>();
        switch (level) {
            case _01:
                list = JDBC.getInstance().getQALevel_0_or_1();
                break;
            case _2:
                list = JDBC.getInstance().getQALevel_2();
                break;
            case _3:
                list = JDBC.getInstance().getQALevel_3();
        }
        return list;
    }

    private void addQuestionToCatalogue(QA qa, int row) {
        Label label = new Label(Utils.getFullDescriptionOfQuestion(qa));
        label.setOnMouseClicked(event -> ShowQA.show(qa));
        table.add(label, 0, row);
    }

    void updateCatalogue(String string) {
        table.getChildren().clear();
        List<QA> qas = getQAs();
        int row = 0;
        for (QA qa : qas) {
            if (qa.getQuestion().contains(string)) {
                addQuestionToCatalogue(qa, row);
                row++;
            }
        }
    }

    void show_01_level() {
        level = Level._01;
        updateCatalogue();
    }

    void show_2_level() {
        level = Level._2;
        updateCatalogue();
    }

    void show_3_level() {
        level = Level._3;
        updateCatalogue();
    }
}
