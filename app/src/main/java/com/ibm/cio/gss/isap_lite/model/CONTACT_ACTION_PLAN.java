package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 9/12/18.
 */

public class CONTACT_ACTION_PLAN
{
    private ACTION_PLANS[] ACTION_PLANS;

    private String VALUE;

    private String KEY;

    private String TYPE;

    public ACTION_PLANS[] getACTION_PLANS ()
    {
        return ACTION_PLANS;
    }

    public void setACTION_PLANS (ACTION_PLANS[] ACTION_PLANS)
    {
        this.ACTION_PLANS = ACTION_PLANS;
    }

    public String getVALUE ()
    {
        return VALUE;
    }

    public void setVALUE (String VALUE)
    {
        this.VALUE = VALUE;
    }

    public String getKEY ()
    {
        return KEY;
    }

    public void setKEY (String KEY)
    {
        this.KEY = KEY;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ACTION_PLANS = "+ACTION_PLANS+", VALUE = "+VALUE+", KEY = "+KEY+", TYPE = "+TYPE+"]";
    }
}
