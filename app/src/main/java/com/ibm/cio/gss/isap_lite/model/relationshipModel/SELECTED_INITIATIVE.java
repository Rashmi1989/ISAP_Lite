package com.ibm.cio.gss.isap_lite.model.relationshipModel;

/**
 * Created by Kabuli on 7/31/2018.
 */

public class SELECTED_INITIATIVE {
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
