package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class INITIATIVES_SUMMARY {
    private String INITIA_COUNT;

    private String OPPTY_COUNT;

    private String INITIA_VAL;

    private String OPPTY_VAL;

    public String getINITIA_COUNT ()
    {
        return INITIA_COUNT;
    }

    public void setINITIA_COUNT (String INITIA_COUNT)
    {
        this.INITIA_COUNT = INITIA_COUNT;
    }

    public String getOPPTY_COUNT ()
    {
        return OPPTY_COUNT;
    }

    public void setOPPTY_COUNT (String OPPTY_COUNT)
    {
        this.OPPTY_COUNT = OPPTY_COUNT;
    }

    public String getINITIA_VAL ()
    {
        return INITIA_VAL;
    }

    public void setINITIA_VAL (String INITIA_VAL)
    {
        this.INITIA_VAL = INITIA_VAL;
    }

    public String getOPPTY_VAL ()
    {
        return OPPTY_VAL;
    }

    public void setOPPTY_VAL (String OPPTY_VAL)
    {
        this.OPPTY_VAL = OPPTY_VAL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [INITIA_COUNT = "+INITIA_COUNT+", OPPTY_COUNT = "+OPPTY_COUNT+", INITIA_VAL = "+INITIA_VAL+", OPPTY_VAL = "+OPPTY_VAL+"]";
    }
}
