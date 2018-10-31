package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 7/5/18.
 */

public class GetCountryModel {

    private String[] regions;

    public String[] getRegions ()
    {
        return regions;
    }

    public void setRegions (String[] regions)
    {
        this.regions = regions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [regions = "+regions+"]";
    }
}
