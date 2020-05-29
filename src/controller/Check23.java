package controller;

import model.QA;
import utils.WindowCreator;

import java.util.List;

public class Check23 {
    private static List<QA> qas;

    static void check(List<QA> list) {
        qas = list;
        WindowCreator.getInstance().createCheckWindow();
    }
}
