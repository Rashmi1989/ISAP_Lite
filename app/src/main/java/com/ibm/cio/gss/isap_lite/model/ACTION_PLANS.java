package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 7/23/2018.
 */

public class ACTION_PLANS
{
    private String VALUE;

    private String KEY;

    private String TYPE;
    private String ACTION_PLAN_KEY;
    private String ACTION_PLAN_TXT;
    private String IBM_CONTACT;
    private String IBM_CONTACT_CNUM;
    private String IBM_CONTACT_EMAIL;
    private String STATUS;
    private String STATUS_VAL;
    private String TARGET_DATE;

    public String getACTION_PLAN_KEY() {
        return ACTION_PLAN_KEY;
    }

    public void setACTION_PLAN_KEY(String ACTION_PLAN_KEY) {
        this.ACTION_PLAN_KEY = ACTION_PLAN_KEY;
    }

    public String getACTION_PLAN_TXT() {
        return ACTION_PLAN_TXT;
    }

    public void setACTION_PLAN_TXT(String ACTION_PLAN_TXT) {
        this.ACTION_PLAN_TXT = ACTION_PLAN_TXT;
    }

    public String getIBM_CONTACT() {
        return IBM_CONTACT;
    }

    public void setIBM_CONTACT(String IBM_CONTACT) {
        this.IBM_CONTACT = IBM_CONTACT;
    }

    public String getIBM_CONTACT_CNUM() {
        return IBM_CONTACT_CNUM;
    }

    public void setIBM_CONTACT_CNUM(String IBM_CONTACT_CNUM) {
        this.IBM_CONTACT_CNUM = IBM_CONTACT_CNUM;
    }

    public String getIBM_CONTACT_EMAIL() {
        return IBM_CONTACT_EMAIL;
    }

    public void setIBM_CONTACT_EMAIL(String IBM_CONTACT_EMAIL) {
        this.IBM_CONTACT_EMAIL = IBM_CONTACT_EMAIL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS_VAL() {
        return STATUS_VAL;
    }

    public void setSTATUS_VAL(String STATUS_VAL) {
        this.STATUS_VAL = STATUS_VAL;
    }

    public String getTARGET_DATE() {
        return TARGET_DATE;
    }

    public void setTARGET_DATE(String TARGET_DATE) {
        this.TARGET_DATE = TARGET_DATE;
    }

    public String getVALUE ()
    {
        return VALUE;
    }

    public void setVALUE (String VALUE)
    {
        this.VALUE = VALUE;
    }

    public String getKEY ()
    {
        return KEY;
    }

    public void setKEY (String KEY)
    {
        this.KEY = KEY;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [VALUE = "+VALUE+", KEY = "+KEY+", TYPE = "+TYPE+"]";
    }
}

