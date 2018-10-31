package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 4/20/18.
 */

public class GoalDetails {
    private INITIATIVES_MORE[] INITIATIVES_MORE;

    private GOAL_MORE[] GOAL_MORE;

    private CITY_GROUP[] CITY_GROUP;

    public INITIATIVES_MORE[] getINITIATIVES_MORE ()
    {
        return INITIATIVES_MORE;
    }

    public void setINITIATIVES_MORE (INITIATIVES_MORE[] INITIATIVES_MORE)
    {
        this.INITIATIVES_MORE = INITIATIVES_MORE;
    }

    public GOAL_MORE[] getGOAL_MORE ()
    {
        return GOAL_MORE;
    }

    public void setGOAL_MORE (GOAL_MORE[] GOAL_MORE)
    {
        this.GOAL_MORE = GOAL_MORE;
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
        return "ClassPojo [INITIATIVES_MORE = "+INITIATIVES_MORE+", GOAL_MORE = "+GOAL_MORE+", CITY_GROUP = "+CITY_GROUP+"]";
    }
}
