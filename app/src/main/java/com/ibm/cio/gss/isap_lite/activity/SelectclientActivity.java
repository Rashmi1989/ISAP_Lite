
package com.ibm.cio.gss.isap_lite.activity;
/*
Class Name : "SelectclientActivity"
Description :"Check if any clients associated with your email ID."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.ClientsModel;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;

import java.util.List;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectclientActivity extends AppCompatActivity {
    private Button btnSelectClient;
    public String userName="";
    public static ISAPService IsapService;
    private ClientsModel clientsModel;
    public static List<ClientsModel> clients;
//    private ProgressDialog progressDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectclient);
        btnSelectClient= findViewById(R.id.select_client);
        userName= ISAP_Utils.LoggedInuserEmail;

        btnSelectClient.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                ISAP_Utils.showISAPProgressDialog(v.getContext(), ISAP_Constants.PROGRESSDIALOG_VERIFY_CLIENTS,false);
//                progressDialog.show();
                //COndition to test If client Exist or not.
                clientsModel = new ClientsModel();
                IsapService = APiUtils.getUserService();
                Call call = IsapService.getClients(userName);

                call.enqueue(new Callback<List<ClientsModel>>() {
                    @Override
                    public void onResponse(Call<List<ClientsModel>> call, Response<List<ClientsModel>> response) {

                        clients = response.body();
//                        progressDialog.dismiss();
                        ISAP_Utils.dismissProgressDialog();
                        //If clients size zero show Request access page else client list page.
                        if(clients.size()==0){
                            Intent intent=new Intent(SelectclientActivity.this,RequestAccessActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent=new Intent(SelectclientActivity.this,ClientsActivity.class);
//                            intent.putExtra("username",userName);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<ClientsModel>> call, Throwable t) {
                        ISAP_Utils.dismissProgressDialog();
                        System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }
}
