package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 7/18/18.
 */

public class DeleteInitiativeModel {

    private String intranetId;

    private String initiativeId;

    public String getIntranetId ()
    {
        return intranetId;
    }

    public void setIntranetId (String intranetId)
    {
        this.intranetId = intranetId;
    }

    public String getInitiativeId ()
    {
        return initiativeId;
    }

    public void setInitiativeId (String initiativeId)
    {
        this.initiativeId = initiativeId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [intranetId = "+intranetId+", initiativeId = "+initiativeId+"]";
    }
}
