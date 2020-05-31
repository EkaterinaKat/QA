package controller.learn;

import controller.learn.Check.Group;
import model.HavingLevel;
import model.QA;

import java.util.List;

interface LearnMode {

    void changeLevel(HavingLevel havingLevel, boolean learningSucceed);

    List<QA> getQAFromDB();

    void tuneGroup(Group group);

    void setAndSynchronizeNewLevels(Group group);

}
