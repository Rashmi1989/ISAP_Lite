package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 5/18/18.
 */

public class OPPTS
{
    private String OPPTY_VALUE;

    private String OPPORTUNITY_ID;

    private String OPPORTUNITY_NM;

    public String getOPPTY_VALUE ()
    {
        return OPPTY_VALUE;
    }

    public void setOPPTY_VALUE (String OPPTY_VALUE)
    {
        this.OPPTY_VALUE = OPPTY_VALUE;
    }

    public String getOPPORTUNITY_ID ()
    {
        return OPPORTUNITY_ID;
    }

    public void setOPPORTUNITY_ID (String OPPORTUNITY_ID)
    {
        this.OPPORTUNITY_ID = OPPORTUNITY_ID;
    }

    public String getOPPORTUNITY_NM ()
    {
        return OPPORTUNITY_NM;
    }

    public void setOPPORTUNITY_NM (String OPPORTUNITY_NM)
    {
        this.OPPORTUNITY_NM = OPPORTUNITY_NM;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [OPPTY_VALUE = "+OPPTY_VALUE+", OPPORTUNITY_ID = "+OPPORTUNITY_ID+", OPPORTUNITY_NM = "+OPPORTUNITY_NM+"]";
    }
}

