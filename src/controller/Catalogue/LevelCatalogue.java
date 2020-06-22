package controller.Catalogue;

import database.JDBC;
import javafx.scene.layout.GridPane;

public class LevelCatalogue implements CatalogueMode {
    private GridPane table;
    private MethToUpdateCatalogue methToUpdateCatalogue;

    LevelCatalogue(GridPane table) {
        this.table = table;
    }

    void show_01_level() {
        Helper.fillGridPainWithQAsAndSubQAs(JDBC.getInstance().getQALevel_0_or_1(), table);
        methToUpdateCatalogue = this::show_01_level;
    }

    void show_2_level() {
        Helper.fillGridPaneWithQAsAndDates(JDBC.getInstance().getQALevel_2(), table);
        methToUpdateCatalogue = this::show_2_level;
    }

    void show_3_level() {
        Helper.fillGridPaneWithPlainQAs(JDBC.getInstance().getQALevel_3(), table);
        methToUpdateCatalogue = this::show_3_level;
    }

    @Override
    public void updateCatalogue() {
        methToUpdateCatalogue.invoke();
    }

    @FunctionalInterface
    interface MethToUpdateCatalogue {
        void invoke();
    }
}
