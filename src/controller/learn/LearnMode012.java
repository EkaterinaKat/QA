package controller.learn;

import controller.learn.Check.Group;
import controller.learn.Check.Group.SubGroup;
import database.JDBC;
import model.HavingLevel;
import model.QA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearnMode012 implements LearnMode {
    @Override
    public void changeLevel(HavingLevel havingLevel, boolean learningSucceed) {
        if (learningSucceed)
            havingLevel.setLevel(havingLevel.getLevel()+1);
    }

    @Override
    public List<QA> getQAFromDB() {
        return JDBC.getInstance().getQALevel_0_or_1();
    }

    @Override
    public void tuneGroup(Group group) {
        List<SubGroup> subGroups = group.getSubGroups();
        if (!subGroups.isEmpty()) {
            group.getCheckBox().setDisable(true);
            for (SubGroup subGroup: subGroups){
                if(subGroup.getLevel()==2)
                    subGroup.setDisableAndTurnGray();
            }
        }
    }

    @Override
    public void setAndSynchronizeNewLevels(Group group) {
        List<SubGroup> subGroups = group.getSubGroups();
        if (subGroups.isEmpty()){
            changeLevel(group.getQa(), group.getCheckBox().isSelected());
        }else {
            for (SubGroup subGroup: subGroups){
                changeLevel(subGroup.getSubQuestion(), subGroup.getCheckBox().isSelected());
            }
            group.getQa().setLevel(getMinSubQLevel(subGroups));
        }
    }

    private int getMinSubQLevel(List<SubGroup> subGroups){
        List<Integer> levels = new ArrayList<>();
        for (SubGroup subGroup: subGroups){
            levels.add(subGroup.getLevel());
        }
        Collections.sort(levels);
        return levels.get(0);
    }

}
