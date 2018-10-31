package com.ibm.cio.gss.isap_lite.fragment;
/*
Class Name : "RelationshipFragment"
Description :"This fragment is used to display the relationships."
Author      :"Kabuli Behera"
Date of Creation :"March 07 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.Gauge;
import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.TubeSpeedometer;
import com.github.anastr.speedviewlib.components.Indicators.Indicator;
import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.adapter.GoalListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.RelationshipAdapter;
import com.ibm.cio.gss.isap_lite.model.InitiativeModel;
import com.ibm.cio.gss.isap_lite.model.RelationshipModel;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.stylekitIcons.MyDividerItemDecoration;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelationshipFragment extends Fragment {
    private int clientId=0;
    private String intranetId="";
    private ISAPService IsapService;
    private RelationshipModel relationshipModel;
    //private SpeedView speedView;
    private PointerSpeedometer speedView;

    private RecyclerView relationView;
    private RelationshipAdapter relationshipAdapter;
    private Gson gson ;
    private View relationshipMoreView;
    private TextView promoterLabel;
    private TextView detractorLabel,speedText;

    public RelationshipFragment() {
        // Required empty public constructor
        //relation_moreView
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ISAP_Utils.currentPage=7;
        clientId= ISAP_Utils.clientID;
        intranetId=ISAP_Utils.LoggedInuserEmail;
        fetchRelationshipSummary(clientId,intranetId);
        View view = getActivity().findViewById(R.id.geo_market);
        view.setVisibility(view.GONE);
        RelativeLayout relativeLayout=getActivity().findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        LinearLayout moreView=getActivity().findViewById(R.id.moreLayout);
        moreView.setVisibility(View.VISIBLE);
        LinearLayout saveView=getActivity().findViewById(R.id.saveLayout);
        saveView.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_relationship, container, false);
    }

    public void createGaugeChart(){


    if(!relationshipModel.getORA().getOVERALL_REL_ASSESSMENT().equalsIgnoreCase("")) {
            float chartValue = Float.parseFloat(relationshipModel.getORA().getOVERALL_REL_ASSESSMENT());
            chartValue = chartValue * 100;
            // chartValue = chartValue*100;

            promoterLabel.setText(relationshipModel.getPD_PTC().getPROMOTERS_PCT() + "%");
            detractorLabel.setText(relationshipModel.getPD_PTC().getDETRACTORS_PCT() + "%");
            // speedView.setMarkColor(Color.parseColor("#eaeaea"));
            int j = (int) chartValue;
            speedView.speedTo(Math.round(j));

            speedView.setUnit("%");
        speedText.setText(Math.round(j)+"%");

        }

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        promoterLabel = (TextView) getView().findViewById(R.id.promoterLabel);
        detractorLabel = (TextView) getView().findViewById(R.id.detractorLabel);
        speedText=(TextView)getView().findViewById(R.id.speedVal);


        speedView = (PointerSpeedometer) getView().findViewById(R.id.speedView);
        relationView = (RecyclerView) getView().findViewById(R.id.relationList);
        speedView.setStartDegree(180);
        speedView.setEndDegree(360);
        speedView.setSpeedometerColor(Color.parseColor("#522295"));
        speedView.setPointerColor(Color.parseColor("#F3F3F3"));
        speedView.setTickRotation(false);
        speedView.setSpeedometerWidth(140);
        speedView.setWithPointer(false);
        speedView.setWithTremble(false);
        speedView.setSpeedTextColor(Color.BLACK);
        speedView.setUnitTextColor(Color.BLACK);
        speedView.setUnitTextSize(40);
        speedView.setSpeedTextSize(70);
        speedView.setSpeedTextTypeface(Typeface.DEFAULT);
       // speedView.setSpeedTextPosition(Gauge.Position.BOTTOM_LEFT);
      //  speedView.setSpeedTextPadding(10);
        //speedView.setForegroundTintMode(PorterDuff.Mode.CLEAR);
        speedView.setUnit("%");
        speedView.setWithIndicatorLight(false);
        speedView.setDrawingCacheEnabled(false);
        speedView.setBackgroundCircleColor(Color.TRANSPARENT);
        speedView.setMarkColor(Color.parseColor("#F3F3F3"));
        speedView.setBackgroundColor(Color.TRANSPARENT);
        speedView.setCenterCircleColor(Color.parseColor("#F3F3F3"));
        speedView.setIndicatorColor(Color.TRANSPARENT);
        //speedView.setIndicator(Indicator.Indicators.NormalIndicator);
       // speedView.setIndicatorColor(Color.DKGRAY);
        //speedView.setIndicatorColor(Color.parseColor("#8cd5e8"));
       // speedView.setIndicatorWidth(25);
        /*DisplayMetrics displayMetrics=ISAP_Utils.getDeviceMetrics((Activity) getContext());

        System.out.println("Device Name : redmi note 4 -  width :"+displayMetrics.widthPixels+" , height : "+displayMetrics.heightPixels);

        if(displayMetrics.heightPixels==2392){
            //Nexus 6 P
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 850);
            relationView.setLayoutParams(lp);

            LinearLayout layout= (LinearLayout)view.findViewById(R.id.relationiList_layout);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,850);
            layout.setLayoutParams(parms);
        }
        if(displayMetrics.heightPixels==2768){
            //samsung galaxy 8
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 950);
            relationView.setLayoutParams(lp);

            LinearLayout layout= (LinearLayout)view.findViewById(R.id.relationiList_layout);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1020);
            layout.setLayoutParams(parms);
        }
*/

    }
    public void fetchRelationshipSummary(int clientId, String intranetId) {
        IsapService = APiUtils.getUserService();
        ISAP_Utils.showISAPProgressDialog(getActivity(), ISAP_Constants.FETCH_RELATIONSHIP,false);
        Call call = IsapService.getRelationshipSummary(clientId,"All",intranetId);
//        Call call = IsapService.getMyPlan(SingletonSession.Instance());
        call.enqueue(new Callback<RelationshipModel>() {
            @Override
            public void onResponse(Call<RelationshipModel> call, Response<RelationshipModel> response) {

                relationshipModel = response.body();
                System.out.println("Relationship summary size:"+relationshipModel.getSHIPS_ARRAY());
                createGaugeChart();
                createRelationshipList();
                gson = new Gson();
                String data=gson.toJson(relationshipModel);
                System.out.println(""+ ISAP_Constants.RELATIONSHIP_SUMMERY+" :- "+data);
               // relationshipModel.getORA().
//                Toast.makeText(RelationshipModel.this, "", Toast.LENGTH_SHORT).show();
                if(ISAP_Utils.isProgressDialogVisible())
                    ISAP_Utils.dismissProgressDialog();

            }



            @Override
            public void onFailure(Call<RelationshipModel> call, Throwable t) {
                if(ISAP_Utils.isProgressDialogVisible())
                    ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createRelationshipList() {

        relationshipAdapter = new RelationshipAdapter(relationshipModel.getSHIPS_ARRAY());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        relationView.setLayoutManager(mLayoutManager);
        relationView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
       // relationView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));

        // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        relationView.setAdapter(relationshipAdapter);

        ISAP_Utils.dismissProgressDialog();
        relationshipAdapter.notifyDataSetChanged();
    }

}
