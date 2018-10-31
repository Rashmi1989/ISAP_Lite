package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 6/26/2018.
 */

public class GoalResponseObj {
    private String goalId="";
    private String flag="";
    private String errormsg="";
    private String initiativeId="";
    private String clientExecKey="";

    public String getClientExecKey() {
        return clientExecKey;
    }

    public void setClientExecKey(String clientExecKey) {
        this.clientExecKey = clientExecKey;
    }


    public String getInitiativeId() {
        return initiativeId;
    }

    public void setInitiativeId(String initiativeId) {
        this.initiativeId = initiativeId;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }


}
