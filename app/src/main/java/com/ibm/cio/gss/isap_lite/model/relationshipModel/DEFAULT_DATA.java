package com.ibm.cio.gss.isap_lite.model.relationshipModel;

/**
 * Created by Kabuli on 7/23/2018.
 */

public class DEFAULT_DATA {
    private SELECTED_INITIATIVE[] SELECTED_INITIATIVE;

    private ACTION_PLANS[] ACTION_PLANS;

    private SELECTED_FIELDS[] SELECTED_FIELDS;

    public SELECTED_INITIATIVE[] getSELECTED_INITIATIVE ()
    {
        return SELECTED_INITIATIVE;
    }

    public void setSELECTED_INITIATIVE (SELECTED_INITIATIVE[] SELECTED_INITIATIVE)
    {
        this.SELECTED_INITIATIVE = SELECTED_INITIATIVE;
    }

    public ACTION_PLANS[] getACTION_PLANS ()
    {
        return ACTION_PLANS;
    }

    public void setACTION_PLANS (ACTION_PLANS[] ACTION_PLANS)
    {
        this.ACTION_PLANS = ACTION_PLANS;
    }

    public SELECTED_FIELDS[] getSELECTED_FIELDS ()
    {
        return SELECTED_FIELDS;
    }

    public void setSELECTED_FIELDS (SELECTED_FIELDS[] SELECTED_FIELDS)
    {
        this.SELECTED_FIELDS = SELECTED_FIELDS;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SELECTED_INITIATIVE = "+SELECTED_INITIATIVE+", ACTION_PLANS = "+ACTION_PLANS+", SELECTED_FIELDS = "+SELECTED_FIELDS+"]";
    }
}
