package com.ibm.cio.gss.isap_lite.model.relationshipModel;

import java.util.List;

/**
 * Created by Kabuli on 7/23/2018.
 */

public class NewRelationship_SaveResponseObj {
    private String flag;

    private String clientExecKey;

    private String[] initiatives;

    private List<ActionPlan> actionPlan;

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    private String errormsg;

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag (String flag)
    {
        this.flag = flag;
    }

    public String getClientExecKey ()
    {
        return clientExecKey;
    }

    public void setClientExecKey (String clientExecKey)
    {
        this.clientExecKey = clientExecKey;
    }

    public String[] getInitiatives ()
    {
        return initiatives;
    }

    public void setInitiatives (String[] initiatives)
    {
        this.initiatives = initiatives;
    }

    public List<ActionPlan> getActionPlan ()
    {
        return actionPlan;
    }

    public void setActionPlan (List<ActionPlan> actionPlan)
    {
        this.actionPlan = actionPlan;
    }
}
