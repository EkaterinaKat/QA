package controller.Catalogue;

import model.QA;

import java.util.List;

interface CatalogueMode {

    List<QA> getQAsFromDB();

    void addQuestionToCatalogue(QA qa, int row);
}
