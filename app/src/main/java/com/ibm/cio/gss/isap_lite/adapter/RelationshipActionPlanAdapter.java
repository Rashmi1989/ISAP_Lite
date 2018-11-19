package com.ibm.cio.gss.isap_lite.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.InitiativeContactModel;
import com.ibm.cio.gss.isap_lite.model.OPPTS;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.ACTION_PLANS;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.NewRelationshipModel;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ibm.cio.gss.isap_lite.activity.ClientsActivity.IsapService;

/**
 * Created by Rashmi on 5/18/18.
 */

public class RelationshipActionPlanAdapter extends RecyclerView.Adapter<RelationshipActionPlanAdapter.MyViewHolder> {

    private List<ACTION_PLANS> action_plansList;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private ACTION_PLANS actionPlansDetail;
    private List<InitiativeContactModel> contactList;
    private InitiativeContactModel contactInfo;
    public Context ctx;
    public View customView;
    private Gson gson;
    RecyclerView contactsRecyclerView;
    InitiativeLeadContactsAdapter initiativeLeadContactsAdapter;
    Dialog contactsDialog;
    int adapterPostion = 0;
    private NewRelationshipModel newRelationshipModel;
    private boolean isEditEnable;
    private Spinner spinner;
    public LinearLayout rel_status;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView serialNumber, actionPlanIBMContactName, targetDate;
        public EditText planNameEditText;
        public View deletePlan, actionPlanShowContacts;
        public MyViewHolder(View view) {
            super(view);
            serialNumber = (TextView) view.findViewById(R.id.actionPlanSerialNo);
            actionPlanIBMContactName = (TextView) view.findViewById(R.id.actionPlanIBMContactName);
            planNameEditText = (EditText) view.findViewById(R.id.actionPlanName);
            deletePlan = (View) view.findViewById(R.id.actionPlan_delete_icon);
            actionPlanShowContacts = (View) view.findViewById(R.id.actionPlanShowContacts);
            targetDate = (TextView) view.findViewById(R.id.rel_actionPlan_date);
            rel_status = (LinearLayout) view.findViewById(R.id.rel_status);
            spinner = new Spinner(view.getContext());
        }
    }
    private List<String> convertStatusObjectToString() {
        List<String> stringList = new ArrayList<String>();
        try{
        for (int i = 0; i < newRelationshipModel.getDATA().getSTATUS().length; i++) {
            stringList.add(newRelationshipModel.getDATA().getSTATUS()[i].getNAME());
        }
        }catch (Exception e){
            //Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void showProgressSpinner(Context context, List<String> statusList, final int adapterPositionValue) {
        try{
        spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        spinner.setBackgroundColor(Color.TRANSPARENT);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.spinner_layout, statusList);
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_layout);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        if (isEditEnable) {
            int spinnerPosition = arrayAdapter.getPosition(newRelationshipModel.getDEFAULT_DATA().getACTION_PLANS()[adapterPositionValue].getSTATUS_VAL());
            spinner.setSelection(spinnerPosition);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    action_plansList.get(adapterPositionValue).setSTATUS_VAL(newRelationshipModel.getDATA().getSTATUS()[position].getNAME());
                    action_plansList.get(adapterPositionValue).setSTATUS(newRelationshipModel.getDATA().getSTATUS()[position].getKEY());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
            spinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    InputMethodManager inputMethodManager  = (InputMethodManager)ctx.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(inputMethodManager.isActive()){/*key board open*/
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return false;
                }
            });
            // Add Spinner to LinearLayout
        if (rel_status != null) {
            rel_status.removeAllViews();
            rel_status.addView(spinner);
        }
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    public RelationshipActionPlanAdapter(List<ACTION_PLANS> action_plansList, NewRelationshipModel newRelationshipModel, boolean isEditingFlag) {
        try{
            this.action_plansList = action_plansList;
            this.newRelationshipModel = newRelationshipModel;
            this.isEditEnable = isEditingFlag;
        }catch (Exception e){

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.relationship_actionplan, parent, false);
        ctx = parent.getContext();

        return new MyViewHolder(itemView);
    }


    private void getProfiledata(String editTextFieldValue) {
        IsapService = APiUtils.getProfileInfoService();
        Call call = IsapService.getProfileInfo("optimized_search", editTextFieldValue);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                ISAP_Utils.dismissProgressDialog();
                gson = new Gson();
                try {

                    if (contactList != null) {
                        contactList.clear();
                    } else {
                        contactList = new ArrayList<InitiativeContactModel>();
                    }
                    String data = response.body().string();
                    JsonObject jsonObject = (new JsonParser()).parse(data).getAsJsonObject();
                    JsonArray array = (JsonArray) jsonObject.get("results");
                    int count = 0;
                    for (JsonElement item : array) {
                        contactInfo = new InitiativeContactModel();
                        if ((item.getAsJsonObject().get("preferredIdentity")) != null) {
                            contactInfo.setEmailId(item.getAsJsonObject().get("preferredIdentity").getAsString());
                        } else {
                            contactInfo.setEmailId("");
                        }
                        if ((item.getAsJsonObject().get("nameFull")) != null) {
                            contactInfo.setName(item.getAsJsonObject().get("nameFull").getAsString());
                        } else {
                            contactInfo.setName("");
                        }
                        if ((item.getAsJsonObject().get("role")) != null) {
                            contactInfo.setRole(item.getAsJsonObject().get("role").getAsString());
                        } else {
                            contactInfo.setRole("");
                        }
                        if ((item.getAsJsonObject().get("uid")) != null) {
                            contactInfo.setCnum(item.getAsJsonObject().get("uid").getAsString());
                        } else {
                            contactInfo.setCnum("");
                        }
                        contactList.add(contactInfo);
                    }
                    showContactsRecyclerView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }catch (Exception e){
//                    Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
            }
        });
    }
    private void showContactsRecyclerView() {
        try{
        initiativeLeadContactsAdapter = new InitiativeLeadContactsAdapter(contactList, "actionPlan");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        contactsRecyclerView.setLayoutManager(mLayoutManager);
        contactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        contactsRecyclerView.setAdapter(initiativeLeadContactsAdapter);
        ISAP_Utils.dismissProgressDialog();
        initiativeLeadContactsAdapter.notifyDataSetChanged();
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try{
        holder.setIsRecyclable(true);
        actionPlansDetail = action_plansList.get(holder.getAdapterPosition());
        holder.serialNumber.setText(Integer.toString(holder.getAdapterPosition() + 1) + ".");
        if (action_plansList.get(holder.getAdapterPosition()).getIBM_CONTACT().length() > 1) {
            holder.actionPlanIBMContactName.setText(action_plansList.get(holder.getAdapterPosition()).getIBM_CONTACT());
        }else{
            holder.actionPlanIBMContactName.setText("");
        }
        if (isEditEnable) {
            holder.targetDate.setText(actionPlansDetail.getTARGET_DATE());
            holder.planNameEditText.setText(actionPlansDetail.getACTION_PLAN_TXT());
        } else{
            holder.targetDate.setText(newRelationshipModel.getDEFAULT_DATA().getACTION_PLANS()[0].getTARGET_DATE());
            //holder.targetDate.setText(actionPlansDetail.getTARGET_DATE());
            holder.planNameEditText.setText(actionPlansDetail.getACTION_PLAN_TXT());
        }
        action_plansList.get(holder.getAdapterPosition()).setACTION_PLAN_TXT(holder.planNameEditText.getText().toString());
        action_plansList.get(holder.getAdapterPosition()).setTARGET_DATE(holder.targetDate.getText().toString());
        holder.planNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) { }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {}
            @Override
            public void afterTextChanged(Editable arg0) {
                action_plansList.get(holder.getAdapterPosition()).setACTION_PLAN_TXT(arg0.toString());
            }
        });
            final View finalView = holder.itemView;
            holder.planNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (holder.getAdapterPosition() == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager) finalView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(holder.planNameEditText.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });
            holder.deletePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                   // action_plansList.remove(holder.getAdapterPosition());
                    Intent intent = new Intent();
                    intent.setAction("RelationshipDataRefresh");
                    intent.putExtra("position", Integer.toString(holder.getAdapterPosition()));
                    intent.putExtra("type", "actionPlanDelete");
                    LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
                    //notifyItemRemoved(holder.getAdapterPosition());
                    //notifyItemRangeChanged(holder.getAdapterPosition(), action_plansList.size());
                }catch (Exception e){
//                    Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

                }

            }
        });
        holder.actionPlanShowContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                try{
                    contactsDialog = new Dialog(ctx);
                    contactsDialog.setContentView(R.layout.contact_ibm);
                    contactsDialog.show();
                    contactsRecyclerView = (RecyclerView) contactsDialog.findViewById(R.id.contactsRecycleView);
                    adapterPostion = holder.getAdapterPosition();
                    TextView cancelEditText = (TextView) contactsDialog.findViewById(R.id.cancelEditText);
                    final EditText contactName = (EditText) contactsDialog.findViewById(R.id.contactsSearchEditText);
                    LocalBroadcastManager.getInstance(ctx).registerReceiver(mMessageReceiver,
                            new IntentFilter("ActionPlanRefresh"));
                    contactName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        }
                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                      int arg3) {
                        }
                        @Override
                        public void afterTextChanged(Editable arg0) {
                            if (arg0.length() > 2) {
                                contactList = contactList == null ? new ArrayList<InitiativeContactModel>() : contactList;
                                getProfiledata(contactName.getText().toString());
                               // InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               // imm.hideSoftInputFromWindow(contactName.getWindowToken(), 0);
                            }
                        }
                    });

                    cancelEditText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try{
                                contactName.setText("");
                                contactList.clear();
                                initiativeLeadContactsAdapter.notifyDataSetChanged();
                            }catch (Exception e){

                            }

                        }
                    });
                }catch (Exception e){
//                    Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

                }

            }

            public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try{
                    int holderPosition = Integer.parseInt(intent.getStringExtra("position"));
                    String adapterType = intent.getStringExtra("type");
                    contactsDialog.dismiss();
                    holder.actionPlanIBMContactName.setText(contactList.get(holderPosition).getName());
                    action_plansList.get(adapterPostion).setIBM_CONTACT_CNUM(contactList.get(holderPosition).getCnum());
                    action_plansList.get(adapterPostion).setIBM_CONTACT(contactList.get(holderPosition).getName());
                    notifyDataSetChanged();
                    }catch (Exception e){
//                        Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                }
            };
        });

        List<String> statusList = convertStatusObjectToString();
//        System.out.println("adapter postion is " + holder.getAdapterPosition());
        showProgressSpinner(holder.itemView.getContext(), statusList, holder.getAdapterPosition());
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(action_plansList!=null)
        return action_plansList.size();
        else return 0;

    }
}
