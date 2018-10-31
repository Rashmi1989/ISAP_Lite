package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 3/27/2018.
 */

public class COUNTRY {
    private String NAME;

    private String ID;

    private String KEY;

    public String getPARENTKEY() {
        return PARENTKEY;
    }

    public void setPARENTKEY(String PARENTKEY) {
        this.PARENTKEY = PARENTKEY;
    }

    private String PARENTKEY;

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
        return "ClassPojo [NAME = "+NAME+", ID = "+ID+", KEY = "+KEY+"]";
    }
}
