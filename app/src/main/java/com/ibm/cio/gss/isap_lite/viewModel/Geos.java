package com.ibm.cio.gss.isap_lite.viewModel;

/**
 * Created by Kabuli on 7/11/2018.
 */

public class Geos {
    private String NAME;

    private String ID;

    private String KEY;

    public String getNAME ()
    {
        return NAME;
    }

    public void setNAME (String NAME)
    {
        this.NAME = NAME;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
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
        return "ClassPojo [NAME = "+NAME+", ID = "+ID+", KEY = "+KEY+"]";
    }
}
