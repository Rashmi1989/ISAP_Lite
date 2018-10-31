package com.ibm.cio.gss.isap_lite.model.relationshipModel;

/**
 * Created by Rashmi on 9/14/18.
 */

public class Contacts {

    private String PRIMARY_CONTACT;

    private String UID;

    public String getPRIMARY_CONTACT ()
    {
        return PRIMARY_CONTACT;
    }

    public void setPRIMARY_CONTACT (String PRIMARY_CONTACT)
    {
        this.PRIMARY_CONTACT = PRIMARY_CONTACT;
    }

    public String getUID ()
    {
        return UID;
    }

    public void setUID (String UID)
    {
        this.UID = UID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PRIMARY_CONTACT = "+PRIMARY_CONTACT+", UID = "+UID+"]";
    }
}
