package com.ibm.cio.gss.isap_lite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.adapter.AddGoalInitiativeMultiselectAdapter;
import com.ibm.cio.gss.isap_lite.adapter.InitiativeLeadContactsAdapter;
import com.ibm.cio.gss.isap_lite.adapter.RelationshipActionPlanAdapter;
import com.ibm.cio.gss.isap_lite.adapter.RelatioshipIdentifyAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedInitiativeListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedRelationshipIdentityAdapter;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.model.InitiativeContactModel;
import com.ibm.cio.gss.isap_lite.model.InitiativeFields_Model;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.ACTION_PLANS;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.ActionPlan;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.Contacts;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.IDENTIFY_INITIATIVE;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.NewRelationshipModel;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.NewRelationshipRequestModel;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.NewRelationship_SaveResponseObj;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;
import com.ibm.cio.gss.isap_lite.utility.LogUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ibm.cio.gss.isap_lite.activity.ClientsActivity.IsapService;

public class NewRelationshipActivity extends Activity {
   private  LinearLayout cancelBtn;
   private  LinearLayout businessUnit_layout,position_layout,rel_assement_layout;
   private  NewRelationshipModel newRelationshipModel;
   private  Gson gson;
   private boolean isEditingFlag=false;
   private Spinner assement_spinner,bu_spinner,position_spinner;
   private String clientExecId="",positionKey="",bunitkey="",assementKey="",clientExecName="";
   private View newRelationshipView1,identityIni_icon;
   private View newRelationshipView2;
   private TextView prevTextLabel,nextTextLabel;
   private EditText executiveName;
   private TextView selectInitiativeTextView,edit_toolBar,saveRelationshipBtn;
   final   Context context = this;
   private int pageCount = 0;
   private RecyclerView recyclerView,selectedInitiativeRecyclerView;
   private List<IDENTIFY_INITIATIVE> selectedInitiativesArrayList,initiativesList;
   private RelatioshipIdentifyAdapter relatioshipIdentifyAdapter;
   private Map<Integer,Boolean> choiceMap;
   private SelectedRelationshipIdentityAdapter selectedRelationshipIdentityAdapter;
   private NewRelationshipRequestModel mSaveModel;
   private NewRelationship_SaveResponseObj saveRespObj;
    private Dialog executiveNameDialog;
    private TextView contactsLeadName;
    private TextView contactsLeadEmail;
    private TextView contactsLeadRole;
    private EditText editContactsLead;
    private RecyclerView contactsRecyclerView;
    private  LinearLayout contactsLeadLinearLayout;
    private  LinearLayout addActionPlanLinearLayout;
    private TextView cancelEditText;
    private List<InitiativeContactModel> contactList;
    private List<Contacts> contactsDataList;
    private InitiativeContactModel contactInfo;
    private InitiativeLeadContactsAdapter initiativeLeadContactsAdapter;
    private RecyclerView actionPlanRecyclerView;
    private RelationshipActionPlanAdapter relationshipActionPlanAdapter;
    private List<ACTION_PLANS> action_plansList;
    private TextView addActionPlanTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.activity_new_relationship_activity);
        //Intialise Elements/componenets
        gson=new Gson();
        cancelBtn = findViewById(R.id.newLinkOpptToolbar);
        businessUnit_layout=findViewById(R.id.bunit_layout);
        position_layout=findViewById(R.id.newRelationshipPositionLayout);
        rel_assement_layout=findViewById(R.id.newRelationshipAssessmentLayout);
        identityIni_icon=(View)findViewById(R.id.newRelationshipIdentifyInitiativeLayout_icon);
        selectedInitiativeRecyclerView = (RecyclerView) findViewById(R.id.addGoal_initiativeSelectedRecyclerView);
        assement_spinner = new Spinner(this);
        bu_spinner = new Spinner(this);
        position_spinner = new Spinner(this);
        newRelationshipView1 = (View) findViewById(R.id.relationshipPage1);
        newRelationshipView2 = (View) findViewById(R.id.relationshipPage2);
        prevTextLabel = (TextView) findViewById(R.id.relationShipPrevTextLabel);
        nextTextLabel = (TextView) findViewById(R.id.relationShipNextTextLabel);
        edit_toolBar=(TextView)findViewById((R.id.edit_relationshipTitle));
        prevTextLabel.setTextColor(Color.GRAY);
        selectedInitiativesArrayList = new ArrayList<IDENTIFY_INITIATIVE>();
        saveRelationshipBtn=(TextView)findViewById(R.id.saveRelationshipBtn);
        executiveNameDialog = new Dialog(context);
        executiveNameDialog.setContentView(R.layout.contact_ibm);
        executiveName = (EditText) findViewById(R.id.executiveName);
        addActionPlanTextView = (TextView) findViewById(R.id.addActionPlanTextView);
        actionPlanRecyclerView = (RecyclerView) findViewById(R.id.relationship_actionPlanRecyclerView);
        action_plansList = new ArrayList<ACTION_PLANS>();
        contactsDataList = new ArrayList<Contacts>();

        contactsLeadName = (TextView) executiveNameDialog.findViewById(R.id.contactsLeadName);
        contactsLeadEmail = (TextView) executiveNameDialog.findViewById(R.id.contactsLeadEmail);
        contactsLeadRole = (TextView) executiveNameDialog.findViewById(R.id.contactsLeadRole);
        //createdDate =(TextView)findViewById(R.id.createdDate);
        editContactsLead = (EditText) executiveNameDialog.findViewById(R.id.contactsSearchEditText);
        contactsRecyclerView = (RecyclerView) executiveNameDialog.findViewById(R.id.contactsRecycleView);
        contactsLeadLinearLayout = executiveNameDialog.findViewById(R.id.contactListLinearLayout);
        contactsLeadLinearLayout.setVisibility(View.INVISIBLE);
        contactsLeadLinearLayout.setVisibility(View.GONE);
        cancelEditText = (TextView) executiveNameDialog.findViewById(R.id.cancelEditText);
        addActionPlanLinearLayout = findViewById(R.id.addActionPlanLinearLayout);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("RelationshipDataRefresh"));

        addActionPlanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addActionPlanDataModel();
              //  relationshipActionPlanAdapter.notifyDataSetChanged();

            }
        });

        executiveName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    //Toast.makeText(this, "Focus Lose", Toast.LENGTH_SHORT).show();
                }else{

                }

            }
        });


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try{
                int position = Integer.parseInt(intent.getStringExtra("position"));
                String adapterType = intent.getStringExtra("type");
                if(adapterType.equalsIgnoreCase("contacts")){
                    updateExecutiveNameField(position);
                }else if(adapterType.equalsIgnoreCase("initiative")){
                    updateSelectedInitiativesList(position);
                }else if(adapterType.equalsIgnoreCase("actionPlanDelete")){
                    updateActionPlanRecyclerView(position);
                    if(action_plansList.size()<3){
                        addActionPlanLinearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }catch (Exception e){
                Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

            }
        }
    };

    private void updateActionPlanRecyclerView(int position){

        try{
            action_plansList.remove(position);
            relationshipActionPlanAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

        }
    }

    private void createActionPlanRecyclerViewUI(){
        try{
            relationshipActionPlanAdapter = new RelationshipActionPlanAdapter(action_plansList,newRelationshipModel,isEditingFlag);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            actionPlanRecyclerView.setLayoutManager(mLayoutManager);
            actionPlanRecyclerView.setItemAnimator(new DefaultItemAnimator());
            actionPlanRecyclerView.setAdapter(relationshipActionPlanAdapter);

            ISAP_Utils.dismissProgressDialog();
            relationshipActionPlanAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }
    private void updateSelectedInitiativesList(int position){
      try{
        if(initiativesList.size()!=0) {
            for (int i = 0; i < initiativesList.size(); i++) {
                if (selectedInitiativesArrayList.get(position).getKEY().equalsIgnoreCase(initiativesList.get(i).getKEY())) {
                    initiativesList.get(i).setisSelected(false);
                }
            }
        }
        relatioshipIdentifyAdapter.notifyDataSetChanged();
        selectedInitiativesArrayList.remove(position);
        selectedRelationshipIdentityAdapter.notifyDataSetChanged();
      }catch (Exception e){
         // Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
      }
    }
    private void updateExecutiveNameField(int position){
       try{
        executiveNameDialog.dismiss();
        executiveName.setText(contactList.get(position).getName());
        clientExecName=contactList.get(position).getName();
       }catch (Exception e){
           Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
       }
    }
    private void showInitiativeLeadRecyclerView(List<InitiativeContactModel> contactList) {

        try{
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editContactsLead.getWindowToken(), 0);
            initiativeLeadContactsAdapter = new InitiativeLeadContactsAdapter(contactList,"relationships");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            contactsRecyclerView.setLayoutManager(mLayoutManager);
            contactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            contactsRecyclerView.setAdapter(initiativeLeadContactsAdapter);
            ISAP_Utils.dismissProgressDialog();
            initiativeLeadContactsAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onStart()
    {
         super.onStart();
         cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish();}
        });
         displayIdentityInitiatives();
         initialiseTransitionBtn();

        try
        {
            clientExecId = getIntent().getExtras().getString("relationshipId");
        }catch (Exception e){
            //Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        if(clientExecId==null)
            clientExecId="";
        if(clientExecId.length() > 1){
            isEditingFlag = true;
            fetchRelationshipData(clientExecId);
            edit_toolBar.setText("Edit Relationship");
        }else{
            isEditingFlag = false;
            createDataListForActionPlanRecyclerView();
            edit_toolBar.setText("New Relationship");
            fetchRelationshipData("");
        }

         initialiseSaveBtn();

    }

    private void initialiseSaveBtn() {

        saveRelationshipBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                boolean status=relationshipValidation();
                populateRelationshipModel();
                if(status==true) {
                    saveRelationshipDetails();
                }
            }
        });
    }

    private void saveRelationshipDetails() {
        ISAP_Utils.showISAPProgressDialog(NewRelationshipActivity.this, ISAP_Constants.SAVE_RELATIONSHIP_INFO, false);
        IsapService = APiUtils.getUserService();
        saveRespObj=new NewRelationship_SaveResponseObj();

        LogUtils.printLog(NewRelationshipActivity.this,"saveRelationshipDetails","saveRelationshipDetails request object :"+gson.toJson(mSaveModel).toString());
        Call call = IsapService.saveRelationshipData(mSaveModel);

        call.enqueue(new Callback<NewRelationship_SaveResponseObj>() {
            @Override
            public void onResponse(Call<NewRelationship_SaveResponseObj> call, Response<NewRelationship_SaveResponseObj> response) {
               try{
                   saveRespObj =response.body();
                LogUtils.printLog(NewRelationshipActivity.this,"saveRelationshipDetails","saveRelationshipDetails response :"+gson.toJson(saveRespObj).toString());

                if(saveRespObj.getFlag().equalsIgnoreCase("true")) {
                    ISAP_Utils.currentPage=8;
                    Intent intent = new Intent(NewRelationshipActivity.this, IsapMenuActivity.class);
                    startActivity(intent);
                    ISAP_Utils.dismissProgressDialog();
                    if(saveRespObj.getFlag().equalsIgnoreCase("true")) {
                        Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.SAVE_RELATIONSHIP_DONE, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.SAVE_RELATIONSHIP_FAILURE, Toast.LENGTH_SHORT).show();

                    }
                }else{
                    ISAP_Utils.dismissProgressDialog();
                    Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.SAVE_RELATIONSHIP_FAILURE, Toast.LENGTH_SHORT).show();
                }
               }catch (Exception e){
                   Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<NewRelationship_SaveResponseObj> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.NETWORK_ISSUE, Toast.LENGTH_SHORT).show();
            }
        });
    }

   private void createDataListForActionPlanRecyclerView(){
       try{
           if(action_plansList == null || action_plansList.size()==0) {
               action_plansList = new ArrayList<ACTION_PLANS>();
               addActionPlanDataModel();
           }
       }catch (Exception e){
           Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

       }

   }

   private void addActionPlanDataModel(){
       try{
           if(action_plansList.size()<3) {
               ACTION_PLANS action_plansData = new ACTION_PLANS();
               action_plansData.setACTION_PLAN_KEY("");
               action_plansData.setACTION_PLAN_TXT("");
               action_plansData.setIBM_CONTACT("");
               action_plansData.setIBM_CONTACT_CNUM("");
               action_plansData.setIBM_CONTACT_EMAIL("");
               action_plansData.setSTATUS("");
               action_plansData.setSTATUS_VAL("");
               action_plansData.setTARGET_DATE(newRelationshipModel.getDEFAULT_DATA().getACTION_PLANS()[0].getTARGET_DATE());
               action_plansList.add(action_plansData);

               if(relationshipActionPlanAdapter!=null){
                   relationshipActionPlanAdapter.notifyDataSetChanged();
               }
               if(action_plansList.size()==3){
                   addActionPlanLinearLayout.setVisibility(View.INVISIBLE);
                   addActionPlanLinearLayout.setVisibility(View.GONE);
               }
           }
       }catch (Exception e){
//           Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
       }
   }
    private void populateRelationshipModel() {

       try{
           mSaveModel=new NewRelationshipRequestModel();
           mSaveModel.setAssesmentKey(assementKey);
           mSaveModel.setBusinessName(bunitkey);
           mSaveModel.setClientExecKey(clientExecId);
           mSaveModel.setGeoKey("-1");
           mSaveModel.setCountryKey("-1");
           mSaveModel.setRegionKey("-1");
           mSaveModel.setSurveyKey("N");
           mSaveModel.setIntranetId(ISAP_Utils.LoggedInuserEmail);
           mSaveModel.setClientId(String.valueOf(ISAP_Utils.clientID));
           String[] initiativeList=getInitiativeList();
           List<ActionPlan> actionPlans=getListOfActions();
           mSaveModel.setInitiatives(initiativeList);
           mSaveModel.setActionPlan(actionPlans);
           mSaveModel.setPositionKey(positionKey);
           mSaveModel.setClientExecName(executiveName.getText().toString());

           if(action_plansList.size()>=1){
               for(int i=0;i<action_plansList.size();i++){
                   if(contactsDataList.size()>0) {
                       for (int j = 0; j < contactsDataList.size(); j++) {
                           if (!(contactsDataList.get(j).getUID().equalsIgnoreCase(action_plansList.get(i).getIBM_CONTACT_CNUM()))) {
                               Contacts contactObject = new Contacts();
                               contactObject.setPRIMARY_CONTACT("false");
                               contactObject.setUID(action_plansList.get(i).getIBM_CONTACT_CNUM());
                               contactsDataList.add(contactObject);

                           }

                       }

                   }else{

                       Contacts contactObject = new Contacts();
                       contactObject.setPRIMARY_CONTACT("false");
                       contactObject.setUID(action_plansList.get(i).getIBM_CONTACT_CNUM());
                       contactsDataList.add(contactObject);

                   }

               }
           }

           Contacts[] contactList = new Contacts[]{};
           contactList = contactsDataList.toArray(new Contacts[contactsDataList.size()]);
           mSaveModel.setContacts(contactList);

          // Contacts [] cList = new Contacts[contactsDataList.size()];
         //  mSaveModel.setContacts(contactsDataList);

       }catch (Exception e){
//           Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

       }

    }

    private List<ActionPlan> getListOfActions() {

        List<ActionPlan> actionList=new ArrayList<>();
        try{
        for(int i=0;i<action_plansList.size();i++){
            ActionPlan actionPlan=new ActionPlan();
            actionPlan.setACTION_PLAN_KEY(action_plansList.get(i).getACTION_PLAN_KEY());
            actionPlan.setACTION_PLAN_TXT(action_plansList.get(i).getACTION_PLAN_TXT());
            actionPlan.setIBM_CONTACT_CNUM(action_plansList.get(i).getIBM_CONTACT_CNUM());
            if(action_plansList.get(i).getSTATUS().length()>0)
            actionPlan.setSTATUS(action_plansList.get(i).getSTATUS());
            else  actionPlan.setSTATUS("N");
            actionPlan.setTARGET_DATE(action_plansList.get(i).getTARGET_DATE());
            actionList.add(actionPlan);
        }
        }catch (Exception e){
            //Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return actionList;
    }

    private String[] getInitiativeList() {
        String[] initiativeList=new String[selectedInitiativesArrayList.size()];
        try{
        for(int i=0;i<selectedInitiativesArrayList.size();i++){
            initiativeList[i]= selectedInitiativesArrayList.get(i).getKEY();
        }
        }catch (Exception e){
           // Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return initiativeList;
    }

    private boolean relationshipValidation() {

        clientExecName = executiveName.toString();
        if(clientExecName.length()==0) {

            Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.FILL_CLIENT_EXEC_NAME, Toast.LENGTH_SHORT).show();
            return false;
        }else  if(positionKey.length()==0) {

            Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.SELECT_REL_POSITION, Toast.LENGTH_SHORT).show();
            return false;
        }else  if(assementKey.length()==0) {

            Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.SELECT_REL_ASSESMENT, Toast.LENGTH_SHORT).show();
            return false;
        }else
        return true;
    }

    private void initialiseTransitionBtn() {
        prevTextLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageCount == 1) {
                    newRelationshipView2.setVisibility(View.INVISIBLE);
                    newRelationshipView2.setVisibility(View.GONE);
                    newRelationshipView1.setVisibility(View.VISIBLE);
                    prevTextLabel.setTextColor(Color.GRAY);
                    nextTextLabel.setTextColor(Color.parseColor("#65b0c1"));
                    pageCount--;
                }
            }
        });

        nextTextLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageCount == 0) {
                    newRelationshipView1.setVisibility(View.INVISIBLE);
                    newRelationshipView1.setVisibility(View.GONE);
                    newRelationshipView2.setVisibility(View.VISIBLE);
                    prevTextLabel.setTextColor(Color.parseColor("#65b0c1"));
                    nextTextLabel.setTextColor(Color.GRAY);
                    pageCount++;
                }
            }
        });
    }

    private void displayIdentityInitiatives() {
       try{
           identityIni_icon.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View arg0) {
                   final Dialog dialog = new Dialog(context);
                   dialog.setContentView(R.layout.add_goal_initiative_dialog);
                   recyclerView = (RecyclerView) dialog.findViewById(R.id.addGoal_initiative_RecyclerView);
                   selectInitiativeTextView = (TextView) dialog.findViewById(R.id.selectInitiativeTextView);
                   selectInitiativeTextView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           dialog.dismiss();
                           if (initiativesList!=null) {
                               for (int key : choiceMap.keySet())
                                   initiativesList.get(key).setisSelected(choiceMap.get(key));

                               selectedInitiativesArrayList.clear();
                               for (int i = 0; i < initiativesList.size(); i++) {

                                   if (initiativesList.get(i).getisSelected()) {
                                       selectedInitiativesArrayList.add(initiativesList.get(i));
                                   }
                               }
                               createSelectedInitiativeSelectedListUI(selectedInitiativesArrayList);
                           }
                       }
                   });
                   dialog.setTitle("Title...");
                   createInitiativeMultiSelectListUI();
                   View dialogButton = (View) dialog.findViewById(R.id.goalIntiativeCancel);
                   dialogButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   dialog.show();
               }
           });
       }catch (Exception e){
           //Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
       }

    }

    private void createSelectedInitiativeSelectedListUI(List<IDENTIFY_INITIATIVE> selectedInitiativesArrayList) {
       try{
           selectedRelationshipIdentityAdapter = new SelectedRelationshipIdentityAdapter(selectedInitiativesArrayList);
           RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
           selectedInitiativeRecyclerView.setLayoutManager(mLayoutManager);
           selectedInitiativeRecyclerView.setItemAnimator(new DefaultItemAnimator());
           selectedInitiativeRecyclerView.setAdapter(selectedRelationshipIdentityAdapter);

           ISAP_Utils.dismissProgressDialog();
           selectedRelationshipIdentityAdapter.notifyDataSetChanged();
       }catch (Exception e){
         //  Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

       }

    }

    private void createInitiativeMultiSelectListUI() {
        try{
            createDataListForInitiativeRecyclerView();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(relatioshipIdentifyAdapter);
            ISAP_Utils.dismissProgressDialog();
            relatioshipIdentifyAdapter.notifyDataSetChanged();
        }catch (Exception e){
          //  Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

        }

    }

    private void createDataListForInitiativeRecyclerView() {

       try{
           if(initiativesList == null || initiativesList.size()==0) {
               initiativesList = new ArrayList<IDENTIFY_INITIATIVE>();
               try{
                   for (int i = 0; i < newRelationshipModel.getDATA().getIDENTIFY_INITIATIVE().length; i++) {
                       IDENTIFY_INITIATIVE identifyData = new IDENTIFY_INITIATIVE();
                       identifyData.setKEY(newRelationshipModel.getDATA().getIDENTIFY_INITIATIVE()[i].getKEY());
                       identifyData.setNAME(newRelationshipModel.getDATA().getIDENTIFY_INITIATIVE()[i].getNAME());
                       initiativesList.add(identifyData);
                   }
               }catch (Exception e){
                   Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
               }
               choiceMap = new HashMap<Integer, Boolean>();
               relatioshipIdentifyAdapter = new RelatioshipIdentifyAdapter(initiativesList,choiceMap);
           }
       }catch (Exception e){
          // Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();

       }

    }

    private void fetchRelationshipData(String clientExecId){

        ISAP_Utils.showISAPProgressDialog(NewRelationshipActivity.this, ISAP_Constants.POPULATE_RELATIONSHIP_FIELDS,false);
        IsapService = APiUtils.getUserService();
        LogUtils.printLog(NewRelationshipActivity.this,"fetchRelationshipData","fetchRelationshipData  for clientId :"+ISAP_Utils.clientID +" , with cleint executive Id:"+clientExecId);
        Call call = IsapService.getRelationshipFields(String.valueOf(ISAP_Utils.clientID),clientExecId);
        call.enqueue(new Callback<NewRelationshipModel>() {
            @Override
            public void onResponse(Call<NewRelationshipModel> call, Response<NewRelationshipModel> response) {
                try{
                 newRelationshipModel = response.body();
                    LogUtils.printLog(NewRelationshipActivity.this,"fetchRelationshipData","fetchRelationshipData response :"+gson.toJson(newRelationshipModel).toString());
                 ISAP_Utils.dismissProgressDialog();
                 populateDefaultFields();
                 if(isEditingFlag){
                 populateActionPlans();
//                 createActionPlanRecyclerViewUI();
                 }
                    createActionPlanRecyclerViewUI();
                }catch (Exception e){
                    Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NewRelationshipModel> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
//                System.out.println("data from New relationship failure:>>>>" + t.toString());
                Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.NETWORK_ISSUE, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void populateActionPlans() {
        try{
        if(isEditingFlag) {
            action_plansList.clear();
            for (int i = 0; i < newRelationshipModel.getDEFAULT_DATA().getACTION_PLANS().length; i++) {
                if(newRelationshipModel.getDEFAULT_DATA().getACTION_PLANS()[i].getACTION_PLAN_KEY().length()>1){
                action_plansList.add(newRelationshipModel.getDEFAULT_DATA().getACTION_PLANS()[i]);}
            }
            if(action_plansList.size()>=3){
                addActionPlanLinearLayout.setVisibility(View.INVISIBLE);
                addActionPlanLinearLayout.setVisibility(View.GONE);
            }
        }
        }catch (Exception e){
           // Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }


    private void populateDefaultFields(){
    try{
    if(isEditingFlag){
        executiveName.setText(newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getCLIENT_EXEC_NM());
        clientExecId=newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getCLIENT_EXEC_KEY();
        positionKey=newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getEXEC_POSITION_CD();
        bunitkey=newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getEXEC_BU_NM();
        assementKey=newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getASSESMENT_CD();
        clientExecName=newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getCLIENT_EXEC_NM();
        createDataListForInitiativeRecyclerView();
        compareIdentifyInitiativeDataForEditandSave();
    }
    showProgressSpinner(businessUnit_layout,convertBUObjectListToString(),bu_spinner,"businessUnit");
    showProgressSpinner(position_layout,convertpositionObjectListToString(),position_spinner,"position");
    showProgressSpinner(rel_assement_layout,convertAssesmentObjectToString(),assement_spinner,"assesment");
    }catch (Exception e){
      //  Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
    }
}

    private void compareIdentifyInitiativeDataForEditandSave() {
        selectedInitiativesArrayList.clear();
        List<String> identifyList = new ArrayList<String>();
        try{
        identifyList = convertSavedIdentifyObjectToStringList();
        for(int i = 0; i< initiativesList.size(); i++){
            if(identifyList.contains(initiativesList.get(i).getKEY())){
                initiativesList.get(i).setisSelected(true);
                selectedInitiativesArrayList.add(initiativesList.get(i));
            }
        }
        createSelectedInitiativeSelectedListUI(selectedInitiativesArrayList);
        }catch (Exception e){
         //   Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }



    private List<String> convertSavedIdentifyObjectToStringList() {

        List<String> stringList = new ArrayList<String>();
        try{
        for(int i=0;i<newRelationshipModel.getDEFAULT_DATA().getSELECTED_INITIATIVE().length;i++){
            stringList.add(newRelationshipModel.getDEFAULT_DATA().getSELECTED_INITIATIVE()[i].getKEY());
        }
        }catch (Exception e){
            //Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private String getSelectedSpinnerString(String type){

        String selectedSpinnerText="";
        try{
        if (type.equalsIgnoreCase("businessUnit")) {
            selectedSpinnerText = newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getEXEC_BU_NM();
        } else if (type.equalsIgnoreCase("assesment")) {
            selectedSpinnerText = newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getREL_ASSESSMENT_VAL();
        } else if (type.equalsIgnoreCase("position")) {
            selectedSpinnerText = newRelationshipModel.getDEFAULT_DATA().getSELECTED_FIELDS()[0].getPOSITION();
        }
        }catch (Exception e){
           // Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return selectedSpinnerText;
    }

    public void showProgressSpinner(LinearLayout linearLayout, final List<String> spinnerArrayList, Spinner spinner, final String type){
    spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    spinner.setBackgroundColor(Color.TRANSPARENT);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout, spinnerArrayList);
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_layout);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    try{
    if(isEditingFlag) {
        int spinnerPosition = arrayAdapter.getPosition(getSelectedSpinnerString(type));
        spinner.setSelection(spinnerPosition);
    }

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             try{
                if(type.equalsIgnoreCase("businessUnit")) {
                    if(position==0)
                        bunitkey="";
                    else bunitkey=newRelationshipModel.getDATA().getBUSINESS_UNIT()[position-1].getNAME();}
                else if(type.equalsIgnoreCase("position")) {
                    if(position==0)
                        positionKey="";
                    else positionKey=newRelationshipModel.getDATA().getPOSITION()[position-1].getKEY();}
                else if(type.equalsIgnoreCase("assesment")) {
                    if(position==0)
                        assementKey="";
                    else assementKey=newRelationshipModel.getDATA().getASSESSMENT()[position-1].getKEY();}

            }catch (Exception ex){
                //Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // isItemSelected = true;
            }
        });
        // Add Spinner to LinearLayout
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            linearLayout.addView(spinner);
        }
    }catch (Exception e){
       // Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
    }
    }
    private List<String> convertBUObjectListToString(){
        List<String> stringList = new ArrayList<String>();
        stringList.add("Select Business Unit");
        try{
        for(int i=0;i<newRelationshipModel.getDATA().getBUSINESS_UNIT().length;i++){
            stringList.add(newRelationshipModel.getDATA().getBUSINESS_UNIT()[i].getNAME());
        }
        }catch (Exception e){
          //  Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }
    private List<String> convertpositionObjectListToString(){

        List<String> stringList = new ArrayList<String>();
        stringList.add("Select Position");
        try{
        for(int i=0;i<newRelationshipModel.getDATA().getPOSITION().length;i++){
            stringList.add(newRelationshipModel.getDATA().getPOSITION()[i].getNAME());
        }
        }catch (Exception e){
            //Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }
    private List<String> convertAssesmentObjectToString(){

        List<String> stringList = new ArrayList<String>();
        stringList.add("Select Assesment");
        try{
        for(int i=0;i<newRelationshipModel.getDATA().getASSESSMENT().length;i++){
            stringList.add(newRelationshipModel.getDATA().getASSESSMENT()[i].getNAME());
        }
        }catch (Exception e){
           // Toast.makeText(NewRelationshipActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

}
