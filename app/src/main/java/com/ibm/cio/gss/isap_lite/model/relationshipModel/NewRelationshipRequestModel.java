package com.ibm.cio.gss.isap_lite.model.relationshipModel;

import java.util.List;

/**
 * Created by Kabuli on 7/23/2018.
 */

public class NewRelationshipRequestModel {
    private String businessName;

    private String intranetId;

    private String clientExecName;

    private String clientExecKey;

    private String[] initiatives;

    private List<ActionPlan> actionPlan;

    private String assesmentKey;

    private String positionKey;

    private String clientId;

    public Contacts[] getContacts() {
        return contacts;
    }

    public void setContacts(Contacts[] contacts) {
        this.contacts = contacts;
    }

    private Contacts[] contacts;

    public String getGeoKey() {
        return geoKey;
    }

    public void setGeoKey(String geoKey) {
        this.geoKey = geoKey;
    }

    public String getCountryKey() {
        return countryKey;
    }

    public void setCountryKey(String countryKey) {
        this.countryKey = countryKey;
    }

    public String getRegionKey() {
        return regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getSurveyKey() {
        return surveyKey;
    }

    public void setSurveyKey(String surveyKey) {
        this.surveyKey = surveyKey;
    }

    private String geoKey;

    private String countryKey;

    private String regionKey;

    private String surveyKey;

    public String getBusinessName ()
    {
        return businessName;
    }

    public void setBusinessName (String businessName)
    {
        this.businessName = businessName;
    }

    public String getIntranetId ()
    {
        return intranetId;
    }

    public void setIntranetId (String intranetId)
    {
        this.intranetId = intranetId;
    }

    public String getClientExecName ()
    {
        return clientExecName;
    }

    public void setClientExecName (String clientExecName)
    {
        this.clientExecName = clientExecName;
    }

    public String getClientExecKey ()
    {
        return clientExecKey;
    }

    public void setClientExecKey (String clientExecKey)
    {
        this.clientExecKey = clientExecKey;
    }

    public String[] getInitiatives ()
    {
        return initiatives;
    }

    public void setInitiatives (String[] initiatives)
    {
        this.initiatives = initiatives;
    }

    public List<ActionPlan> getActionPlan ()
    {
        return actionPlan;
    }

    public void setActionPlan (List<ActionPlan> actionPlan)
    {
        this.actionPlan = actionPlan;
    }

    public String getAssesmentKey ()
    {
        return assesmentKey;
    }

    public void setAssesmentKey (String assesmentKey)
    {
        this.assesmentKey = assesmentKey;
    }

    public String getPositionKey ()
    {
        return positionKey;
    }

    public void setPositionKey (String positionKey)
    {
        this.positionKey = positionKey;
    }

    public String getClientId ()
    {
        return clientId;
    }

    public void setClientId (String clientId)
    {
        this.clientId = clientId;
    }
}
