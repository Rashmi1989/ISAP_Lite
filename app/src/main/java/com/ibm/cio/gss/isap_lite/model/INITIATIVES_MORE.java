package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 4/20/18.
 */

public class INITIATIVES_MORE {
    private GOALS[] GOALS;

    private String BUSINESS_UNITS;

    private String INITIATIVE_KEY_NM;

    private String INITIATIVE_VAL;

    private String PROGRESSION_CODE;

    private String INITIATIVE_LEAD;

    private String INDUSTRY_SOLUTION;

    private String INITIATIVE_KEY;

    public GOALS[] getGOALS ()
    {
        return GOALS;
    }

    public void setGOALS (GOALS[] GOALS)
    {
        this.GOALS = GOALS;
    }

    public String getBUSINESS_UNITS ()
    {
        return BUSINESS_UNITS;
    }

    public void setBUSINESS_UNITS (String BUSINESS_UNITS)
    {
        this.BUSINESS_UNITS = BUSINESS_UNITS;
    }

    public String getINITIATIVE_KEY_NM ()
    {
        return INITIATIVE_KEY_NM;
    }

    public void setINITIATIVE_KEY_NM (String INITIATIVE_KEY_NM)
    {
        this.INITIATIVE_KEY_NM = INITIATIVE_KEY_NM;
    }

    public String getINITIATIVE_VAL ()
    {
        return INITIATIVE_VAL;
    }

    public void setINITIATIVE_VAL (String INITIATIVE_VAL)
    {
        this.INITIATIVE_VAL = INITIATIVE_VAL;
    }

    public String getPROGRESSION_CODE ()
    {
        return PROGRESSION_CODE;
    }

    public void setPROGRESSION_CODE (String PROGRESSION_CODE)
    {
        this.PROGRESSION_CODE = PROGRESSION_CODE;
    }

    public String getINITIATIVE_LEAD ()
    {
        return INITIATIVE_LEAD;
    }

    public void setINITIATIVE_LEAD (String INITIATIVE_LEAD)
    {
        this.INITIATIVE_LEAD = INITIATIVE_LEAD;
    }

    public String getINDUSTRY_SOLUTION ()
    {
        return INDUSTRY_SOLUTION;
    }

    public void setINDUSTRY_SOLUTION (String INDUSTRY_SOLUTION)
    {
        this.INDUSTRY_SOLUTION = INDUSTRY_SOLUTION;
    }

    public String getINITIATIVE_KEY ()
    {
        return INITIATIVE_KEY;
    }

    public void setINITIATIVE_KEY (String INITIATIVE_KEY)
    {
        this.INITIATIVE_KEY = INITIATIVE_KEY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GOALS = "+GOALS+", BUSINESS_UNITS = "+BUSINESS_UNITS+", INITIATIVE_KEY_NM = "+INITIATIVE_KEY_NM+", INITIATIVE_VAL = "+INITIATIVE_VAL+", PROGRESSION_CODE = "+PROGRESSION_CODE+", INITIATIVE_LEAD = "+INITIATIVE_LEAD+", INDUSTRY_SOLUTION = "+INDUSTRY_SOLUTION+", INITIATIVE_KEY = "+INITIATIVE_KEY+"]";
    }
}
