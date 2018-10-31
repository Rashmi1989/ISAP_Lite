package com.ibm.cio.gss.isap_lite.remote;

/*
Class Name : "APiUtils"
Description :"Utillity class to configure your server level Base url."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

public class APiUtils {
//    public static final String BASE_URL="https://isaplitedev.w3ibm.mybluemix.net/services/";
    //public static final String GetClients_URL="https://isaplitedev.w3ibm.mybluemix.net/services/GetClientsInfoServlet?intranetId=adamd@us.ibm.com";

    public static ISAPService getUserService(){

        return RetrofitClient.getClient(ISAP_Constants.BASE_URL).create(ISAPService.class);
    }
    public static ISAPService getProfileInfoService(){

        return RetrofitClient.getClient(ISAP_Constants.PROFILE_INFO).create(ISAPService.class);
    }


}
