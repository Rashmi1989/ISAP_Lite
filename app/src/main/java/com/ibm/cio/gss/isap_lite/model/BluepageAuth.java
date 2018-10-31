package com.ibm.cio.gss.isap_lite.model;

/*
Class Name : "BluepageAuth"
Description :"Model object to hold user login credential and send to backend for Auth purpose."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

public class BluepageAuth {
    private String intranetId="";
    private String password="";


    public BluepageAuth(String intranetId, String password) {
        this.intranetId = intranetId;
        this.password = password;
    }
}
