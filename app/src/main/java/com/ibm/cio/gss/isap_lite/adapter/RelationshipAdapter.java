package com.ibm.cio.gss.isap_lite.adapter;

/**
 * Created by Rashmi on 4/25/18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.NewRelationshipActivity;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
//import com.ibm.cio.gss.isap_lite.model.ROLES_ARRAY;
import com.ibm.cio.gss.isap_lite.model.RelationshipExpandList;
import com.ibm.cio.gss.isap_lite.model.SHIPS_ARRAY;
import com.ibm.cio.gss.isap_lite.model.ACTION_PLANS;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.Relationship_DelObj;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.MyViewHolder> {

    private SHIPS_ARRAY[] relationShipList;

    private SHIPS_ARRAY relationshipObj;

    public Context ctx;

    private ISAPService IsapService;

    private RelationshipExpandList relExapndList;

    private LinearLayout expandListView;

    private Gson gson;

    private AlertDialog.Builder alertDialogBuilder;

    private AlertDialog alertDialog;

    private Relationship_DelObj relDeleteModel;

    private String selectedContactName;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView relExecName, relBUName, relExecPosition, relAssement, primaryContact, actionPlan, initiativeCount,

        leadName, leadValue, contCount, firstCont, iniCount, firstIni, relBunit, linkedOptyValue, linkedOptyCount, closDataRange,

        holderBarColorTextView, expandListBarTextView;

        public SwipeLayout swipeLayout;

        public TextView Delete;

        public TextView Edit;

        private TextView title;

        private TextView genre;

        private TextView year;

        private LinearLayout mLinearHead;

        private View listView, showContactsPopupView, showActionPlanPopupView, showInitiativesPopupView, subItem, showExpandView, showExpandView2, showExpandView3, showExpandView4,

        showCollapseView, showCollapseView2, showCollapseView3, showCollapseView4, initiativeLabelGreyLine, initiativeListGreyLine;

        private TextView initiativeLabel;


        public MyViewHolder(View view) {


            super(view);

            listView = view.findViewById(R.id.item_title);

            subItem = view.findViewById(R.id.sub_item);

            // IBMContactLabel = view.findViewById(R.id.IBMContactLabel);

            // IBMContactLabel = subItem.findViewById(R.id.IBMContactLabel);

            // ibmContactsGreyLine = view.findViewById(R.id.ibmContactsGreyLine);

            // ibmContactsListGreyLine = view.findViewById(R.id.ibmContactsListGreyLine);

            //  actionPlanLevel = view.findViewById(R.id.actionPlanLevel);

            initiativeLabel = view.findViewById(R.id.initiativeLabel);

            initiativeLabelGreyLine = view.findViewById(R.id.initiativeLabelGreyLine);

            initiativeListGreyLine = view.findViewById(R.id.initiativeListGreyLine);


            relExecName = (TextView) view.findViewById(R.id.exe_name);

            relBUName = (TextView) view.findViewById(R.id.exe_bu_name);

            relExecPosition = (TextView) view.findViewById(R.id.exe_postion);

            relAssement = (TextView) view.findViewById(R.id.rel_assement);

            showContactsPopupView = (View) view.findViewById(R.id.show_contacts);

            showActionPlanPopupView = (View) view.findViewById(R.id.show_action_plan);

            showInitiativesPopupView = (View) view.findViewById(R.id.show_initiatives);

            showExpandView = (View) view.findViewById(R.id.show_expand);

            showExpandView2 = (View) view.findViewById(R.id.show_expand2);

            showExpandView3 = (View) view.findViewById(R.id.show_expand3);

            showExpandView4 = (View) view.findViewById(R.id.show_expand4);

            showCollapseView = (View) view.findViewById(R.id.show_collaps6);

            showCollapseView2 = (View) view.findViewById(R.id.show_collaps8);

            showCollapseView3 = (View) view.findViewById(R.id.show_collaps7);

            showCollapseView4 = (View) view.findViewById(R.id.show_collaps9);


            primaryContact = (TextView) view.findViewById(R.id.primary_contact);

            actionPlan = (TextView) view.findViewById(R.id.action_plan);

            initiativeCount = (TextView) view.findViewById(R.id.initiative_count);

            leadName = (TextView) view.findViewById(R.id.lead);

            leadValue = (TextView) view.findViewById(R.id.lead_value);

            contCount = (TextView) view.findViewById(R.id.contact_count);

            firstCont = (TextView) view.findViewById(R.id.first_contact);

            firstIni = (TextView) view.findViewById(R.id.first_initiative);

            linkedOptyCount = (TextView) view.findViewById(R.id.linked_oppty_count);

            relBunit = (TextView) view.findViewById(R.id.rel_business_unit);

            linkedOptyValue = (TextView) view.findViewById(R.id.linked_oppty_value);

            closDataRange = (TextView) view.findViewById(R.id.date_range);

            holderBarColorTextView = (TextView) view.findViewById(R.id.holderexpandListBarColor);

            expandListBarTextView = (TextView) view.findViewById(R.id.expandListBarColor);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.rel_swipe);

            Delete = (TextView) itemView.findViewById(R.id.delete_rel);

            Edit = (TextView) itemView.findViewById(R.id.edit_rel);

            mLinearHead = itemView.findViewById(R.id.ll_head);

        }

    }


    public void fetchRelationshipExpandList(final MyViewHolder holder, final int position) {


        ISAP_Utils.showISAPProgressDialog(ctx, "Fetching Relationship details", false);

        IsapService = APiUtils.getUserService();

        Call call = IsapService.getRelationshipExpandList(ISAP_Utils.clientID, relationShipList[position].getCLIENT_EXEC_KEY());

        call.enqueue(new Callback<RelationshipExpandList>() {

            @Override

            public void onResponse(Call<RelationshipExpandList> call, Response<RelationshipExpandList> response) {

                relExapndList = response.body();

                gson = new Gson();

                String data = gson.toJson(relExapndList);

                System.out.println("" + ISAP_Constants.RELATIONSHIP_EXAPANDLIST + " :- " + data);

                try {

                    holder.primaryContact.setText(relExapndList.getIBM_PRIMARY_CONTACT());

                    holder.actionPlan.setText(relExapndList.getPLANS_ARRAY()[0].getVALUE());

                    holder.leadName.setText(relExapndList.getINITIATIVE_ARRAY()[0].getINITIATIVE_DETAIL().getINITIATIVE_LEAD_NAME());

                    holder.leadValue.setText(relExapndList.getINITIATIVE_ARRAY()[0].getINITIATIVE_DETAIL().getINITIATVIE_VALUE());

                    holder.contCount.setText(Integer.toString(relExapndList.getIBM_CONTACT_ARRAY().length));


                    if (relExapndList.getIBM_CONTACT_ARRAY().length >= 1) {

                        holder.firstCont.setText(relExapndList.getIBM_CONTACT_ARRAY()[0].getVALUE());

                    }


                    if (relExapndList.getINITIATIVE_ARRAY().length >= 1) {

                        if (relExapndList.getINITIATIVE_ARRAY()[0].getVALUE().toLowerCase().equalsIgnoreCase("none assigned") && relExapndList.getINITIATIVE_ARRAY().length == 1) {

                            holder.firstIni.setText("There are no initiatives");

                            holder.initiativeCount.setText("0");

                        } else {

                            holder.firstIni.setText(relExapndList.getINITIATIVE_ARRAY()[0].getVALUE());

                            holder.initiativeCount.setText(Integer.toString(relExapndList.getINITIATIVE_ARRAY().length));

                        }

                    } else {

                        holder.firstIni.setVisibility(View.GONE);

                        holder.initiativeLabel.setVisibility(View.GONE);

                        holder.showInitiativesPopupView.setVisibility(View.GONE);

                        holder.initiativeListGreyLine.setVisibility(View.GONE);

                        holder.initiativeLabelGreyLine.setVisibility(View.GONE);

                    }


                    holder.linkedOptyCount.setText(relExapndList.getINITIATIVE_ARRAY()[0].getINITIATIVE_DETAIL().getOPPTY_COUNT());

                    holder.linkedOptyValue.setText(relExapndList.getINITIATIVE_ARRAY()[0].getINITIATIVE_DETAIL().getOPPTY_VAL());

                    holder.relBunit.setText(relExapndList.getINITIATIVE_ARRAY()[0].getINITIATIVE_DETAIL().getBUSINESS_UNIT());

                    holder.closDataRange.setText(relExapndList.getINITIATIVE_ARRAY()[0].getINITIATIVE_DETAIL().getCLOSING_DATA_RANGE());

                } catch (Exception ex) {

//                    Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

                }


                ISAP_Utils.dismissProgressDialog();

            }

            @Override

            public void onFailure(Call<RelationshipExpandList> call, Throwable t) {

                if (ISAP_Utils.isProgressDialogVisible())

                    ISAP_Utils.dismissProgressDialog();

            }

        });

    }

    public RelationshipAdapter(SHIPS_ARRAY[] rel_list) {

        this.relationShipList = rel_list;

    }

    public void deleteRelationship(String relationshipId, final Context v, final int adapterPosition) {

        IsapService = APiUtils.getUserService();

        ISAP_Utils.showISAPProgressDialog(v, ISAP_Constants.DELETE_RELATIONSHIP_INPROGRESS, false);

        relDeleteModel = new Relationship_DelObj();

        relDeleteModel.setIntranetId(ISAP_Utils.LoggedInuserEmail);

        relDeleteModel.setClientExecKey(relationshipId);

//        LogUtils.printLog(,"deleteRelationship","deleteRelationship for id :"+gson.toJson(relDeleteModel).toString());

        Call call = IsapService.deleteRelationship(relDeleteModel);

        call.enqueue(new Callback<GoalResponseObj>() {

            @Override

            public void onResponse(Call<GoalResponseObj> call, Response<GoalResponseObj> response) {

                GoalResponseObj resObj = response.body();

                ISAP_Utils.dismissProgressDialog();


                try {

                    if (resObj.getFlag().equalsIgnoreCase("true")) {

                        Toast.makeText(v, ISAP_Constants.DELETE_RELATIONSHIP, Toast.LENGTH_SHORT).show();

                        List<SHIPS_ARRAY> list = new ArrayList<SHIPS_ARRAY>(Arrays.asList(relationShipList));

                        list.remove(adapterPosition);

                        relationShipList = list.toArray(new SHIPS_ARRAY[0]);

                        notifyItemRemoved(adapterPosition);

                        notifyItemRangeChanged(adapterPosition, relationShipList.length);

                    } else

                        Toast.makeText(v, ISAP_Constants.DELETE_RELATIONSHIP_FAILURE, Toast.LENGTH_SHORT).show();


                } catch (Exception e) {

                    e.printStackTrace();

                }


            }


            @Override

            public void onFailure(Call<GoalResponseObj> call, Throwable t) {

                ISAP_Utils.dismissProgressDialog();

            }

        });

    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.relationship_list, parent, false);

        ctx = parent.getContext();

        return new MyViewHolder(itemView);

    }


    @Override

    public void onBindViewHolder(final MyViewHolder holder,int position) {

        try {

            holder.setIsRecyclable(true);

            relationshipObj = null;

            relationshipObj = relationShipList[holder.getAdapterPosition()];

            holder.relExecName.setText(relationshipObj.getCLIENT_EXEC_NM());

            holder.relBUName.setText(relationshipObj.getREL_TITLE());

            holder.relAssement.setText(relationshipObj.getREL_ASSESSMENT());

            holder.relExecPosition.setText(relationshipObj.getEXEC_POSITION_CD());


            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wraper));

            holder.swipeLayout.setLeftSwipeEnabled(false);

            if (ISAP_Utils.userAccess == false) {

                holder.swipeLayout.setRightSwipeEnabled(false);

            }

            if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("PMTR"))

            {

                holder.relAssement.setTextColor(Color.parseColor("#433A8E"));

                holder.holderBarColorTextView.setBackgroundColor(Color.parseColor("#433A8E"));

                holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#433A8E"));

            } else if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("DTCT"))

            {

                holder.relAssement.setTextColor(Color.parseColor("#456CA5"));

                holder.holderBarColorTextView.setBackgroundColor(Color.parseColor("#456CA5"));

                holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#456CA5"));

            } else if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("NTRL")) {

                holder.relAssement.setTextColor(Color.parseColor("#777677"));

                holder.holderBarColorTextView.setBackgroundColor(Color.parseColor("#777677"));

                holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#777677"));

            } else

            {

                holder.relAssement.setTextColor(Color.parseColor("#a5a5a5"));

                holder.holderBarColorTextView.setBackgroundColor(Color.parseColor("#a5a5a5"));

                holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#a5a5a5"));

            }


            if (relationShipList[holder.getAdapterPosition()].isExpanded()) {

                holder.subItem.setVisibility(View.VISIBLE);

                if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("PMTR"))

                {

                    holder.showExpandView.setVisibility(View.GONE);

                    holder.showExpandView2.setVisibility(View.GONE);

                    holder.showExpandView4.setVisibility(View.GONE);

                    holder.showExpandView3.setVisibility(View.GONE);


                    holder.showCollapseView.setVisibility(View.GONE);

                    holder.showCollapseView2.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.VISIBLE);


                    holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#456CA5"));

                } else if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("DTCT"))

                {

                    holder.showExpandView.setVisibility(View.GONE);

                    holder.showExpandView2.setVisibility(View.GONE);

                    holder.showExpandView4.setVisibility(View.GONE);

                    holder.showExpandView3.setVisibility(View.GONE);

                    holder.showCollapseView2.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.GONE);

                    holder.showCollapseView.setVisibility(View.VISIBLE);

                    holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#456CA5"));

                } else if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("NTRL"))

                {

                    holder.showExpandView.setVisibility(View.GONE);

                    holder.showExpandView2.setVisibility(View.GONE);

                    holder.showExpandView4.setVisibility(View.GONE);

                    holder.showExpandView3.setVisibility(View.GONE);

                    holder.showCollapseView2.setVisibility(View.VISIBLE);

                    holder.showCollapseView.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.GONE);


                    holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#777677"));

                } else

                {

                    holder.showExpandView.setVisibility(View.GONE);

                    holder.showExpandView2.setVisibility(View.GONE);

                    holder.showExpandView4.setVisibility(View.GONE);

                    holder.showExpandView3.setVisibility(View.GONE);

                    holder.showCollapseView2.setVisibility(View.GONE);

                    holder.showCollapseView.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.VISIBLE);


                    holder.expandListBarTextView.setBackgroundColor(Color.parseColor("#a5a5a5"));

                }

            } else {

                holder.subItem.setVisibility(View.GONE);

                if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("PMTR"))

                {

                    holder.showExpandView.setVisibility(View.GONE);

                    holder.showExpandView2.setVisibility(View.GONE);

                    holder.showExpandView4.setVisibility(View.VISIBLE);

                    holder.showExpandView3.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.GONE);

                    holder.showCollapseView2.setVisibility(View.GONE);

                    holder.showCollapseView.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.GONE);

                } else if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("DTCT"))

                {

                    holder.showExpandView.setVisibility(View.VISIBLE);

                    holder.showExpandView2.setVisibility(View.GONE);

                    holder.showExpandView4.setVisibility(View.GONE);

                    holder.showExpandView3.setVisibility(View.GONE);

                    holder.showCollapseView.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.GONE);

                    holder.showCollapseView2.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.GONE);

                } else if (relationshipObj.getASSESMENT_CD().equalsIgnoreCase("NTRL"))

                {

                    holder.showExpandView.setVisibility(View.GONE);

                    holder.showExpandView2.setVisibility(View.VISIBLE);

                    holder.showExpandView4.setVisibility(View.GONE);

                    holder.showExpandView3.setVisibility(View.GONE);

                    holder.showCollapseView2.setVisibility(View.GONE);

                    holder.showCollapseView.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.GONE);

                } else

                {

                    holder.showExpandView.setVisibility(View.GONE);

                    holder.showExpandView2.setVisibility(View.GONE);

                    holder.showExpandView4.setVisibility(View.GONE);

                    holder.showExpandView3.setVisibility(View.VISIBLE);

                    holder.showCollapseView2.setVisibility(View.GONE);

                    holder.showCollapseView.setVisibility(View.GONE);

                    holder.showCollapseView4.setVisibility(View.GONE);

                    holder.showCollapseView3.setVisibility(View.GONE);

                }

            }


            holder.showContactsPopupView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {


                    ArrayList<String> topList = new ArrayList<String>();

                    try {

                        for (int j = 0; j < relExapndList.getIBM_CONTACT_ARRAY().length; j++) {

                            topList.add(relExapndList.getIBM_CONTACT_ARRAY()[j].getVALUE());

                        }

                        System.out.println("data:" + topList);

                        alertDialogBuilder = new AlertDialog.Builder(ctx);

                        alertDialog = alertDialogBuilder.create();

                        final String[] stringArray = topList.toArray(new String[topList.size()]);

                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View convertView = (View) inflater.inflate(R.layout.citi_group, null);

                        alertDialog.setView(convertView);

                        ListView lv = (ListView) convertView.findViewById(R.id.citigroup);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, stringArray);

                        lv.setAdapter(adapter);

                        alertDialog.show();

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

                        {

                            @Override

                            public void onItemClick(AdapterView<?> parent, View view,

                                                    int adapterPosition, long id) {

                                alertDialog.dismiss();

                                holder.firstCont.setText(relExapndList.getIBM_CONTACT_ARRAY()[adapterPosition].getVALUE());

                                selectedContactName = relExapndList.getIBM_CONTACT_ARRAY()[adapterPosition].getVALUE();


                                String[] topList = new String[0];


                                for (int k = 0; k < relExapndList.getCONTACT_ACTION_PLAN().length; k++) {


                                    if (relExapndList.getCONTACT_ACTION_PLAN()[k].getVALUE().equalsIgnoreCase(selectedContactName)) {

                                        int size=relExapndList.getCONTACT_ACTION_PLAN()[k].getACTION_PLANS().length;
                                        topList=new String[size];
                                        for(int i=0;i<size;i++){
                                            topList[i]=relExapndList.getCONTACT_ACTION_PLAN()[k].getACTION_PLANS()[i].getVALUE();
                                        }
//

                                    }


                                }


                                holder.actionPlan.setText(topList[0]);

                            }

                        });

                    } catch (Exception ex) {

//                    Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

//                    LogUtils.printLog(NewGoalActivity.this, "MarketMyPlan response", ex.getMessage());

                    }

                }

            });

            holder.showActionPlanPopupView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {


                    try {

                        String[] topList = new String[0];


                        for (int k = 0; k < relExapndList.getCONTACT_ACTION_PLAN().length; k++) {


                            if (relExapndList.getCONTACT_ACTION_PLAN()[k].getVALUE().equalsIgnoreCase(selectedContactName)) {

                                int size=relExapndList.getCONTACT_ACTION_PLAN()[k].getACTION_PLANS().length;
                                topList=new String[size];
                                for(int i=0;i<size;i++){
                                    topList[i] = relExapndList.getCONTACT_ACTION_PLAN()[k].getACTION_PLANS()[i].getVALUE();
                                }


//                                topList = Arrays.copyOf(relExapndList.getCONTACT_ACTION_PLAN()[k].getACTION_PLANS(), relExapndList.getCONTACT_ACTION_PLAN()[k].getACTION_PLANS().length);


                            }


                        }

                        //System.out.println("data:"+topList);

                        alertDialogBuilder = new AlertDialog.Builder(ctx);

                        alertDialog = alertDialogBuilder.create();

                        //String [] stringArray = new String[topList.length];

                        // stringArray = topList;

                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View convertView = (View) inflater.inflate(R.layout.citi_group, null);

                        alertDialog.setView(convertView);

                        ListView lv = (ListView) convertView.findViewById(R.id.citigroup);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, topList);

                        lv.setAdapter(adapter);

                        alertDialog.show();

                        final String[] finalStringArray = topList;

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

                        {

                            @Override

                            public void onItemClick(AdapterView<?> parent, View view,

                                                    int adapterPosition, long id) {


                                alertDialog.dismiss();

                                holder.actionPlan.setText(finalStringArray[adapterPosition]);

                            }

                        });


                    } catch (Exception e) {


                    }


                }

            });

            holder.showInitiativesPopupView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {

                    try {

                        ArrayList<String> topList = new ArrayList<String>();

                        for (int j = 0; j < relExapndList.getINITIATIVE_ARRAY().length; j++) {

                            topList.add(relExapndList.getINITIATIVE_ARRAY()[j].getVALUE());

                        }

                        System.out.println("data:" + topList);

                        alertDialogBuilder = new AlertDialog.Builder(ctx);

                        alertDialog = alertDialogBuilder.create();

                        final String[] stringArray = topList.toArray(new String[topList.size()]);

                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View convertView = (View) inflater.inflate(R.layout.citi_group, null);

                        alertDialog.setView(convertView);

                        ListView lv = (ListView) convertView.findViewById(R.id.citigroup);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, stringArray);

                        lv.setAdapter(adapter);

                        alertDialog.show();

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

                        {

                            @Override

                            public void onItemClick(AdapterView<?> parent, View view,

                                                    int adapterPosition, long id) {


                                alertDialog.dismiss();

                                holder.firstIni.setText(relExapndList.getINITIATIVE_ARRAY()[adapterPosition].getVALUE());

                                holder.linkedOptyCount.setText(relExapndList.getINITIATIVE_ARRAY()[adapterPosition].getINITIATIVE_DETAIL().getOPPTY_COUNT());

                                holder.linkedOptyValue.setText(relExapndList.getINITIATIVE_ARRAY()[adapterPosition].getINITIATIVE_DETAIL().getOPPTY_VAL());

                                holder.relBunit.setText(relExapndList.getINITIATIVE_ARRAY()[adapterPosition].getINITIATIVE_DETAIL().getBUSINESS_UNIT());

                                holder.leadName.setText(relExapndList.getINITIATIVE_ARRAY()[adapterPosition].getINITIATIVE_DETAIL().getINITIATIVE_LEAD_NAME());

                                holder.leadValue.setText(relExapndList.getINITIATIVE_ARRAY()[adapterPosition].getINITIATIVE_DETAIL().getINITIATVIE_VALUE());

                                holder.closDataRange.setText(relExapndList.getINITIATIVE_ARRAY()[adapterPosition].getINITIATIVE_DETAIL().getCLOSING_DATA_RANGE());

                            }

                        });

                    } catch (Exception ex) {

//                    Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

                    }

                }


            });

            holder.showCollapseView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {


                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);

                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView.setVisibility(View.GONE);

                        holder.showCollapseView.setVisibility(View.VISIBLE);

                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());

                    } else {

                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView.setVisibility(View.VISIBLE);

                        holder.showCollapseView.setVisibility(View.GONE);

                    }

                }

            });

            holder.showCollapseView2.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {

                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);

                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView2.setVisibility(View.GONE);

                        holder.showCollapseView2.setVisibility(View.VISIBLE);

                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());

                    } else {

                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView2.setVisibility(View.VISIBLE);

                        holder.showCollapseView2.setVisibility(View.GONE);

                    }

                }

            });

            holder.showCollapseView4.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {

                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);

                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView4.setVisibility(View.GONE);

                        holder.showCollapseView4.setVisibility(View.VISIBLE);

                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());

                    } else {

                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView4.setVisibility(View.VISIBLE);

                        holder.showCollapseView4.setVisibility(View.GONE);

                    }


                }

            });


            holder.showCollapseView3.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {


                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);


                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView3.setVisibility(View.GONE);

                        holder.showCollapseView3.setVisibility(View.VISIBLE);


                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());


                    } else {


                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView3.setVisibility(View.VISIBLE);

                        holder.showCollapseView3.setVisibility(View.GONE);


                    }


                }

            });


            holder.showExpandView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(final View v) {


                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);


                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView.setVisibility(View.GONE);

                        holder.showCollapseView.setVisibility(View.VISIBLE);


                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());


                    } else {


                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView.setVisibility(View.VISIBLE);

                        holder.showCollapseView.setVisibility(View.GONE);


                    }


                    //holder.subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);


                }


            });


            holder.showExpandView2.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(final View v) {


                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);


                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView2.setVisibility(View.GONE);

                        holder.showCollapseView2.setVisibility(View.VISIBLE);


                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());


                    } else {


                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView2.setVisibility(View.VISIBLE);

                        holder.showCollapseView2.setVisibility(View.GONE);


                    }


                    //holder.subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);


                }


            });


            holder.showExpandView4.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(final View v) {


                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);


                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView4.setVisibility(View.GONE);

                        holder.showCollapseView4.setVisibility(View.VISIBLE);


                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());


                    } else {


                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView4.setVisibility(View.VISIBLE);

                        holder.showCollapseView4.setVisibility(View.GONE);


                    }


                    //holder.subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);


                }


            });


            holder.showExpandView3.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(final View v) {


                    boolean expanded = relationShipList[holder.getAdapterPosition()].isExpanded();

                    relationShipList[holder.getAdapterPosition()].setExpanded(!expanded);


                    if (!expanded) {

                        holder.subItem.setVisibility(View.VISIBLE);

                        holder.showExpandView3.setVisibility(View.GONE);

                        holder.showCollapseView3.setVisibility(View.VISIBLE);


                        fetchRelationshipExpandList(holder, holder.getAdapterPosition());


                    } else {


                        holder.subItem.setVisibility(View.GONE);

                        holder.showExpandView3.setVisibility(View.VISIBLE);

                        holder.showCollapseView3.setVisibility(View.GONE);


                    }


                }


            });


            holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {

                @Override

                public void onStartOpen(SwipeLayout layout) {
                }

                @Override

                public void onOpen(SwipeLayout layout) {

                    holder.mLinearHead.setVisibility(View.INVISIBLE);

                }

                @Override

                public void onStartClose(SwipeLayout layout) {
                }

                @Override

                public void onClose(SwipeLayout layout) {

                    holder.mLinearHead.setVisibility(View.VISIBLE);

                }

                @Override

                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                }

                @Override

                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                }

            });


            holder.Edit.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {


                    System.out.println("Edit item On Click Listner");


                    Intent intent = new Intent(ctx, NewRelationshipActivity.class);

                    //intent.putextra("your_extra","your_class_value");

                    intent.putExtra("relationshipId", relationShipList[holder.getAdapterPosition()].getCLIENT_EXEC_KEY());

                    ctx.startActivity(intent);


                }

            });


            holder.Delete.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                    builder.setMessage(ISAP_Constants.DELETE_RELATIONSHIP_ALERT);

                    builder.setCancelable(false);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override

                        public void onClick(DialogInterface dialog, int which) {

                            deleteRelationship(relationShipList[holder.getAdapterPosition()].getCLIENT_EXEC_KEY(), ctx, holder.getAdapterPosition());

                            //List<SHIPS_ARRAY> list = new ArrayList<SHIPS_ARRAY>(Arrays.asList(relationShipList));

                            //list.remove(holder.getAdapterPosition());

                            //relationShipList = list.toArray(new SHIPS_ARRAY[0]);

                            //notifyItemRemoved(holder.getAdapterPosition());

                            //notifyItemRangeChanged(holder.getAdapterPosition(), relationShipList.length);


                        }

                    });


                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override

                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(ctx, "You've changed your mind to delete  record", Toast.LENGTH_SHORT).show();

                            dialog.cancel();

                        }

                    });


                    builder.show();


                }

            });

        } catch (Exception e) {

            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

        }


    }


    @Override

    public int getItemCount() {

        if (relationShipList != null)

            return relationShipList.length;

        else return 0;
    }

}
