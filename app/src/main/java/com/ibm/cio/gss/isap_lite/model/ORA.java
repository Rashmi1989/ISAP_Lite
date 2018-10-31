package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class ORA {
    private String TOTAL_COUNT;

    private String OVERALL_REL_ASSESSMENT;

    private String WEIGHTED_COUNT;

    public String getTOTAL_COUNT ()
    {
        return TOTAL_COUNT;
    }

    public void setTOTAL_COUNT (String TOTAL_COUNT)
    {
        this.TOTAL_COUNT = TOTAL_COUNT;
    }

    public String getOVERALL_REL_ASSESSMENT ()
    {
        return OVERALL_REL_ASSESSMENT;
    }

    public void setOVERALL_REL_ASSESSMENT (String OVERALL_REL_ASSESSMENT)
    {
        this.OVERALL_REL_ASSESSMENT = OVERALL_REL_ASSESSMENT;
    }

    public String getWEIGHTED_COUNT ()
    {
        return WEIGHTED_COUNT;
    }

    public void setWEIGHTED_COUNT (String WEIGHTED_COUNT)
    {
        this.WEIGHTED_COUNT = WEIGHTED_COUNT;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TOTAL_COUNT = "+TOTAL_COUNT+", OVERALL_REL_ASSESSMENT = "+OVERALL_REL_ASSESSMENT+", WEIGHTED_COUNT = "+WEIGHTED_COUNT+"]";
    }
}
