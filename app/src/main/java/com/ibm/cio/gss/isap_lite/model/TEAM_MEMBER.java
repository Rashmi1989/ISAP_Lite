package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 3/27/2018.
 */

public class TEAM_MEMBER {
    private String EMP_NAME;

    private String PLAN_UNIT_KEY;

    private String PROFILE_IMG;

    private String RSPNSBLTY_NM;

    private String MEMBER_CNUM;

    private String INTRANET_ID;

    public String getEMP_NAME ()
    {
        return EMP_NAME;
    }

    public void setEMP_NAME (String EMP_NAME)
    {
        this.EMP_NAME = EMP_NAME;
    }

    public String getPLAN_UNIT_KEY ()
    {
        return PLAN_UNIT_KEY;
    }

    public void setPLAN_UNIT_KEY (String PLAN_UNIT_KEY)
    {
        this.PLAN_UNIT_KEY = PLAN_UNIT_KEY;
    }

    public String getPROFILE_IMG ()
    {
        return PROFILE_IMG;
    }

    public void setPROFILE_IMG (String PROFILE_IMG)
    {
        this.PROFILE_IMG = PROFILE_IMG;
    }

    public String getRSPNSBLTY_NM ()
    {
        return RSPNSBLTY_NM;
    }

    public void setRSPNSBLTY_NM (String RSPNSBLTY_NM)
    {
        this.RSPNSBLTY_NM = RSPNSBLTY_NM;
    }

    public String getMEMBER_CNUM ()
    {
        return MEMBER_CNUM;
    }

    public void setMEMBER_CNUM (String MEMBER_CNUM)
    {
        this.MEMBER_CNUM = MEMBER_CNUM;
    }

    public String getINTRANET_ID ()
    {
        return INTRANET_ID;
    }

    public void setINTRANET_ID (String INTRANET_ID)
    {
        this.INTRANET_ID = INTRANET_ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [EMP_NAME = "+EMP_NAME+", PLAN_UNIT_KEY = "+PLAN_UNIT_KEY+", PROFILE_IMG = "+PROFILE_IMG+", RSPNSBLTY_NM = "+RSPNSBLTY_NM+", MEMBER_CNUM = "+MEMBER_CNUM+", INTRANET_ID = "+INTRANET_ID+"]";
    }
}
