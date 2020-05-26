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
import utils.WindowCreator;

import java.util.ArrayList;
import java.util.List;

public class Learn12 {
    private List<SelectableQuestion> selectableQuestions;
    @FXML
    private TableColumn<SelectableQuestion, Number> countColumn;
    @FXML
    private TableColumn<SelectableQuestion, Boolean> checkBoxColumn;
    @FXML
    private TableColumn<SelectableQuestion, String> questionColumn;
    @FXML
    private TableView<SelectableQuestion> table;

    static void learn(){
        WindowCreator.getInstance().createLearn12Window();
    }

    @FXML
    private void initialize() {
        tuneColumns();
        table.setEditable(true);
        updateTable();
    }

    private void tuneColumns() {
        countColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(
                table.getItems().indexOf(column.getValue()) + 1));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
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

    private void updateTable(){
        List<QA> qas= JDBC.getInstance().getQAlevel1();
        ObservableList<SelectableQuestion> observableList = FXCollections.observableArrayList();
        selectableQuestions = convertEntriesToSelectableEntries(qas);
        observableList.addAll(selectableQuestions);
        table.setItems(observableList);
    }

    private List<SelectableQuestion> convertEntriesToSelectableEntries(List<QA> qas){
        List<SelectableQuestion> result = new ArrayList<>();
        for (QA qa: qas){
            result.add(new SelectableQuestion(qa));
        }
        return result;
    }

    @FXML
    private void checkButtonListener() {
        List<SelectableQuestion> selectedQuestions = getSelectedQuestions();
        removeFromTable(selectedQuestions);
        List<QA> qas = getQAs(selectedQuestions);
        Check.check(qas);
    }

    private List<SelectableQuestion> getSelectedQuestions(){
        List<SelectableQuestion> result = new ArrayList<>();
        for (SelectableQuestion selectableQuestion: selectableQuestions){
            if (selectableQuestion.isSelected()){
                result.add(selectableQuestion);
            }
        }
        return result;
    }

    private void removeFromTable(List<SelectableQuestion> list){
        for(SelectableQuestion selectableQuestion: list){
            selectableQuestions.remove(selectableQuestion);
        }
    }

    private List<QA> getQAs(List<SelectableQuestion> selectableQuestions){
        List<QA> result = new ArrayList<>();
        for (SelectableQuestion selectableQuestion: selectableQuestions){
            result.add(selectableQuestion.getQa());
        }
        return result;
    }

    public class SelectableQuestion{
        private boolean selected;
        private QA qa;

        public SelectableQuestion(QA qa) {
            this.qa = qa;
            selected = false;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }

        String getQuestion(){
            return qa.getQuestion();
        }

        public QA getQa() {
            return qa;
        }
    }
}
