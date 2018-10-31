package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class ROLES_ARRAY {
    private String EXEC_BU_NM;

    private String CLIENT_EXEC_KEY;

    private String REL_TITLE;

    private String PLAN_UNIT_KEY;

    private String REL_ASSESSMENT;

    private String EXEC_POSITION_CD;

    private String ASSESMENT_CD;

    private String CLIENT_EXEC_NM;

    private boolean expanded;

    public String getEXEC_BU_NM ()
    {
        return EXEC_BU_NM;
    }

    public void setEXEC_BU_NM (String EXEC_BU_NM)
    {
        this.EXEC_BU_NM = EXEC_BU_NM;
    }

    public String getCLIENT_EXEC_KEY ()
    {
        return CLIENT_EXEC_KEY;
    }

    public void setCLIENT_EXEC_KEY (String CLIENT_EXEC_KEY)
    {
        this.CLIENT_EXEC_KEY = CLIENT_EXEC_KEY;
    }

    public String getREL_TITLE ()
    {
        return REL_TITLE;
    }

    public void setREL_TITLE (String REL_TITLE)
    {
        this.REL_TITLE = REL_TITLE;
    }

    public String getPLAN_UNIT_KEY ()
    {
        return PLAN_UNIT_KEY;
    }

    public void setPLAN_UNIT_KEY (String PLAN_UNIT_KEY)
    {
        this.PLAN_UNIT_KEY = PLAN_UNIT_KEY;
    }

    public String getREL_ASSESSMENT ()
    {
        return REL_ASSESSMENT;
    }

    public void setREL_ASSESSMENT (String REL_ASSESSMENT)
    {
        this.REL_ASSESSMENT = REL_ASSESSMENT;
    }

    public String getEXEC_POSITION_CD ()
    {
        return EXEC_POSITION_CD;
    }

    public void setEXEC_POSITION_CD (String EXEC_POSITION_CD)
    {
        this.EXEC_POSITION_CD = EXEC_POSITION_CD;
    }

    public String getASSESMENT_CD ()
    {
        return ASSESMENT_CD;
    }

    public void setASSESMENT_CD (String ASSESMENT_CD)
    {
        this.ASSESMENT_CD = ASSESMENT_CD;
    }

    public String getCLIENT_EXEC_NM ()
    {
        return CLIENT_EXEC_NM;
    }

    public void setCLIENT_EXEC_NM (String CLIENT_EXEC_NM)
    {
        this.CLIENT_EXEC_NM = CLIENT_EXEC_NM;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [EXEC_BU_NM = "+EXEC_BU_NM+", CLIENT_EXEC_KEY = "+CLIENT_EXEC_KEY+", REL_TITLE = "+REL_TITLE+", PLAN_UNIT_KEY = "+PLAN_UNIT_KEY+", REL_ASSESSMENT = "+REL_ASSESSMENT+", EXEC_POSITION_CD = "+EXEC_POSITION_CD+", ASSESMENT_CD = "+ASSESMENT_CD+", CLIENT_EXEC_NM = "+CLIENT_EXEC_NM+"]";
    }
}
