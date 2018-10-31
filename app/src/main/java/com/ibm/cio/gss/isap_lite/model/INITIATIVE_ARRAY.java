package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 5/29/2018.
 */

public class INITIATIVE_ARRAY {
    private String VALUE;

    private String KEY;

    private INITIATIVE_DETAIL INITIATIVE_DETAIL;

    private String TYPE;

    private String INITIATIVE_DETAIL_IS_HIDE;

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

    public INITIATIVE_DETAIL getINITIATIVE_DETAIL ()
    {
        return INITIATIVE_DETAIL;
    }

    public void setINITIATIVE_DETAIL (INITIATIVE_DETAIL INITIATIVE_DETAIL)
    {
        this.INITIATIVE_DETAIL = INITIATIVE_DETAIL;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

    public String getINITIATIVE_DETAIL_IS_HIDE ()
    {
        return INITIATIVE_DETAIL_IS_HIDE;
    }

    public void setINITIATIVE_DETAIL_IS_HIDE (String INITIATIVE_DETAIL_IS_HIDE)
    {
        this.INITIATIVE_DETAIL_IS_HIDE = INITIATIVE_DETAIL_IS_HIDE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [VALUE = "+VALUE+", KEY = "+KEY+", INITIATIVE_DETAIL = "+INITIATIVE_DETAIL+", TYPE = "+TYPE+", INITIATIVE_DETAIL_IS_HIDE = "+INITIATIVE_DETAIL_IS_HIDE+"]";
    }
}
