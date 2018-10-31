package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 5/29/2018.
 */

public class INITIATIVE_DETAIL {
    private String INITIATIVE_LEAD_EMAIL;

    private String OPPTY_COUNT;

    private String BUSINESS_UNIT;

    private String INITIATIVE_KEY_NM;

    private String PLAN_UNIT_KEY;

    private String INITIATVIE_VALUE;

    private String INITIATIVE_LEAD_NAME;

    private String INITIATIVE_OWNER_CNUM;

    private String OPPTY_VAL;

    private String INITIATIVE_KEY;

    private String CLOSING_DATA_RANGE;

    public String getINITIATIVE_LEAD_EMAIL ()
    {
        return INITIATIVE_LEAD_EMAIL;
    }

    public void setINITIATIVE_LEAD_EMAIL (String INITIATIVE_LEAD_EMAIL)
    {
        this.INITIATIVE_LEAD_EMAIL = INITIATIVE_LEAD_EMAIL;
    }

    public String getOPPTY_COUNT ()
    {
        return OPPTY_COUNT;
    }

    public void setOPPTY_COUNT (String OPPTY_COUNT)
    {
        this.OPPTY_COUNT = OPPTY_COUNT;
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

    public String getPLAN_UNIT_KEY ()
    {
        return PLAN_UNIT_KEY;
    }

    public void setPLAN_UNIT_KEY (String PLAN_UNIT_KEY)
    {
        this.PLAN_UNIT_KEY = PLAN_UNIT_KEY;
    }

    public String getINITIATVIE_VALUE ()
    {
        return INITIATVIE_VALUE;
    }

    public void setINITIATVIE_VALUE (String INITIATVIE_VALUE)
    {
        this.INITIATVIE_VALUE = INITIATVIE_VALUE;
    }

    public String getINITIATIVE_LEAD_NAME ()
    {
        return INITIATIVE_LEAD_NAME;
    }

    public void setINITIATIVE_LEAD_NAME (String INITIATIVE_LEAD_NAME)
    {
        this.INITIATIVE_LEAD_NAME = INITIATIVE_LEAD_NAME;
    }

    public String getINITIATIVE_OWNER_CNUM ()
    {
        return INITIATIVE_OWNER_CNUM;
    }

    public void setINITIATIVE_OWNER_CNUM (String INITIATIVE_OWNER_CNUM)
    {
        this.INITIATIVE_OWNER_CNUM = INITIATIVE_OWNER_CNUM;
    }

    public String getOPPTY_VAL ()
    {
        return OPPTY_VAL;
    }

    public void setOPPTY_VAL (String OPPTY_VAL)
    {
        this.OPPTY_VAL = OPPTY_VAL;
    }

    public String getINITIATIVE_KEY ()
    {
        return INITIATIVE_KEY;
    }

    public void setINITIATIVE_KEY (String INITIATIVE_KEY)
    {
        this.INITIATIVE_KEY = INITIATIVE_KEY;
    }

    public String getCLOSING_DATA_RANGE ()
    {
        return CLOSING_DATA_RANGE;
    }

    public void setCLOSING_DATA_RANGE (String CLOSING_DATA_RANGE)
    {
        this.CLOSING_DATA_RANGE = CLOSING_DATA_RANGE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [INITIATIVE_LEAD_EMAIL = "+INITIATIVE_LEAD_EMAIL+", OPPTY_COUNT = "+OPPTY_COUNT+", BUSINESS_UNIT = "+BUSINESS_UNIT+", INITIATIVE_KEY_NM = "+INITIATIVE_KEY_NM+", PLAN_UNIT_KEY = "+PLAN_UNIT_KEY+", INITIATVIE_VALUE = "+INITIATVIE_VALUE+", INITIATIVE_LEAD_NAME = "+INITIATIVE_LEAD_NAME+", INITIATIVE_OWNER_CNUM = "+INITIATIVE_OWNER_CNUM+", OPPTY_VAL = "+OPPTY_VAL+", INITIATIVE_KEY = "+INITIATIVE_KEY+", CLOSING_DATA_RANGE = "+CLOSING_DATA_RANGE+"]";
    }
}
