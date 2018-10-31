package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 7/3/2018.
 */

public class PROGRESS {
    private String NAME;

    private String KEY;

    private String DISPLAY_SEQ;

    public String getNAME ()
    {
        return NAME;
    }

    public void setNAME (String NAME)
    {
        this.NAME = NAME;
    }

    public String getKEY ()
    {
        return KEY;
    }

    public void setKEY (String KEY)
    {
        this.KEY = KEY;
    }

    public String getDISPLAY_SEQ ()
    {
        return DISPLAY_SEQ;
    }

    public void setDISPLAY_SEQ (String DISPLAY_SEQ)
    {
        this.DISPLAY_SEQ = DISPLAY_SEQ;
    }

}
