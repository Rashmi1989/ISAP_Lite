package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 6/21/18.
 */

public class DATA {

    private CATEGORY[] CATEGORY;

    private GEO[] GEO;

    private COUNTRY[] COUNTRY;

    private MARKETS[] MARKETS;

    private INITIATIVES[] INITIATIVES;

    private String[] CLIENT_BUSINESS_UNIT;

    public CATEGORY[] getCATEGORY ()
    {
        return CATEGORY;
    }

    public void setCATEGORY (CATEGORY[] CATEGORY)
    {
        this.CATEGORY = CATEGORY;
    }

    public GEO[] getGEO ()
    {
        return GEO;
    }

    public void setGEO (GEO[] GEO)
    {
        this.GEO = GEO;
    }

    public COUNTRY[] getCOUNTRY ()
    {
        return COUNTRY;
    }

    public void setCOUNTRY (COUNTRY[] COUNTRY)
    {
        this.COUNTRY = COUNTRY;
    }

    public MARKETS[] getMARKETS ()
    {
        return MARKETS;
    }

    public void setMARKETS (MARKETS[] MARKETS)
    {
        this.MARKETS = MARKETS;
    }

    public INITIATIVES[] getINITIATIVES ()
    {
        return INITIATIVES;
    }

    public void setINITIATIVES (INITIATIVES[] INITIATIVES)
    {
        this.INITIATIVES = INITIATIVES;
    }

    public String[] getCLIENT_BUSINESS_UNIT ()
    {
        return CLIENT_BUSINESS_UNIT;
    }

    public void setCLIENT_BUSINESS_UNIT (String[] CLIENT_BUSINESS_UNIT)
    {
        this.CLIENT_BUSINESS_UNIT = CLIENT_BUSINESS_UNIT;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CATEGORY = "+CATEGORY+", GEO = "+GEO+", COUNTRY = "+COUNTRY+", MARKETS = "+MARKETS+", INITIATIVES = "+INITIATIVES+", CLIENT_BUSINESS_UNIT = "+CLIENT_BUSINESS_UNIT+"]";
    }
}
