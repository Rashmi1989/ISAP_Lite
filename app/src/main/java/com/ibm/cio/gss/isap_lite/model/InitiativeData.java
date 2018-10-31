package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 7/3/2018.
 */

public class InitiativeData {

    private INDUSTRYSOLUTIONS[] INDUSTRYSOLUTIONS;

    private PROGRESS[] PROGRESS;

    private COUNTRY[] COUNTRY;

    private PARTNERSTATUS[] PARTNERSTATUS;

    private RELEVANTBRANDSORUNITS[][] RELEVANTBRANDSORUNITS;

    private STRATEGICIMPERATIVES[] STRATEGICIMPERATIVES;

    private PARTNERROLE[] PARTNERROLE;

    private HUNDREDDATA[] HUNDREDDATA;

    private LOPT_INITIATIVES[] LOPT_INITIATIVES;

    private GOALS[] GOALS;

    private GEO[] GEO;

    private LINKEDOPPT[] LINKEDOPPT;

    private REGION[] REGION;

    public INDUSTRYSOLUTIONS[] getINDUSTRYSOLUTIONS ()
    {
        return INDUSTRYSOLUTIONS;
    }

    public void setINDUSTRYSOLUTIONS (INDUSTRYSOLUTIONS[] INDUSTRYSOLUTIONS)
    {
        this.INDUSTRYSOLUTIONS = INDUSTRYSOLUTIONS;
    }

    public PROGRESS[] getPROGRESS ()
    {
        return PROGRESS;
    }

    public void setPROGRESS (PROGRESS[] PROGRESS)
    {
        this.PROGRESS = PROGRESS;
    }

    public COUNTRY[] getCOUNTRY ()
    {
        return COUNTRY;
    }

    public void setCOUNTRY (COUNTRY[] COUNTRY)
    {
        this.COUNTRY = COUNTRY;
    }

    public PARTNERSTATUS[] getPARTNERSTATUS ()
    {
        return PARTNERSTATUS;
    }

    public void setPARTNERSTATUS (PARTNERSTATUS[] PARTNERSTATUS)
    {
        this.PARTNERSTATUS = PARTNERSTATUS;
    }

    public RELEVANTBRANDSORUNITS[][] getRELEVANTBRANDSORUNITS ()
    {
        return RELEVANTBRANDSORUNITS;
    }

    public void setRELEVANTBRANDSORUNITS (RELEVANTBRANDSORUNITS[][] RELEVANTBRANDSORUNITS)
    {
        this.RELEVANTBRANDSORUNITS = RELEVANTBRANDSORUNITS;
    }

    public STRATEGICIMPERATIVES[] getSTRATEGICIMPERATIVES ()
    {
        return STRATEGICIMPERATIVES;
    }

    public void setSTRATEGICIMPERATIVES (STRATEGICIMPERATIVES[] STRATEGICIMPERATIVES)
    {
        this.STRATEGICIMPERATIVES = STRATEGICIMPERATIVES;
    }

    public PARTNERROLE[] getPARTNERROLE ()
    {
        return PARTNERROLE;
    }

    public void setPARTNERROLE (PARTNERROLE[] PARTNERROLE)
    {
        this.PARTNERROLE = PARTNERROLE;
    }

    public HUNDREDDATA[] getHUNDREDDATA ()
    {
        return HUNDREDDATA;
    }

    public void setHUNDREDDATA (HUNDREDDATA[] HUNDREDDATA)
    {
        this.HUNDREDDATA = HUNDREDDATA;
    }

    public LOPT_INITIATIVES[] getLOPT_INITIATIVES ()
    {
        return LOPT_INITIATIVES;
    }

    public void setLOPT_INITIATIVES (LOPT_INITIATIVES[] LOPT_INITIATIVES)
    {
        this.LOPT_INITIATIVES = LOPT_INITIATIVES;
    }

    public GOALS[] getGOALS ()
    {
        return GOALS;
    }

    public void setGOALS (GOALS[] GOALS)
    {
        this.GOALS = GOALS;
    }

    public GEO[] getGEO ()
    {
        return GEO;
    }

    public void setGEO (GEO[] GEO)
    {
        this.GEO = GEO;
    }

    public LINKEDOPPT[] getLINKEDOPPT ()
    {
        return LINKEDOPPT;
    }

    public void setLINKEDOPPT (LINKEDOPPT[] LINKEDOPPT)
    {
        this.LINKEDOPPT = LINKEDOPPT;
    }

    public REGION[] getREGION ()
    {
        return REGION;
    }

    public void setREGION (REGION[] REGION)
    {
        this.REGION = REGION;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [INDUSTRYSOLUTIONS = "+INDUSTRYSOLUTIONS+", PROGRESS = "+PROGRESS+", COUNTRY = "+COUNTRY+", PARTNERSTATUS = "+PARTNERSTATUS+", RELEVANTBRANDSORUNITS = "+RELEVANTBRANDSORUNITS+", STRATEGICIMPERATIVES = "+STRATEGICIMPERATIVES+", PARTNERROLE = "+PARTNERROLE+", HUNDREDDATA = "+HUNDREDDATA+", LOPT_INITIATIVES = "+LOPT_INITIATIVES+", GOALS = "+GOALS+", GEO = "+GEO+", LINKEDOPPT = "+LINKEDOPPT+", REGION = "+REGION+"]";
    }
}
