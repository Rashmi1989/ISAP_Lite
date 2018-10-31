package com.ibm.cio.gss.isap_lite.viewModel;

/**
 * Created by Rashmi on 7/17/18.
 */

public class V2_RELEVANTUNITS {


    private String BRAND_UNITS_PCT;

    private String BRAND_GROUP;

    private String KEY;

    private String BRAND_DESCR;


    public String getPCT ()
    {
        return BRAND_UNITS_PCT;
    }

    public void setPCT (String BRAND_UNITS_PCT)
    {
        this.BRAND_UNITS_PCT = BRAND_UNITS_PCT;
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
        return BRAND_DESCR;
    }

    public void setBRAND (String BRAND_DESCR)
    {
        this.BRAND_DESCR = BRAND_DESCR;
    }

}
