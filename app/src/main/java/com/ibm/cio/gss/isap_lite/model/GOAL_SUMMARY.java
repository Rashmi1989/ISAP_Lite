package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/4/2018.
 */

public class GOAL_SUMMARY {
    private String STRATEGIC_GOAL_KEY;

    private String STRATEGIC_GOAL_NM;

    private String GOAL_DESC_TXT;

    private String NO_OF_INTIATIVES;

    private String ESTIMATE_SIZE_AMT;

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

    public String getGOAL_DESC_TXT ()
    {
        return GOAL_DESC_TXT;
    }

    public void setGOAL_DESC_TXT (String GOAL_DESC_TXT)
    {
        this.GOAL_DESC_TXT = GOAL_DESC_TXT;
    }

    public String getNO_OF_INTIATIVES ()
    {
        return NO_OF_INTIATIVES;
    }

    public void setNO_OF_INTIATIVES (String NO_OF_INTIATIVES)
    {
        this.NO_OF_INTIATIVES = NO_OF_INTIATIVES;
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
        return "ClassPojo [STRATEGIC_GOAL_KEY = "+STRATEGIC_GOAL_KEY+", STRATEGIC_GOAL_NM = "+STRATEGIC_GOAL_NM+", GOAL_DESC_TXT = "+GOAL_DESC_TXT+", NO_OF_INTIATIVES = "+NO_OF_INTIATIVES+", ESTIMATE_SIZE_AMT = "+ESTIMATE_SIZE_AMT+"]";
    }
}
