package com.ibm.cio.gss.isap_lite.model.relationshipModel;

/**
 * Created by Kabuli on 7/23/2018.
 */

public class ActionPlan {
    private String IBM_CONTACT_CNUM;

    private String TARGET_DATE;

    private String ACTION_PLAN_KEY;

    private String ACTION_PLAN_TXT;

    private String STATUS;

    public String getIBM_CONTACT_CNUM ()
    {
        return IBM_CONTACT_CNUM;
    }

    public void setIBM_CONTACT_CNUM (String IBM_CONTACT_CNUM)
    {
        this.IBM_CONTACT_CNUM = IBM_CONTACT_CNUM;
    }

    public String getTARGET_DATE ()
    {
        return TARGET_DATE;
    }

    public void setTARGET_DATE (String TARGET_DATE)
    {
        this.TARGET_DATE = TARGET_DATE;
    }

    public String getACTION_PLAN_KEY ()
    {
        return ACTION_PLAN_KEY;
    }

    public void setACTION_PLAN_KEY (String ACTION_PLAN_KEY)
    {
        this.ACTION_PLAN_KEY = ACTION_PLAN_KEY;
    }

    public String getACTION_PLAN_TXT ()
    {
        return ACTION_PLAN_TXT;
    }

    public void setACTION_PLAN_TXT (String ACTION_PLAN_TXT)
    {
        this.ACTION_PLAN_TXT = ACTION_PLAN_TXT;
    }

    public String getSTATUS ()
    {
        return STATUS;
    }

    public void setSTATUS (String STATUS)
    {
        this.STATUS = STATUS;
    }
}
