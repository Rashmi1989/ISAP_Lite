package com.ibm.cio.gss.isap_lite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kabuli on 4/12/2018.
 */

public class MarketMyPlan {
    private CHART[] CHART;

    private COUNTRY[] COUNTRY;

    private String[][] GRAPH;
    @SerializedName("BUSINESS UNIT")
    private BUSINESS_UNIT[] BUSINESS_UNIT;

    public CHART[] getCHART ()
    {
        return CHART;
    }

    public void setCHART (CHART[] CHART)
    {
        this.CHART = CHART;
    }

    public COUNTRY[] getCOUNTRY ()
    {
        return COUNTRY;
    }

    public void setCOUNTRY (COUNTRY[] COUNTRY)
    {
        this.COUNTRY = COUNTRY;
    }

    public String[][] getGRAPH ()
    {
        return GRAPH;
    }

    public void setGRAPH (String[][] GRAPH)
    {
        this.GRAPH = GRAPH;
    }

    @SerializedName("BUSINESS UNIT")
    public BUSINESS_UNIT[] getBUSINESS_UNIT ()
    {
        return BUSINESS_UNIT;
    }
    @SerializedName("BUSINESS UNIT")
    public void setBUSINESS_UNIT (BUSINESS_UNIT[] BUSINESS_UNIT)
    {
        this.BUSINESS_UNIT = BUSINESS_UNIT;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CHART = "+CHART+", COUNTRY = "+COUNTRY+", GRAPH = "+GRAPH+", BUSINESS UNIT = "+BUSINESS_UNIT+"]";
    }
}
