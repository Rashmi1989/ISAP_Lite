package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/4/2018.
 */

public class GoalsModel {
    private GOAL_SUMMARY[] GOAL_SUMMARY;

    private CITY_GROUP[] CITY_GROUP;

    public GOAL_SUMMARY[] getGOAL_SUMMARY ()
    {
        return GOAL_SUMMARY;
    }

    public void setGOAL_SUMMARY (GOAL_SUMMARY[] GOAL_SUMMARY)
    {
        this.GOAL_SUMMARY = GOAL_SUMMARY;
    }

    public CITY_GROUP[] getCITY_GROUP ()
    {
        return CITY_GROUP;
    }

    public void setCITY_GROUP (CITY_GROUP[] CITY_GROUP)
    {
        this.CITY_GROUP = CITY_GROUP;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GOAL_SUMMARY = "+GOAL_SUMMARY+", CITY_GROUP = "+CITY_GROUP+"]";
    }
}
