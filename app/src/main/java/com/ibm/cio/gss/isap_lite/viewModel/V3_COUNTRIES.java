package com.ibm.cio.gss.isap_lite.viewModel;

/**
 * Created by Rashmi on 7/17/18.
 */

public class V3_COUNTRIES {

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
