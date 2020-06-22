package controller.Catalogue;

import javafx.scene.layout.GridPane;

public class Catalogue {
    private static Catalogue instance;
    private CatalogueMode activeCatalougeMode;
    private LevelCatalogue levelCatalogue;
    private SectionsCatalogue sectionsCatalogue;
    private SearchCatalogue searchCatalogue;

    public static Catalogue getInstance() {
        return instance;
    }

    public static void create(GridPane table) {
        instance = new Catalogue(table);
    }

    private Catalogue(GridPane table) {
        levelCatalogue = new LevelCatalogue(table);
        sectionsCatalogue = new SectionsCatalogue(table);
        searchCatalogue = new SearchCatalogue(table);
    }

    public void search(String s) {
        searchCatalogue.searchAndShow(s);
        activeCatalougeMode = searchCatalogue;
    }

    public void show_01_level() {
        levelCatalogue.show_01_level();
        activeCatalougeMode = levelCatalogue;
    }

    public void show_2_level() {
        levelCatalogue.show_2_level();
        activeCatalougeMode = levelCatalogue;
    }

    public void show_3_level() {
        levelCatalogue.show_3_level();
        activeCatalougeMode = levelCatalogue;
    }

    public void showSections() {
        sectionsCatalogue.showSections();
        activeCatalougeMode = sectionsCatalogue;
    }

    public void updateCatalogue() {
        activeCatalougeMode.updateCatalogue();
    }
}
