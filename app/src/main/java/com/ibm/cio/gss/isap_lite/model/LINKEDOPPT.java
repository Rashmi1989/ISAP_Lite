package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 7/3/2018.
 */

public class LINKEDOPPT {
    private String CLOSE_DATE;

    private String TCV_OPPTY_VAL;

    private String OPP_OWNER_CNUM;

    private String SALES_STAGE;

    private String LINKED_INITIATIVE;

    private String OPPORTUNITY_ID;

    private String INITIATIVE_KEY;

    private String OWNER;

    private String OPPORTUNITY_NM;

    private boolean isSelected;

    public void setisSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getisSelected() {
        return isSelected;
    }


    public String getCLOSE_DATE ()
    {
        return CLOSE_DATE;
    }

    public void setCLOSE_DATE (String CLOSE_DATE)
    {
        this.CLOSE_DATE = CLOSE_DATE;
    }

    public String getTCV_OPPTY_VAL ()
    {
        return TCV_OPPTY_VAL;
    }

    public void setTCV_OPPTY_VAL (String TCV_OPPTY_VAL)
    {
        this.TCV_OPPTY_VAL = TCV_OPPTY_VAL;
    }

    public String getOPP_OWNER_CNUM ()
    {
        return OPP_OWNER_CNUM;
    }

    public void setOPP_OWNER_CNUM (String OPP_OWNER_CNUM)
    {
        this.OPP_OWNER_CNUM = OPP_OWNER_CNUM;
    }

    public String getSALES_STAGE ()
    {
        return SALES_STAGE;
    }

    public void setSALES_STAGE (String SALES_STAGE)
    {
        this.SALES_STAGE = SALES_STAGE;
    }

    public String getLINKED_INITIATIVE ()
    {
        return LINKED_INITIATIVE;
    }

    public void setLINKED_INITIATIVE (String LINKED_INITIATIVE)
    {
        this.LINKED_INITIATIVE = LINKED_INITIATIVE;
    }

    public String getOPPORTUNITY_ID ()
    {
        return OPPORTUNITY_ID;
    }

    public void setOPPORTUNITY_ID (String OPPORTUNITY_ID)
    {
        this.OPPORTUNITY_ID = OPPORTUNITY_ID;
    }

    public String getINITIATIVE_KEY ()
    {
        return INITIATIVE_KEY;
    }

    public void setINITIATIVE_KEY (String INITIATIVE_KEY)
    {
        this.INITIATIVE_KEY = INITIATIVE_KEY;
    }

    public String getOWNER ()
    {
        return OWNER;
    }

    public void setOWNER (String OWNER)
    {
        this.OWNER = OWNER;
    }

    public String getOPPORTUNITY_NM ()
    {
        return OPPORTUNITY_NM;
    }

    public void setOPPORTUNITY_NM (String OPPORTUNITY_NM)
    {
        this.OPPORTUNITY_NM = OPPORTUNITY_NM;
    }
}
