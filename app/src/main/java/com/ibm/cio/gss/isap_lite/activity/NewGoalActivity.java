package com.ibm.cio.gss.isap_lite.activity;

import android.app.Activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.adapter.AddGoalInitiativeMultiselectAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedInitiativeListAdapter;
import com.ibm.cio.gss.isap_lite.databinding.ActivityNewGoalBinding;
import com.ibm.cio.gss.isap_lite.model.COUNTRY;
import com.ibm.cio.gss.isap_lite.model.CountryMyPlan;
import com.ibm.cio.gss.isap_lite.model.GOAL_FIELDS;
import com.ibm.cio.gss.isap_lite.model.GeoMyplanModel;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.model.MARKETS;
import com.ibm.cio.gss.isap_lite.model.MarketMyPlan;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;
import com.ibm.cio.gss.isap_lite.utility.LogUtils;
import com.ibm.cio.gss.isap_lite.viewModel.GoalViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;


public class NewGoalActivity extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private ISAPService IsapService;
    private View categoryDropDownView;
    private static int clientId = 0;
    private GOAL_FIELDS goal_fieldsModel;
    LinearLayout categoryLinearLayout;
    LinearLayout countryLinearLayout;
    LinearLayout marketLinearLayout;
    LinearLayout geoLinearLayout;
    private MARKETS[] marketData;
    private COUNTRY[] countryData;
    private GeoMyplanModel my_geodata;
    private MarketMyPlan my_marketdata;
    private String globalGeoID;
    private String globalGeoName;
    private EditText goalName;
    private LinearLayout category;
    private TextView saveGoal;
    private TextView goalNameCharacterCount;
    private TextView goalDescription, toolbar;
    private TextView goalDescriptionCharacterCount;
    private boolean isItemSelected;
    private boolean isMarketSelected;
    private boolean isCountrySelected;
    private boolean isRedboxVisible;
    private String marketKey = "-1", geoKey = "-1", countryKey = "-1", categoryKey = "-1", cbuVal = "";
    private String[] initiatives;

    private View rightArrowIcon;
    final Context context = this;
    private TextView selectInitiativeTextView;
    List<INITIATIVES> selectedInitiativesArrayList;
    private boolean isEditingFlag;
    private RecyclerView recyclerView;
    private RecyclerView selectedInitiativeRecyclerView;
    private AddGoalInitiativeMultiselectAdapter adapter;
    private SelectedInitiativeListAdapter selectedInitiativeListAdapter;
    private Map<Integer, Boolean> choiceMap;
    private List<INITIATIVES> initiativesList;
    private ActivityNewGoalBinding mbinding;
    private GoalViewModel mViewModel;
    private Gson gson;
    private Spinner marketSpinner;
    private Spinner countrySpinner;
    private AutoCompleteTextView textView = null;
    private ArrayAdapter<String> autoCompleteAdapter;
    private String goalId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new GoalViewModel();
        gson = new Gson();
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_new_goal);
        mbinding.setGoal(mViewModel);
        textView = (AutoCompleteTextView) findViewById(R.id.cbuList);
        category = (LinearLayout) findViewById(R.id.categoryDropDownLayout);

        selectedInitiativesArrayList = new ArrayList<INITIATIVES>();
        selectedInitiativeRecyclerView = (RecyclerView) findViewById(R.id.addGoal_initiativeSelectedRecyclerView);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("DataRefresh"));
        goalName = (EditText) findViewById(R.id.goalName);
        recyclerView = (RecyclerView) findViewById(R.id.addGoal_initiativeSelectedRecyclerView);
        saveGoal = (TextView) findViewById(R.id.saveGoal);
        goalNameCharacterCount = (TextView) findViewById(R.id.goalNameCharacters);
        goalDescription = (TextView) findViewById(R.id.goalDescription);
        goalDescriptionCharacterCount = (TextView) findViewById(R.id.descriptionCharacters);
        isMarketSelected = false;
        isCountrySelected = false;
        marketSpinner = new Spinner(this);
        countrySpinner = new Spinner(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        categoryLinearLayout = findViewById(R.id.categoryDropDownLayout);
        countryLinearLayout = findViewById(R.id.countryDropDownLayout);
        marketLinearLayout = findViewById(R.id.marketDropDownLayout);
        geoLinearLayout = findViewById(R.id.geoDropDownLayout);

        try {
            goalId = getIntent().getExtras().getString("goalId");
        } catch (NullPointerException e) {
            System.out.print("NullPointerException Caught");
        }
        clientId = ISAP_Utils.clientID;
        if (goalId.length() > 1) {
            isEditingFlag = true;
            fetchGoalFields(clientId, Integer.parseInt(goalId));
        } else {
            isEditingFlag = false;
            fetchGoalFields(clientId, 0);
        }
        LinearLayout cancelBtn = findViewById(R.id.newToolbar);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
        rightArrowIcon = (View) findViewById(R.id.editGoalInitiativeRightArrow);
        // add button listener
        rightArrowIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_goal_initiative_dialog);
                recyclerView = (RecyclerView) dialog.findViewById(R.id.addGoal_initiative_RecyclerView);
                selectInitiativeTextView = (TextView) dialog.findViewById(R.id.selectInitiativeTextView);
                selectInitiativeTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        try {
                            for (int key : choiceMap.keySet())
                                initiativesList.get(key).setisSelected(choiceMap.get(key));
                            selectedInitiativesArrayList.clear();
                            for (int i = 0; i < initiativesList.size(); i++) {
                                if (initiativesList.get(i).getisSelected()) {
                                    selectedInitiativesArrayList.add(initiativesList.get(i));
                                }
                            }
                            createSelectedInitiativeSelectedListUI(selectedInitiativesArrayList);
                        } catch (Exception ex) {
                            LogUtils.printLog(NewGoalActivity.this, "rightArrowIcon click", ex.getMessage());
//                        Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
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
        saveGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean status = goalValidation();
                String jsonInString = gson.toJson(mViewModel);
                if (status == true) {
                    saveGoalDetails(jsonInString);
                }
            }
        });
        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                if (isRedboxVisible) {
                    goalName.setBackgroundResource(0);
                    isRedboxVisible = true;
                }
                goalNameCharacterCount.setText(String.valueOf(s.length()) + "/50");
            }

            public void afterTextChanged(Editable s) {
            }
        };
        goalName.addTextChangedListener(mTextEditorWatcher);

        final TextWatcher mTextEditorWatcherDescription = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                goalDescriptionCharacterCount.setText(String.valueOf(s.length()) + "/1000");
            }

            public void afterTextChanged(Editable s) {
            }
        };
        goalDescription.addTextChangedListener(mTextEditorWatcherDescription);
        toolbar = findViewById(R.id.editPageTittle);
        System.out.println("toolbar" + toolbar);
    }

    private void updateGoalView() {
        ISAP_Utils.currentPage = 1;
        Intent intent = new Intent(NewGoalActivity.this, IsapMenuActivity.class);
        startActivity(intent);
    }

    private void createSelectedInitiativeSelectedListUI(List<INITIATIVES> selectedInitiativesArrayList) {
        selectedInitiativeListAdapter = new SelectedInitiativeListAdapter(selectedInitiativesArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        selectedInitiativeRecyclerView.setLayoutManager(mLayoutManager);
        selectedInitiativeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectedInitiativeRecyclerView.setAdapter(selectedInitiativeListAdapter);

        ISAP_Utils.dismissProgressDialog();
        selectedInitiativeListAdapter.notifyDataSetChanged();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = Integer.parseInt(intent.getStringExtra("position"));
            if (initiativesList != null && initiativesList.size() > 0) {
                for (int i = 0; i < initiativesList.size(); i++) {
                    if(selectedInitiativesArrayList!=null && selectedInitiativesArrayList.size()>0){
                        if (selectedInitiativesArrayList.get(position).getKEY().equalsIgnoreCase(initiativesList.get(i).getKEY())) {
                            initiativesList.get(i).setisSelected(false);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
                if(selectedInitiativesArrayList!=null && selectedInitiativesArrayList.size()>0) {
                    selectedInitiativesArrayList.remove(position);
                }
                selectedInitiativeListAdapter.notifyDataSetChanged();
            }
        }
    };

    private void saveGoalDetails(String jsonInString) {
        ISAP_Utils.showISAPProgressDialog(NewGoalActivity.this, ISAP_Constants.SAVE_GOAL_INFO, false);
        initiatives = new String[selectedInitiativesArrayList.size()];
        for (int i = 0; i < selectedInitiativesArrayList.size(); i++) {
            initiatives[i] = selectedInitiativesArrayList.get(i).getKEY();
        }
        mViewModel.setCategoryKey(categoryKey);
        mViewModel.setGeoKey(geoKey);
        mViewModel.setRegionKey(marketKey);
        mViewModel.setCountryKey(countryKey);
        mViewModel.setInitiatives(initiatives);
        mViewModel.setClientId(String.valueOf(clientId));
        if(cbuVal.length()<1)
            mViewModel.setClientBusinessUnit(textView.getText().toString());
       else  mViewModel.setClientBusinessUnit(cbuVal);
        if (isEditingFlag)
            mViewModel.setGoalId(goalId);
        else mViewModel.setGoalId("");
        mViewModel.setIntranetId(ISAP_Utils.LoggedInuserEmail);
        mViewModel.setGoalName(goalName.getText().toString());
        mViewModel.setDescription(goalDescription.getText().toString());
        IsapService = APiUtils.getUserService();
        gson = new Gson();
        LogUtils.printLog(NewGoalActivity.this, "saveGoalDetails", "Save Goal request object :" + gson.toJson(mViewModel).toString());
        Call call = IsapService.saveGoalData(mViewModel);
        call.enqueue(new Callback<GoalResponseObj>() {
            @Override
            public void onResponse(Call<GoalResponseObj> call, Response<GoalResponseObj> response) {
                try {
                    GoalResponseObj resp = response.body();
                    LogUtils.printLog(NewGoalActivity.this, "saveGoalDetails", "Save Goal response  :" + gson.toJson(resp).toString());

                    if (resp.getFlag() == "true") {
//                    ISAP_Utils.currentPage = 1;
                        if (ISAP_Utils.currentPage == 4)
                            ISAP_Utils.isNewGoal = true;
                        Intent intent = new Intent(NewGoalActivity.this, IsapMenuActivity.class);
                        startActivity(intent);
                        ISAP_Utils.dismissProgressDialog();
                        if (resp.getFlag().equalsIgnoreCase("true")) {
                            Toast.makeText(NewGoalActivity.this, ISAP_Constants.SAVE_GOAL_DONE, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(NewGoalActivity.this, ISAP_Constants.SAVE_GOAL_FAILURE, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    LogUtils.printLog(NewGoalActivity.this, "GoalResponseObj response", ex.getMessage());
//                    Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GoalResponseObj> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean goalValidation() {
        boolean status = true;
        if (goalName == null || goalName.getText().length() == 0) {
            // goalName.setBackgroundColor(Color.parseColor("#FF0000"));
            ShapeDrawable sd = new ShapeDrawable();
            // Specify the shape of ShapeDrawable
            sd.setShape(new RectShape());
            // Specify the border color of shape
            sd.getPaint().setColor(Color.RED);
            // Set the border width
            sd.getPaint().setStrokeWidth(10f);
            // Specify the style is a Stroke
            sd.getPaint().setStyle(Paint.Style.STROKE);
            // Finally, add the drawable background to TextView
            goalName.setBackground(sd);
            status = false;
            isRedboxVisible = true;
            // goalName.setPaintFlags(goalName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        } else if (isItemSelected == true) {
            ShapeDrawable sd = new ShapeDrawable();
            // Specify the shape of ShapeDrawable
            sd.setShape(new RectShape());
            // Specify the border color of shape
            sd.getPaint().setColor(Color.RED);
            // Set the border width
            sd.getPaint().setStrokeWidth(10f);
            // Specify the style is a Stroke
            sd.getPaint().setStyle(Paint.Style.STROKE);
            // Finally, add the drawable background to TextView
            category.setBackground(sd);
            status = false;
            Toast.makeText(NewGoalActivity.this, R.string.error_category_selection, Toast.LENGTH_SHORT).show();
        }
        return status;
    }

    private void createDataListForInitiativeRecyclerView() {

        if (initiativesList == null || initiativesList.size() == 0) {
            initiativesList = new ArrayList<INITIATIVES>();
            for (int i = 0; i < goal_fieldsModel.getDATA().getINITIATIVES().length; i++) {
                INITIATIVES goalData = new INITIATIVES();
                goalData.setKEY(goal_fieldsModel.getDATA().getINITIATIVES()[i].getKEY());
                goalData.setNAME(goal_fieldsModel.getDATA().getINITIATIVES()[i].getNAME());
                initiativesList.add(goalData);
            }
        }
    }

    private void createInitiativeMultiSelectListUI() {
        if (!isEditingFlag) {
            createDataListForInitiativeRecyclerView();
            choiceMap = new HashMap<Integer, Boolean>();
        }
        adapter = new AddGoalInitiativeMultiselectAdapter(initiativesList, choiceMap);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        ISAP_Utils.dismissProgressDialog();
        adapter.notifyDataSetChanged();
    }

    private List<String> convertCategoryObjectListtoStringList() {
        List<String> stringList = new ArrayList<String>();
        try {
            stringList.add("");
            for (int i = 0; i < goal_fieldsModel.getDATA().getCATEGORY().length; i++) {
                stringList.add(goal_fieldsModel.getDATA().getCATEGORY()[i].getNAME());
            }
        } catch (Exception ex) {
            LogUtils.printLog(NewGoalActivity.this, "convertCategoryObjectListtoStringList", ex.getMessage());
//            Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private List<String> convertGeoObjectListToStringList() {
        List<String> stringList = new ArrayList<String>();
        try {
            stringList.add("Geo");
            for (int i = 0; i < goal_fieldsModel.getDATA().getGEO().length; i++) {
                stringList.add(goal_fieldsModel.getDATA().getGEO()[i].getNAME());
            }
        } catch (Exception ex) {
            LogUtils.printLog(NewGoalActivity.this, "convertGeoObjectListToStringList", ex.getMessage());
//            Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private List<String> convertMarketObjectListToString() {
        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < my_geodata.getMARKETS().length; i++) {
                stringList.add(my_geodata.getMARKETS()[i].getNAME());
            }
        } catch (Exception ex) {
            LogUtils.printLog(NewGoalActivity.this, "convertMarketObjectListToString", ex.getMessage());
//            Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private List<String> convertCountryObjectListToString() {
        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < my_marketdata.getCOUNTRY().length; i++) {
                stringList.add(my_marketdata.getCOUNTRY()[i].getNAME());
            }
        } catch (Exception ex) {
            LogUtils.printLog(NewGoalActivity.this, "convertCountryObjectListToString", ex.getMessage());
//            Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void closeActivity() {
        super.onBackPressed();
    }

    private void prepareUIWithEditField() {
        try {
            goalName.setText(goal_fieldsModel.getDEFAULT_DATA().getGOALNAME());
            textView.setText(goal_fieldsModel.getDEFAULT_DATA().getCLIENTBUSINESSUNIT());
            goalDescription.setText(goal_fieldsModel.getDEFAULT_DATA().getDESCRIPTION());
            List<String> marketStringList = new ArrayList<String>();
            marketStringList.add(goal_fieldsModel.getDEFAULT_DATA().getREGIONNAME());
            showMarketSpinner(marketLinearLayout, marketStringList);
            List<String> countryStringList = new ArrayList<String>();
            countryStringList.add(goal_fieldsModel.getDEFAULT_DATA().getCOUNTRYNAME());
            showCountrySpinner(countryLinearLayout, countryStringList);
            List<String> initiativesStringList = Arrays.asList(goal_fieldsModel.getDEFAULT_DATA().getINITIATIVES());
            for (int i = 0; i < goal_fieldsModel.getDATA().getINITIATIVES().length; i++) {
                if (initiativesStringList.contains(goal_fieldsModel.getDATA().getINITIATIVES()[i].getKEY())) {
                    INITIATIVES goalData = new INITIATIVES();
                    goalData.setKEY(goal_fieldsModel.getDATA().getINITIATIVES()[i].getKEY());
                    goalData.setNAME(goal_fieldsModel.getDATA().getINITIATIVES()[i].getNAME());
                    selectedInitiativesArrayList.add(goalData);
                }
            }
            createSelectedInitiativeSelectedListUI(selectedInitiativesArrayList);
            addExistingInitiativestoRecyclerView(initiativesStringList);
        } catch (Exception ex) {
            LogUtils.printLog(NewGoalActivity.this, "prepareUIWithEditField", ex.getMessage());
//            Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void addExistingInitiativestoRecyclerView(List<String> initiativesStringList) {
        choiceMap = new HashMap<Integer, Boolean>();
        try {
            if (initiativesList == null || initiativesList.size() == 0) {
                initiativesList = new ArrayList<INITIATIVES>();
                for (int i = 0; i < goal_fieldsModel.getDATA().getINITIATIVES().length; i++) {
                    INITIATIVES goalData = new INITIATIVES();
                    goalData.setKEY(goal_fieldsModel.getDATA().getINITIATIVES()[i].getKEY());
                    goalData.setNAME(goal_fieldsModel.getDATA().getINITIATIVES()[i].getNAME());
                    initiativesList.add(goalData);
                    if (initiativesStringList.contains(goal_fieldsModel.getDATA().getINITIATIVES()[i].getKEY())) {
                        goalData.setisSelected(true);
                        choiceMap.put(i, true);
                    }
                }
            }
            adapter = new AddGoalInitiativeMultiselectAdapter(initiativesList, choiceMap);
        } catch (Exception ex) {
            LogUtils.printLog(NewGoalActivity.this, "addExistingInitiativestoRecyclerView", ex.getMessage());
//        Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchMarketandCountry(int clientId, String geoKey, String geoName, String geoId) {
        ISAP_Utils.showISAPProgressDialog(NewGoalActivity.this, ISAP_Constants.FETCH_MARKET_DETAILS, false);
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getGeoMyPlan(clientId, geoKey, geoName, geoId);
        call.enqueue(new Callback<GeoMyplanModel>() {
            @Override
            public void onResponse(Call<GeoMyplanModel> call, Response<GeoMyplanModel> response) {
                try {
                    my_geodata = response.body();
                    gson = new Gson();
                    String data = gson.toJson(my_geodata);
                    LogUtils.printLog(NewGoalActivity.this, "fetchMarket", "Fetch market response :" + gson.toJson(my_geodata).toString());
                    marketData = my_geodata.getMARKETS();
                    showMarketSpinner(marketLinearLayout, convertMarketObjectListToString());
                    ISAP_Utils.dismissProgressDialog();
                } catch (Exception ex) {
                    LogUtils.printLog(NewGoalActivity.this, "fetchMarketandCountry", ex.getMessage());
//                Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeoMyplanModel> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                LogUtils.printLog(NewGoalActivity.this, "data from fetchMarket failure:>>>>", "Fetch market failure message :" + t.getMessage());

//                System.out.println("data from fetchMarketsandCountry failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCountry(int clientId, String geoId, String geoName, String marketId, String marketName, String marketKey) {
        ISAP_Utils.showISAPProgressDialog(NewGoalActivity.this, ISAP_Constants.FETCH_COUNTRY_DETAILS, false);
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getMarketMyPlan(clientId, geoId, geoName, marketId, marketName, marketKey);
//        System.out.println("fetch data for:clientId:"+clientId+",geo:"+geoId+",m-key:"+marketId+",m-name:"+marketName);
        call.enqueue(new Callback<MarketMyPlan>() {
            @Override
            public void onResponse(Call<MarketMyPlan> call, Response<MarketMyPlan> response) {
                try {
                    my_marketdata = response.body();
                    gson = new Gson();
                    String data = gson.toJson(my_marketdata);
                    LogUtils.printLog(NewGoalActivity.this, "fetchCountry", "Fetch country response :" + gson.toJson(my_marketdata).toString());
                    countryData = my_marketdata.getCOUNTRY();//Reset country drop down
                    showCountrySpinner(countryLinearLayout, convertCountryObjectListToString());
                    ISAP_Utils.dismissProgressDialog();
                } catch (Exception ex) {
                    LogUtils.printLog(NewGoalActivity.this, "MarketMyPlan response", ex.getMessage());
//                  Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MarketMyPlan> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                LogUtils.printLog(NewGoalActivity.this, "data from fetchCountry failure:>>>>", "Fetch country failure message :" + t.getMessage());
            }
        });
    }

    private void fetchGoalFields(int clientId, int goalId) {
        ISAP_Utils.showISAPProgressDialog(NewGoalActivity.this, ISAP_Constants.POPULATE_GOAL_FIELDS, false);
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getGoalFields(clientId, goalId);
        call.enqueue(new Callback<GOAL_FIELDS>() {
            @Override
            public void onResponse(Call<GOAL_FIELDS> call, Response<GOAL_FIELDS> response) {
                try {
                    goal_fieldsModel = response.body();
                    gson = new Gson();
                    LogUtils.printLog(NewGoalActivity.this, "fetchGoalFields", "Fetch goal field response :" + gson.toJson(goal_fieldsModel).toString());
                    showCBUnit(goal_fieldsModel.getDATA().getCLIENT_BUSINESS_UNIT());
                    showCategorySpinner(categoryLinearLayout, convertCategoryObjectListtoStringList(), "category");
                    showCategorySpinner(geoLinearLayout, convertGeoObjectListToStringList(), "geo");
                    if (isEditingFlag) {
                        prepareUIWithEditField();
                    }
                    ISAP_Utils.dismissProgressDialog();
                } catch (Exception ex) {
                    LogUtils.printLog(NewGoalActivity.this, "GOAL_FIELDS response", ex.getMessage());
//                Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GOAL_FIELDS> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                LogUtils.printLog(NewGoalActivity.this, "data from getGoalFields failure:>>>>", "Fetch goal field failure message :" + t.getMessage());
            }
        });
    }

    private void showCBUnit(String[] client_business_unit) {
        autoCompleteAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, client_business_unit);
        textView.setThreshold(1);
        //Set adapter to AutoCompleteTextView
        textView.setAdapter(autoCompleteAdapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);
//        textView.setText();
    }

    public void showMarketSpinner(LinearLayout linearLayout, final List<String> spinnerArrayList) {
        marketSpinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        marketSpinner.setBackgroundColor(Color.TRANSPARENT);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout, spinnerArrayList);
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_layout);
        marketSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
       /* if(isEditingFlag) {

            int spinnerPosition = arrayAdapter.getPosition(getSelectedSpinnerString("market"));
            marketSpinner.setSelection(spinnerPosition);

        }*/

        marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(NewGoalActivity.this, getString(R.string.selected_item) + " " + personNames[position], Toast.LENGTH_SHORT).show();
                try {
                    if (isCountrySelected == true) {
                        marketKey = marketData[position].getKEY();
                        fetchCountry(clientId, globalGeoID, globalGeoName, marketData[position].getID(), marketData[position].getNAME(), marketData[position].getKEY());
                    } else {
                        if (!isEditingFlag) {
                            List<String> stringList = new ArrayList<String>();
                            stringList.add("Country");
                            showCountrySpinner(countryLinearLayout, stringList);
                        }
                    }
                    isCountrySelected = true;
                } catch (Exception ex) {
                    LogUtils.printLog(NewGoalActivity.this, "marketSpinner.setOnItemSelectedListener", ex.getMessage());
//                Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            linearLayout.addView(marketSpinner);
        }
    }

    public void showCountrySpinner(LinearLayout linearLayout, final List<String> spinnerArrayList) {
        countrySpinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        countrySpinner.setBackgroundColor(Color.TRANSPARENT);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout, spinnerArrayList);
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_layout);
        countrySpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        /*if(isEditingFlag) {

            int spinnerPosition = arrayAdapter.getPosition(getSelectedSpinnerString("country"));
            marketSpinner.setSelection(spinnerPosition);

        }*/
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerArrayList.size() > 1)
                    countryKey = my_marketdata.getCOUNTRY()[position].getKEY();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // Add Spinner to LinearLayout
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            linearLayout.addView(countrySpinner);
        }
    }

    public void onStart() {
        super.onStart();
        toolbar = findViewById(R.id.editPageTittle);
        if (goalId.length() > 1)
            toolbar.setText("Edit Goal");
        else toolbar.setText("New Goal");
    }

    private String getSelectedSpinnerString(String type) {
        String selectedSpinnerText = "";
        try {
            if (type.equalsIgnoreCase("geo")) {
                selectedSpinnerText = goal_fieldsModel.getDEFAULT_DATA().getGEONAME();

            } else if (type.equalsIgnoreCase("category")) {
                selectedSpinnerText = goal_fieldsModel.getDEFAULT_DATA().getCATEGORYNAME();
            } else if (type.equalsIgnoreCase("market")) {
                selectedSpinnerText = goal_fieldsModel.getDEFAULT_DATA().getREGIONNAME();
            } else if (type.equalsIgnoreCase("country")) {
                selectedSpinnerText = goal_fieldsModel.getDEFAULT_DATA().getCOUNTRYNAME();
            }
        } catch (Exception ex) {
            LogUtils.printLog(NewGoalActivity.this, "getSelectedSpinnerString", ex.getMessage());
//            Toast.makeText(NewGoalActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return selectedSpinnerText;
    }

    public void showCategorySpinner(LinearLayout linearLayout, final List<String> spinnerArrayList, final String categoryOrGeo) {
        final Spinner spinner = new Spinner(this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        spinner.setBackgroundColor(Color.TRANSPARENT);
        //final String[] personNames = {"Rahul", "Jack", "Rajeev", "Aryan", "Rashmi", "Jaspreet", "Akbar"};
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout, spinnerArrayList);
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_layout);
        spinner.setAdapter(arrayAdapter);
        if (isEditingFlag) {
            int spinnerPosition = arrayAdapter.getPosition(getSelectedSpinnerString(categoryOrGeo));
            spinner.setSelection(spinnerPosition);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(NewGoalActivity.this, getString(R.string.selected_item) + " " + personNames[position], Toast.LENGTH_SHORT).show();
                if (categoryOrGeo.equalsIgnoreCase("geo")) {
                    //fetchCountryPlan();
                    globalGeoID = goal_fieldsModel.getDATA().getGEO()[position].getID();
                    globalGeoName = goal_fieldsModel.getDATA().getGEO()[position].getNAME();
                    if (isMarketSelected == true) {
                        isCountrySelected = false;
                        if (position == 0) {
                            fetchMarketandCountry(clientId, goal_fieldsModel.getDATA().getGEO()[position].getKEY(), goal_fieldsModel.getDATA().getGEO()[position].getNAME(), goal_fieldsModel.getDATA().getGEO()[position].getID());
                        } else {
                            fetchMarketandCountry(clientId, goal_fieldsModel.getDATA().getGEO()[position - 1].getKEY(), goal_fieldsModel.getDATA().getGEO()[position - 1].getNAME(), goal_fieldsModel.getDATA().getGEO()[position - 1].getID());
                            geoKey = goal_fieldsModel.getDATA().getGEO()[position - 1].getKEY();
                        }
                    } else {
                        if (!isEditingFlag) {
                            List<String> stringList = new ArrayList<String>();
                            stringList.add("Markets");
                            showMarketSpinner(marketLinearLayout, stringList);
                        }
                    }
                    isMarketSelected = true;
                } else {
                    if (position == 0) {/*first position is category hint*/
                        isItemSelected = true;
                    } else {
                        isItemSelected = false;
                        category.setBackground(null);
                        categoryKey = goal_fieldsModel.getDATA().getCATEGORY()[position-1].getKEY();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isItemSelected = true;
            }
        });
        // Add Spinner to LinearLayout
        if (linearLayout != null) {
            linearLayout.addView(spinner);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(context, "item selected>>>"+textView.getText().toString(), Toast.LENGTH_SHORT).show();
        cbuVal = textView.getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(context, "item selected nothing", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cbuVal = textView.getText().toString();
//        Toast.makeText(context, "item selected viw:::"+textView.getText().toString(), Toast.LENGTH_SHORT).show();

    }
}
