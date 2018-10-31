package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class InitiativeModel {
    private SALES_OPT_TABLE[] SALES_OPT_TABLE;

    private INITIATIVES_GRAPH INITIATIVES_GRAPH;

    private INITIATIVES_TABLE[] INITIATIVES_TABLE;

    private CITY_GROUP[] CITY_GROUP;

    private INITIATIVES_SUMMARY INITIATIVES_SUMMARY;

    public SALES_OPT_TABLE[] getSALES_OPT_TABLE ()
    {
        return SALES_OPT_TABLE;
    }

    public void setSALES_OPT_TABLE (SALES_OPT_TABLE[] SALES_OPT_TABLE)
    {
        this.SALES_OPT_TABLE = SALES_OPT_TABLE;
    }

    public INITIATIVES_GRAPH getINITIATIVES_GRAPH ()
    {
        return INITIATIVES_GRAPH;
    }

    public void setINITIATIVES_GRAPH (INITIATIVES_GRAPH INITIATIVES_GRAPH)
    {
        this.INITIATIVES_GRAPH = INITIATIVES_GRAPH;
    }

    public INITIATIVES_TABLE[] getINITIATIVES_TABLE ()
    {
        return INITIATIVES_TABLE;
    }

    public void setINITIATIVES_TABLE (INITIATIVES_TABLE[] INITIATIVES_TABLE)
    {
        this.INITIATIVES_TABLE = INITIATIVES_TABLE;
    }

    public CITY_GROUP[] getCITY_GROUP ()
    {
        return CITY_GROUP;
    }

    public void setCITY_GROUP (CITY_GROUP[] CITY_GROUP)
    {
        this.CITY_GROUP = CITY_GROUP;
    }

    public INITIATIVES_SUMMARY getINITIATIVES_SUMMARY ()
    {
        return INITIATIVES_SUMMARY;
    }

    public void setINITIATIVES_SUMMARY (INITIATIVES_SUMMARY INITIATIVES_SUMMARY)
    {
        this.INITIATIVES_SUMMARY = INITIATIVES_SUMMARY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SALES_OPT_TABLE = "+SALES_OPT_TABLE+", INITIATIVES_GRAPH = "+INITIATIVES_GRAPH+", INITIATIVES_TABLE = "+INITIATIVES_TABLE+", CITY_GROUP = "+CITY_GROUP+", INITIATIVES_SUMMARY = "+INITIATIVES_SUMMARY+"]";
    }
}
