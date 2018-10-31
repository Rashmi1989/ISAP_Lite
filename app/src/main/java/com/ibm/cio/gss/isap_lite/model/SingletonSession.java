package com.ibm.cio.gss.isap_lite.model;

/*
Class Name : "SingletonSession"
Description :"Hold selected client inormation ,which will be available in session."
Author      :"Kabuli Behera"
Date of Creation :"March 25 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/
public class SingletonSession {
    private static SingletonSession instance;
    private int    clientId;
    private String clientName;
    private String intranetId;
    private String geoId;
    private String geoKey;
    private String geoName;
    private String regionId;
    private String regionKey;
    private String countryId;
    private String countryKey;

    //no outer class can initialize this class's object
    private SingletonSession() {}

    public String getGeoName() {
        return geoName;
    }

    public void setGeoName(String geoName) {
        this.geoName = geoName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIntranetId() {
        return intranetId;
    }

    public void setIntranetId(String intranetId) {
        this.intranetId = intranetId;
    }

    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    public String getGeoKey() {
        return geoKey;
    }

    public void setGeoKey(String geoKey) {
        this.geoKey = geoKey;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionKey() {
        return regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryKey() {
        return countryKey;
    }

    public void setCountryKey(String countryKey) {
        this.countryKey = countryKey;
    }

    public static SingletonSession Instance()
    {

        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new SingletonSession();
        }
        return instance;
    }

}
