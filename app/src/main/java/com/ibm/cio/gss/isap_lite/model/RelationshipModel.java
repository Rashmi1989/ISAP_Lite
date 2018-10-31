package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class RelationshipModel {
    private SHIPS_ARRAY[] SHIPS_ARRAY;

    private ORA ORA;

    private PD_PTC PD_PTC;

    private CITY_GROUP[] CITY_GROUP;

    public SHIPS_ARRAY[] getSHIPS_ARRAY ()
    {
        return SHIPS_ARRAY;
    }

    public void setSHIPS_ARRAY (SHIPS_ARRAY[] SHIPS_ARRAY)
    {
        this.SHIPS_ARRAY = SHIPS_ARRAY;
    }

    public ORA getORA ()
    {
        return ORA;
    }

    public void setORA (ORA ORA)
    {
        this.ORA = ORA;
    }

    public PD_PTC getPD_PTC ()
    {
        return PD_PTC;
    }

    public void setPD_PTC (PD_PTC PD_PTC)
    {
        this.PD_PTC = PD_PTC;
    }

    public CITY_GROUP[] getCITY_GROUP ()
    {
        return CITY_GROUP;
    }

    public void setCITY_GROUP (CITY_GROUP[] CITY_GROUP)
    {
        this.CITY_GROUP = CITY_GROUP;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SHIPS_ARRAY = "+SHIPS_ARRAY+", ORA = "+ORA+", PD_PTC = "+PD_PTC+", CITY_GROUP = "+CITY_GROUP+"]";
    }
}
