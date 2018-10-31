package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 3/27/2018.
 */

public class BUSINESS_UNIT {
    private String DO_YTY;

    private String DO_TITLE;

    private String DO_TOTAL;

    private String DO_ID;

    public String getDO_YTY ()
    {
        return DO_YTY;
    }

    public void setDO_YTY (String DO_YTY)
    {
        this.DO_YTY = DO_YTY;
    }

    public String getDO_TITLE ()
    {
        return DO_TITLE;
    }

    public void setDO_TITLE (String DO_TITLE)
    {
        this.DO_TITLE = DO_TITLE;
    }

    public String getDO_TOTAL ()
    {
        return DO_TOTAL;
    }

    public void setDO_TOTAL (String DO_TOTAL)
    {
        this.DO_TOTAL = DO_TOTAL;
    }

    public String getDO_ID ()
    {
        return DO_ID;
    }

    public void setDO_ID (String DO_ID)
    {
        this.DO_ID = DO_ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DO_YTY = "+DO_YTY+", DO_TITLE = "+DO_TITLE+", DO_TOTAL = "+DO_TOTAL+", DO_ID = "+DO_ID+"]";
    }
}
