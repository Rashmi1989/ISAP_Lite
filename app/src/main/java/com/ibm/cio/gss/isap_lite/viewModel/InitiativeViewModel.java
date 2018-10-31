package com.ibm.cio.gss.isap_lite.viewModel;

import java.math.BigDecimal;

/**
 * Created by Kabuli on 7/11/2018.
 */

public class InitiativeViewModel {
    private String leadCnum;

    private String roleKey;

    private String closeDate;

    private String h1h2;

    private String industrySolutionKey;

    private String describeText;

    private Countries[] countries;

    private Regions[] regions;

    private String valueToClient;

    private Units[] units;

    private String clientId;

    private Geos[] geos;

    private String intranetId;

    private String statusKey;

    private String inititativeId;

    private String progressKey;

    private String value;

    private String[] strategicImperativesKey;

    private Integer[] linkedGoals;

    private String initiativeName;

    private String[] splitValues;

    private String[] linkedOppts;

    private String createDate;

    public String getLeadCnum ()
    {
        return leadCnum;
    }

    public void setLeadCnum (String leadCnum)
    {
        this.leadCnum = leadCnum;
    }

    public String getRoleKey ()
    {
        return roleKey;
    }

    public void setRoleKey (String roleKey)
    {
        this.roleKey = roleKey;
    }

    public String getCloseDate ()
    {
        return closeDate;
    }

    public void setCloseDate (String closeDate)
    {
        this.closeDate = closeDate;
    }

    public String getH1h2 ()
    {
        return h1h2;
    }

    public void setH1h2 (String h1h2)
    {
        this.h1h2 = h1h2;
    }

    public String getIndustrySolutionKey ()
    {
        return industrySolutionKey;
    }

    public void setIndustrySolutionKey (String industrySolutionKey)
    {
        this.industrySolutionKey = industrySolutionKey;
    }

    public String getDescribeText ()
    {
        return describeText;
    }

    public void setDescribeText (String describeText)
    {
        this.describeText = describeText;
    }

    public Countries[] getCountries ()
    {
        return countries;
    }

    public void setCountries (Countries[] countries)
    {
        this.countries = countries;
    }

    public Regions[] getRegions ()
    {
        return regions;
    }

    public void setRegions (Regions[] regions)
    {
        this.regions = regions;
    }

    public String getValueToClient ()
    {
        return valueToClient;
    }

    public void setValueToClient (String valueToClient)
    {
        this.valueToClient = valueToClient;
    }

    public Units[] getUnits ()
    {
        return units;
    }

    public void setUnits (Units[] units)
    {
        this.units = units;
    }

    public String getClientId ()
    {
        return clientId;
    }

    public void setClientId (String clientId)
    {
        this.clientId = clientId;
    }

    public Geos[] getGeos ()
    {
        return geos;
    }

    public void setGeos (Geos[] geos)
    {
        this.geos = geos;
    }

    public String getIntranetId ()
    {
        return intranetId;
    }

    public void setIntranetId (String intranetId)
    {
        this.intranetId = intranetId;
    }

    public String getStatusKey ()
    {
        return statusKey;
    }

    public void setStatusKey (String statusKey)
    {
        this.statusKey = statusKey;
    }

    public String getInititativeId ()
    {
        return inititativeId;
    }

    public void setInititativeId (String inititativeId)
    {
        this.inititativeId = inititativeId;
    }

    public String getProgressKey ()
    {
        return progressKey;
    }

    public void setProgressKey (String progressKey)
    {
        this.progressKey = progressKey;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String[] getStrategicImperativesKey ()
    {
        return strategicImperativesKey;
    }

    public void setStrategicImperativesKey (String[] strategicImperativesKey)
    {
        this.strategicImperativesKey = strategicImperativesKey;
    }

    public Integer[] getLinkedGoals ()
    {
        return linkedGoals;
    }

    public void setLinkedGoals (Integer[] linkedGoals)
    {
        this.linkedGoals = linkedGoals;
    }

    public String getInitiativeName ()
    {
        return initiativeName;
    }

    public void setInitiativeName (String initiativeName)
    {
        this.initiativeName = initiativeName;
    }

    public String[] getSplitValues ()
    {
        return splitValues;
    }

    public void setSplitValues (String[] splitValues)
    {
        this.splitValues = splitValues;
    }

    public String[] getLinkedOppts ()
    {
        return linkedOppts;
    }

    public void setLinkedOppts (String[] linkedOppts)
    {
        this.linkedOppts = linkedOppts;
    }

    public String getCreateDate ()
    {
        return createDate;
    }

    public void setCreateDate (String createDate)
    {
        this.createDate = createDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [leadCnum = "+leadCnum+", roleKey = "+roleKey+", closeDate = "+closeDate+", h1h2 = "+h1h2+", industrySolutionKey = "+industrySolutionKey+", describeText = "+describeText+", countries = "+countries+", regions = "+regions+", valueToClient = "+valueToClient+", units = "+units+", clientId = "+clientId+", geos = "+geos+", intranetId = "+intranetId+", statusKey = "+statusKey+", inititativeId = "+inititativeId+", progressKey = "+progressKey+", value = "+value+", strategicImperativesKey = "+strategicImperativesKey+", linkedGoals = "+linkedGoals+", initiativeName = "+initiativeName+", splitValues = "+splitValues+", linkedOppts = "+linkedOppts+", createDate = "+createDate+"]";
    }
}
