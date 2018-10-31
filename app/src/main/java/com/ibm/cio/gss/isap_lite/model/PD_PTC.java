package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 4/5/2018.
 */

public class PD_PTC {
    private String DETRACTORS_PCT;

    private String PROMOTERS_PCT;

    private String TOTAL_COUNT;

    private String DETRACTORS_COUNT;

    private String PROMOTERS_COUNT;

    public String getDETRACTORS_PCT ()
    {
        return DETRACTORS_PCT;
    }

    public void setDETRACTORS_PCT (String DETRACTORS_PCT)
    {
        this.DETRACTORS_PCT = DETRACTORS_PCT;
    }

    public String getPROMOTERS_PCT ()
    {
        return PROMOTERS_PCT;
    }

    public void setPROMOTERS_PCT (String PROMOTERS_PCT)
    {
        this.PROMOTERS_PCT = PROMOTERS_PCT;
    }

    public String getTOTAL_COUNT ()
    {
        return TOTAL_COUNT;
    }

    public void setTOTAL_COUNT (String TOTAL_COUNT)
    {
        this.TOTAL_COUNT = TOTAL_COUNT;
    }

    public String getDETRACTORS_COUNT ()
    {
        return DETRACTORS_COUNT;
    }

    public void setDETRACTORS_COUNT (String DETRACTORS_COUNT)
    {
        this.DETRACTORS_COUNT = DETRACTORS_COUNT;
    }

    public String getPROMOTERS_COUNT ()
    {
        return PROMOTERS_COUNT;
    }

    public void setPROMOTERS_COUNT (String PROMOTERS_COUNT)
    {
        this.PROMOTERS_COUNT = PROMOTERS_COUNT;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DETRACTORS_PCT = "+DETRACTORS_PCT+", PROMOTERS_PCT = "+PROMOTERS_PCT+", TOTAL_COUNT = "+TOTAL_COUNT+", DETRACTORS_COUNT = "+DETRACTORS_COUNT+", PROMOTERS_COUNT = "+PROMOTERS_COUNT+"]";
    }
}
