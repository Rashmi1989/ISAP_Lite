package com.ibm.cio.gss.isap_lite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kabuli on 3/27/2018.
 */

public class MyPlanModel {

    private CHART[] CHART;

    private GEO[] GEO;

    private COUNTRY[] COUNTRY;

    private MARKETS[] MARKETS;

    private SELECT_GEO_REGION[] SELECT_GEO_REGION;

    private String[][] GRAPH;

    public String getACCESS() {
        return ACCESS;
    }

    public void setACCESS(String ACCESS) {
        this.ACCESS = ACCESS;
    }

    private String ACCESS;

    @SerializedName("BUSINESS UNIT")
    private BUSINESS_UNIT[] BUSINESS_UNIT;

    private TEAM_MEMBER[] TEAM_MEMBER;

    private CITY_GROUP[] CITY_GROUP;

    public CHART[] getCHART ()
    {
        return CHART;
    }

    public void setCHART (CHART[] CHART)
    {
        this.CHART = CHART;
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

    public SELECT_GEO_REGION[] getSELECT_GEO_REGION ()
    {
        return SELECT_GEO_REGION;
    }

    public void setSELECT_GEO_REGION (SELECT_GEO_REGION[] SELECT_GEO_REGION)
    {
        this.SELECT_GEO_REGION = SELECT_GEO_REGION;
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

    public TEAM_MEMBER[] getTEAM_MEMBER ()
    {
        return TEAM_MEMBER;
    }

    public void setTEAM_MEMBER (TEAM_MEMBER[] TEAM_MEMBER)
    {
        this.TEAM_MEMBER = TEAM_MEMBER;
    }

    public CITY_GROUP[] getCITY_GROUP ()
    {
        return CITY_GROUP;
    }

    public void setCITY_GROUP (CITY_GROUP[] CITY_GROUP)
    {
        this.CITY_GROUP = CITY_GROUP;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CHART = "+CHART+", GEO = "+GEO+", COUNTRY = "+COUNTRY+", " +
                "MARKETS = "+MARKETS+", SELECT_GEO_REGION = "+SELECT_GEO_REGION+", GRAPH = "+GRAPH+", ACCESS = "+ACCESS+", BUSINESS UNIT = "+BUSINESS_UNIT+", TEAM_MEMBER = "+TEAM_MEMBER+", CITY_GROUP = "+CITY_GROUP+"]";
    }
}
