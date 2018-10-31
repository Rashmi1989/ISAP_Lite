package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 5/22/2018.
 */

public class REL_ASSESSMENT_ARRAY {
    private String REL_ASSESSMENT;

    private String ASSESMENT_CD;

    public String getREL_ASSESSMENT ()
    {
        return REL_ASSESSMENT;
    }

    public void setREL_ASSESSMENT (String REL_ASSESSMENT)
    {
        this.REL_ASSESSMENT = REL_ASSESSMENT;
    }

    public String getASSESMENT_CD ()
    {
        return ASSESMENT_CD;
    }

    public void setASSESMENT_CD (String ASSESMENT_CD)
    {
        this.ASSESMENT_CD = ASSESMENT_CD;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [REL_ASSESSMENT = "+REL_ASSESSMENT+", ASSESMENT_CD = "+ASSESMENT_CD+"]";
    }

}
