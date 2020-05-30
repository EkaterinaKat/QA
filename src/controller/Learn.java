package controller;

import database.JDBC;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.QA;
import utils.DateManager;
import utils.Utils;
import utils.WindowCreator;

import java.util.ArrayList;
import java.util.List;

/* Класс относительно закончен */
public class Learn {
    private static LearningMode mode;
    private List<SelectableQuestion> selectableQuestions;
    @FXML
    private TableColumn<SelectableQuestion, Number> countColumn;
    @FXML
    private TableColumn<SelectableQuestion, Boolean> checkBoxColumn;
    @FXML
    private TableColumn<SelectableQuestion, String> questionColumn;
    @FXML
    private TableColumn<SelectableQuestion, Integer> levelColumn;
    @FXML
    private TableView<SelectableQuestion> table;

    enum LearningMode {
        TO_2_LEVEL, TO_3_LEVEL
    }

    static void start(LearningMode learningMode) {
        mode = learningMode;
        WindowCreator.getInstance().createLearnWindow();
    }

    @FXML
    private void initialize() {
        tuneColumns();
        table.setEditable(true);
        fillTable();
    }

    private void tuneColumns() {
        countColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(
                table.getItems().indexOf(column.getValue()) + 1));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        checkBoxColumn.setCellValueFactory(param -> {
            SelectableQuestion selectableQuestion = param.getValue();
            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(selectableQuestion.isSelected());
            booleanProperty.addListener((observable, oldValue, newValue) -> {
                selectableQuestion.setSelected(newValue);
            });
            return booleanProperty;
        });
        checkBoxColumn.setCellFactory(param -> {
            CheckBoxTableCell<SelectableQuestion, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    /* Этот метод можно вызывать только один раз при инициализации */
    private void fillTable() {
        List<QA> qas = new ArrayList<>();
        switch (mode) {
            case TO_2_LEVEL:
                qas = JDBC.getInstance().getQALevel_0_or_1();
                break;
            case TO_3_LEVEL:
                qas = JDBC.getInstance().getQALevel_2();
                new DateManager().removeQAThatYonger2Month(qas);
        }
        ObservableList<SelectableQuestion> observableList = FXCollections.observableArrayList();
        selectableQuestions = convertQuestionsToSelectableQuestions(qas);
        observableList.addAll(selectableQuestions);
        table.setItems(observableList);
    }

    private List<SelectableQuestion> convertQuestionsToSelectableQuestions(List<QA> qas) {
        List<SelectableQuestion> result = new ArrayList<>();
        for (QA qa : qas) {
            result.add(new SelectableQuestion(qa));
        }
        return result;
    }

    @FXML
    private void checkButtonListener() {
        List<SelectableQuestion> selectedQuestions = getSelectedQuestions();
        removeFromTable(selectedQuestions);
        List<QA> qas = convertSelectableQuestionsToQuestions(selectedQuestions);
        switch (mode) {
            case TO_2_LEVEL:
                Check012.check(qas);
                break;
            case TO_3_LEVEL:
                Check23.check(qas);
        }
    }

    private List<SelectableQuestion> getSelectedQuestions() {
        List<SelectableQuestion> result = new ArrayList<>();
        for (SelectableQuestion selectableQuestion : selectableQuestions) {
            if (selectableQuestion.isSelected()) {
                result.add(selectableQuestion);
            }
        }
        return result;
    }

    private void removeFromTable(List<SelectableQuestion> list) {
        for (SelectableQuestion selectableQuestion : list) {
            selectableQuestions.remove(selectableQuestion);
        }
        fillTableWithRestQA();
    }

    /* Вызывается после удаления части ВО */
    private void fillTableWithRestQA() {
        table.getItems().clear();
        ObservableList<SelectableQuestion> observableList = FXCollections.observableArrayList();
        observableList.addAll(selectableQuestions);
        table.setItems(observableList);
    }

    private List<QA> convertSelectableQuestionsToQuestions(List<SelectableQuestion> selectableQuestions) {
        List<QA> result = new ArrayList<>();
        for (SelectableQuestion selectableQuestion : selectableQuestions) {
            result.add(selectableQuestion.getQa());
        }
        return result;
    }

    public class SelectableQuestion {
        private boolean selected;
        private QA qa;

        public SelectableQuestion(QA qa) {
            this.qa = qa;
            selected = false;
        }

        public Integer getLevel() {
            return qa.getLevel();
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }

        public String getQuestion() {
            return Utils.getFullDescriptionOfQuestion(qa);
        }

        public QA getQa() {
            return qa;
        }
    }
}