package com.ibm.cio.gss.isap_lite.viewModel;

import android.databinding.Bindable;

import com.ibm.cio.gss.isap_lite.BR;
import android.databinding.BaseObservable;


/**
 * Created by Kabuli on 6/22/2018.
 */

public class GoalViewModel extends BaseObservable {
    private String clientId,goalId,goalName,regionKey,geoKey,countryKey,categoryKey,intranetId,description,clientBusinessUnit;
    private String[] initiatives;

    @Bindable
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
        notifyPropertyChanged(BR.clientId);
    }
    @Bindable
    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
        notifyPropertyChanged(BR.goalId);
    }
    @Bindable
    public String getRegionKey() {
        return regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
        notifyPropertyChanged(BR.regionKey);
    }
    @Bindable
    public String getGeoKey() {
        return geoKey;
    }

    public void setGeoKey(String geoKey) {
        this.geoKey = geoKey;
        notifyPropertyChanged(BR.geoKey);
    }
    @Bindable
    public String getCountryKey() {
        return countryKey;
    }

    public void setCountryKey(String countryKey) {
        this.countryKey = countryKey;
        notifyPropertyChanged(BR.countryKey);
    }
    @Bindable
    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
        notifyPropertyChanged(BR.categoryKey);
    }
    @Bindable
    public String getIntranetId() {
        return intranetId;
    }

    public void setIntranetId(String intranetId) {
        this.intranetId = intranetId;
        notifyPropertyChanged(BR.intranetId);
    }
    @Bindable
    public String[] getInitiatives() {
        return initiatives;
    }

    public void setInitiatives(String[] initiatives) {
        this.initiatives = initiatives;
        notifyPropertyChanged(BR.initiatives);
    }

    @Bindable
    public String getDescription ()
    {

        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getGoalName ()
    {
        return goalName;
    }

    public void setGoalName (String goalName)
    {
        this.goalName = goalName;
        notifyPropertyChanged(BR.goalName);
    }
    @Bindable
    public String getClientBusinessUnit ()
    {
        return clientBusinessUnit;
    }

    public void setClientBusinessUnit (String clientBusinessUnit)
    {
        this.clientBusinessUnit = clientBusinessUnit;
        notifyPropertyChanged(BR.clientBusinessUnit);
    }
}
