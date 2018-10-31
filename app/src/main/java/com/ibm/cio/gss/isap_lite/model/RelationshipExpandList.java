package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 5/29/2018.
 */

public class RelationshipExpandList {
    private PLANS_ARRAY[] PLANS_ARRAY;

    private String IBM_PRIMARY_CONTACT;

    private IBM_CONTACT_ARRAY[] IBM_CONTACT_ARRAY;

    private INITIATIVE_ARRAY[] INITIATIVE_ARRAY;

    private CONTACT_ACTION_PLAN[] CONTACT_ACTION_PLAN;

    private String STATUS;

    public PLANS_ARRAY[] getPLANS_ARRAY ()
    {
        return PLANS_ARRAY;
    }

    public void setPLANS_ARRAY (PLANS_ARRAY[] PLANS_ARRAY)
    {
        this.PLANS_ARRAY = PLANS_ARRAY;
    }

    public String getIBM_PRIMARY_CONTACT ()
    {
        return IBM_PRIMARY_CONTACT;
    }

    public void setIBM_PRIMARY_CONTACT (String IBM_PRIMARY_CONTACT)
    {
        this.IBM_PRIMARY_CONTACT = IBM_PRIMARY_CONTACT;
    }

    public IBM_CONTACT_ARRAY[] getIBM_CONTACT_ARRAY ()
    {
        return IBM_CONTACT_ARRAY;
    }

    public void setIBM_CONTACT_ARRAY (IBM_CONTACT_ARRAY[] IBM_CONTACT_ARRAY)
    {
        this.IBM_CONTACT_ARRAY = IBM_CONTACT_ARRAY;
    }

    public INITIATIVE_ARRAY[] getINITIATIVE_ARRAY ()
    {
        return INITIATIVE_ARRAY;
    }

    public void setINITIATIVE_ARRAY (INITIATIVE_ARRAY[] INITIATIVE_ARRAY)
    {
        this.INITIATIVE_ARRAY = INITIATIVE_ARRAY;
    }

    public CONTACT_ACTION_PLAN[] getCONTACT_ACTION_PLAN ()
    {
        return CONTACT_ACTION_PLAN;
    }

    public void setCONTACT_ACTION_PLAN (CONTACT_ACTION_PLAN[] CONTACT_ACTION_PLAN)
    {
        this.CONTACT_ACTION_PLAN = CONTACT_ACTION_PLAN;
    }

    public String getSTATUS ()
    {
        return STATUS;
    }

    public void setSTATUS (String STATUS)
    {
        this.STATUS = STATUS;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PLANS_ARRAY = "+PLANS_ARRAY+", IBM_PRIMARY_CONTACT = "+IBM_PRIMARY_CONTACT+", IBM_CONTACT_ARRAY = "+IBM_CONTACT_ARRAY+", INITIATIVE_ARRAY = "+INITIATIVE_ARRAY+", CONTACT_ACTION_PLAN = "+CONTACT_ACTION_PLAN+", STATUS = "+STATUS+"]";
    }
}
