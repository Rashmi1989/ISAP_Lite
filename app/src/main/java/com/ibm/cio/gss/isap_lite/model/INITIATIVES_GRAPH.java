package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class INITIATIVES_GRAPH {
    private String[] SALES_STAGE_ARRAY;

    private String[][] OPPTY_VALUE_ARRAY;

    public String[] getSALES_STAGE_ARRAY ()
    {
        return SALES_STAGE_ARRAY;
    }

    public void setSALES_STAGE_ARRAY (String[] SALES_STAGE_ARRAY)
    {
        this.SALES_STAGE_ARRAY = SALES_STAGE_ARRAY;
    }

    public String[][] getOPPTY_VALUE_ARRAY ()
    {
        return OPPTY_VALUE_ARRAY;
    }

    public void setOPPTY_VALUE_ARRAY (String[][] OPPTY_VALUE_ARRAY)
    {
        this.OPPTY_VALUE_ARRAY = OPPTY_VALUE_ARRAY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SALES_STAGE_ARRAY = "+SALES_STAGE_ARRAY+", OPPTY_VALUE_ARRAY = "+OPPTY_VALUE_ARRAY+"]";
    }
}
