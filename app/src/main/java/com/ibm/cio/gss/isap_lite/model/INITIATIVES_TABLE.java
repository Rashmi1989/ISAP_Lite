package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class INITIATIVES_TABLE {
    private String CLOSING_DATE;

    private String BUSINESS_UNIT;

    private String INITIATIVE_KEY_NM;

    private String INITIATIVES_VAL;

    private String INITIATIVE_KEY;

    public String getCLOSING_DATE ()
    {
        return CLOSING_DATE;
    }

    public void setCLOSING_DATE (String CLOSING_DATE)
    {
        this.CLOSING_DATE = CLOSING_DATE;
    }

    public String getBUSINESS_UNIT ()
    {
        return BUSINESS_UNIT;
    }

    public void setBUSINESS_UNIT (String BUSINESS_UNIT)
    {
        this.BUSINESS_UNIT = BUSINESS_UNIT;
    }

    public String getINITIATIVE_KEY_NM ()
    {
        return INITIATIVE_KEY_NM;
    }

    public void setINITIATIVE_KEY_NM (String INITIATIVE_KEY_NM)
    {
        this.INITIATIVE_KEY_NM = INITIATIVE_KEY_NM;
    }

    public String getINITIATIVES_VAL ()
    {
        return INITIATIVES_VAL;
    }

    public void setINITIATIVES_VAL (String INITIATIVES_VAL)
    {
        this.INITIATIVES_VAL = INITIATIVES_VAL;
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
        return "ClassPojo [CLOSING_DATE = "+CLOSING_DATE+", BUSINESS_UNIT = "+BUSINESS_UNIT+", INITIATIVE_KEY_NM = "+INITIATIVE_KEY_NM+", INITIATIVES_VAL = "+INITIATIVES_VAL+", INITIATIVE_KEY = "+INITIATIVE_KEY+"]";
    }
}
