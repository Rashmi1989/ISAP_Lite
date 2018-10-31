package com.ibm.cio.gss.isap_lite.model.relationshipModel;

/**
 * Created by Kabuli on 7/23/2018.
 */

public class DATA {
    private IDENTIFY_INITIATIVE[] IDENTIFY_INITIATIVE;

    private BUSINESS_UNIT[] BUSINESS_UNIT;

    private ASSESSMENT[] ASSESSMENT;

    private STATUS[] STATUS;

    private POSITION[] POSITION;

    public IDENTIFY_INITIATIVE[] getIDENTIFY_INITIATIVE ()
    {
        return IDENTIFY_INITIATIVE;
    }

    public void setIDENTIFY_INITIATIVE (IDENTIFY_INITIATIVE[] IDENTIFY_INITIATIVE)
    {
        this.IDENTIFY_INITIATIVE = IDENTIFY_INITIATIVE;
    }

    public BUSINESS_UNIT[] getBUSINESS_UNIT ()
    {
        return BUSINESS_UNIT;
    }

    public void setBUSINESS_UNIT (BUSINESS_UNIT[] BUSINESS_UNIT)
    {
        this.BUSINESS_UNIT = BUSINESS_UNIT;
    }

    public ASSESSMENT[] getASSESSMENT ()
    {
        return ASSESSMENT;
    }

    public void setASSESSMENT (ASSESSMENT[] ASSESSMENT)
    {
        this.ASSESSMENT = ASSESSMENT;
    }

    public STATUS[] getSTATUS ()
    {
        return STATUS;
    }

    public void setSTATUS (STATUS[] STATUS)
    {
        this.STATUS = STATUS;
    }

    public POSITION[] getPOSITION ()
    {
        return POSITION;
    }

    public void setPOSITION (POSITION[] POSITION)
    {
        this.POSITION = POSITION;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [IDENTIFY_INITIATIVE = "+IDENTIFY_INITIATIVE+", BUSINESS_UNIT = "+BUSINESS_UNIT+", ASSESSMENT = "+ASSESSMENT+", STATUS = "+STATUS+", POSITION = "+POSITION+"]";
    }
}
