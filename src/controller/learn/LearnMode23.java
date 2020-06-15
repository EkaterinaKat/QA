package controller.learn;

import controller.learn.Check.Group.SubGroup;
import database.JDBC;
import model.HavingLevel;
import model.QA;
import utils.DateManager;

import java.util.List;

public class LearnMode23 implements LearnMode {
    @Override
    public void changeLevel(HavingLevel havingLevel, boolean learningSucceed) {
        if (learningSucceed)
            havingLevel.setLevel(havingLevel.getLevel() + 1);
        else
            havingLevel.setLevel(1);
    }

    @Override
    public List<QA> getQAFromDB() {
        List<QA> qas = JDBC.getInstance().getQALevel_2();
        DateManager.getInstance().removeQAThatYounger2Month(qas);
        return qas;
    }

    @Override
    public void tuneGroup(Check.Group group) {
        List<SubGroup> subGroups = group.getSubGroups();
        for (SubGroup subGroup : subGroups) {
            subGroup.setDisableAndTurnGray();
        }
    }

    @Override
    public void setAndSynchronizeNewLevels(Check.Group group) {
        changeLevel(group.getQa(), group.getCheckBox().isSelected());
        List<SubGroup> subGroups = group.getSubGroups();
        for (SubGroup subGroup : subGroups) {
            subGroup.getSubQuestion().setLevel(group.getQa().getLevel());
        }
        updateDateIfNeeded(group.getQa());
    }

    private void updateDateIfNeeded(QA qa) {
        if (qa.getLevel() == 3)
            qa.setDate(DateManager.getInstance().getCurrentDateString());
    }
}
