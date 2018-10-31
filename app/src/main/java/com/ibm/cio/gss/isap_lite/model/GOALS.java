package com.ibm.cio.gss.isap_lite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rashmi on 4/20/18.
 */

public class GOALS {


    private String VALUE;

    private String KEY;

    private String TYPE;

    private boolean isSelected;

    private String NAME;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }


    public void setisSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getisSelected() {
        return isSelected;
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
        return "ClassPojo [VALUE = "+VALUE+", KEY = "+KEY+", TYPE = "+TYPE+"]";
    }
}
