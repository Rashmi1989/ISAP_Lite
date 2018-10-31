package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 7/5/18.
 */

public class GetRegionModel {

    private String[] geos;

    public String[] getGeos ()
    {
        return geos;
    }

    public void setGeos (String[] geos)
    {
        this.geos = geos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [geos = "+geos+"]";
    }
}
