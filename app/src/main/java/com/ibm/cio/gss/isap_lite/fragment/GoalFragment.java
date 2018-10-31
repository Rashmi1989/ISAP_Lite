package com.ibm.cio.gss.isap_lite.fragment;
/*
Class Name : "GoalFragment"
Description :"This fragment is used to display list of goals"
Author      :"Kabuli Behera"
Date of Creation :"March 07 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.NewGoalActivity;
import com.ibm.cio.gss.isap_lite.adapter.GoalRecyclerAdapter;
import com.ibm.cio.gss.isap_lite.model.GOAL_SUMMARY;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.GoalsModel;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends Fragment {

    int clientId = 0;
    String intranetId = "";
    private ISAPService IsapService;
    private GoalsModel goalModel;
    private GoalDetails goalMore_data;
    private TextView addMoreTextView;
    private LinearLayout newView;

    private RecyclerView recyclerView;
    private GoalRecyclerAdapter goalRecyclerAdapter;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";



    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getActivity().findViewById(R.id.geo_market);
        view.setVisibility(view.GONE);
        clientId = ISAP_Utils.clientID;
        intranetId = ISAP_Utils.LoggedInuserEmail;
        //intranetId = "adamd@us.ibm.com";
        RelativeLayout relativeLayout=getActivity().findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        LinearLayout moreView=getActivity().findViewById(R.id.moreLayout);
        moreView.setVisibility(View.GONE);
         newView=getActivity().findViewById(R.id.saveLayout);

        /*if(ISAP_Utils.userAccess==true)
            newView.setVisibility(View.VISIBLE);
        else newView.setVisibility(View.GONE);*/

        if(ISAP_Utils.userAccess==true){
            newView.setVisibility(View.VISIBLE);

        }else {
            newView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);

        }

        fetchGoalSummary(clientId, intranetId);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        addMoreTextView = (TextView) getView().findViewById(R.id.saveLayout);

        recyclerView = (RecyclerView) getView().findViewById(R.id.goalFragmentRecyclerView);
        /*if(ISAP_Utils.userAccess==true) {
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0, 3, 0, 400);
            recyclerView.setLayoutParams(marginLayoutParams);
        }else{
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0, 3, 0, 120);
            recyclerView.setLayoutParams(marginLayoutParams);
        }*/
        TextView newText=getActivity().findViewById(R.id.newText);
        newText.setText("New Goal");

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), NewGoalActivity.class);
                startActivity(intent);

            }
        });

    }



    public void fetchGoalSummary(int clientId, String intranetId) {
        IsapService = APiUtils.getUserService();
         ISAP_Utils.showISAPProgressDialog(getActivity(), ISAP_Constants.FETCH_GOALS,false);

        Call call = IsapService.getMyGoalSummary(clientId, intranetId, "All", "-1", "-1");
//        Call call = IsapService.getMyPlan(SingletonSession.Instance());
        call.enqueue(new Callback<GoalsModel>() {
            @Override
            public void onResponse(Call<GoalsModel> call, Response<GoalsModel> response) {

                goalModel = response.body();
               System.out.println("Goal summary size:"+goalModel.getGOAL_SUMMARY());
                if (goalModel.getGOAL_SUMMARY() == null) {

                    Toast.makeText(getActivity(), ISAP_Constants.NO_GOALS, Toast.LENGTH_SHORT).show();

                }
//                System.out.println("Goal summary size:"+goalModel.getGOAL_SUMMARY().length);
//                if (ISAP_Utils.isProgressDialogVisible())
                else
                {
                   // createGoalUI(goalModel);
                    createGoalListUI();
                }
                ISAP_Utils.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<GoalsModel> call, Throwable t) {
                if (ISAP_Utils.isProgressDialogVisible())
                    ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
                Toast.makeText(getActivity(), ISAP_Constants.NO_GOALS, Toast.LENGTH_SHORT).show();
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//

    private void createGoalListUI(){


        List<GOAL_SUMMARY> goal_Summarylist=new ArrayList<GOAL_SUMMARY>();

        for(int i =0;i < goalModel.getGOAL_SUMMARY().length;i++){
            GOAL_SUMMARY goal_summaryData=new GOAL_SUMMARY();

            goal_summaryData.setSTRATEGIC_GOAL_NM(goalModel.getGOAL_SUMMARY()[i].getSTRATEGIC_GOAL_NM());
            goal_summaryData.setGOAL_DESC_TXT(goalModel.getGOAL_SUMMARY()[i].getGOAL_DESC_TXT());
            goal_summaryData.setNO_OF_INTIATIVES(goalModel.getGOAL_SUMMARY()[i].getNO_OF_INTIATIVES());
            goal_summaryData.setESTIMATE_SIZE_AMT(goalModel.getGOAL_SUMMARY()[i].getESTIMATE_SIZE_AMT());
            goal_summaryData.setSTRATEGIC_GOAL_KEY(goalModel.getGOAL_SUMMARY()[i].getSTRATEGIC_GOAL_KEY());


            goal_Summarylist.add(goal_summaryData);

        }


        goalRecyclerAdapter = new GoalRecyclerAdapter(goal_Summarylist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));

        // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(goalRecyclerAdapter);

        ISAP_Utils.dismissProgressDialog();
        goalRecyclerAdapter.notifyDataSetChanged();

    }




    private void navigatetoGoalsDetailFragment(String data){

        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        GoalDetailFragment goalDetailFragment = new GoalDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putString("goalId", data);
        goalDetailFragment.setArguments(bundle);
        t.replace(R.id.frame_layout_main,goalDetailFragment);
        t.addToBackStack("");
      //  t.addToBackStack(null);
        getFragmentManager().beginTransaction().replace(R.id.frame_layout_main, goalDetailFragment).addToBackStack("my_fragment").commit();
        t.commit();
    }

    private View.OnClickListener goalClicked = new View.OnClickListener() {
        public void onClick(View v) {
             //String i = v.getTag().toString();
            ISAP_Utils.isInitiativeActive=false;
            Integer cardPosition = Integer.parseInt(v.getTag().toString());
            String goalId = goalModel.getGOAL_SUMMARY()[cardPosition].getSTRATEGIC_GOAL_KEY();
            //fetchGoalsMore(clientId,intranetId,goalId);
            navigatetoGoalsDetailFragment(goalId);
            // do something when the button is clicked


            //getFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new tasks());
            // Yes we will handle click here but which button clicked??? We don't know

        }
    };
}
