package com.ibm.cio.gss.isap_lite.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.activity.NewGoalActivity;
import com.ibm.cio.gss.isap_lite.activity.NewInitiativeActivity;
import com.ibm.cio.gss.isap_lite.adapter.GoalListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.InitiativesMoreAdapter;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES_MORE;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.stylekitIcons.MyDividerItemDecoration;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalDetailFragment extends Fragment {


    int clientId = 0;
    String intranetId = "";
    private ISAPService IsapService;
    private GoalDetails goalMore_data;
    private GOAL_MORE goalMoreList;
    private List<GoalDetails> goalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GoalListAdapter goalAdapter;
    private InitiativesMoreAdapter initiativesMoreAdapter;
    private Button goalTabButton;
    private Button initiativesTabButton;
    private TextView goalText;
    private TextView initiativeText;
    private LinearLayout newView;


    public GoalDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        View view = getActivity().findViewById(R.id.geo_market);
        view.setVisibility(view.GONE);
        clientId = ISAP_Utils.clientID;
        intranetId = ISAP_Utils.LoggedInuserEmail;
        newView=getActivity().findViewById(R.id.saveLayout);
        /*if(ISAP_Utils.userAccess==true)
            newView.setVisibility(View.VISIBLE);
        else newView.setVisibility(View.GONE);
        LinearLayout moreView=getActivity().findViewById(R.id.moreLayout);
        moreView.setVisibility(View.GONE);*/

        if(ISAP_Utils.userAccess==true){
            newView.setVisibility(View.VISIBLE);
        }else {
            RelativeLayout relativeLayout=getActivity().findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.GONE);
        }





        Bundle bundle=getArguments();
        boolean initiatives =ISAP_Utils.isInitiativeActive;
        if (initiatives == true)
        {
            fetchGoalsMore(clientId,intranetId,-1);
            ISAP_Utils.currentPage=3;
            ISAP_Utils.isInitiativeTabSelected = true;
            ISAP_Utils.isGoalTabSelected = false;
            TextView newText=getActivity().findViewById(R.id.newText);
            newText.setText("New Initiative");
            LinearLayout saveView=getActivity().findViewById(R.id.saveLayout);
            if(ISAP_Utils.userAccess==true)
                saveView.setVisibility(View.VISIBLE);
            else saveView.setVisibility(View.GONE);

        }
        else {
//            String data = String.valueOf(bundle.getString("goalId"));
            String data="0";
            System.out.println("Goal id is:"+data);
            fetchGoalsMore(clientId,intranetId,Integer.parseInt(data));
            ISAP_Utils.currentPage=4;
            ISAP_Utils.isGoalTabSelected = true;
            ISAP_Utils.isInitiativeTabSelected = false;
            LinearLayout saveView=getActivity().findViewById(R.id.saveLayout);
            if(ISAP_Utils.userAccess==true)
                saveView.setVisibility(View.VISIBLE);
            else saveView.setVisibility(View.GONE);
            TextView newText=getActivity().findViewById(R.id.newText);
            newText.setText("New Goal");
        }


        IsapMenuActivity.nextText.setText("");
        IsapMenuActivity.nextBtn.setVisibility(getView().INVISIBLE);
        return inflater.inflate(R.layout.fragment_goal_detail, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ISAP_Utils.isGoalTabSelected==true)
                {

                    Intent intent = new Intent(getActivity(), NewGoalActivity.class);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(getActivity(), NewInitiativeActivity.class);
                    startActivity(intent);
                }

            }
        });
        goalText=(TextView)getView().findViewById(R.id.goal_text);
        initiativeText=(TextView)getView().findViewById(R.id.ini_text) ;
        goalTabButton = (Button) getView().findViewById(R.id.goal_tab);
        goalTabButton.setOnClickListener(showGoalsMore);
        initiativesTabButton = (Button) getView().findViewById(R.id.initiative_tab);
        initiativesTabButton.setOnClickListener(showInitiativesMore);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Bundle bundle=getArguments();
        boolean initiatives = ISAP_Utils.isInitiativeActive;
        if (initiatives == true)
        {
            initiativesTabButton.setBackground(getResources().getDrawable(R.drawable.initiative_tab_active, null));
            initiativesTabButton.setTextColor(getResources().getColor(R.color.colorLogin, null));
            goalTabButton.setBackground(getResources().getDrawable(R.drawable.goal_tab_inactive, null));
            goalTabButton.setTextColor(getResources().getColor(R.color.colorAccent, null));
        }else{
//            initiativesTabButton.setBackground(getResources().getDrawable(R.drawable.initiative_round_btn, null));
//            initiativesTabButton.setTextColor(getResources().getColor(R.color.colorLogin, null));
//            goalTabButton.setBackground(getResources().getDrawable(R.drawable.goal_round_btn, null));
//            goalTabButton.setTextColor(getResources().getColor(R.color.colorAccent, null));
        }
       /* //hide button in case user dont have edit access
        if(ISAP_Utils.userAccess==true) {
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0, 3, 0, 400);
            recyclerView.setLayoutParams(marginLayoutParams);
        }else{
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.setMargins(0, 3, 0, 180);
            recyclerView.setLayoutParams(marginLayoutParams);
        }*/

    }

    private View.OnClickListener showGoalsMore = new View.OnClickListener() {
        public void onClick(View v) {
            //String i = v.getTag().toString();
            IsapMenuActivity.prevText.setText("Goals");
            LinearLayout saveView=getActivity().findViewById(R.id.saveLayout);
            TextView newText=getActivity().findViewById(R.id.newText);
            newText.setText("New Goal");
//            saveView.setVisibility(View.VISIBLE);//need to enable and change text in next sprint
            if(ISAP_Utils.userAccess==true)
                saveView.setVisibility(View.VISIBLE);
            else saveView.setVisibility(View.GONE);
            ISAP_Utils.isInitiativeTabSelected = false;
            ISAP_Utils.isGoalTabSelected = true;
            initiativesTabButton.setBackground(getResources().getDrawable(R.drawable.initiative_round_btn,null));
            goalTabButton.setBackground(getResources().getDrawable(R.drawable.goal_round_btn,null));
            initiativesTabButton.setTextColor(getResources().getColor(R.color.colorAccent,null));
            goalTabButton.setTextColor(getResources().getColor(R.color.colorLogin,null));
            goalText.setVisibility(View.VISIBLE);
            initiativeText.setVisibility(View.GONE);
            createGoalsMoreData();

        }
    };

    public void createGoalsMoreData(){


        List<GOAL_MORE> goal_list=new ArrayList<GOAL_MORE>();

        try {

            for(int i =0;i < goalMore_data.getGOAL_MORE().length;i++){
                GOAL_MORE goalData=new GOAL_MORE();
                goalData.setCLIENT_BUSINESS_AREA(goalMore_data.getGOAL_MORE()[i].getCLIENT_BUSINESS_AREA());
                goalData.setESTIMATE_SIZE_AMT(goalMore_data.getGOAL_MORE()[i].getESTIMATE_SIZE_AMT());
                goalData.setGoal_label("Goal");
                goalData.setINITIATIVES(goalMore_data.getGOAL_MORE()[i].getINITIATIVES());
                goalData.setSTRATEGIC_GOAL_KEY(goalMore_data.getGOAL_MORE()[i].getSTRATEGIC_GOAL_KEY());
                goalData.setSTRATEGIC_GOAL_NM(goalMore_data.getGOAL_MORE()[i].getSTRATEGIC_GOAL_NM());
                goal_list.add(goalData);

            }

            goalAdapter = new GoalListAdapter(goal_list);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
            recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));

            // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(goalAdapter);

            ISAP_Utils.dismissProgressDialog();
            goalAdapter.notifyDataSetChanged();


        }
        catch (Exception e){

            System.out.println("Exception Message:"+e.getMessage());
        }



    }

    private void showInitiatives() {
        //String i = v.getTag().toString();
        IsapMenuActivity.prevText.setText("Initiatives");
        LinearLayout saveView=getActivity().findViewById(R.id.saveLayout);
        if(ISAP_Utils.userAccess==true)
            saveView.setVisibility(View.VISIBLE);
        else saveView.setVisibility(View.GONE);
        TextView newText=getActivity().findViewById(R.id.newText);
        newText.setText("New Initiative");
        ISAP_Utils.isGoalTabSelected = false;
        ISAP_Utils.isInitiativeTabSelected = true;
        initiativesTabButton.setBackground(getResources().getDrawable(R.drawable.initiative_tab_active, null));
        initiativesTabButton.setTextColor(getResources().getColor(R.color.colorLogin, null));
        goalTabButton.setBackground(getResources().getDrawable(R.drawable.goal_tab_inactive, null));
        goalTabButton.setTextColor(getResources().getColor(R.color.colorAccent, null));
        goalText.setVisibility(View.GONE);
        initiativeText.setVisibility(View.VISIBLE);
        List<INITIATIVES_MORE> initiatives_moreList = new ArrayList<INITIATIVES_MORE>();
        try {

            for (int i = 0; i < goalMore_data.getINITIATIVES_MORE().length; i++) {

                INITIATIVES_MORE initiativesMoreData = new INITIATIVES_MORE();
                initiativesMoreData.setINITIATIVE_KEY_NM(goalMore_data.getINITIATIVES_MORE()[i].getINITIATIVE_KEY_NM());
                initiativesMoreData.setGOALS(goalMore_data.getINITIATIVES_MORE()[i].getGOALS());
                initiativesMoreData.setINITIATIVE_LEAD(goalMore_data.getINITIATIVES_MORE()[i].getINITIATIVE_LEAD());
                initiativesMoreData.setBUSINESS_UNITS(goalMore_data.getINITIATIVES_MORE()[i].getBUSINESS_UNITS());
                initiativesMoreData.setINDUSTRY_SOLUTION(goalMore_data.getINITIATIVES_MORE()[i].getINDUSTRY_SOLUTION());
                initiativesMoreData.setPROGRESSION_CODE(goalMore_data.getINITIATIVES_MORE()[i].getPROGRESSION_CODE());
                initiativesMoreData.setINITIATIVE_VAL(goalMore_data.getINITIATIVES_MORE()[i].getINITIATIVE_VAL());
                initiativesMoreData.setINITIATIVE_KEY(goalMore_data.getINITIATIVES_MORE()[i].getINITIATIVE_KEY());
                initiatives_moreList.add(initiativesMoreData);


            }

            initiativesMoreAdapter = new InitiativesMoreAdapter(initiatives_moreList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
            recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));

            // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(initiativesMoreAdapter);

            ISAP_Utils.dismissProgressDialog();
            initiativesMoreAdapter.notifyDataSetChanged();

        } catch (Exception e) {

        }
    }

    private View.OnClickListener showInitiativesMore = new View.OnClickListener() {
        public void onClick(View v) {

            showInitiatives();
        }
    };


    public void fetchGoalsMore(final int clientId,String intranetId,int goalId) {
       String message="";
       if(ISAP_Utils.isInitiativeActive==false)
           message=ISAP_Constants.FETCH_GOALS_Details;
       else message=ISAP_Constants.FETCH_INITIATIVE_Details;
        ISAP_Utils.showISAPProgressDialog(getActivity(), message,false);
        IsapService = APiUtils.getUserService();
        // Call call = IsapService.getGoalsMore(clientId,"All",intranetId,26811,-1,-1);
        Call call = IsapService.getGoalsMore(clientId,"All",intranetId,goalId,-1,-1);
        call.enqueue(new Callback<GoalDetails>() {
            @Override
            public void onResponse(Call<GoalDetails> call, Response<GoalDetails> response) {

                goalMore_data = response.body();
                // goalsMore_data = response.body();
                Gson gson = new Gson();
                String data=gson.toJson(goalMore_data);
//                System.out.println("Response for GoalsMore Data is :"+clientId+" > :"+data);

               if (ISAP_Utils.isInitiativeTabSelected == true)
                {
                    showInitiatives();
                }
                else {
                    createGoalsMoreData();
                }
//                Log.i("Response of my plan:"+data);
//                System.out.println("my plan data city group:"+myplan_data.getCITY_GROUP().toString());

                // drawGoalsLayout();
               // goalMoreList = goalMore_data.getGOAL_MORE();


            }

            @Override
            public void onFailure(Call<GoalDetails> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
                Toast.makeText(getContext(), ISAP_Constants.SERVER_ISSUE, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
