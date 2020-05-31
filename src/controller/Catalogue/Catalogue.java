package controller.Catalogue;

import javafx.scene.layout.GridPane;
import model.QA;

import java.util.List;

public class Catalogue {
    private static Catalogue instance;
    private CatalogueMode mode;
    private GridPane table;

    public static Catalogue getInstance() {
        return instance;
    }

    public static void create(GridPane table) {
        instance = new Catalogue(table);
    }

    private Catalogue(GridPane table) {
        this.table = table;
        mode = new CatalogueMode01(table);
        updateCatalogue();
    }

    public void updateCatalogue() {
        table.getChildren().clear();
        List<QA> qas = mode.getQAsFromDB();
        int row = 0;
        for (QA qa : qas) {
            mode.addQuestionToCatalogue(qa, row);
            row++;
        }
    }

    public void updateCatalogue(String string) {
        table.getChildren().clear();
        List<QA> qas = mode.getQAsFromDB();
        int row = 0;
        for (QA qa : qas) {
            if (qa.getQuestion().contains(string)) {
                mode.addQuestionToCatalogue(qa, row);
                row++;
            }
        }
    }

    public void show_01_level() {
        mode = new CatalogueMode01(table);
        updateCatalogue();
    }

    public void show_2_level() {
        mode = new CatalogueMode2(table);
        updateCatalogue();
    }

    public void show_3_level() {
        mode = new CatalogueMode3(table);
        updateCatalogue();
    }
}
