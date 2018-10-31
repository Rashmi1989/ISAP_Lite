package com.ibm.cio.gss.isap_lite.model.relationshipModel;

/**
 * Created by Kabuli on 7/23/2018.
 */

public class NewRelationshipModel {
    private DEFAULT_DATA DEFAULT_DATA;

    private DATA DATA;

    public DEFAULT_DATA getDEFAULT_DATA ()
    {
        return DEFAULT_DATA;
    }

    public void setDEFAULT_DATA (DEFAULT_DATA DEFAULT_DATA)
    {
        this.DEFAULT_DATA = DEFAULT_DATA;
    }

    public DATA getDATA ()
    {
        return DATA;
    }

    public void setDATA (DATA DATA)
    {
        this.DATA = DATA;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DEFAULT_DATA = "+DEFAULT_DATA+", DATA = "+DATA+"]";
    }
}
