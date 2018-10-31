package com.ibm.cio.gss.isap_lite.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.adapter.LinkedOpportunityAdapter;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.LinkedOpportunitiesModel;
import com.ibm.cio.gss.isap_lite.model.OPPTS;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.stylekitIcons.MyDividerItemDecoration;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LinkedOpportunitesFragment extends Fragment {


    private ISAPService IsapService;
    private LinkedOpportunitiesModel linkedOpportunitiesData;
    private TextView initiativeName;
    private TextView oppurtinityCount;
    private TextView opportunityValue;
    private TextView opportunityTextLabel;

    private TextView goalLabel;
    private RecyclerView recyclerView;
    private LinkedOpportunityAdapter linkedOpportunityAdapter;
    public View goalDropDownView;
    private  AlertDialog.Builder alertDialogBuilder;
    private  AlertDialog alertDialog;



    public LinkedOpportunitesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle=getArguments();
        String data = String.valueOf(bundle.getString("initiative_Key"));
        fetchLinkedOpportunities(data);


        ISAP_Utils.currentPage=5;
        return inflater.inflate(R.layout.fragment_linked_opportunites, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.linkedOppurtunitiesRecyclerView);
        goalDropDownView = (View) view.findViewById(R.id.goalDropdown);




    }



    public void updateUI(){

      // linkedOpportunitiesData.getINITIATIVE_KEY_NM();
      // linkedOpportunitiesData.
        initiativeName = (TextView) getView().findViewById(R.id.opportunityInitiativeName);
        oppurtinityCount = (TextView) getView().findViewById(R.id.opportunityCountLabel);
        opportunityValue = (TextView) getView().findViewById(R.id.opportunityValueLabel);
        goalLabel = (TextView) getView().findViewById(R.id.initiativeGoalLabel);
        opportunityTextLabel = (TextView) getView().findViewById(R.id.opportunityTextLabel);

        initiativeName.setText(linkedOpportunitiesData.getINITIATIVE_KEY_NM());
        oppurtinityCount.setText(linkedOpportunitiesData.getOPT_COUNT());

        if(Integer.parseInt(linkedOpportunitiesData.getOPT_COUNT()) > 1){
            opportunityTextLabel.setText("Opportunities");
        }else{
            opportunityTextLabel.setText("Opportunity");
        }
        oppurtinityCount.setText(linkedOpportunitiesData.getOPT_COUNT());
        opportunityValue.setText(linkedOpportunitiesData.getTOTAL());
        goalLabel.setText(linkedOpportunitiesData.getGOALS()[0].getVALUE());


        List<OPPTS> opptsList=new ArrayList<OPPTS>();

        if(linkedOpportunitiesData.getOPPTS().length >= 1) {
            for (int i = 0; i < linkedOpportunitiesData.getOPPTS().length; i++) {

                OPPTS opptsData = new OPPTS();
                opptsData.setOPPORTUNITY_ID(linkedOpportunitiesData.getOPPTS()[i].getOPPORTUNITY_ID());
                opptsData.setOPPORTUNITY_NM(linkedOpportunitiesData.getOPPTS()[i].getOPPORTUNITY_NM());
                opptsData.setOPPTY_VALUE(linkedOpportunitiesData.getOPPTS()[i].getOPPTY_VALUE());
                opptsList.add(opptsData);

            }
        }else {
            OPPTS opptsData = new OPPTS();
            opptsData.setOPPORTUNITY_ID("");
            opptsData.setOPPORTUNITY_NM("There are no opportunities linked to initiatives for this client");
            opptsData.setOPPTY_VALUE("");
            opptsList.add(opptsData);
        }

        linkedOpportunityAdapter = new LinkedOpportunityAdapter(opptsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(linkedOpportunityAdapter);

        ISAP_Utils.dismissProgressDialog();
        linkedOpportunityAdapter.notifyDataSetChanged();

        goalDropDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // perform ur click here


                //initiativesDetailList=initiativesList.get(position);
//                createTopClientDialog(goalDetailsList.getINITIATIVES());
                ArrayList<String> topList = new ArrayList<String>();
                for(int j=0;j<linkedOpportunitiesData.getGOALS().length;j++){
                    topList.add(linkedOpportunitiesData.getGOALS()[j].getVALUE());
                }
                System.out.println("data:"+topList);
                alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialog = alertDialogBuilder.create();
                final String [] stringArray = topList.toArray(new String[topList.size()]);
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.citi_group, null);
                alertDialog.setView(convertView);
//        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP;
//        wmlp.y = 160;
                ListView lv = (ListView) convertView.findViewById(R.id.citigroup);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,stringArray);
                lv.setAdapter(adapter);
                alertDialog.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        alertDialog.dismiss();
                        //holder.goalListLabel.setText(initiativesDetailList.getGOALS()[position].getVALUE());
                        goalLabel.setText(linkedOpportunitiesData.getGOALS()[position].getVALUE());

                    }
                });



            }
        });



    }

    public void fetchLinkedOpportunities(String initiativeKey){

        ISAP_Utils.showISAPProgressDialog(getActivity(), ISAP_Constants.FETCH_LINKEDOPPORTUNITIES,false);
        IsapService = APiUtils.getUserService();
        // Call call = IsapService.getGoalsMore(clientId,"All",intranetId,26811,-1,-1);
        Call call = IsapService.getLinkedOpportunities(Integer.parseInt(initiativeKey) ,-1,-1);
        call.enqueue(new Callback<LinkedOpportunitiesModel>() {
            @Override
            public void onResponse(Call<LinkedOpportunitiesModel> call, Response<LinkedOpportunitiesModel> response) {

                linkedOpportunitiesData = response.body();
                // goalsMore_data = response.body();
                updateUI();
                Gson gson = new Gson();
                String data=gson.toJson(linkedOpportunitiesData);
                System.out.println("Response for linked opportunities is"+data);
                ISAP_Utils.dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<LinkedOpportunitiesModel> call, Throwable t) {


                ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());

            }


        });




    }

}
