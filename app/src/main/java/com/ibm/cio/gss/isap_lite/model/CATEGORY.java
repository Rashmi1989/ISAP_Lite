package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 6/21/18.
 */

public class CATEGORY {

    private String NAME;

    private String KEY;

    public String getNAME ()
    {
        return NAME;
    }

    public void setNAME (String NAME)
    {
        this.NAME = NAME;
    }

    public String getKEY ()
    {
        return KEY;
    }

    public void setKEY (String KEY)
    {
        this.KEY = KEY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [NAME = "+NAME+", KEY = "+KEY+"]";
    }
}
