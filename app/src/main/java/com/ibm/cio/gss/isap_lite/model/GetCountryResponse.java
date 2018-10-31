package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 7/5/18.
 */

public class GetCountryResponse {

    private COUNTRY[] COUNTRY;

    public COUNTRY[] getCOUNTRY ()
    {
        return COUNTRY;
    }

    public void setCOUNTRY (COUNTRY[] COUNTRY)
    {
        this.COUNTRY = COUNTRY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COUNTRY = "+COUNTRY+"]";
    }
}
