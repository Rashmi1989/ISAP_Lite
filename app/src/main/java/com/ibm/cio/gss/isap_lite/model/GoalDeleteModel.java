package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 6/28/18.
 */

public class GoalDeleteModel {


    private String intranetId;
    private String goalId;

    public String getIntranetId() {
        return intranetId;
    }

    public void setIntranetId(String intranetId) {
        this.intranetId = intranetId;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [INTRANETID = "+intranetId+",GOALID = "+goalId+"]";
    }



}
