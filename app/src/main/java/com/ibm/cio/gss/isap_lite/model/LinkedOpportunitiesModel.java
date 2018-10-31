
package com.ibm.cio.gss.isap_lite.model;

public class LinkedOpportunitiesModel
{
    private GOALS[] GOALS;

    private String INITIATIVE_KEY_NM;

    private String TOTAL;

    private String INITIATIVE_KEY;

    private String OPT_COUNT;

    private OPPTS[] OPPTS;

    public GOALS[] getGOALS ()
    {
        return GOALS;
    }

    public void setGOALS (GOALS[] GOALS)
    {
        this.GOALS = GOALS;
    }

    public String getINITIATIVE_KEY_NM ()
    {
        return INITIATIVE_KEY_NM;
    }

    public void setINITIATIVE_KEY_NM (String INITIATIVE_KEY_NM)
    {
        this.INITIATIVE_KEY_NM = INITIATIVE_KEY_NM;
    }

    public String getTOTAL ()
    {
        return TOTAL;
    }

    public void setTOTAL (String TOTAL)
    {
        this.TOTAL = TOTAL;
    }

    public String getINITIATIVE_KEY ()
    {
        return INITIATIVE_KEY;
    }

    public void setINITIATIVE_KEY (String INITIATIVE_KEY)
    {
        this.INITIATIVE_KEY = INITIATIVE_KEY;
    }

    public String getOPT_COUNT ()
    {
        return OPT_COUNT;
    }

    public void setOPT_COUNT (String OPT_COUNT)
    {
        this.OPT_COUNT = OPT_COUNT;
    }

    public OPPTS[] getOPPTS ()
    {
        return OPPTS;
    }

    public void setOPPTS (OPPTS[] OPPTS)
    {
        this.OPPTS = OPPTS;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GOALS = "+GOALS+", INITIATIVE_KEY_NM = "+INITIATIVE_KEY_NM+", TOTAL = "+TOTAL+", INITIATIVE_KEY = "+INITIATIVE_KEY+", OPT_COUNT = "+OPT_COUNT+", OPPTS = "+OPPTS+"]";
    }
}

