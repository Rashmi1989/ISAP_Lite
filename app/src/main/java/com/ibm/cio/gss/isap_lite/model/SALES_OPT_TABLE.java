package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class SALES_OPT_TABLE {
    private String OPPTY_VALUE;

    private String OPP_CLOSE_DT;

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

    public String getOPP_CLOSE_DT ()
    {
        return OPP_CLOSE_DT;
    }

    public void setOPP_CLOSE_DT (String OPP_CLOSE_DT)
    {
        this.OPP_CLOSE_DT = OPP_CLOSE_DT;
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
        return "ClassPojo [OPPTY_VALUE = "+OPPTY_VALUE+", OPP_CLOSE_DT = "+OPP_CLOSE_DT+", OPPORTUNITY_ID = "+OPPORTUNITY_ID+", OPPORTUNITY_NM = "+OPPORTUNITY_NM+"]";
    }
}
