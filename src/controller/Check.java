package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import model.QA;
import utils.Utils;
import utils.WindowCreator;

import java.util.List;

public class Check {
    private static List<QA> qas;
    @FXML
    private GridPane table;

    static void check(List<QA> list){
        qas = list;
        WindowCreator.getInstance().createCheckWindow();
    }

    @FXML
    private void initialize(){
        int row = 0;
        for(QA qa: qas){
            table.add(new Label(qa.getQuestion()+"\n"), 0, row);
            row++;
            table.add(new Label(qa.getAnswer()+"\n"), 0, row);
            row++;
//            table.add();
        }
    }

//    private Button getButton(QA qa){
//
//
//    }

    @FXML
    private void okButtonListener() {
        Utils.closeWindowThatContains(table);
    }
}
