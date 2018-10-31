package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 7/3/2018.
 */

public class STRATEGICIMPERATIVES {
    private String NAME;

    private String PCT;

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

    public String getPCT ()
    {
        return PCT;
    }

    public void setPCT (String PCT)
    {
        this.PCT = PCT;
    }

    public String getKEY ()
    {
        return KEY;
    }

    public void setKEY (String KEY)
    {
        this.KEY = KEY;
    }
}
