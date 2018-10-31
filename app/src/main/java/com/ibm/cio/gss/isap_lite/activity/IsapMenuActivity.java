package com.ibm.cio.gss.isap_lite.activity;

/*
Class Name : "IsapMenuActivity"
Description :"Generic class to handle page navigation along with action bar,geographically drop downs"
Author      :"Kabuli Behera"
Date of Creation :"March 07 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.fragment.GoalDetailFragment;
import com.ibm.cio.gss.isap_lite.fragment.GoalFragment;
import com.ibm.cio.gss.isap_lite.fragment.InitiativeFragment;
import com.ibm.cio.gss.isap_lite.fragment.MyPlanFragment;
import com.ibm.cio.gss.isap_lite.fragment.RelationshipFragment;
import com.ibm.cio.gss.isap_lite.fragment.RelationshipMoreFragment;
import com.ibm.cio.gss.isap_lite.model.CITY_GROUP;
import com.ibm.cio.gss.isap_lite.model.COUNTRY;
import com.ibm.cio.gss.isap_lite.model.CountryMyPlan;
import com.ibm.cio.gss.isap_lite.model.GeoMyplanModel;
import com.ibm.cio.gss.isap_lite.model.MARKETS;
import com.ibm.cio.gss.isap_lite.model.MarketMyPlan;
import com.ibm.cio.gss.isap_lite.model.MyPlanModel;
import com.ibm.cio.gss.isap_lite.model.SingletonSession;
import com.ibm.cio.gss.isap_lite.model.GetImages;
import com.ibm.cio.gss.isap_lite.model.CircularImageView;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import  android.app.AlertDialog;
import   android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IsapMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ISAPService IsapService;
    private MyPlanModel myplan_data;
    private AlertDialog alertDialog;
    private ListView topClients;
    private AlertDialog.Builder alertDialogBuilder;
    private ImageView  user;
    private String geo_ID="";
    private String geo_key="";
    private String geo_Name="";
    private String pageID="";
    private String market_ID="";
    private String market_Key="";
    private String country_ID="";
    private static int clientId=0;
    private static String intranetId="";
    //private ImageView prevBtn;
    private View prevBtn;
   // public static ImageView nextBtn;
    public static View nextBtn;
    public static TextView prevText;
    public static TextView nextText;
    private static String PageName="Goals";
    private  int index;
    private int checkedGeo;
    private int checkedMarket;
    private int checkedCountry;
    private static boolean isGeoSelected=false;
    private static boolean isMarketSelected=false;
    private static boolean isCountrySelected=false;

    private ArrayList<String> geoData;
    private MARKETS[] marketData;
    private COUNTRY[] countryData;
    private GeoMyplanModel my_geodata;
    private MarketMyPlan  my_marketdata;
    private CountryMyPlan my_countydata;
    private Gson gson ;
    private LinearLayout moreBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isap_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        moreBtn = findViewById(R.id.moreLayout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("");


        //Set Top tool bar with selected client name
        TextView topClientTitle=(TextView)findViewById(R.id.topClients);
        topClientTitle.setText(ISAP_Utils.clientName);
        //Get sub header icon with text and perform navigation.
        prevBtn=findViewById(R.id.prevView);
        nextBtn=findViewById(R.id.nextView);
        prevText=findViewById(R.id.mypage);
        nextText=findViewById(R.id.nextpage);

        prevBtn.setVisibility(View.INVISIBLE);


        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        CircularImageView userImage =headerLayout.findViewById(R.id.signInUser);
        new GetImages(ISAP_Utils.photoUrl, userImage, "").execute();
        TextView userName =headerLayout.findViewById(R.id.userName);
        userName.setText(ISAP_Utils.userName.toString());
      //  TextView userEmail =headerLayout.findViewById(R.id.userEmail);
       // userEmail.setText(ISAP_Utils.LoggedInuserEmail.toString());

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ISAP_Utils.currentPage==2) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    ft.replace(R.id.frame_layout_main, new GoalDetailFragment());
                   // getFragmentManager().beginTransaction().replace(R.id.frame_layout_main, new GoalDetailFragment()).addToBackStack("my_fragment").commit();
                    //ft.addToBackStack(null);
                    ft.commit();
                    ISAP_Utils.isInitiativeActive = true;
                    boolean initiatives = true;
                }else if(ISAP_Utils.currentPage==7){
                    FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    ft.replace(R.id.frame_layout_main,new RelationshipMoreFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPrevBtn();

            }
        });

        prevText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPrevBtn();

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           selectNextbtn();

            }
        });

        nextText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { selectNextbtn(); }
        });

        //Set default Fragment as my plan.
        clientId=ISAP_Utils.clientID;//static
        intranetId= ISAP_Utils.LoggedInuserEmail;
        if(ISAP_Utils.currentPage==0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_main, new MyPlanFragment());
            ft.commit();
            navigationView.setCheckedItem(R.id.nav_myplan);
            fetchMyPlan(clientId,intranetId);
        }else if(ISAP_Utils.currentPage==1 || ISAP_Utils.currentPage==4 || ISAP_Utils.currentPage==8) {
//            if(ISAP_Utils.isProgressDialogVisible())
//                ISAP_Utils.dismissProgressDialog();
            prevBtn.setVisibility(View.VISIBLE);
            setPageSubHeader(ISAP_Utils.currentPage);
            fetchSummary(ISAP_Utils.currentPage);
        }
//        getSupportActionBar().setTitle("");

        //set default my plan data for initial service call like ,geoId,geoKey,countryId,countryKey etc..
        setDefaultUser();


//        ISAP_Utils.showISAPProgressDialog(this, ISAP_Constants.FETCH_CLIENTS_DETAILS,false);

        user=findViewById(R.id.signInUser);
    }

    private void selectNextbtn() {
//System.out.println("Current page on click of Next Button:"+ISAP_Utils.currentPage);


        if(ISAP_Utils.currentPage==3 || ISAP_Utils.currentPage==7)
            ISAP_Utils.currentPage=-1;
        ISAP_Utils.currentPage++;
        //}else currentPage++;
        if(ISAP_Utils.currentPage==0)
            prevBtn.setVisibility(View.INVISIBLE);
        else   prevBtn.setVisibility(View.VISIBLE);
        setPageSubHeader(ISAP_Utils.currentPage);

        fetchSummary(ISAP_Utils.currentPage);
    }

    private void selectPrevBtn() {
//        System.out.println("Current page on click of Prev Button:"+ISAP_Utils.currentPage);
        if(ISAP_Utils.currentPage==4)
            ISAP_Utils.currentPage=2;
        if(ISAP_Utils.currentPage==7)
            ISAP_Utils.currentPage=3;
        if(ISAP_Utils.currentPage==8) {
            ISAP_Utils.currentPage = 4;nextText.setVisibility(View.VISIBLE);
        }
        if(ISAP_Utils.currentPage!=0) {
            ISAP_Utils.currentPage--;
        }else {
            prevBtn.setVisibility(View.INVISIBLE);
        }
        if(ISAP_Utils.currentPage==0)
            prevBtn.setVisibility(View.INVISIBLE);
        setPageSubHeader(ISAP_Utils.currentPage);
        nextBtn.setVisibility(View.VISIBLE);
        fetchSummary(ISAP_Utils.currentPage);
    }

    public void showGeoMarketCountry(final View v) {

        try{
            ArrayList<String> geo_mrkt_cntry = new ArrayList<String>();
            String clitckBtn="";
            int checkedItem = 0; // cow
            if(v.getTag().equals("0")) {
                clitckBtn = "GEO";
                for(int j=0;j<myplan_data.getGEO().length;j++){
                    geo_mrkt_cntry.add(myplan_data.getGEO()[j].getNAME());
                    checkedItem = checkedGeo;
                }
            } else if(v.getTag().equals("1")) {
                clitckBtn = "Market";
                for(int j=0;j<marketData.length;j++){
                    geo_mrkt_cntry.add(marketData[j].getNAME());
                    checkedItem = checkedMarket;
                }
            } else if(v.getTag().equals("2")) {
                clitckBtn = "Country";
                for(int j=0;j<countryData.length;j++){
                    geo_mrkt_cntry.add(countryData[j].getNAME());
                    checkedItem = checkedCountry;
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(IsapMenuActivity.this,R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Select a "+clitckBtn);

            // final String[] listitems = { "ALL", "AMERICA", "GREATER CHINA", "EUROPE","ASIA PACIFIC","MIDDLE EAST/AFRICA","NORTH AMERICA","GREATER CHINA","GREATER CHINA","INDIA","AUSTRALIA" };
            final  String [] listitems = geo_mrkt_cntry.toArray(new String[geo_mrkt_cntry.size()]);
            builder.setSingleChoiceItems(listitems, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(v.getTag().equals("0")) {
                        checkedGeo = which;
                        isGeoSelected = true;
                    }
                    if(v.getTag().equals("1")) {
                        checkedMarket = which;
                        isMarketSelected = true;
                    }
                    if(v.getTag().equals("2")) {
                        checkedCountry = which;
                        isCountrySelected = true;
                    }
                    //  index = which;
                    // user checked an item
                }
            });

// add OK and Cancel buttons
            builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText text1= findViewById(R.id.editText2);
                    EditText text2= findViewById(R.id.editText3);
                    EditText text3= findViewById(R.id.editText4);
                    if (v.getId() == R.id.buttonGeo){
                        String selectedGeo = myplan_data.getGEO()[checkedGeo].getNAME();
                        int len = selectedGeo.length();
                        geo_ID=myplan_data.getGEO()[checkedGeo].getID();
                        geo_key=myplan_data.getGEO()[checkedGeo].getKEY();
                        geo_Name=myplan_data.getGEO()[checkedGeo].getNAME();
                        if (isGeoSelected == false)
                        {
                            if (isCountrySelected == true || isMarketSelected == true)
                                fetchGeoPlan(clientId,geo_ID,myplan_data.getGEO()[checkedGeo].getKEY(),myplan_data.getGEO()[checkedGeo].getNAME());
                        }
                        else
                            fetchGeoPlan(clientId,geo_ID,myplan_data.getGEO()[checkedGeo].getKEY(),myplan_data.getGEO()[checkedGeo].getNAME());

                        if (len > 12 )
                        {
                            selectedGeo = selectedGeo.substring(0,9);
                            StringBuilder s = new StringBuilder(100);
                            s.append(selectedGeo);
                            s.append("...");
                            text1.setText(s);
                        }
                        else
                        {
                            text1.setText(selectedGeo);
                        }
                        text2.setText("All");
                        checkedMarket = 0;
                        text3.setText("All");
                        checkedCountry = 0;
                        // index = 0;
                    }
                    if (v.getId() == R.id.buttonMarkets){
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        String selectedGeo = marketData[checkedMarket].getNAME();
                        int len = selectedGeo.length();
                        market_ID=marketData[checkedMarket].getID();
                        if(geo_Name.length()==0)geo_Name="All";
                        if(market_ID.length()==0)market_ID="-1";
                        if(geo_ID.length()==0)geo_ID="-1";
                        if(market_Key.length()==0)market_Key="-1";
                        if (isMarketSelected == false)
                        {
                            if (isGeoSelected == true || isCountrySelected == true)
                            fetchMarketPlan(clientId,geo_ID,geo_Name,market_ID,marketData[checkedMarket].getNAME(),market_Key);
                        }
                        else
                            fetchMarketPlan(clientId,geo_ID,geo_Name,market_ID,marketData[checkedMarket].getNAME(),market_Key);
                        if (len > 12 )
                        {
                            selectedGeo = selectedGeo.substring(0,9);
                            StringBuilder s = new StringBuilder(100);
                            s.append(selectedGeo);
                            s.append("...");
                            text2.setText(s);
                        }
                        else
                        {
                            text2.setText(selectedGeo);
                        }
                        text3.setText("All");
                        checkedCountry = 0;
                        // index = 0;
                    }

                    if (v.getId() == R.id.buttonCountry){
                        String selectedGeo = countryData[checkedCountry].getNAME();
                        int len = selectedGeo.length();
                        if(market_ID.length()==0)market_ID="-1";
                        if(geo_key.length()==0)geo_key="-1";
                        if(market_Key.length()==0)market_Key="-1";
                        if (isCountrySelected == false)
                        {
                            if (isGeoSelected == true || isMarketSelected == true)
                            fetchCountryPlan(clientId,geo_ID,market_ID,countryData[checkedCountry].getID(),countryData[checkedCountry].getKEY(),market_Key,geo_key);
                        }
                        else
                            fetchCountryPlan(clientId,geo_ID,market_ID,countryData[checkedCountry].getID(),countryData[checkedCountry].getKEY(),market_Key,geo_key);
                        if (len > 12 )
                        {
                            selectedGeo = selectedGeo.substring(0,9);
                            StringBuilder s = new StringBuilder(100);
                            s.append(selectedGeo);
                            s.append("...");
                            text3.setText(s);
                        }
                        else
                        {
                            text3.setText(selectedGeo);
                        }
                        // index = 0;
                    }
                    // user clicked OK
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
// create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();


            int blueColor = Color.parseColor("#77c0d3");
            Button cancel = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button select = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            cancel.setTextColor(blueColor);
            cancel.setTextSize(21);
            select.setTextColor(blueColor);
            select.setTextSize(21);
        }catch (Exception e){
            System.out.println("Exception Message:"+e.getMessage());
        }

    }
//////////////////////endof methdd///////////
    private void fetchCountryPlan(int clientId, String geo_id, String marketId, String countryId, String countryKey, String market_key, String geo_key) {
        ISAP_Utils.showISAPProgressDialog(IsapMenuActivity.this,ISAP_Constants.FETCH_CLIENTS_DETAILS,false);
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getCountryMyPlan(clientId,geo_id,marketId,countryId,countryKey,market_key,geo_key);
        System.out.println("fetch data for:clientId:"+clientId+",countyrid:"+countryId+",m-key:"+marketId+",m-name:");

        call.enqueue(new Callback<CountryMyPlan>() {
            @Override
            public void onResponse(Call<CountryMyPlan> call, Response<CountryMyPlan> response) {

                my_countydata = response.body();
                 gson = new Gson();
                String data=gson.toJson(my_countydata);
                System.out.println(""+ISAP_Constants.Myplan_Country_Selection+"-"+data);
                MyPlanFragment myPlanFragment = (MyPlanFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                myPlanFragment.populateMyPlanView(my_countydata.getGRAPH(),my_countydata.getBUSINESS_UNIT(),null);
                ISAP_Utils.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<CountryMyPlan> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMarketPlan(int clientId, String geoId, String geoName, String marketId, String marketName,String marketKey) {
        ISAP_Utils.showISAPProgressDialog(IsapMenuActivity.this,ISAP_Constants.FETCH_CLIENTS_DETAILS,false);
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getMarketMyPlan(clientId,geoId,geoName,marketId,marketName,marketKey);
        System.out.println("fetch data for:clientId:"+clientId+",geo:"+geoId+",m-key:"+marketId+",m-name:"+marketName);

        call.enqueue(new Callback<MarketMyPlan>() {
            @Override
            public void onResponse(Call<MarketMyPlan> call, Response<MarketMyPlan> response) {

                my_marketdata = response.body();
                gson = new Gson();
                String data=gson.toJson(my_marketdata);
                System.out.println(""+ISAP_Constants.Myplan_Market_Selection+"-"+data);
                countryData=my_marketdata.getCOUNTRY();//Reset country drop down

                MyPlanFragment myPlanFragment = (MyPlanFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                myPlanFragment.populateMyPlanView(my_marketdata.getGRAPH(),my_marketdata.getBUSINESS_UNIT(),null);
                ISAP_Utils.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<MarketMyPlan> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchGeoPlan(int clientId, String geoId, String geoKey, String geoName) {
        ISAP_Utils.showISAPProgressDialog(IsapMenuActivity.this,ISAP_Constants.FETCH_CLIENTS_DETAILS,false);
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getGeoMyPlan(clientId,geoKey,geoName,geoId);
        System.out.println("fetch data for:clientId:"+clientId+",geo:"+geoId+",key:"+geoKey+",name:"+geoName);

        call.enqueue(new Callback<GeoMyplanModel>() {
            @Override
            public void onResponse(Call<GeoMyplanModel> call, Response<GeoMyplanModel> response) {


                my_geodata = response.body();
                gson = new Gson();
                String data=gson.toJson(my_geodata);
                marketData=my_geodata.getMARKETS();
                System.out.println(""+ISAP_Constants.Myplan_Geo_Selection+"-"+data);
                MyPlanFragment myPlanFragment = (MyPlanFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                myPlanFragment.populateMyPlanView(my_geodata.getGRAPH(),my_geodata.getBUSINESS_UNIT(),null);
                ISAP_Utils.dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<GeoMyplanModel> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchSummary(int currentPage) {
        if(currentPage==0) {

            fetchMyPlan(clientId, intranetId);
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_main,new MyPlanFragment());
            ft.addToBackStack(null);
            ft.commit();
        }else if(currentPage==1){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_main,new GoalFragment());
            ft.addToBackStack(null);
            ft.commit();

        }else if(currentPage==2){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_main,new InitiativeFragment());
            ft.addToBackStack(null);
            ft.commit();

        }else if(currentPage==3){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_main,new RelationshipFragment());
            ft.addToBackStack(null);
            ft.commit();
        }else if(currentPage==4){
            if(ISAP_Utils.isNewGoal==false)
            ISAP_Utils.isInitiativeActive=true;
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_main,new GoalDetailFragment());
            ft.addToBackStack(null);
            ft.commit();
        }else if(currentPage==8){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            ft.replace(R.id.frame_layout_main,new RelationshipMoreFragment());
            ft.addToBackStack(null);
            ft.commit();
        }



    }

    private void setPageSubHeader(int currentPage) {
        if(currentPage==0)
        {
            prevText.setText("My Plan");
            nextText.setText("Goals");
            PageName="MyPlan";
        }else if(currentPage==1){
            prevText.setText("Goals");
            nextText.setText("Initiatives");
            PageName="Goals";
        }else if(currentPage==2){
            prevText.setText("Initiatives");
            nextText.setText("Relationships");
            PageName="Initiatives";
        }else if(currentPage==3){
            prevText.setText("Relationships");
            nextText.setText("My Plan");
            PageName="Relationships";
        }else if(currentPage==4){
            prevText.setText("Goals");
            PageName="Goals";
        }else if(currentPage==8){
            prevText.setText("Relationships");
            nextText.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.INVISIBLE);
            PageName="Relationships";
        }
    }

    private void fetchMyPlan(final int clientId, String intranetId) {
        ISAP_Utils.showISAPProgressDialog(IsapMenuActivity.this,ISAP_Constants.FETCH_CLIENTS_DETAILS,false);
        IsapService = APiUtils.getUserService();
        System.out.println("my plan req:clientId:"+clientId+" ,intranet> "+intranetId);
        Call call = IsapService.getMyPlan(clientId,intranetId,"All","-1","-1","-1","-1","-1","-1");
//        Call call = IsapService.getMyPlan(SingletonSession.Instance());
        call.enqueue(new Callback<MyPlanModel>() {
            @Override
            public void onResponse(Call<MyPlanModel> call, Response<MyPlanModel> response) {

                myplan_data = response.body();
                Gson gson = new Gson();
                ISAP_Utils.userAccess = Boolean.parseBoolean(myplan_data.getACCESS().toLowerCase());
                String data=gson.toJson(myplan_data);
                //System.out.println("my plan data:"+data.toString());
                marketData=myplan_data.getMARKETS();
                countryData=myplan_data.getCOUNTRY();
                createTopClientDialog(IsapMenuActivity.this,myplan_data.getCITY_GROUP());
//                myplan_data.getTEAM_MEMBER()
                MyPlanFragment myPlanFragment = (MyPlanFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                myPlanFragment.populateMyPlanView(myplan_data.getGRAPH(),myplan_data.getBUSINESS_UNIT(),myplan_data.getTEAM_MEMBER());
                //myPlanFragment.populateGridData(myplan_data.getBUSINESS_UNIT());
                ISAP_Utils.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<MyPlanModel> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void createTopClientDialog(Context ctx, final CITY_GROUP[] city_group){

        try{
            ArrayList<String> topList = new ArrayList<String>();
            for(int j=0;j<city_group.length;j++){
                topList.add(city_group[j].getVALUE());
            }
//        System.out.println("data:"+topList);
            alertDialogBuilder = new AlertDialog.Builder(ctx);
            alertDialog = alertDialogBuilder.create();
            final String [] stringArray = topList.toArray(new String[topList.size()]);

            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.citi_group, null);
            alertDialog.setView(convertView);
            WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.TOP;
            wmlp.y = 160;
            ListView lv = (ListView) convertView.findViewById(R.id.citigroup);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringArray);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    alertDialog.dismiss();

//                TextView modelTextview = (TextView)findViewById(R.id.mypage);
                    TextView topClientTitle=(TextView)findViewById(R.id.topClients);
                    topClientTitle.setText(city_group[position].getVALUE());
                    clientId= Integer.parseInt(city_group[position].getKEY());
                    ISAP_Utils.clientID = clientId;
                    onSelectionChanges(ISAP_Utils.currentPage,-1,-1,-1);
                }
            });
        }catch (Exception e){
            System.out.println("Exception Message:"+e.getMessage());
        }


    }
    public void showTopClientsDialog(View view){
        //mMaterialDialog.show();
        if(alertDialog!=null)
        alertDialog.show();
    }
    private void setDefaultUser() {
        SingletonSession.Instance().setClientId(clientId);
        SingletonSession.Instance().setClientName("All");
        SingletonSession.Instance().setIntranetId(intranetId);
        SingletonSession.Instance().setGeoId("-1");
        SingletonSession.Instance().setGeoKey("-1");
        SingletonSession.Instance().setRegionId("-1");
        SingletonSession.Instance().setRegionKey("-1");
        SingletonSession.Instance().setCountryId("-1");
        SingletonSession.Instance().setCountryKey("-1");
    }

    private void displayTopClients(CITY_GROUP[] city_group) {

        try{
            topClients= findViewById(R.id.citigroup);
            List<CITY_GROUP> ClientList=new ArrayList<CITY_GROUP>();
            for(int i=0; i < city_group.length ; i++) {
                CITY_GROUP clientObj=new CITY_GROUP();
                clientObj.setKEY(city_group[i].getKEY());
                clientObj.setVALUE(city_group[i].getVALUE());
                ClientList.add(clientObj);
            }
//        ListAdapter<> clients_adapter = new ListAdapter<>(getApplicationContext(), R.layout.client_alpha_index_layout, ClientList);
//        topClients.setAdapter(clients_adapter);
            System.out.println("data:"+ClientList);
        }catch (Exception e){
            System.out.println("Exception Message:"+e.getMessage());
        }


    }




    @Override
    public void onBackPressed() {

        selectPrevBtn();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.isap_menu, menu);
        return false;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myclients) {
            // Handle the camera action
            Intent intent=new Intent(IsapMenuActivity.this,ClientsActivity.class);
            startActivity(intent);
            ISAP_Utils.clientID=0;//reset client selection
            ISAP_Utils.currentPage=0;
        } else if (id == R.id.nav_myplan) {
            fetchMyPlan(clientId, intranetId);
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_main,new MyPlanFragment());
            ft.commit();
            TextView modelTextview = (TextView)findViewById(R.id.mypage);
            modelTextview.setText("My Plan");
            TextView nextPage = (TextView)findViewById(R.id.nextpage);
            nextPage.setText("Goals");
            ISAP_Utils.currentPage=0;
            prevBtn.setVisibility(View.GONE);
            nextBtn.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_goals) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_main,new GoalFragment());
            ft.commit();
            TextView modelTextview = (TextView)findViewById(R.id.mypage);
            modelTextview.setText("Goals");
            TextView nextPage = (TextView)findViewById(R.id.nextpage);
            nextPage.setText("Initiatives");
            ISAP_Utils.currentPage=1;
            prevBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_initiatives) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_main,new InitiativeFragment());
            ft.commit();
            TextView modelTextview = (TextView)findViewById(R.id.mypage);
            modelTextview.setText("Initiatives");
            TextView nextPage = (TextView)findViewById(R.id.nextpage);
            nextPage.setText("Relationships");
            ISAP_Utils.currentPage=2;
            prevBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_relationship) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_main,new RelationshipFragment());
            ft.commit();
            TextView modelTextview = (TextView)findViewById(R.id.mypage);
            modelTextview.setText("Relationships");
            TextView nextPage = (TextView)findViewById(R.id.nextpage);
            nextPage.setText("My Plan");
            prevBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
            ISAP_Utils.currentPage=3;
        }
//        else if (id == R.id.nav_help) {
//            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.frame_layout_main,new HelpFragment());
//            ft.commit();
      //  }
        else if (id == R.id.nav_logout) {
            Intent intent=new Intent(IsapMenuActivity.this,LoginActivity.class);
            startActivity(intent);
            SecurePreferences.setValue("username", "");
            SecurePreferences.setValue("password", "");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onSelectionChanges(int pageID, int geoId, int countryId, int marketId){
        {
            // Handle navigation view item clicks here.

            if (pageID == 0) {
                //populate my plan view
                fetchMyPlan(clientId,intranetId);
                EditText text1= findViewById(R.id.editText2);
                EditText text2= findViewById(R.id.editText3);
                EditText text3= findViewById(R.id.editText4);
                text1.setText("All");
                checkedGeo = 0;
                text2.setText("All");
                checkedMarket = 0;
                text3.setText("All");
                checkedCountry = 0;
            } else if (pageID == 1) {
                //populate Goal view
                GoalFragment goalSummary = (GoalFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                goalSummary.fetchGoalSummary(clientId, intranetId);
            } else if (pageID == 2) {
                //populate Initiative view
                InitiativeFragment initiatives = (InitiativeFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                initiatives.fetchInitiativeSummary(clientId,intranetId);
            } else if (pageID == 3) {
                //populate Relationship view
                GoalDetailFragment goalDetail = (GoalDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                goalDetail.fetchGoalsMore(clientId,intranetId,-1);
                boolean initiatives = true;
                Bundle bundle=new Bundle();
                bundle.putBoolean("initiatives", initiatives);
                goalDetail.setArguments(bundle);
            }else if (pageID == 4) {
                //populate Goal Details view
                GoalDetailFragment goalDetail = (GoalDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                goalDetail.fetchGoalsMore(clientId,intranetId,-1);
            }
            else if (pageID == 5) {
                //populate Goal Details view
                GoalDetailFragment goalDetail = (GoalDetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                goalDetail.fetchGoalsMore(clientId,intranetId,-1);
            }else if (pageID == 7 ) {
                RelationshipFragment relationshipSummary = (RelationshipFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                relationshipSummary.fetchRelationshipSummary(clientId, intranetId);
            }else if (pageID == 8) {
                //populate Goal Details view
                //do not do anything.
            }
        }
    }
}
