package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 3/27/2018.
 */

public class SELECT_GEO_REGION {
    private String GEO_IOT_NAME;

    private String REGION_IMT_ID;

    private String GEO_IOT_ID;

    private String REGION_IMT_NAME;

    public String getGEO_IOT_NAME ()
    {
        return GEO_IOT_NAME;
    }

    public void setGEO_IOT_NAME (String GEO_IOT_NAME)
    {
        this.GEO_IOT_NAME = GEO_IOT_NAME;
    }

    public String getREGION_IMT_ID ()
    {
        return REGION_IMT_ID;
    }

    public void setREGION_IMT_ID (String REGION_IMT_ID)
    {
        this.REGION_IMT_ID = REGION_IMT_ID;
    }

    public String getGEO_IOT_ID ()
    {
        return GEO_IOT_ID;
    }

    public void setGEO_IOT_ID (String GEO_IOT_ID)
    {
        this.GEO_IOT_ID = GEO_IOT_ID;
    }

    public String getREGION_IMT_NAME ()
    {
        return REGION_IMT_NAME;
    }

    public void setREGION_IMT_NAME (String REGION_IMT_NAME)
    {
        this.REGION_IMT_NAME = REGION_IMT_NAME;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GEO_IOT_NAME = "+GEO_IOT_NAME+", REGION_IMT_ID = "+REGION_IMT_ID+", GEO_IOT_ID = "+GEO_IOT_ID+", REGION_IMT_NAME = "+REGION_IMT_NAME+"]";
    }
}
