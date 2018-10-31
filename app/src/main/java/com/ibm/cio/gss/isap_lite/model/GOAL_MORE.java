package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 4/20/18.
 */

public class GOAL_MORE {
    private String STRATEGIC_GOAL_KEY;

    private String STRATEGIC_GOAL_NM;

    private INITIATIVES[] INITIATIVES;

    private String CLIENT_BUSINESS_AREA;

    private String ESTIMATE_SIZE_AMT;
    private String Goal_label;

    public String getGoal_label() {
        return Goal_label;
    }

    public void setGoal_label(String goal_label) {
        Goal_label = goal_label;
    }

    public String getSTRATEGIC_GOAL_KEY ()
    {
        return STRATEGIC_GOAL_KEY;
    }

    public void setSTRATEGIC_GOAL_KEY (String STRATEGIC_GOAL_KEY)
    {
        this.STRATEGIC_GOAL_KEY = STRATEGIC_GOAL_KEY;
    }

    public String getSTRATEGIC_GOAL_NM ()
    {
        return STRATEGIC_GOAL_NM;
    }

    public void setSTRATEGIC_GOAL_NM (String STRATEGIC_GOAL_NM)
    {
        this.STRATEGIC_GOAL_NM = STRATEGIC_GOAL_NM;
    }

    public INITIATIVES[] getINITIATIVES ()
    {
        return INITIATIVES;
    }

    public void setINITIATIVES (INITIATIVES[] INITIATIVES)
    {
        this.INITIATIVES = INITIATIVES;
    }

    public String getCLIENT_BUSINESS_AREA ()
    {
        return CLIENT_BUSINESS_AREA;
    }

    public void setCLIENT_BUSINESS_AREA (String CLIENT_BUSINESS_AREA)
    {
        this.CLIENT_BUSINESS_AREA = CLIENT_BUSINESS_AREA;
    }

    public String getESTIMATE_SIZE_AMT ()
    {
        return ESTIMATE_SIZE_AMT;
    }

    public void setESTIMATE_SIZE_AMT (String ESTIMATE_SIZE_AMT)
    {
        this.ESTIMATE_SIZE_AMT = ESTIMATE_SIZE_AMT;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [STRATEGIC_GOAL_KEY = "+STRATEGIC_GOAL_KEY+", STRATEGIC_GOAL_NM = "+STRATEGIC_GOAL_NM+", INITIATIVES = "+INITIATIVES+", CLIENT_BUSINESS_AREA = "+CLIENT_BUSINESS_AREA+", ESTIMATE_SIZE_AMT = "+ESTIMATE_SIZE_AMT+"]";
    }
}
