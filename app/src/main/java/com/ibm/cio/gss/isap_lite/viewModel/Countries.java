package com.ibm.cio.gss.isap_lite.viewModel;

/**
 * Created by Kabuli on 7/11/2018.
 */

public class Countries {
    private String NAME;

    private String PARENTKEY;

    private String ID;

    private String KEY;

    public String getNAME ()
    {
        return NAME;
    }

    public void setNAME (String NAME)
    {
        this.NAME = NAME;
    }

    public String getPARENTKEY ()
    {
        return PARENTKEY;
    }

    public void setPARENTKEY (String PARENTKEY)
    {
        this.PARENTKEY = PARENTKEY;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }

    public String getKEY ()
    {
        return KEY;
    }

    public void setKEY (String KEY)
    {
        this.KEY = KEY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [NAME = "+NAME+", PARENTKEY = "+PARENTKEY+", ID = "+ID+", KEY = "+KEY+"]";
    }
}
