package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 5/22/2018.
 */

public class RelationshipMore {
    private SHIPS_ARRAY[] SHIPS_ARRAY;

    private REL_ASSESSMENT_ARRAY[] REL_ASSESSMENT_ARRAY;

    public SHIPS_ARRAY[] getSHIPS_ARRAY ()
    {
        return SHIPS_ARRAY;
    }

    public void setSHIPS_ARRAY (SHIPS_ARRAY[] SHIPS_ARRAY)
    {
        this.SHIPS_ARRAY = SHIPS_ARRAY;
    }

    public REL_ASSESSMENT_ARRAY[] getREL_ASSESSMENT_ARRAY ()
    {
        return REL_ASSESSMENT_ARRAY;
    }

    public void setREL_ASSESSMENT_ARRAY (REL_ASSESSMENT_ARRAY[] REL_ASSESSMENT_ARRAY)
    {
        this.REL_ASSESSMENT_ARRAY = REL_ASSESSMENT_ARRAY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SHIPS_ARRAY = "+SHIPS_ARRAY+", REL_ASSESSMENT_ARRAY = "+REL_ASSESSMENT_ARRAY+"]";
    }
}
