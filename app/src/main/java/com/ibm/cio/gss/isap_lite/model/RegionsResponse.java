package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 7/5/18.
 */

public class RegionsResponse {

    private COUNTRY[] COUNTRY;

    private REGION[] REGION;

    public COUNTRY[] getCOUNTRY ()
    {
        return COUNTRY;
    }

    public void setCOUNTRY (COUNTRY[] COUNTRY)
    {
        this.COUNTRY = COUNTRY;
    }

    public REGION[] getREGION ()
    {
        return REGION;
    }

    public void setREGION (REGION[] REGION)
    {
        this.REGION = REGION;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COUNTRY = "+COUNTRY+", REGION = "+REGION+"]";
    }
}
