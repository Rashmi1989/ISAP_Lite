package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 7/5/18.
 */

public class REGION
{
    private String NAME;

    private String PARENTKEY;

    private String ID;

    private String KEY;

    private boolean isSelected;

    public void setisSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getisSelected() {
        return isSelected;
    }

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

