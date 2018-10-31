package com.ibm.cio.gss.isap_lite.model;

/**
 * Created by Kabuli on 7/5/2018.
 */

public class InitiativeContactModel {
    private String name="";
    private String role="";
    private String emailId="";
    private String imageUrl="";
    private String cnum="";


    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
