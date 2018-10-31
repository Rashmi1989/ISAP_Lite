package com.ibm.cio.gss.isap_lite.viewModel;

/**
 * Created by Kabuli on 7/11/2018.
 */

public class Units {
    private Double PCT;

    private String KEY;

    public Double getPCT ()
    {
        return PCT;
    }

    public void setPCT (Double PCT)
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

    @Override
    public String toString()
    {
        return "ClassPojo [PCT = "+PCT+", KEY = "+KEY+"]";
    }
}
