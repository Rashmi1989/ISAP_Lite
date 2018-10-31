package com.ibm.cio.gss.isap_lite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kabuli on 7/3/2018.
 */

public class InitiativeFields_Model {
    @SerializedName("DEFAULT_DATA")
    private InitiativeDefault InitiativeDefault;

    @SerializedName("DATA")
    private InitiativeData InitiativeData;
    @SerializedName("DEFAULT_DATA")
    public InitiativeDefault getInitiativeDefault() {
        return InitiativeDefault;
    }
    @SerializedName("DEFAULT_DATA")
    public void setInitiativeDefault(InitiativeDefault initiativeDefault) {
        InitiativeDefault = initiativeDefault;
    }
    @SerializedName("DATA")
    public InitiativeData getInitiativeData() {
        return InitiativeData;
    }
    @SerializedName("DATA")
    public void setInitiativeData(InitiativeData initiativeData) {
        InitiativeData = initiativeData;
    }

}
