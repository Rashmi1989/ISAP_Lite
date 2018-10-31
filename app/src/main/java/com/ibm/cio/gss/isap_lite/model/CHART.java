package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 3/27/2018.
 */

public class CHART {
    private String VALUE;

    private String CHART_KEY;

    private String TITLE;

    public String getVALUE ()
    {
        return VALUE;
    }

    public void setVALUE (String VALUE)
    {
        this.VALUE = VALUE;
    }

    public String getCHART_KEY ()
    {
        return CHART_KEY;
    }

    public void setCHART_KEY (String CHART_KEY)
    {
        this.CHART_KEY = CHART_KEY;
    }

    public String getTITLE ()
    {
        return TITLE;
    }

    public void setTITLE (String TITLE)
    {
        this.TITLE = TITLE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [VALUE = "+VALUE+", CHART_KEY = "+CHART_KEY+", TITLE = "+TITLE+"]";
    }
}
