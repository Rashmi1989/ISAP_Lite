package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 7/3/2018.
 */

public class RELEVANTBRANDSORUNITS {
    private boolean section=false;
    private String BRAND_GROUP_SEQ;

    private String PCT;

    private String BRAND_GROUP;

    private String KEY;

    private String BRAND;

    private boolean isSelected;

    public void setisSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getisSelected() {
        return isSelected;
    }

    public boolean isSection() {
        return section;
    }

    public void setSection(boolean section) {
        this.section = section;
    }

    public String getBRAND_GROUP_SEQ ()
    {
        return BRAND_GROUP_SEQ;
    }

    public void setBRAND_GROUP_SEQ (String BRAND_GROUP_SEQ)
    {
        this.BRAND_GROUP_SEQ = BRAND_GROUP_SEQ;
    }

    public String getPCT ()
    {
        return PCT;
    }

    public void setPCT (String PCT)
    {
        this.PCT = PCT;
    }

    public String getBRAND_GROUP ()
    {
        return BRAND_GROUP;
    }

    public void setBRAND_GROUP (String BRAND_GROUP)
    {
        this.BRAND_GROUP = BRAND_GROUP;
    }

    public String getKEY ()
    {
        return KEY;
    }

    public void setKEY (String KEY)
    {
        this.KEY = KEY;
    }

    public String getBRAND ()
    {
        return BRAND;
    }

    public void setBRAND (String BRAND)
    {
        this.BRAND = BRAND;
    }
}
