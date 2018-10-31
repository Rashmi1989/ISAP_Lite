package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 5/29/2018.
 */

public class IBM_CONTACT_ARRAY {
    private String VALUE;

    private String KEY;

    private String TYPE;

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
        return "ClassPojo [VALUE = "+VALUE+", KEY = "+KEY+", TYPE = "+TYPE+"]";
    }
}
