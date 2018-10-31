package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Rashmi on 6/21/18.
 */

public class DEFAULT_DATA {

    private String COUNTRYNAME;

    private String CLIENTBUSINESSUNIT;

    private String DESCRIPTION;

    private String[] INITIATIVES;

    private String REGIONKEY;

    private String GOALNAME;

    private String COUNTRYKEY;

    private String GEONAME;

    private String CATEGORYKEY;



    private String CATEGORYNAME;

    private String GOALID;

    private String GEOKEY;

    private String REGIONNAME;

    public String getCOUNTRYNAME ()
    {
        return COUNTRYNAME;
    }

    public void setCOUNTRYNAME (String COUNTRYNAME)
    {
        this.COUNTRYNAME = COUNTRYNAME;
    }

    public String getCLIENTBUSINESSUNIT ()
    {
        return CLIENTBUSINESSUNIT;
    }

    public void setCLIENTBUSINESSUNIT (String CLIENTBUSINESSUNIT) {this.CLIENTBUSINESSUNIT = CLIENTBUSINESSUNIT;}

    public String getDESCRIPTION ()
    {
        return DESCRIPTION;
    }

    public void setDESCRIPTION (String DESCRIPTION)
    {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String[] getINITIATIVES ()
    {
        return INITIATIVES;
    }

    public void setINITIATIVES (String[] INITIATIVES)
    {
        this.INITIATIVES = INITIATIVES;
    }

    public String getREGIONKEY ()
    {
        return REGIONKEY;
    }

    public void setREGIONKEY (String REGIONKEY)
    {
        this.REGIONKEY = REGIONKEY;
    }

    public String getGOALNAME ()
    {
        return GOALNAME;
    }

    public void setGOALNAME (String GOALNAME)
    {
        this.GOALNAME = GOALNAME;
    }

    public String getCOUNTRYKEY ()
    {
        return COUNTRYKEY;
    }

    public void setCOUNTRYKEY (String COUNTRYKEY)
    {
        this.COUNTRYKEY = COUNTRYKEY;
    }

    public String getGEONAME ()
    {
        return GEONAME;
    }

    public void setGEONAME (String GEONAME)
    {
        this.GEONAME = GEONAME;
    }

    public String getCATEGORYKEY ()
    {
        return CATEGORYKEY;
    }

    public void setCATEGORYKEY (String CATEGORYKEY)
    {
        this.CATEGORYKEY = CATEGORYKEY;
    }

    public String getCATEGORYNAME() {return CATEGORYNAME;}

    public void setCATEGORYNAME(String CATEGORYNAME) {this.CATEGORYNAME = CATEGORYNAME;}

    public String getGOALID ()
    {
        return GOALID;
    }

    public void setGOALID (String GOALID)
    {
        this.GOALID = GOALID;
    }

    public String getGEOKEY ()
    {
        return GEOKEY;
    }

    public void setGEOKEY (String GEOKEY)
    {
        this.GEOKEY = GEOKEY;
    }

    public String getREGIONNAME ()
    {
        return REGIONNAME;
    }

    public void setREGIONNAME (String REGIONNAME)
    {
        this.REGIONNAME = REGIONNAME;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COUNTRYNAME = "+COUNTRYNAME+", CLIENTBUSINESSUNIT = "+CLIENTBUSINESSUNIT+", DESCRIPTION = "+DESCRIPTION+", INITIATIVES = "+INITIATIVES+", REGIONKEY = "+REGIONKEY+", GOALNAME = "+GOALNAME+", COUNTRYKEY = "+COUNTRYKEY+", GEONAME = "+GEONAME+", CATEGORYKEY = "+CATEGORYKEY+", GOALID = "+GOALID+", GEOKEY = "+GEOKEY+", REGIONNAME = "+REGIONNAME+"]";
    }
}
