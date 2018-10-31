package com.ibm.cio.gss.isap_lite.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.activity.NewRelationshipActivity;
import com.ibm.cio.gss.isap_lite.adapter.RelationshipAdapter;
import com.ibm.cio.gss.isap_lite.model.RelationshipMore;
import com.ibm.cio.gss.isap_lite.model.SHIPS_ARRAY;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RelationshipMoreFragment extends Fragment {

    private int clientId=0;
    private String intranetId="";
    private RelationshipMore relationshipMore;
   // private SHIPS_ARRAY ships_array;
    private SpeedView speedView;
    private RecyclerView relationView;
    private RelationshipAdapter relationshipAdapter;
    private Gson gson ;
    private ISAPService IsapService;
    private TextView relationshipType;
    private  AlertDialog.Builder alertDialogBuilder;
    private  AlertDialog alertDialog;
    public  Context ctx;
    private LinearLayout saveView;

    public RelationshipMoreFragment() {
        // Required empty public constructo
        //
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        relationshipType = (TextView) getView().findViewById(R.id.relationType);

        ctx=getActivity();


        relationView = (RecyclerView) getView().findViewById(R.id.relationshipMoreRecycle);
        relationView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //hide button in case user dont have edit access
        /*if(ISAP_Utils.userAccess==true) {
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) relationView.getLayoutParams();
            marginLayoutParams.setMargins(0, 3, 0, 400);
            relationView.setLayoutParams(marginLayoutParams);
        }else{
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) relationView.getLayoutParams();
            marginLayoutParams.setMargins(0, 3, 0, 180);
            relationView.setLayoutParams(marginLayoutParams);
        }*/


        relationshipType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ArrayList<String> topList = new ArrayList<String>();
               // ArrayList<String> relationshipCodeList = new ArrayList<String>();

                for(int j=0;j<relationshipMore.getREL_ASSESSMENT_ARRAY().length;j++){
                    topList.add(relationshipMore.getREL_ASSESSMENT_ARRAY()[j].getREL_ASSESSMENT());
                   // relationshipCodeList.add(relationshipMore.getREL_ASSESSMENT_ARRAY()[j].getASSESMENT_CD());
                }
                System.out.println("data:"+topList);
                alertDialogBuilder = new AlertDialog.Builder(ctx);
                alertDialog = alertDialogBuilder.create();
                final String [] stringArray = topList.toArray(new String[topList.size()]);
                LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.citi_group, null);
                alertDialog.setView(convertView);
//        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP;
//        wmlp.y = 160;
                ListView lv = (ListView) convertView.findViewById(R.id.citigroup);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,stringArray);
                lv.setAdapter(adapter);
                alertDialog.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {


                       SHIPS_ARRAY[] shipsArray = new SHIPS_ARRAY[]{};
                       List<SHIPS_ARRAY> relationArList = new ArrayList<SHIPS_ARRAY>();


                        alertDialog.dismiss();

                        String relationshipTypeText = relationshipMore.getREL_ASSESSMENT_ARRAY()[position].getREL_ASSESSMENT();
                        String relationshipCodeString = relationshipMore.getREL_ASSESSMENT_ARRAY()[position].getASSESMENT_CD();
                        relationshipType.setText(relationshipTypeText);

                        if(relationshipCodeString.equalsIgnoreCase("All")){

                            createRecyclerAdapterLayout(relationshipMore.getSHIPS_ARRAY());

                        }else{

                            for(int j=0;j<relationshipMore.getSHIPS_ARRAY().length;j++){


                                if(relationshipCodeString.equalsIgnoreCase(relationshipMore.getSHIPS_ARRAY()[j].getASSESMENT_CD()) ){

                                    relationArList.add(relationshipMore.getSHIPS_ARRAY()[j]);
                                }


                            }

                            shipsArray =  relationArList.toArray(new SHIPS_ARRAY[relationArList.size()]);
                            createRecyclerAdapterLayout(shipsArray);

                        }



                    }
                });


            }

        });

        saveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(MainActivity.this, MyOtherActivity.class));
                startActivity( new Intent(getActivity(),NewRelationshipActivity.class) );
            }
        });

    }



    private void createRecyclerAdapterLayout(SHIPS_ARRAY[] shipsArrays){

        relationshipAdapter = new RelationshipAdapter(shipsArrays);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        relationView.setLayoutManager(mLayoutManager);
        relationView.setItemAnimator(new DefaultItemAnimator());

       // relationView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));

        relationView.setAdapter(relationshipAdapter);

        relationshipAdapter.notifyDataSetChanged();

    }


    private void fetchRelationshipMore(int clientId, String intranetId) {
        IsapService = APiUtils.getUserService();
        ISAP_Utils.showISAPProgressDialog(getActivity(), ISAP_Constants.FETCH_RELATIONSHIP,false);
        Call call = IsapService.getRelationshipMore(clientId,"All",intranetId,"All");
//        Call call = IsapService.getMyPlan(SingletonSession.Instance());
        call.enqueue(new Callback<RelationshipMore>() {
            @Override
            public void onResponse(Call<RelationshipMore> call, Response<RelationshipMore> response) {

                relationshipMore = response.body();
               // System.out.println("Relationship summary size:"+relationshipModel.getSHIPS_ARRAY());
                createRecyclerAdapterLayout(relationshipMore.getSHIPS_ARRAY());
               // gson = new Gson();
                //String data=gson.toJson(relationshipModel);
                //System.out.println(""+ ISAP_Constants.RELATIONSHIP_SUMMERY+" :- "+data);
                // relationshipModel.getORA().
//                Toast.makeText(RelationshipModel.this, "", Toast.LENGTH_SHORT).show();
                if(ISAP_Utils.isProgressDialogVisible())
                    ISAP_Utils.dismissProgressDialog();

            }



            @Override
            public void onFailure(Call<RelationshipMore> call, Throwable t) {
                if(ISAP_Utils.isProgressDialogVisible())
                    ISAP_Utils.dismissProgressDialog();
                System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout moreView=getActivity().findViewById(R.id.moreLayout);
        moreView.setVisibility(View.GONE);
         saveView=getActivity().findViewById(R.id.saveLayout);
        RelativeLayout relativeLayout=getActivity().findViewById(R.id.relativeLayout);
        if(ISAP_Utils.userAccess==true){
            saveView.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setVisibility(View.GONE);
            saveView.setVisibility(View.GONE);
        }

         /*if(ISAP_Utils.userAccess==true)
        saveView.setVisibility(View.VISIBLE);
         else saveView.setVisibility(View.GONE); //need to enable and change text in next sprint*/
        TextView newText=getActivity().findViewById(R.id.newText);
        newText.setText("New Relationship");
        ISAP_Utils.currentPage=8;
        clientId= ISAP_Utils.clientID;
        intranetId=ISAP_Utils.LoggedInuserEmail;
        fetchRelationshipMore(clientId,intranetId);
        IsapMenuActivity.nextText.setText("");
        IsapMenuActivity.nextBtn.setVisibility(getView().INVISIBLE);
        View view = getActivity().findViewById(R.id.geo_market);
        view.setVisibility(view.GONE);
        return inflater.inflate(R.layout.fragment_relationship_more, container, false);
    }


}
