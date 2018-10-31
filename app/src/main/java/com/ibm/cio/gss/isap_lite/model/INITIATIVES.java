package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 4/20/18.
 */

public class INITIATIVES {

    private String NAME;

    private String VALUE;

    private String KEY;

    private String TYPE;

    private boolean isSelected;

    public void setisSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getisSelected() {
        return isSelected;
    }

    public String getNAME () {return NAME;}

    public void setNAME (String NAME) {this.NAME = NAME;}
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
        return "ClassPojo [NAME = "+NAME+",VALUE = "+VALUE+", KEY = "+KEY+", TYPE = "+TYPE+"]";
    }
}
