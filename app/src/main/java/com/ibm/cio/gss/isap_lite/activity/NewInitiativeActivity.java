package com.ibm.cio.gss.isap_lite.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.adapter.AddLinkedOpptAdapter;
import com.ibm.cio.gss.isap_lite.adapter.BrandsAdapter;
import com.ibm.cio.gss.isap_lite.adapter.CountryMultiSelectListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.GeoMultiSelectListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.InitiativeBrandUnitAdapter;
import com.ibm.cio.gss.isap_lite.adapter.InitiativeLeadContactsAdapter;
import com.ibm.cio.gss.isap_lite.adapter.LinkedGoalsMultiSelectAdapter;
import com.ibm.cio.gss.isap_lite.adapter.LinkedSelectedGoalsListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.MarketsMultiSelectListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedBrandUnitsAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedCountryListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedGeoListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedLinkedOpptsAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedMarketListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SelectedStrategicImperativesListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.StrategicImperativesMultiSelectAdapter;
import com.ibm.cio.gss.isap_lite.model.COUNTRY;
import com.ibm.cio.gss.isap_lite.model.GEO;
import com.ibm.cio.gss.isap_lite.model.GOALS;
import com.ibm.cio.gss.isap_lite.model.GetCountryModel;
import com.ibm.cio.gss.isap_lite.model.GetCountryResponse;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
import com.ibm.cio.gss.isap_lite.model.InitiativeContactModel;
import com.ibm.cio.gss.isap_lite.model.GetRegionModel;
import com.ibm.cio.gss.isap_lite.model.InitiativeFields_Model;
import com.ibm.cio.gss.isap_lite.model.LINKEDOPPT;
import com.ibm.cio.gss.isap_lite.model.REGION;
import com.ibm.cio.gss.isap_lite.model.RELEVANTBRANDSORUNITS;
import com.ibm.cio.gss.isap_lite.model.RegionsResponse;
import com.ibm.cio.gss.isap_lite.model.STRATEGICIMPERATIVES;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;
import com.ibm.cio.gss.isap_lite.utility.LogUtils;
import com.ibm.cio.gss.isap_lite.utility.NumberTextWatcher;
import com.ibm.cio.gss.isap_lite.viewModel.Countries;
import com.ibm.cio.gss.isap_lite.viewModel.Geos;
import com.ibm.cio.gss.isap_lite.viewModel.InitiativeViewModel;
import com.ibm.cio.gss.isap_lite.viewModel.Regions;
import com.ibm.cio.gss.isap_lite.viewModel.Units;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewInitiativeActivity extends Activity {

    final Context context = this;
    private TextView nextLabelTextView;
    private TextView previousLabelTextView, toolbar;
    private View initiativeLayoutView1;
    private View initiativeLayoutView2;
    private View initiativeLayoutView3;
    int initiativeLayoutPageNumber = 1;
    private View strategicImperativesView, relevantBU;
    private View linkedGoalsView;
    private View intitiativeContactsPopup;
    private ISAPService IsapService;
    private InitiativeFields_Model initiativeFields_model;
    private Gson gson;
    private static int clientId = 0;
    private RecyclerView selectedGoalsMultiselectRecyclerView;
    private RecyclerView dialog_recycleView;
    private Dialog dialogBox;
    private LinkedGoalsMultiSelectAdapter linkedGoalsMultiSelectAdapter;
    private LinkedSelectedGoalsListAdapter linkedSelectedGoalsListAdapter;
    private TextView selectGoalsTextView;
    private TextView popupTitle;
    public static boolean isAllGeos = false;
    private Dialog contactsLeadDialog;
    private List<GOALS> goalsList;
    List<GOALS> selectedGoalsArrayList;
    private Map<Integer, Boolean> choiceMap;
    private Spinner progressSpinner;
    private Spinner industrySolutionsSpinner;
    private Spinner partnerRoleSpinner;
    private Spinner yearSpinner;
    LinearLayout progressLinearLayout;
    LinearLayout industrySolutionsLinearLayout;
    LinearLayout partnerRoleLinearLayout;
    LinearLayout contactsLeadLinearLayout;
    private TextView selectStrategicImperativesView;
    private RecyclerView strategicImperativesRecyclerView;
    private RecyclerView selectedStrategicImperativesRecyclerView;
    private StrategicImperativesMultiSelectAdapter strategicImperativesMultiSelectAdapter;
    private InitiativeBrandUnitAdapter initiativeBrandUnitAdapter;
    private SelectedStrategicImperativesListAdapter selectedStrategicImperativesListAdapter;
    private InitiativeLeadContactsAdapter initiativeLeadContactsAdapter;
    private List<STRATEGICIMPERATIVES> strategicImperativesList;
    private List<RELEVANTBRANDSORUNITS> relevantBUList;
    //    private List<RelevantBUmodel> rele_BUList;
    private List<InitiativeContactModel> initiativeContactModelList;
    private InitiativeContactModel initiativeContactModel;
    List<STRATEGICIMPERATIVES> selectedStrategicImperativesList;
    private Map<Integer, Boolean> strategicImperativesChoiceMap;
    private ListView listView;

    private View geoPopupClick;
    private View marketPopupClick;
    private View countryPopupClick;

    private RecyclerView selectedGeoListRecyclerView;
    private GeoMultiSelectListAdapter geoMultiSelectListAdapter;
    private SelectedGeoListAdapter selectedGeoListAdapter;
    private List<GEO> geoList;
    List<GEO> selectedGeoList;
    private Map<Integer, Boolean> geoChoiceMap;

    private RecyclerView selectedMarketListRecyclerView;
    private MarketsMultiSelectListAdapter marketMultiSelectListAdapter;
    private SelectedMarketListAdapter selectedMarketListAdapter;
    private List<REGION> marketList;
    List<REGION> selectedMarketList;
    private Map<Integer, Boolean> marketChoiceMap;

    private RecyclerView selectedCountryListRecyclerView;
    private CountryMultiSelectListAdapter countryMultiSelectListAdapter;
    private SelectedCountryListAdapter selectedCountryListAdapter;
    private List<COUNTRY> countryList;
    private String[] BusinessUnits;
    private BrandsAdapter adapter;
    List<COUNTRY> selectedCountryList;
    private Map<Integer, Boolean> countryChoiceMap;

    private RegionsResponse regionsResponseModel;
    private GetCountryResponse getCountryResponseModel;
    //    private static int splitIndex = 1;
    private TextView splitYear0, splitYear1, splitYear2, splitYear3, splitYear4;
    //    private TextView splitLabel0, splitLabel1, splitLabel2, splitLabel3, splitLabel4;
    //    private TextView splitValue0, splitValue1,splitValue2, splitValue3, splitValue4;
    private EditText splitValue0, splitValue1, splitValue2, splitValue3, splitValue4;
    private LinearLayout mLinearSplit;
    private RelativeLayout rlSplit0, rlSplit1, rlSplit2, rlSplit3, rlSplit4;
    //    private SeekBar seekBar;
    private TextView saveInitiativeBtn;
    private InitiativeViewModel mviewModel;
    private EditText ini_name, ini_value, ini_desc, ini_clientValue;

    private TextView contactsLeadName, createdDate,iniNameCharacterCount;
    private TextView contactsLeadRole;
    private TextView contactsLeadEmail;
    private EditText editContactsLead;
    private InitiativeContactModel contactInfo;
    private List<InitiativeContactModel> contactList;
    private RecyclerView contactsRecyclerView;
    private TextView initiateLeadNameTextView;
    private TextView cancelEditText;
    private LinearLayout yearSpinnerLinearLayout;
    private Switch aSwitch;
    private String progressKey = "", pRole = "", industrykey = "", leadCnum = "", h1h2 = "01", statuKey = "", closeDate = "";
    private RadioGroup radioGroup;
    private int splitTotal = 0, sp0 = 0, sp1 = 0, sp2 = 0, sp3 = 0, sp4 = 0, splitClick = 1;

    private List<RELEVANTBRANDSORUNITS> selectedBUList;
    private RecyclerView selectedBURecyclerView;
    private SelectedBrandUnitsAdapter selectedBrandUnitsAdapter;
    private Map<Integer, Boolean> relevBUChoiceMap;
    private int relevantBusinessUnitTotalPCT = 0;
    private String initiativeKey = "";
    private Boolean isEditingFlag;
    private TextView createdDateTextView;
    private boolean isItemSelected;
    private boolean isStrategicImperativesSelected;
    private boolean isRedboxVisible;
    private TextView addMoreLinkedOpprtunities;

    private Dialog addLinkedOpptsDialog;
    private RecyclerView addLinkedOpptsRecyclerView;
    private RecyclerView selectedLinkedOpptsRecyclerView;
    private AddLinkedOpptAdapter addLinkedOpptAdapter;
    private SelectedLinkedOpptsAdapter selectedLinkedOpptsAdapter;
    private List<LINKEDOPPT> linkedopptList;
    private List<LINKEDOPPT> selectedLinkedOpptList;
    private TextView addMoreOpptDone;
    private TextView linkedOPPTSName;
    private TextView linkedOPPTSSalesStage;
    private TextView linkedOPPTSClosedDate;
    private Map<Integer, Boolean> linkedOPPTsChoiceMap;
    private TextView linkedOpptsCountLabel;
    private TextView linkedOpptsTotalValue;
    private LinearLayout mSplitValueByYear;
    private String[] temparaySplit;
    private String[] splitDisplayList;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final BigDecimal ONE_MILLION = new BigDecimal(1000000);
    private TextView mRemaingValue;
    private RelativeLayout mExpand;
    private boolean isExpand;
    //private String splitValue1String="";

    private Boolean splitValue1Changed = false;
    private Boolean splitValue2Changed = false;
    private Boolean splitValue3Changed = false;
    private Boolean splitValue4Changed = false;
    private int intialSize = 5, spinnerPosition;
    private DecimalFormat decim = new DecimalFormat("#,###.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_initiative);
        isItemSelected = true;
        isStrategicImperativesSelected = true;
        // TextView initiativeToolBar=findViewById(R.id.editPageTittle);
        aSwitch = findViewById(R.id.simpleSwitch);
        getSwitchValue();
//        seekBar = (SeekBar) findViewById(R.id.seekProgress);
        displaySeekBar();

        initialiseSplitElements();
//        sumSpliteValues();
        mExpand = findViewById(R.id.rl_expand);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                statuKey = initiativeFields_model.getInitiativeData().getPARTNERSTATUS()[index].getKEY();
                //System.out.println("The status key is "+statuKey);
                if(statuKey.equalsIgnoreCase("NONE")){
                    partnerRoleSpinner.setEnabled(false);
                }else{
                    partnerRoleSpinner.setEnabled(true);
                }

            }
        });

        saveInitiativeBtn = (TextView) findViewById(R.id.saveInitiativeBtn);
        nextLabelTextView = (TextView) findViewById(R.id.nextTextLabel);
        previousLabelTextView = (TextView) findViewById(R.id.prevTextLabel);
        initiativeLayoutView1 = (View) findViewById(R.id.newInitiativeLayout1);
        initiativeLayoutView2 = (View) findViewById(R.id.newInitiativeLayout2);
        initiativeLayoutView3 = (View) findViewById(R.id.newInitiativeLayout3);
        intitiativeContactsPopup = (View) findViewById(R.id.intitiativeContactsPopup_icon);
        addMoreLinkedOpprtunities = (TextView) findViewById(R.id.addMoreLinkedOppt);
        mSplitValueByYear = findViewById(R.id.ll_split_year);
        mLinearSplit = findViewById(R.id.ll_split_value);
        mRemaingValue = findViewById(R.id.txt_remain_value);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        LinearLayout cancelBtn = findViewById(R.id.newIniToolbar);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("InitiativeDataRefresh"));
        dialogBox = new Dialog(context);
        dialogBox.setContentView(R.layout.add_goal_initiative_dialog);
        addLinkedOpptsDialog = new Dialog(context);
        addLinkedOpptsDialog.setContentView(R.layout.activity_add_more_opprtunities);
        addLinkedOpptsRecyclerView = (RecyclerView) addLinkedOpptsDialog.findViewById(R.id.addLinkedopptsRecyclerView);
        addMoreOpptDone = (TextView) addLinkedOpptsDialog.findViewById(R.id.addMoreOpptDone);
        linkedOPPTSName = (TextView) addLinkedOpptsDialog.findViewById(R.id.linkedOPPTSName);
        linkedOPPTSSalesStage = (TextView) addLinkedOpptsDialog.findViewById(R.id.linkedOPPTSSalesStage);
        linkedOPPTSClosedDate = (TextView) addLinkedOpptsDialog.findViewById(R.id.linkedOPPTSClosedDate);
        selectedLinkedOpptsRecyclerView = (RecyclerView) findViewById(R.id.selectedLinkedOpptsRecyclerView);
        initiateLeadNameTextView = (TextView) findViewById(R.id.initiateLeadName);
        yearSpinnerLinearLayout = findViewById(R.id.yearSpinnerLinearLayout);
        contactsLeadDialog = new Dialog(context);
        contactsLeadDialog.setContentView(R.layout.contact_ibm);
        contactsLeadName = (TextView) contactsLeadDialog.findViewById(R.id.contactsLeadName);
        contactsLeadEmail = (TextView) contactsLeadDialog.findViewById(R.id.contactsLeadEmail);
        contactsLeadRole = (TextView) contactsLeadDialog.findViewById(R.id.contactsLeadRole);
        createdDate = (TextView) findViewById(R.id.createdDate);
        editContactsLead = (EditText) contactsLeadDialog.findViewById(R.id.contactsSearchEditText);
        contactsRecyclerView = (RecyclerView) contactsLeadDialog.findViewById(R.id.contactsRecycleView);
        contactsLeadLinearLayout = contactsLeadDialog.findViewById(R.id.contactListLinearLayout);
        cancelEditText = (TextView) contactsLeadDialog.findViewById(R.id.cancelEditText);
        createdDateTextView = (TextView) findViewById(R.id.createdDate);
        popupTitle = (TextView) dialogBox.findViewById(R.id.goalInitiativePageTitle);
        dialog_recycleView = (RecyclerView) dialogBox.findViewById(R.id.addGoal_initiative_RecyclerView);
        listView = (ListView) dialogBox.findViewById(R.id.initiative_dialog_View);
        selectedStrategicImperativesRecyclerView = (RecyclerView) findViewById(R.id.strategic_initiativeSelectedRecyclerView);
        selectedGoalsArrayList = new ArrayList<GOALS>();
        selectedGeoList = new ArrayList<GEO>();
        selectedMarketList = new ArrayList<REGION>();
        selectedCountryList = new ArrayList<COUNTRY>();
        selectedBUList = new ArrayList<RELEVANTBRANDSORUNITS>();
        linkedopptList = new ArrayList<LINKEDOPPT>();
        selectedLinkedOpptList = new ArrayList<LINKEDOPPT>();
        selectedGoalsMultiselectRecyclerView = (RecyclerView) findViewById(R.id.selectedGoalRecyclerView);
        selectedGeoListRecyclerView = (RecyclerView) findViewById(R.id.geoSelectRecyclerView);
        selectedMarketListRecyclerView = (RecyclerView) findViewById(R.id.marketSelectRecyclerView);
        selectedCountryListRecyclerView = (RecyclerView) findViewById(R.id.countrySelectRecyclerView);
        selectedBURecyclerView = (RecyclerView) findViewById(R.id.relevantBURecyclerView);
        previousLabelTextView.setTextColor(Color.GRAY);
        selectedStrategicImperativesList = new ArrayList<STRATEGICIMPERATIVES>();
        progressLinearLayout = findViewById(R.id.intitiativeNewProgress);
        industrySolutionsLinearLayout = findViewById(R.id.industrySolutionSpinnerLayout);
        partnerRoleLinearLayout = findViewById(R.id.partner_role);
        progressSpinner = new Spinner(this);
        industrySolutionsSpinner = new Spinner(this);
        partnerRoleSpinner = new Spinner(this);
        yearSpinner = new Spinner(this);
        ini_name = findViewById(R.id.initiativeName);
        iniNameCharacterCount = findViewById(R.id.iniNameCharacters);
        ini_value = findViewById(R.id.initiative_value);
        ini_value.addTextChangedListener(new NumberTextWatcher(ini_value));
        ini_desc = findViewById(R.id.initiativeDescription);
        ini_clientValue = findViewById(R.id.ini_clientValue);
        linkedOpptsCountLabel = (TextView) findViewById(R.id.linkedOpptCount);
        linkedOpptsTotalValue = (TextView) findViewById(R.id.totalLinkedOpptTextValue);
        addMoreLinkedOpprtunities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//System.out.println("mviewmodel"+mviewModel+" moel obj:"+initiativeFields_model.getInitiativeDefault().getV1_INITIATIVEID());
                if (initiativeFields_model.getInitiativeDefault().getV1_INITIATIVEID().length() > 1) {
                    addLinkedOpptsDialog.show();
                    View dialogButton = (View) addLinkedOpptsDialog.findViewById(R.id.cancelLinkedBtn);

                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addLinkedOpptsDialog.dismiss();
                        }
                    });
                } else
                    Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.SAVE_INI_TO_LINK_OPPO, Toast.LENGTH_SHORT).show();

            }
        });

        addMoreOpptDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLinkedOpptsDialog.dismiss();
                displaySelectedLinkedOppts();
            }
        });

        try {
            initiativeKey = getIntent().getExtras().getString("InitiativeId");
        } catch (NullPointerException e) {
            System.out.print("NullPointerException Caught");
        }

        clientId = ISAP_Utils.clientID;

        if (initiativeKey.length() > 1) {
            isEditingFlag = true;
            fetchInitiativeDetails(clientId, Integer.parseInt(initiativeKey));
        } else {
            isEditingFlag = false;
            fetchInitiativeDetails(clientId, 0);
        }

        editContactsLead.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
//                System.out.println("search char after : "+arg0);
                if (arg0.length() > 2) {
                    getProfiledata(editContactsLead.getText().toString());
                }
            }
        });
        cancelEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //contactsLeadDialog.dismiss();
                editContactsLead.setText("");
                contactsLeadLinearLayout.setVisibility(View.INVISIBLE);
                contactsLeadLinearLayout.setVisibility(View.GONE);
                contactList.clear();
            }
        });
        nextLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(initiativeLayoutPageNumber == 3)) {
                    if (ini_name == null || ini_name.getText().length() == 0) {
                        ShapeDrawable sd = new ShapeDrawable();
                        sd.setShape(new RectShape());
                        sd.getPaint().setColor(Color.RED);
                        sd.getPaint().setStrokeWidth(10f);
                        sd.getPaint().setStyle(Paint.Style.STROKE);
                        ini_name.setBackground(sd);
                        isRedboxVisible = true;
                    } else if (isItemSelected == true) {
                        Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.FILL_LINKED_GOALS, Toast.LENGTH_SHORT).show();
                    } else if (leadCnum == null || leadCnum.length() == 0) {
                        Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.FILL_INITIATIVE_LEAD, Toast.LENGTH_SHORT).show();
                    } else if (initiativeLayoutPageNumber == 2) {
                        if (isStrategicImperativesSelected == true)
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.FILL_STATEGIC_IMPERTIVES, Toast.LENGTH_SHORT).show();
                        else {
                            initiativeLayoutPageNumber++;
                            animateForwardViewLayouts();
                        }
                    } else {
                        initiativeLayoutPageNumber++;
                        animateForwardViewLayouts();
                    }
                }
            }
        });

        previousLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(initiativeLayoutPageNumber == 1)) {
                    initiativeLayoutPageNumber--;
                    animateBackwardViewLayouts();
                }
            }
        });
        intitiativeContactsPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactsLeadDialog.show();
//                contactsLeadLinearLayout.setVisibility(View.INVISIBLE);
                contactsLeadLinearLayout.setVisibility(View.GONE);
                contactList = contactList == null ? new ArrayList<InitiativeContactModel>() : contactList;
                if (contactList != null && contactList.size() != 0) {
                    contactsLeadLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        geoPopupClick = (View) findViewById(R.id.geoPopUpClick);
        geoPopupClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiselectDialog("geo", "Geo");
            }
        });

        marketPopupClick = (View) findViewById(R.id.marketPopUpClick);
        marketPopupClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllGeos) {/*all geos selected*/
                    Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.ERROR_GEO, Toast.LENGTH_SHORT).show();
                } else {

                    if (!(selectedGeoList == null || selectedGeoList.size() == 0)) {
                        showMultiselectDialog("market", "Market");
                    }
                }
            }
        });

        countryPopupClick = (View) findViewById(R.id.countryPopUpClick);
        countryPopupClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllGeos) {/*all geos selected*/
                    Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.ERROR_GEO, Toast.LENGTH_SHORT).show();

                } else {

                    if (!(selectedMarketList == null || selectedMarketList.size() == 0)) {
                        showMultiselectDialog("country", "Countries");
                    }
                }
            }
        });

        strategicImperativesView = (View) findViewById(R.id.strategicImperativesButton);
        strategicImperativesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showMultiselectDialog("strategicImperatives", "Strategic Imperatives");
            }
        });

        relevantBU = (View) findViewById(R.id.relevantBUIcon);
        relevantBU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiselectDialog("relevantBU", "Relevant Brand and Units");
            }
        });
//Display Linked Goals
        linkedGoalsView = (View) findViewById(R.id.intitiativeLinkedGoal_icon);
        linkedGoalsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiselectDialog("goal", "Linked Goals");
            }
        });

        saveInitiativeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean status = initiativeValidation();
                emptySplitValues();
//                String jsonInString = gson.toJson(mviewModel);
                populateInitiativeModel();
                if (status == true) {
                    if (!(mviewModel.getStatusKey().equalsIgnoreCase("NONE"))&&(mviewModel.getRoleKey().equalsIgnoreCase(""))) {

                        Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_STATUS_KEY, Toast.LENGTH_SHORT).show();
                    } else {
                        saveInitiativeDetails();
                    }
                }
            }
        });
        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                if (isRedboxVisible) {
                    ini_name.setBackgroundResource(0);
                    isRedboxVisible = true;
                }
                iniNameCharacterCount.setText(String.valueOf(s.length()) + "/50");
            }

            public void afterTextChanged(Editable s) {
            }
        };
        ini_name.addTextChangedListener(mTextEditorWatcher);


        splitValue1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // splitValue1.setText(temparaySplit[1]);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    String iniValue = ini_value.getText().toString().trim();
                    if (iniValue.isEmpty()) {
                        Toast.makeText(NewInitiativeActivity.this, "Please enter value", Toast.LENGTH_LONG).show();
                    } else {

                        if (editable.toString().isEmpty() || editable.toString().equals("0") || editable.toString().equals(" ") || editable.toString().equals("")) {
                            temparaySplit[1] = "0.00";
                            getSubtractionRemaingValue();
                        } else {
                            if (!splitValue1Changed) {
                                getAdditionRemainingValue(1, editable.toString());
                            }
                            splitValue1Changed = false;
                        }


                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }


            }
        });


        splitValue2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    String iniValue = ini_value.getText().toString().trim();
                    if (iniValue.isEmpty()) {
                        Toast.makeText(NewInitiativeActivity.this, "Please enter value", Toast.LENGTH_LONG).show();
                    } else {
                        if (editable.toString().isEmpty() || editable.toString().equals("0") || editable.toString().equals(" ") || editable.toString().equals("")) {
                            temparaySplit[2] = "0.00";
                            getSubtractionRemaingValue();
                        } else {
                            if (!splitValue2Changed) {
                                getAdditionRemainingValue(2, editable.toString());
                            }
                            splitValue2Changed = false;
                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        });


        splitValue3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    String iniValue = ini_value.getText().toString().trim();
                    if (iniValue.isEmpty()) {
                        Toast.makeText(NewInitiativeActivity.this, "Please enter value", Toast.LENGTH_LONG).show();
                    } else {
                        if (editable.toString().isEmpty() || editable.toString().equals("0") || editable.toString().equals(" ") || editable.toString().equals("")) {
                            temparaySplit[3] = "0.00";
                            getSubtractionRemaingValue();
                        } else {
                            if (!splitValue3Changed) {
                                getAdditionRemainingValue(3, editable.toString());
                            }
                            splitValue3Changed = false;
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.printLog(NewInitiativeActivity.this,"splitValue3.addTextChangedListener",e.getMessage());
                }


            }
        });


        splitValue4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    String iniValue = ini_value.getText().toString().trim();
                    if (iniValue.isEmpty()) {
                        Toast.makeText(NewInitiativeActivity.this, "Please enter value", Toast.LENGTH_LONG).show();
                    } else {
                        if (editable.toString().isEmpty() || editable.toString().equals("0") || editable.toString().equals(" ") || editable.toString().equals("")) {
                            temparaySplit[4] = "0.00";
                            getSubtractionRemaingValue();
                        } else {
                            if (!splitValue4Changed) {
                                getAdditionRemainingValue(4, editable.toString());
                            }
                            splitValue4Changed = false;
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.printLog(NewInitiativeActivity.this,"splitValue4.addTextChangedListener",e.getMessage());
                }


            }
        });
        splitValue1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                try {
                    if (!hasFocus) {/*false*/
                        /*splitValue1Changed = true;
                        Double split1DisplayValue = Double.parseDouble(splitValue1.getText().toString());
                        split1DisplayValue = split1DisplayValue/1000000;
                        String str = String.format("%1.2f", split1DisplayValue);
                        split1DisplayValue = Double.valueOf(str);
                        splitDisplayList[1] = String.valueOf(split1DisplayValue);
                        getAdditionRemainingValue(1,splitValue1.getText().toString());
                        String finalSplitValue1String = split1DisplayValue + " M";
                        splitValue1.setText(finalSplitValue1String);*/

                        splitValue1Changed = true;
                        String value = splitValue1.getText().toString();
                        String million = getBigDecimalToMillian(value);
                        million = million.replace(".00", "");

                        splitDisplayList[1] = million;
                        getAdditionRemainingValue(1, splitValue1.getText().toString());
                        String finalSplitValue1String = million + " M";
                        splitValue1.setText(finalSplitValue1String);


                    } else {/*true*/
//                        splitIndex = 1;

                        resetColorView(1);
                        splitValue1.requestFocus();
                        if (!temparaySplit[1].equals("0.00")) {
                            splitValue1.setText(temparaySplit[1].replace(".00", ""));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.printLog(NewInitiativeActivity.this,"splitValue1.setOnFocusChangeListener",e.getMessage());
                }

            }
        });

        splitValue2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {/*false*/
                        /*splitValue2Changed = true;
                        Double split2DisplayValue = Double.parseDouble(splitValue2.getText().toString());
                        split2DisplayValue = split2DisplayValue/1000000;
                        String str = String.format("%1.2f", split2DisplayValue);
                        split2DisplayValue = Double.valueOf(str);
                        splitDisplayList[2] = String.valueOf(split2DisplayValue);
                        getAdditionRemainingValue(2,splitValue2.getText().toString());
                        String finalSplitValue2String = split2DisplayValue + " M";
                        splitValue2.setText(finalSplitValue2String);*/


                        splitValue2Changed = true;
                        String value = splitValue2.getText().toString();
                        String million = getBigDecimalToMillian(value);
                        million = million.replace(".00", "");

                        splitDisplayList[2] = million;
                        getAdditionRemainingValue(2, splitValue2.getText().toString());
                        String finalSplitValue1String = million + " M";
                        splitValue2.setText(finalSplitValue1String);


                    } else {/*true*/
//                        splitIndex = 2;
                        resetColorView(2);
                        splitValue2.requestFocus();
                        if (!temparaySplit[2].equals("0.00")) {
                            splitValue2.setText(temparaySplit[2].replace(".00", ""));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.printLog(NewInitiativeActivity.this,"splitValue2.setOnFocusChangeListener",e.getMessage());
                }
            }
        });

        splitValue3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {/*false*/
                        /*splitValue3Changed = true;
                        Double split3DisplayValue = Double.parseDouble(splitValue3.getText().toString());
                        split3DisplayValue = split3DisplayValue/1000000;
                        String str = String.format("%1.2f", split3DisplayValue);
                        split3DisplayValue = Double.valueOf(str);
                        splitDisplayList[3] = String.valueOf(split3DisplayValue);
                        getAdditionRemainingValue(3,splitValue2.getText().toString());
                        String finalSplitValue3String = split3DisplayValue + " M";
                        splitValue3.setText(finalSplitValue3String);*/

                        splitValue3Changed = true;
                        String value = splitValue3.getText().toString();
                        String million = getBigDecimalToMillian(value);
                        million = million.replace(".00", "");

                        splitDisplayList[3] = million;
                        getAdditionRemainingValue(3, splitValue3.getText().toString());
                        String finalSplitValue1String = million + " M";
                        splitValue3.setText(finalSplitValue1String);


                    } else {/*true*/
//                        splitIndex = 3;
                        if (spinnerPosition < 3) {
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.ERROR_CLOSE_DATE, Toast.LENGTH_SHORT).show();
                            splitValue3.clearFocus();
                        } else {
                            resetColorView(3);
                            splitValue3.requestFocus();
                            if (!temparaySplit[3].equals("0.00")) {
                                splitValue3.setText(temparaySplit[3].replace(".00", ""));
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.printLog(NewInitiativeActivity.this,"splitValue3.setOnFocusChangeListener",e.getMessage());

                }
            }
        });
        splitValue4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {/*false*/
                        /*splitValue4Changed = true;
                        Double split4DisplayValue = Double.parseDouble(splitValue4.getText().toString());
                        split4DisplayValue = split4DisplayValue/1000000;
                        String str = String.format("%1.2f", split4DisplayValue);
                        split4DisplayValue = Double.valueOf(str);
                        splitDisplayList[4] = String.valueOf(split4DisplayValue);
                        getAdditionRemainingValue(4,splitValue4.getText().toString());
                        String finalSplitValue4String = split4DisplayValue + " M";
                        splitValue4.setText(finalSplitValue4String);*/
                        splitValue4Changed = true;
                        String value = splitValue4.getText().toString();
                        String million = getBigDecimalToMillian(value);
                        million = million.replace(".00", "");

                        splitDisplayList[4] = million;
                        getAdditionRemainingValue(4, splitValue4.getText().toString());
                        String finalSplitValue1String = million + " M";
                        splitValue4.setText(finalSplitValue1String);


                    } else {/*true*/
//                        splitIndex = 4;
                        if (spinnerPosition < 4) {
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.ERROR_CLOSE_DATE, Toast.LENGTH_SHORT).show();
                            splitValue4.clearFocus();
                        }else {
                            resetColorView(4);
                            splitValue4.requestFocus();
                            if (!temparaySplit[4].equals("0.00")) {
                                splitValue4.setText(temparaySplit[4].replace(".00", ""));
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.printLog(NewInitiativeActivity.this,"splitValue4.setOnFocusChangeListener",e.getMessage());
                }
            }
        });

        mExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpand) {/*View closed*/
                    isExpand = false;
                    mLinearSplit.setVisibility(View.GONE);
                    mRemaingValue.setVisibility(View.GONE);
                    ((View) findViewById(R.id.show_collaps_plus)).setVisibility(View.VISIBLE);
                    ((View) findViewById(R.id.show_collaps_minus)).setVisibility(View.GONE);

                } else {/*View Open*/
                    isExpand = true;
                    mLinearSplit.setVisibility(View.VISIBLE);
                    mRemaingValue.setVisibility(View.VISIBLE);
                    ((View) findViewById(R.id.show_collaps_plus)).setVisibility(View.GONE);
                    ((View) findViewById(R.id.show_collaps_minus)).setVisibility(View.VISIBLE);
                }
            }
        });
        ini_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                compareSplitCurrentYear(closeDate);

            }
        });
        if(isEditingFlag){/*edit init*/
            ((LinearLayout)findViewById(R.id.ll_linked_oop)).setVisibility(View.VISIBLE);
        }else {/*new init*/
            ((LinearLayout)findViewById(R.id.ll_linked_oop)).setVisibility(View.GONE);
        }
    }


    public void onStart() {
        super.onStart();
        toolbar = findViewById(R.id.ini_toolbar);
        if (initiativeKey.length() > 1)
            toolbar.setText("Edit Initiative");
        else toolbar.setText("New Initiative");

        initialiseSpliteArray();
    }

    private void initialiseSpliteArray() {
        temparaySplit = new String[intialSize];

        temparaySplit[0] = "0.00";
        temparaySplit[1] = "0.00";
        temparaySplit[2] = "0.00";
        temparaySplit[3] = "0.00";
        temparaySplit[4] = "0.00";


        splitDisplayList = new String[intialSize];
        splitDisplayList[0] = "0.00";
        splitDisplayList[1] = "0.00";
        splitDisplayList[2] = "0.00";
        splitDisplayList[3] = "0.00";
        splitDisplayList[4] = "0.00";
    }

    private void compareGoalsForEditandDefaultData() {

        try {
            selectedGoalsArrayList.clear();
            List<String> goalsKeyList = Arrays.asList(initiativeFields_model.getInitiativeDefault().getV1_LINKEDGOALKEYS());
            for (int i = 0; i < goalsList.size(); i++) {

                if (goalsKeyList.contains(goalsList.get(i).getKEY())) {
                    goalsList.get(i).setisSelected(true);
                    selectedGoalsArrayList.add(goalsList.get(i));
                }
            }
            if (selectedGoalsArrayList.size() > 0)
                isItemSelected = false;
            else
                isItemSelected = true;
            createSelectedGoalsListUI(selectedGoalsArrayList);

        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"compareGoalsForEditandDefaultData",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }

    private void compareStrategicImperativesForEditAndDefaultData() {

        try {
            selectedStrategicImperativesList.clear();
            List<String> strategicImperativesKeyList = new ArrayList<String>();
            strategicImperativesKeyList = convertSavedStrategicImperativesObjectToStringList();
            for (int i = 0; i < strategicImperativesList.size(); i++) {
                if (strategicImperativesKeyList.contains(strategicImperativesList.get(i).getKEY())) {
                    strategicImperativesList.get(i).setisSelected(true);
                    selectedStrategicImperativesList.add(strategicImperativesList.get(i));
                }
            }
            if (selectedStrategicImperativesList.size() > 0)
                isStrategicImperativesSelected = false;
            else
                isStrategicImperativesSelected = true;
            createSelectedStrategicImperativesListUI(selectedStrategicImperativesList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"compareStrategicImperativesForEditAndDefaultData",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> convertSavedStrategicImperativesObjectToStringList() {
        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < initiativeFields_model.getInitiativeDefault().getV2_STRATEICIMPERATEIVES().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeDefault().getV2_STRATEICIMPERATEIVES()[i].getKEY());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertSavedStrategicImperativesObjectToStringList",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void compareRelevantBrandUnitsDataForEditAndDefaultData() {
        try {
            selectedBUList.clear();
            for (int i = 0; i < relevantBUList.size(); i++) {
                if (!(relevantBUList.get(i).isSection())) {
                    for (int j = 0; j < initiativeFields_model.getInitiativeDefault().getV2_RELEVANTUNITS().length; j++) {
                        if (relevantBUList.get(i).getKEY().equalsIgnoreCase(initiativeFields_model.getInitiativeDefault().getV2_RELEVANTUNITS()[j].getKEY())) {
                            relevantBUList.get(i).setPCT(initiativeFields_model.getInitiativeDefault().getV2_RELEVANTUNITS()[j].getPCT());
                            relevantBUList.get(i).setisSelected(true);
                            selectedBUList.add(relevantBUList.get(i));
                        }
                    }
                }
            }
            createdSelectedBUnitsListUI(selectedBUList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"compareRelevantBrandUnitsDataForEditAndDefaultData",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> convertSavedGoalsObjectToStringList() {
        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < initiativeFields_model.getInitiativeDefault().getV3_GEOS().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeDefault().getV3_GEOS()[i].getKEY());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertSavedGoalsObjectToStringList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void compareGeoDataForEditandSave() {
        try {
            selectedGeoList.clear();
            List<String> geoKeyList = new ArrayList<String>();
            geoKeyList = convertSavedGoalsObjectToStringList();
            for (int i = 0; i < geoList.size(); i++) {
                if (geoKeyList.contains(geoList.get(i).getKEY())) {
                    geoList.get(i).setisSelected(true);
                    selectedGeoList.add(geoList.get(i));
                }
            }
            createSelectedGeoListUI(selectedGeoList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"compareGeoDataForEditandSave",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> convertSavedRegionsObjectToStringList() {
        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < initiativeFields_model.getInitiativeDefault().getV3_REGIONS().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeDefault().getV3_REGIONS()[i].getKEY());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertSavedRegionsObjectToStringList",e.getMessage());
            //  Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void compareRegionDatForEditandSave() {
        try {
            selectedMarketList.clear();
            List<String> regionKeyList = new ArrayList<String>();
            regionKeyList = convertSavedRegionsObjectToStringList();
            for (int i = 0; i < marketList.size(); i++) {
                if (regionKeyList.contains(marketList.get(i).getKEY())) {
                    marketList.get(i).setisSelected(true);
                    selectedMarketList.add(marketList.get(i));
                }
            }
            createSelectedMarketListUI(selectedMarketList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"compareRegionDatForEditandSave",e.getMessage());

            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> convertSavedCountryObjectToStringList() {
        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < initiativeFields_model.getInitiativeDefault().getV3_COUNTRIES().length; i++) {

                stringList.add(initiativeFields_model.getInitiativeDefault().getV3_COUNTRIES()[i].getKEY());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertSavedCountryObjectToStringList",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void compareCountryDatForEditandSave() {
        try {
            selectedCountryList.clear();
            List<String> countryKeyList = new ArrayList<String>();
            countryKeyList = convertSavedCountryObjectToStringList();
            for (int i = 0; i < countryList.size(); i++) {
                if (countryKeyList.contains(countryList.get(i).getKEY())) {
                    countryList.get(i).setisSelected(true);
                    selectedCountryList.add(countryList.get(i));
                }
            }
            createSelectedCountryListUI(selectedCountryList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"compareCountryDatForEditandSave",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUIFieldsforEditingInitiative() {
        try {
            if (initiativeFields_model.getInitiativeDefault().getV1_CREATEDATE() != null && initiativeFields_model.getInitiativeDefault().getV1_CREATEDATE().length() > 1) {
                createdDateTextView.setText(initiativeFields_model.getInitiativeDefault().getV1_CREATEDATE());
            }
            ini_name.setText(initiativeFields_model.getInitiativeDefault().getV1_INITIATIVENAME());
            ini_value.setText(initiativeFields_model.getInitiativeDefault().getV1_VALUE().split("\\.", 2)[0]);
            ini_desc.setText(initiativeFields_model.getInitiativeDefault().getV2_DESCRIBEINITIATIVE());
            initiateLeadNameTextView.setText(initiativeFields_model.getInitiativeDefault().getV1_INITIATIVELEADID());
            leadCnum = initiativeFields_model.getInitiativeDefault().getV1_INITIATIVELEADCNUM();
            initiativeKey = initiativeFields_model.getInitiativeDefault().getV1_INITIATIVEID();
            ini_clientValue.setText(initiativeFields_model.getInitiativeDefault().getV2_VALUETOCLIENT());
            populatePartnerStatus();
            populateSplitFields();
            if (initiativeFields_model.getInitiativeDefault().getV1_1H2H().equalsIgnoreCase("02")) {
                aSwitch.setChecked(true);
                h1h2 = "02";
            } else {
                aSwitch.setChecked(false);
                h1h2 = "01";
            }
            createDataListForStrategicMultiselectRecyclerView();
            compareStrategicImperativesForEditAndDefaultData();

            createDataListForGoalsMultiselectRecyclerView();
            compareGoalsForEditandDefaultData();

            createDataListforRelevantBrandUnits();
            compareRelevantBrandUnitsDataForEditAndDefaultData();

            createDataListForGeoMultiselectRecyclerView();
            compareGeoDataForEditandSave();

            createDataListForSavedMarketList();
            compareRegionDatForEditandSave();

            createDataListForSavedCountryList();
            compareCountryDatForEditandSave();

            createDataListForAddedLinkedOpptsRecyclerView();
            compareLinkedOpptsDateForEditandSave();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"updateUIFieldsforEditingInitiative",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void compareLinkedOpptsDateForEditandSave() {
        try {
            Double opptTotalValue = 0.0;
            selectedLinkedOpptList.clear();
            List<String> linkedOpptsKeyList = new ArrayList<String>();
            linkedOpptsKeyList = convertSavedLinkedOpptsObjectToStringList();
            for (int i = 0; i < linkedopptList.size(); i++) {
                if (linkedOpptsKeyList.contains(linkedopptList.get(i).getOPPORTUNITY_ID())) {
                    linkedopptList.get(i).setisSelected(true);
                    selectedLinkedOpptList.add(linkedopptList.get(i));
                    opptTotalValue = opptTotalValue + (Double.parseDouble(linkedopptList.get(i).getTCV_OPPTY_VAL()));
                }
            }
            opptTotalValue = opptTotalValue / 1000;
            linkedOpptsCountLabel.setText(Integer.toString(selectedLinkedOpptList.size()) + " ");
            linkedOpptsTotalValue.setText("$" + Double.toString(opptTotalValue) + "k");
            createSelectedLinkedOpptsListUI(selectedLinkedOpptList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"compareLinkedOpptsDateForEditandSave",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }


    private List<String> convertSavedLinkedOpptsObjectToStringList() {
        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < initiativeFields_model.getInitiativeDefault().getV3_LINKEDOPT().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeDefault().getV3_LINKEDOPT()[i].getOPPORTUNITY_ID());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertSavedLinkedOpptsObjectToStringList",e.getMessage());
            //  Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void populateSplitFields() {

        try {
            /*splitValue0.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[0].getPCT() + "%");
            splitValue1.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[1].getPCT() + "%");
            splitValue2.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[2].getPCT() + "%");
            splitValue3.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[3].getPCT() + "%");
            splitValue4.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[4].getPCT() + "%");*/


            splitYear0.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[0].getYEAR());
            splitYear1.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[1].getYEAR());
            splitYear2.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[2].getYEAR());
            splitYear3.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[3].getYEAR());
            splitYear4.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[4].getYEAR());

           /*
            if(Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[0].getVALUE())!=0)

                splitLabel0.setText("$"+Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[0].getVALUE())/1000000+"M");
            if(Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[1].getVALUE())!=0)
                splitLabel1.setText("$"+Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[1].getVALUE())/1000000+"M");
            if(Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[2].getVALUE())!=0)
                splitLabel2.setText("$"+Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[2].getVALUE())/1000000+"M");
            if(Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[3].getVALUE())!=0)
                splitLabel3.setText("$"+Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[3].getVALUE())/1000000+"M");
            if(Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[4].getVALUE())!=0)
                splitLabel4.setText("$"+Double.parseDouble(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[4].getVALUE())/1000000+"M");*/
            String value0, value1, value2, value3, value4;
            value0 = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[0].getVALUE();
            value1 = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[1].getVALUE();
            value2 = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[2].getVALUE();
            value3 = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[3].getVALUE();
            value4 = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[4].getVALUE();


            if (value0 != null) {
                String million = getBigDecimalToMillian(value0);
                million = million.replace(".00", "");
                if (!million.equals("0")) {
                    splitValue0.setText(million + " M");
                }

            }
            if (value1 != null) {
                String million = getBigDecimalToMillian(value1);
                million = million.replace(".00", "");
                if (!million.equals("0")) {
                    splitValue1.setText(million + " M");
                }

            }
            if (value2 != null) {
                String million = getBigDecimalToMillian(value2);
                million = million.replace(".00", "");
                if (!million.equals("0")) {
                    splitValue2.setText(million + " M");
                }
            }
            if (value3 != null) {
                String million = getBigDecimalToMillian(value3);
                million = million.replace(".00", "");
                if (!million.equals("0")) {
                    splitValue3.setText(million + " M");
                }
            }
            if (value4 != null) {
                String million = getBigDecimalToMillian(value4);
                million = million.replace(".00", "");
                if (!million.equals("0")) {
                    splitValue4.setText(million + " M");
                }
            }


            temparaySplit[0] = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[0].getVALUE();
            temparaySplit[1] = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[1].getVALUE();
            temparaySplit[2] = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[2].getVALUE();
            temparaySplit[3] = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[3].getVALUE();
            temparaySplit[4] = initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[4].getVALUE();
            mRemaingValue.setText("");
            mRemaingValue.setText("$ 0 remaining to assign.");


        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"populateSplitFields",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }


    private void populatePartnerStatus() {
        try {
            for (int i = 0; i < initiativeFields_model.getInitiativeData().getPARTNERSTATUS().length; i++) {
                if (initiativeFields_model.getInitiativeData().getPARTNERSTATUS()[i].getKEY().equalsIgnoreCase(initiativeFields_model.getInitiativeDefault().getV3_PARTNERSTATUSKEY())) {
                    if (((RadioButton) radioGroup.getChildAt(i) != null))
                        ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"populatePartnerStatus",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void sumSpliteValues() {
        try {
            String boxString1 = splitValue1.getText().toString();
            sp1 = Integer.parseInt(boxString1.substring(0, boxString1.length() - 1));
            String boxString2 = splitValue2.getText().toString();
            sp2 = Integer.parseInt(boxString2.substring(0, boxString2.length() - 1));
            String boxString3 = splitValue3.getText().toString();
            sp3 = Integer.parseInt(boxString3.substring(0, boxString3.length() - 1));
            String boxString4 = splitValue4.getText().toString();
            sp4 = Integer.parseInt(boxString4.substring(0, boxString4.length() - 1));
            splitTotal = sp1 + sp2 + sp3 + sp4;
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"sumSpliteValues",e.getMessage());
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void initialiseSplitElements() {
        try {
            rlSplit0 = findViewById(R.id.rl_split0);
            rlSplit1 = findViewById(R.id.rl_split1);
            rlSplit2 = findViewById(R.id.rl_split2);
            rlSplit3 = findViewById(R.id.rl_split3);
            rlSplit4 = findViewById(R.id.rl_split4);

            splitValue0 = findViewById(R.id.splitValue0);
            splitValue1 = findViewById(R.id.splitValue1);
            splitValue2 = findViewById(R.id.splitValue2);
            splitValue3 = findViewById(R.id.splitValue3);
            splitValue4 = findViewById(R.id.splitValue4);
//initialise lebels
            /*splitLabel0 = (TextView) findViewById(R.id.splitLabel0);
            splitLabel1 = (TextView) findViewById(R.id.splitLabel1);
            splitLabel2 = (TextView) findViewById(R.id.splitLabel2);
            splitLabel3 = (TextView) findViewById(R.id.splitLabel3);
            splitLabel4 = (TextView) findViewById(R.id.splitLabel4);*/
            //initialise split years
            splitYear0 = findViewById(R.id.splitYear0);
            splitYear1 = findViewById(R.id.splitYear1);
            splitYear2 = findViewById(R.id.splitYear2);
            splitYear3 = findViewById(R.id.splitYear3);
            splitYear4 = findViewById(R.id.splitYear4);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"initialiseSplitElements",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void getSwitchValue() {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked)
                    h1h2 = "02";
                else h1h2 = "01";
            }
        });
    }

    private void saveInitiativeDetails() {
        try {
            ISAP_Utils.showISAPProgressDialog(NewInitiativeActivity.this, ISAP_Constants.SAVE_INITIATIVE_INFO, false);
            IsapService = APiUtils.getUserService();
            gson = new Gson();
            System.out.println("save Initiative info before submit:" + gson.toJson(mviewModel).toString());
            Call call = IsapService.saveInitiativeData(mviewModel);
            call.enqueue(new Callback<GoalResponseObj>() {
                @Override
                public void onResponse(Call<GoalResponseObj> call, Response<GoalResponseObj> response) {
                    //System.out.println("save ini response:"+response.body().toString());
                    GoalResponseObj resp = response.body();
                    LogUtils.printLog(NewInitiativeActivity.this, "saveInitiativeDetails", "save Initiative :" + gson.toJson(resp).toString());

                    if (resp.getFlag().equalsIgnoreCase("true")) {
                        ISAP_Utils.currentPage = 4;
                        if (ISAP_Utils.currentPage == 4)
                            ISAP_Utils.isNewGoal = false;
                        Intent intent = new Intent(NewInitiativeActivity.this, IsapMenuActivity.class);
                        startActivity(intent);
                        ISAP_Utils.dismissProgressDialog();
                        if (resp.getFlag().equalsIgnoreCase("true")) {
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.SAVE_INITIATIVE_DONE, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.SAVE_INITIATIVE_FAILURE, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ISAP_Utils.dismissProgressDialog();
                        Toast.makeText(NewInitiativeActivity.this, resp.getErrormsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GoalResponseObj> call, Throwable t) {
                    ISAP_Utils.dismissProgressDialog();
                    LogUtils.printLog(NewInitiativeActivity.this, "saveInitiativeDetails", "Save InitiativeDetails failure :" + t.getMessage());
                    Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.NETWORK_ISSUE, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"saveInitiativeDetails",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void populateInitiativeModel() {

        try {
            mviewModel = new InitiativeViewModel();
            mviewModel.setClientId(String.valueOf(ISAP_Utils.clientID));
            mviewModel.setIntranetId(ISAP_Utils.LoggedInuserEmail);
            mviewModel.setInitiativeName(ini_name.getText().toString());
            mviewModel.setValue(ini_value.getText().toString().replace(",", ""));
            mviewModel.setValueToClient(ini_clientValue.getText().toString());
            mviewModel.setDescribeText(ini_desc.getText().toString());
            mviewModel.setCloseDate(closeDate);
            mviewModel.setCreateDate(initiativeFields_model.getInitiativeDefault().getV1_CREATEDATE());
            mviewModel.setInititativeId(initiativeKey);
            mviewModel.setH1h2(h1h2);
            mviewModel.setLeadCnum(leadCnum);
            mviewModel.setIndustrySolutionKey(industrykey);
            /*if(!(statuKey.equalsIgnoreCase("NONE"))){

            }*/
            mviewModel.setRoleKey(pRole);
            mviewModel.setProgressKey(progressKey);
            mviewModel.setStatusKey(statuKey);

//            String str = "2.88E9";
//            BigDecimal bd = new BigDecimal(str);
//            bd = bd.round(new MathContext(2, RoundingMode.HALF_UP));
//            System.out.println(bd.toPlainString());
           /* Double[] spliteValues = getSplitValues();
            String[] splitVal = new String[5];
            double total = 0.0;
            for (int i = 0; i < spliteValues.length; i++) {
                System.out.print("e val:" + spliteValues[i]);
                String str = String.valueOf(spliteValues[i]);
                BigDecimal bd = new BigDecimal(str);
                bd = bd.round(new MathContext(2, RoundingMode.HALF_UP));
                System.out.println("convert val" + bd.toPlainString());
                System.out.println("convert val2" + bd.toString());
                splitVal[i] = bd.toPlainString();
//                total= total+Double.parseDouble(bd.toString());
            }*/
//            mviewModel.setSplitValues(temparaySplit);
            Integer[] linkedGoals = getLinkedGoals();
            String[] strategicImpList = getStrategicList();
            Geos[] geoList = getGeolist();
            Regions[] marketList = getmarketList();
            Countries[] countryList = getCountryList();
            Units[] relevantselectedBUList = getRlelevantBUList();
            String[] linkedOpts = getLinkedOpportunity();
            mviewModel.setSplitValues(temparaySplit);
            mviewModel.setStrategicImperativesKey(strategicImpList);
            mviewModel.setLinkedGoals(linkedGoals);

            if (isAllGeos) {/*all geos is selected*/
                geoList = new Geos[1];
                Geos geoObj = new Geos();
                geoObj.setKEY("-1");
                geoObj.setNAME("All");
                geoObj.setID("-1");
                geoList[0] = geoObj;
                mviewModel.setGeos(geoList);

                marketList = new Regions[1];
                Regions obj = new Regions();
                obj.setKEY("-1");
                obj.setNAME("All");
                obj.setID("-1");
                obj.setPARENTKEY("-1");
                marketList[0] = obj;
                mviewModel.setRegions(marketList);

                countryList=new Countries[1];
                Countries objCountry = new Countries();
                objCountry.setKEY("-1");
                objCountry.setNAME("All");
                objCountry.setID("-1");
                objCountry.setPARENTKEY("-1");
                countryList[0] = objCountry;

                mviewModel.setCountries(countryList);

            } else {
                mviewModel.setRegions(marketList);
                mviewModel.setCountries(countryList);
                mviewModel.setGeos(geoList);
            }
            mviewModel.setUnits(relevantselectedBUList);
            mviewModel.setLinkedOppts(linkedOpts);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"populateInitiativeModel",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private String[] getLinkedOpportunity() {

        String[] oppoList = new String[selectedLinkedOpptList.size()];
        try {
            for (int i = 0; i < selectedLinkedOpptList.size(); i++) {
                oppoList[i] = selectedLinkedOpptList.get(i).getOPPORTUNITY_ID();
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getLinkedOpportunity",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return oppoList;
    }

    private Units[] getRlelevantBUList() {

        Units[] relevantBUList = new Units[selectedBUList.size()];
        try {
            for (int i = 0; i < selectedBUList.size(); i++) {
                Units relBuObj = new Units();
                relBuObj.setPCT(Double.valueOf(selectedBUList.get(i).getPCT().substring(0, selectedBUList.get(i).getPCT().length())));
                relBuObj.setKEY(selectedBUList.get(i).getKEY());
                relevantBUList[i] = relBuObj;
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getRlelevantBUList",e.getMessage());
            //  Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return relevantBUList;
    }

    private String[] getStrategicList() {

        String[] strategicList = new String[selectedStrategicImperativesList.size()];
        try {
            for (int i = 0; i < selectedStrategicImperativesList.size(); i++) {
                strategicList[i] = selectedStrategicImperativesList.get(i).getKEY();
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getStrategicList",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return strategicList;
    }

    private Integer[] getLinkedGoals() {

        Integer[] linkedGoals = new Integer[selectedGoalsArrayList.size()];
        try {
            for (int i = 0; i < selectedGoalsArrayList.size(); i++) {
                linkedGoals[i] = Integer.valueOf(selectedGoalsArrayList.get(i).getKEY());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getLinkedGoals",e.getMessage());
            //  Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return linkedGoals;
    }

    private Geos[] getGeolist() {

        Geos[] geoList = new Geos[selectedGeoList.size()];
        try {
            for (int i = 0; i < selectedGeoList.size(); i++) {
                Geos obj = new Geos();
                obj.setKEY(selectedGeoList.get(i).getKEY());
                obj.setNAME(selectedGeoList.get(i).getNAME());
                obj.setID(selectedGeoList.get(i).getID());
                geoList[i] = obj;
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getGeolist",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return geoList;
    }

    private Regions[] getmarketList() {

        Regions[] marketList = new Regions[selectedMarketList.size()];
        try {
            for (int i = 0; i < selectedMarketList.size(); i++) {
                Regions obj = new Regions();
                obj.setKEY(selectedMarketList.get(i).getKEY());
                obj.setNAME(selectedMarketList.get(i).getNAME());
                obj.setID(selectedMarketList.get(i).getID());
                obj.setPARENTKEY(selectedMarketList.get(i).getPARENTKEY());
                marketList[i] = obj;
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getmarketList",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return marketList;
    }

    private Countries[] getCountryList() {

        Countries[] countryList = new Countries[selectedCountryList.size()];
        try {
            for (int i = 0; i < selectedCountryList.size(); i++) {
                Countries obj = new Countries();
                obj.setKEY(selectedCountryList.get(i).getKEY());
                obj.setNAME(selectedCountryList.get(i).getNAME());
                obj.setID(selectedCountryList.get(i).getID());
                obj.setPARENTKEY(selectedCountryList.get(i).getPARENTKEY());
                countryList[i] = obj;
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getCountryList",e.getMessage());
            //  Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return countryList;
    }

    /*private Double[] getSplitValues() {
        Double[] spliteValues = new Double[5];

        try {
            if (Double.valueOf(splitLabel0.getText().toString().substring(1, splitLabel0.getText().toString().length() - 1)) != 0.0)
                spliteValues[0] = Double.valueOf(splitLabel0.getText().toString().substring(1, splitLabel0.getText().toString().length() - 1)) * 1000000;
            else spliteValues[0] = 0.0;
            if (Double.valueOf(splitLabel1.getText().toString().substring(1, splitLabel1.getText().toString().length() - 1)) != 0.0)
                spliteValues[1] = Double.valueOf(splitLabel1.getText().toString().substring(1, splitLabel1.getText().toString().length() - 1)) * 1000000;
            else spliteValues[1] = 0.0;
            if (Double.valueOf(splitLabel2.getText().toString().substring(1, splitLabel2.getText().toString().length() - 1)) != 0.0)
                spliteValues[2] = Double.valueOf(splitLabel2.getText().toString().substring(1, splitLabel2.getText().toString().length() - 1)) * 1000000;
            else spliteValues[2] = 0.0;
            if (Double.valueOf(splitLabel3.getText().toString().substring(1, splitLabel3.getText().toString().length() - 1)) != 0.0)
                spliteValues[3] = Double.valueOf(splitLabel3.getText().toString().substring(1, splitLabel3.getText().toString().length() - 1)) * 1000000;
            else spliteValues[3] = 0.0;
            if (Double.valueOf(splitLabel4.getText().toString().substring(1, splitLabel4.getText().toString().length() - 1)) != 0.0)
                spliteValues[4] = Double.valueOf(splitLabel4.getText().toString().substring(1, splitLabel4.getText().toString().length() - 1)) * 1000000;
            else spliteValues[4] = 0.0;
        } catch (Exception e) {
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return spliteValues;
    }*/

    private boolean initiativeValidation() {
        if (ini_name == null || ini_name.getText().length() == 0) {
            ShapeDrawable sd = new ShapeDrawable();
            sd.setShape(new RectShape());
            sd.getPaint().setColor(Color.RED);
            sd.getPaint().setStrokeWidth(10f);
            sd.getPaint().setStyle(Paint.Style.STROKE);
            ini_name.setBackground(sd);
            isRedboxVisible = true;
            return false;
        } else if (closeDate.isEmpty()) {
            Toast.makeText(NewInitiativeActivity.this, "Please select close date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ini_value == null || ini_value.getText().length() == 0) {
            Toast.makeText(NewInitiativeActivity.this, "Please enter value", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isItemSelected == true) {
            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.FILL_LINKED_GOALS, Toast.LENGTH_SHORT).show();
            return false;
        } else if (leadCnum == null || leadCnum.length() == 0) {
            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.FILL_INITIATIVE_LEAD, Toast.LENGTH_SHORT).show();
            return false;
        } else if (isStrategicImperativesSelected == true) {
            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.FILL_STATEGIC_IMPERTIVES, Toast.LENGTH_SHORT).show();
            return false;
        }
        /*else if (splitTotal != 100) {
            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_SPLITE_STATUS, Toast.LENGTH_SHORT).show();
            return false;
        } */
        else
            return true;
    }

    private void displaySeekBar() {
        /*seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Write code to perform some action when progress is changed.
                displaySplitvalue("x", seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is started.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is stopped.
            }
        });*/
    }

    private void getProfiledata(String editTextFieldValue) {

        try {
            IsapService = APiUtils.getProfileInfoService();
            Call call = IsapService.getProfileInfo("optimized_search", editTextFieldValue);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ISAP_Utils.dismissProgressDialog();
//                Response<Response> r1esp = response.body();
                    gson = new Gson();
//                String data=gson.toJson(response.body());

                    try {
                        if (contactList != null) {
                            contactList.clear();
                        } else {
                            contactList = new ArrayList<InitiativeContactModel>();
                        }
                        String data = response.body().string();
                        JsonObject jsonObject = (new JsonParser()).parse(data).getAsJsonObject();
                        LogUtils.printLog(NewInitiativeActivity.this, "getProfiledata", "get profile data:" + gson.toJson(data).toString());

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
                        showInitiativeLeadRecyclerView(contactList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ISAP_Utils.dismissProgressDialog();
                    LogUtils.printLog(NewInitiativeActivity.this, "getProfiledata", "get profile data failure" + t.getMessage());
                }
            });
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getProfiledata",e.getMessage());
            //  Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setSplitValue(View v) {
//        displaySplitvalue(v.getTag().toString(), 0);
    }

    /*public void displaySplitvalue(String v, int seekValue) {
        try {
            if (ini_value.getText().length() > 0) {


                Double tcvVal = Double.parseDouble(ini_value.getText().toString().replace(",", ""));
                if (seekValue == 0 && !(v.equalsIgnoreCase("x")))
                    splitIndex = Integer.parseInt(v);
                if (splitIndex == 1) {
//                    sumSpliteValues();
                    if (seekValue == 0) {
                        String boxString = splitValue1.getText().toString();
                        int boxValue = Integer.parseInt(boxString.substring(0, boxString.length() - 1));
                        temparaySplit[1] = getBigDecimalCalculation(ini_value, boxValue);
//                        seekBar.setProgress(boxValue);
                        splitValue1.setText(boxValue + "%");
                        Double lable_val = Double.valueOf(boxValue);
                        if (lable_val > 0) {
                            lable_val = (tcvVal * lable_val / 100.0) / 1000000;
                            lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                            splitLabel1.setText("$" + lable_val + "M");
                            splitValue1.setText(lable_val + "M");
                        } else {
//                            splitLabel1.setText("$" + lable_val + "M");
                            splitValue1.setText(lable_val + "M");
                        }
                    } else {
                        splitTotal = splitTotal - sp1;
                        splitTotal = splitTotal + seekValue;
                        sp1 = seekValue;
                        if (splitTotal <= 100) {
                            splitValue1.setText(seekValue + "%");
                            Double lable_val = Double.valueOf(seekValue);
                            temparaySplit[1] = getBigDecimalCalculation(ini_value, seekValue);
                            if (lable_val > 0) {
                                lable_val = (tcvVal * lable_val / 100.0) / 1000000;
                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel1.setText("$" + lable_val + "M");
                                splitValue1.setText(lable_val + "M");
                            } else {
                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel1.setText("$" + lable_val + "M");
                                splitValue1.setText(lable_val + "M");
                            }
                        } else {
                            splitTotal = 100;
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_SPLIT_SUM_CAN_NOT_EXCEED, Toast.LENGTH_SHORT).show();
                        }
                    }
                    resetColorView(1);
                } else if (splitIndex == 2) {
//                    sumSpliteValues();
                    if (seekValue == 0) {
                        String boxString = splitValue2.getText().toString();
                        int boxValue = Integer.parseInt(boxString.substring(0, boxString.length() - 1));
                        temparaySplit[2] = getBigDecimalCalculation(ini_value, boxValue);
//                        seekBar.setProgress(boxValue);
                        splitValue2.setText(boxValue + "%");
                        Double lable_val = Double.valueOf(boxValue);
                        if (lable_val > 0) {
                            lable_val = (tcvVal * lable_val / 100.0) / 1000000;
                            lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                            splitLabel2.setText("$" + lable_val + "M");
                            splitValue2.setText(lable_val + "M");
                        } else {
                            lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                            splitLabel2.setText("$" + lable_val + "M");
                            splitValue2.setText(lable_val + "M");
                        }
                    } else {
                        splitTotal = splitTotal - sp2;
                        splitTotal = splitTotal + seekValue;
                        sp2 = seekValue;
                        if (splitTotal <= 100) {
                            splitValue2.setText(seekValue + "%");
                            temparaySplit[2] = getBigDecimalCalculation(ini_value, seekValue);
                            Double lable_val = Double.valueOf(seekValue);
                            if (lable_val > 0) {
                                lable_val = (tcvVal * lable_val / 100.0) / 1000000;

                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel2.setText("$" + lable_val + "M");
                                splitValue2.setText(lable_val + "M");
                            } else {
                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel2.setText("$" + lable_val + "M");
                                splitValue2.setText(lable_val + "M");
                            }
                        } else {
                            splitTotal = 100;
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_SPLIT_SUM_CAN_NOT_EXCEED, Toast.LENGTH_SHORT).show();
                        }
                    }
                    resetColorView(2);
                } else if (splitIndex == 3) {
//                    sumSpliteValues();
                    if (seekValue == 0) {
                        String boxString = splitValue3.getText().toString();
                        int boxValue = Integer.parseInt(boxString.substring(0, boxString.length() - 1));
                        temparaySplit[3] = getBigDecimalCalculation(ini_value, boxValue);
//                        seekBar.setProgress(boxValue);
                        splitValue3.setText(boxValue + "%");
                        Double lable_val = Double.valueOf(boxValue);
                        if (lable_val > 0) {
                            lable_val = (tcvVal * lable_val / 100.0) / 1000000;

                            lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                            splitLabel3.setText("$" + lable_val + "M");
                            splitValue3.setText(lable_val + "M");
                        } else {
                            lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                            splitLabel3.setText("$" + lable_val + "M");
                            splitValue3.setText(lable_val + "M");
                        }
                    } else {

                        splitTotal = splitTotal - sp3;
                        splitTotal = splitTotal + seekValue;
                        sp3 = seekValue;
                        if (splitTotal <= 100) {
                            splitValue3.setText(seekValue + "%");
                            temparaySplit[3] = getBigDecimalCalculation(ini_value, seekValue);
                            Double lable_val = Double.valueOf(seekValue);
                            if (lable_val > 0) {
                                lable_val = (tcvVal * lable_val / 100.0) / 1000000;

                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel3.setText("$" + lable_val + "M");
                                splitValue3.setText(lable_val + "M");
                            } else {
                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel3.setText("$" + lable_val + "M");
                                splitValue3.setText(lable_val + "M");
                            }
                        } else {
                            splitTotal = 100;
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_SPLIT_SUM_CAN_NOT_EXCEED, Toast.LENGTH_SHORT).show();
                        }
                    }
                    resetColorView(3);
                } else if (splitIndex == 4) {
//                    sumSpliteValues();
                    if (seekValue == 0) {
                        String boxString = splitValue4.getText().toString();
                        int boxValue = Integer.parseInt(boxString.substring(0, boxString.length() - 1));
                        temparaySplit[4] = getBigDecimalCalculation(ini_value, boxValue);
//                        seekBar.setProgress(boxValue);
                        splitValue4.setText(boxValue + "%");
                        Double lable_val = Double.valueOf(boxValue);
                        if (lable_val > 0) {
                            lable_val = (tcvVal * lable_val / 100.0) / 1000000;

                            lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                            splitLabel4.setText("$" + lable_val + "M");
                            splitValue4.setText(lable_val + "M");
                        } else {
                            lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                            splitLabel4.setText("$" + lable_val + "M");
                            splitValue4.setText(lable_val + "M");
                        }
                    } else {
                        splitTotal = splitTotal - sp4;
                        splitTotal = splitTotal + seekValue;
                        sp4 = seekValue;
                        if (splitTotal <= 100) {
                            splitValue4.setText(seekValue + "%");
                            temparaySplit[4] = getBigDecimalCalculation(ini_value, seekValue);
                            Double lable_val = Double.valueOf(seekValue);
                            if (lable_val > 0) {
                                lable_val = (tcvVal * lable_val / 100.0) / 1000000;

                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel4.setText("$" + lable_val + "M");
                                splitValue4.setText(lable_val + "M");
                            } else {
                                lable_val = Double.valueOf(new DecimalFormat("##.##").format(lable_val));
//                                splitLabel4.setText("$" + lable_val + "M");
                                splitValue4.setText(lable_val + "M");
                            }
                        } else {
                            splitTotal = 100;
                            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_SPLIT_SUM_CAN_NOT_EXCEED, Toast.LENGTH_SHORT).show();
                        }
                    }
                    resetColorView(4);
                }
            } else {
                Toast.makeText(this, "Please enter Value first", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            //Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }*/


    private void resetColorView(int i) {
        if (i == 1) {
            blackBackGroundChange(splitYear1, rlSplit1, splitValue1);
            grayBackGroundChange(splitYear2, rlSplit2, splitValue2);
            grayBackGroundChange(splitYear3, rlSplit3, splitValue3);
            grayBackGroundChange(splitYear4, rlSplit4, splitValue4);


        } else if (i == 2) {
            blackBackGroundChange(splitYear2, rlSplit2, splitValue2);
            grayBackGroundChange(splitYear1, rlSplit1, splitValue1);
            grayBackGroundChange(splitYear3, rlSplit3, splitValue3);
            grayBackGroundChange(splitYear4, rlSplit4, splitValue4);
        } else if (i == 3) {
            blackBackGroundChange(splitYear3, rlSplit3, splitValue3);
            grayBackGroundChange(splitYear2, rlSplit2, splitValue2);
            grayBackGroundChange(splitYear1, rlSplit1, splitValue1);
            grayBackGroundChange(splitYear4, rlSplit4, splitValue4);
        } else if (i == 4) {
            blackBackGroundChange(splitYear4, rlSplit4, splitValue4);
            grayBackGroundChange(splitYear2, rlSplit2, splitValue2);
            grayBackGroundChange(splitYear3, rlSplit3, splitValue3);
            grayBackGroundChange(splitYear1, rlSplit1, splitValue1);
        }


        /*if (i == 1) {
//            splitLabel1.setTextColor(Color.BLACK);
            splitYear1.setTextColor(Color.BLACK);
//            splitValue1.setTextColor(Color.BLACK);
            rlSplit1.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
//            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg);
            rlSplit2.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg);
            splitValue1.requestFocus();
        } else if (i == 2) {
//            splitLabel2.setTextColor(Color.BLACK);
            splitYear2.setTextColor(Color.BLACK);
            splitValue2.setTextColor(Color.BLACK);
            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg);

//            splitLabel1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue1.setBackgroundResource(R.drawable.initiative_splite_bg);
            splitValue2.requestFocus();
        } else if (i == 3) {
//            splitLabel3.setTextColor(Color.BLACK);
            splitYear3.setTextColor(Color.BLACK);
            splitValue3.setTextColor(Color.BLACK);
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue1.setBackgroundResource(R.drawable.initiative_splite_bg);
            splitValue3.requestFocus();
        } else if (i == 4) {
//            splitLabel4.setTextColor(Color.BLACK);
            splitYear4.setTextColor(Color.BLACK);
            splitValue4.setTextColor(Color.BLACK);
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue1.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue1.setBackgroundResource(R.drawable.initiative_splite_bg);
            splitValue4.requestFocus();
        }*/
    }


    private void showMultiselectDialog(final String type, String title) {
        if (type.equalsIgnoreCase("relevantBU")) {
            listView.setVisibility(View.VISIBLE);
            dialog_recycleView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            dialog_recycleView.setVisibility(View.VISIBLE);
        }
        dialogBox.show();
        popupTitle.setText(title);
        selectGoalsTextView = (TextView) dialogBox.findViewById(R.id.selectInitiativeTextView);
        if (type.equalsIgnoreCase("goal")) {
            showLinkedGoalsRecyclerView();
        } else if (type.equalsIgnoreCase("geo")) {
            showGeoRecyclerView();
        } else if (type.equalsIgnoreCase("market")) {
            showMarketRecyclerView();
        } else if (type.equalsIgnoreCase("country")) {
            showCountryRecyclerView();
        } else if (type.equalsIgnoreCase("strategicImperatives")) {
            showStrategicImperativesRecyclerView();
        } else if (type.equalsIgnoreCase("relevantBU")) {
            showRelevantBURecyclerView();
        } else if (type.equalsIgnoreCase("contact")) {
        }
        selectGoalsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equalsIgnoreCase("goal")) {
                    displaySelectedGoals();
                    dialogBox.dismiss();
                } else if (type.equalsIgnoreCase("geo")) {
                    displaySelectedGeos();
                    dialogBox.dismiss();
                } else if (type.equalsIgnoreCase("market")) {
                    displaySelectedMarkets();
                    dialogBox.dismiss();
                } else if (type.equalsIgnoreCase("country")) {
                    displaySelectedCountrys();
                    dialogBox.dismiss();
                } else if (type.equalsIgnoreCase("strategicImperatives")) {
                    displaySelectedStrategicImperatives();
                    dialogBox.dismiss();
                } else if (type.equalsIgnoreCase("relevantBU")) {
                    displaySelectedRelevantBrandUnits();
                    if (relevantBusinessUnitTotalPCT == 100) {
                        dialogBox.dismiss();
                    } else {
                        Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_RELEVANT_BU_STATUS, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        View dialogCloseButton = (View) dialogBox.findViewById(R.id.goalIntiativeCancel);
        dialogCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.dismiss();
            }
        });
    }

    private void displaySelectedRelevantBrandUnits() {

        try {
            relevantBusinessUnitTotalPCT = 0;
            for (int key : relevBUChoiceMap.keySet())
                relevantBUList.get(key).setisSelected(relevBUChoiceMap.get(key));
            selectedBUList.clear();
            for (int i = 0; i < relevantBUList.size(); i++) {
                if (!(relevantBUList.get(i).isSection()) && relevantBUList.get(i).getisSelected()) {
                    relevantBusinessUnitTotalPCT = (int) (relevantBusinessUnitTotalPCT + Double.parseDouble(relevantBUList.get(i).getPCT()));
                    if (relevantBusinessUnitTotalPCT <= 100) {
                        selectedBUList.add(relevantBUList.get(i));
                    } else {
                        break;
                    }
                }
            }
            if (relevantBusinessUnitTotalPCT == 100) {
                createdSelectedBUnitsListUI(selectedBUList);
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"displaySelectedRelevantBrandUnits",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }


    private void showInitiativeLeadRecyclerView(List<InitiativeContactModel> contactList) {

        try {
            // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            // imm.hideSoftInputFromWindow(editContactsLead.getWindowToken(), 0);
            initiativeLeadContactsAdapter = new InitiativeLeadContactsAdapter(contactList, "initiatives");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            contactsRecyclerView.setLayoutManager(mLayoutManager);
            contactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            contactsRecyclerView.setAdapter(initiativeLeadContactsAdapter);
            ISAP_Utils.dismissProgressDialog();
            initiativeLeadContactsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showInitiativeLeadRecyclerView",e.getMessage());
            // Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySelectedGeos() {

        try {
            for (int key : geoChoiceMap.keySet())
                geoList.get(key).setisSelected(geoChoiceMap.get(key));
            selectedGeoList.clear();
            for (int i = 0; i < geoList.size(); i++) {
                if (geoList.get(i).getisSelected()) {
                    selectedGeoList.add(geoList.get(i));
                }
            }
            createSelectedGeoListUI(selectedGeoList);
            if (marketList != null && marketList.size() > 0)
                marketList.clear();
            if (selectedMarketList != null && selectedMarketList.size() > 0) {
                selectedMarketList.clear();
                selectedMarketListAdapter.notifyDataSetChanged();
            }
            if (countryList != null && countryList.size() > 0)
                countryList.clear();
            if (selectedCountryList != null && selectedCountryList.size() > 0) {
                selectedCountryList.clear();
                selectedCountryListAdapter.notifyDataSetChanged();
            }
            if (!isAllGeos) {
                fetchRegions(createDataListForRegionsDisplay(selectedGeoList));
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"displaySelectedGeos",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private String[] createDataListForCountriesDisplayList(List<REGION> selectedMarketList) {

        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < selectedMarketList.size(); i++) {
                stringList.add(selectedMarketList.get(i).getKEY());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForCountriesDisplayList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        String[] stringArray = new String[stringList.size()];
        stringList.toArray(stringArray);
        return stringArray;
    }

    private String[] createDataListForRegionsDisplay(List<GEO> selectedGeoList) {

        List<String> stringList = new ArrayList<String>();
        try {
            for (int i = 0; i < selectedGeoList.size(); i++) {
                stringList.add(selectedGeoList.get(i).getKEY());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForRegionsDisplay",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        String[] stringArray = new String[stringList.size()];
        stringList.toArray(stringArray);
        return stringArray;
    }

    private void createSelectedGeoListUI(List<GEO> geoList) {

        try {
            selectedGeoListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedGeoListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedGeoListAdapter = new SelectedGeoListAdapter(geoList);
            selectedGeoListRecyclerView.setAdapter(selectedGeoListAdapter);
            ISAP_Utils.dismissProgressDialog();
            selectedGeoListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedGeoListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedGeoListRecyclerView.setAdapter(selectedGeoListAdapter);
            selectedGeoListAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createSelectedGeoListUI",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySelectedMarkets() {

        try {
            for (int key : marketChoiceMap.keySet())
                marketList.get(key).setisSelected(marketChoiceMap.get(key));
            selectedMarketList.clear();
            for (int i = 0; i < marketList.size(); i++) {
                if (marketList.get(i).getisSelected()) {
                    selectedMarketList.add(marketList.get(i));
                }
            }
            createSelectedMarketListUI(selectedMarketList);
            if (!isAllGeos) {
                fetchCountries(createDataListForCountriesDisplayList(selectedMarketList));
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"displaySelectedMarkets",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createSelectedMarketListUI(List<REGION> marketList) {

        try {
            selectedMarketListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedMarketListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedMarketListAdapter = new SelectedMarketListAdapter(marketList);
            selectedMarketListRecyclerView.setAdapter(selectedMarketListAdapter);
            ISAP_Utils.dismissProgressDialog();
            selectedMarketListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedMarketListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedMarketListRecyclerView.setAdapter(selectedMarketListAdapter);
            selectedMarketListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createSelectedMarketListUI",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySelectedCountrys() {

        try {
            for (int key : countryChoiceMap.keySet())
                countryList.get(key).setisSelected(countryChoiceMap.get(key));
            selectedCountryList.clear();
            for (int i = 0; i < countryList.size(); i++) {
                if (countryList.get(i).getisSelected()) {
                    selectedCountryList.add(countryList.get(i));
                }
            }
            createSelectedCountryListUI(selectedCountryList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"displaySelectedCountrys",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createSelectedCountryListUI(List<COUNTRY> selectedCountryList) {

        try {
            selectedCountryListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedCountryListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedCountryListAdapter = new SelectedCountryListAdapter(selectedCountryList);
            selectedCountryListRecyclerView.setAdapter(selectedCountryListAdapter);
            ISAP_Utils.dismissProgressDialog();
            selectedCountryListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedCountryListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedCountryListRecyclerView.setAdapter(selectedCountryListAdapter);
            selectedCountryListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createSelectedCountryListUI",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySelectedGoals() {

        try {
            for (int key : choiceMap.keySet()) goalsList.get(key).setisSelected(choiceMap.get(key));
            selectedGoalsArrayList.clear();
            for (int i = 0; i < goalsList.size(); i++) {
                if (goalsList.get(i).getisSelected()) {
                    selectedGoalsArrayList.add(goalsList.get(i));
                }
            }
            if (selectedGoalsArrayList.size() > 0)
                isItemSelected = false;
            else
                isItemSelected = true;
            createSelectedGoalsListUI(selectedGoalsArrayList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"displaySelectedGoals",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySelectedLinkedOppts() {

        try {
            Double opptTotalValue = 0.0;
            for (int key : linkedOPPTsChoiceMap.keySet())
                linkedopptList.get(key).setisSelected(linkedOPPTsChoiceMap.get(key));
            selectedLinkedOpptList.clear();
            for (int i = 0; i < linkedopptList.size(); i++) {
                if (linkedopptList.get(i).getisSelected()) {
                    selectedLinkedOpptList.add(linkedopptList.get(i));
                    opptTotalValue = opptTotalValue + (Double.parseDouble(linkedopptList.get(i).getTCV_OPPTY_VAL()));
                }
            }
            opptTotalValue = opptTotalValue / 1000;
            linkedOpptsCountLabel.setText(Integer.toString(selectedLinkedOpptList.size()) + " ");
            linkedOpptsTotalValue.setText("$" + Double.toString(opptTotalValue) + "k");
            createSelectedLinkedOpptsListUI(selectedLinkedOpptList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"displaySelectedLinkedOppts",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySelectedStrategicImperatives() {

        try {
            for (int key : strategicImperativesChoiceMap.keySet())
                strategicImperativesList.get(key).setisSelected(strategicImperativesChoiceMap.get(key));
            selectedStrategicImperativesList.clear();
            for (int i = 0; i < strategicImperativesList.size(); i++) {
                if (strategicImperativesList.get(i).getisSelected()) {
                    selectedStrategicImperativesList.add(strategicImperativesList.get(i));
                }
            }
            if (strategicImperativesList.size() > 0)
                isStrategicImperativesSelected = false;
            else
                isStrategicImperativesSelected = true;
            createSelectedStrategicImperativesListUI(selectedStrategicImperativesList);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"displaySelectedStrategicImperatives",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListforRelevantBrandUnits() {

        try {
            if (relevantBUList == null || relevantBUList.size() == 0) {
                relevantBUList = sortAndAddSections();
            }
            relevBUChoiceMap = new HashMap<Integer, Boolean>();
            adapter = new BrandsAdapter(getApplicationContext(), relevBUChoiceMap, relevantBUList, true);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListforRelevantBrandUnits",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void showRelevantBURecyclerView() {
        createDataListforRelevantBrandUnits();
        listView.setAdapter(adapter);
    }

    private List<RELEVANTBRANDSORUNITS> sortAndAddSections() {

        ArrayList tempList = new ArrayList();
        try {
            String header = "";
            for (int i = 0; i < initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS().length; i++) {
                //If it is the start of a new section we create a new listcell and add it to our array
                for (int j = 0; j < initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS()[i].length; j++) {
                    if ((!header.equalsIgnoreCase(initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS()[i][j].getBRAND_GROUP()) && (initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS()[i][j].getBRAND_GROUP() != null))) {
                        RELEVANTBRANDSORUNITS sectionCell = new RELEVANTBRANDSORUNITS();
                        sectionCell.setSection(true);
                        sectionCell.setBRAND_GROUP(initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS()[i][j].getBRAND_GROUP());
                        tempList.add(sectionCell);
                        header = initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS()[i][j].getBRAND_GROUP();
                        j--;
                    } else {
                        if (initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS()[i][j].getBRAND_GROUP() != null)
                            tempList.add(initiativeFields_model.getInitiativeData().getRELEVANTBRANDSORUNITS()[i][j]);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"sortAndAddSections",e.getMessage());

        }
        return tempList;
    }

    private void showStrategicImperativesRecyclerView() {

        try {
            createDataListForStrategicMultiselectRecyclerView();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            dialog_recycleView.setLayoutManager(mLayoutManager);
            dialog_recycleView.setItemAnimator(new DefaultItemAnimator());
            dialog_recycleView.setAdapter(strategicImperativesMultiSelectAdapter);
            ISAP_Utils.dismissProgressDialog();
            strategicImperativesMultiSelectAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showStrategicImperativesRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForStrategicMultiselectRecyclerView() {

        try {
            if (strategicImperativesList == null || strategicImperativesList.size() == 0) {
                strategicImperativesList = new ArrayList<STRATEGICIMPERATIVES>();
                for (int i = 0; i < initiativeFields_model.getInitiativeData().getSTRATEGICIMPERATIVES().length; i++) {
                    STRATEGICIMPERATIVES strategicimperativesData = new STRATEGICIMPERATIVES();
                    strategicimperativesData.setNAME(initiativeFields_model.getInitiativeData().getSTRATEGICIMPERATIVES()[i].getNAME());
                    strategicimperativesData.setKEY(initiativeFields_model.getInitiativeData().getSTRATEGICIMPERATIVES()[i].getKEY());
                    strategicImperativesList.add(strategicimperativesData);
                }
            }
            strategicImperativesChoiceMap = new HashMap<Integer, Boolean>();
            strategicImperativesMultiSelectAdapter = new StrategicImperativesMultiSelectAdapter(strategicImperativesList, strategicImperativesChoiceMap);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForStrategicMultiselectRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForGeoMultiselectRecyclerView() {

        try {
            if (geoList == null || geoList.size() == 0) {
                geoList = new ArrayList<GEO>();
                for (int i = 0; i < initiativeFields_model.getInitiativeData().getGEO().length; i++) {
                    GEO geoData = new GEO();
                    geoData.setNAME(initiativeFields_model.getInitiativeData().getGEO()[i].getNAME());
                    geoData.setKEY(initiativeFields_model.getInitiativeData().getGEO()[i].getKEY());
                    geoData.setID(initiativeFields_model.getInitiativeData().getGEO()[i].getID());
                    geoList.add(geoData);
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForGeoMultiselectRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForSavedCountryList() {

        try {
            if (countryList == null || countryList.size() == 0) {
                //if initiativesList (which is initiative only null, we need to preare data set from service else use
                countryList = new ArrayList<COUNTRY>();
                for (int i = 0; i < initiativeFields_model.getInitiativeData().getCOUNTRY().length; i++) {
                    COUNTRY countryData = new COUNTRY();
                    countryData.setNAME(initiativeFields_model.getInitiativeData().getCOUNTRY()[i].getNAME());
                    countryData.setKEY(initiativeFields_model.getInitiativeData().getCOUNTRY()[i].getKEY());
                    countryData.setID(initiativeFields_model.getInitiativeData().getCOUNTRY()[i].getID());
                    countryData.setPARENTKEY(initiativeFields_model.getInitiativeData().getCOUNTRY()[i].getPARENTKEY());
                    countryList.add(countryData);
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForSavedCountryList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForSavedMarketList() {


        try {
            if (marketList == null || marketList.size() == 0) {
                //if initiativesList (which is initiative only null, we need to preare data set from service else use
                //what is available
                marketList = new ArrayList<REGION>();
                for (int i = 0; i < initiativeFields_model.getInitiativeData().getREGION().length; i++) {
                    REGION marketsData = new REGION();
                    marketsData.setNAME(initiativeFields_model.getInitiativeData().getREGION()[i].getNAME());
                    marketsData.setKEY(initiativeFields_model.getInitiativeData().getREGION()[i].getKEY());
                    marketsData.setID(initiativeFields_model.getInitiativeData().getREGION()[i].getID());
                    marketsData.setPARENTKEY(initiativeFields_model.getInitiativeData().getREGION()[i].getPARENTKEY());
                    marketList.add(marketsData);
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForSavedMarketList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForMarketMultiselectRecyclerView() {

        try {
            if (marketList == null || marketList.size() == 0) {
                marketList = new ArrayList<REGION>();
                if (regionsResponseModel != null) {
                    for (int i = 0; i < regionsResponseModel.getREGION().length; i++) {
                        REGION marketsData = new REGION();
                        marketsData.setNAME(regionsResponseModel.getREGION()[i].getNAME());
                        marketsData.setKEY(regionsResponseModel.getREGION()[i].getKEY());
                        marketsData.setID(regionsResponseModel.getREGION()[i].getID());
                        marketsData.setPARENTKEY(regionsResponseModel.getREGION()[i].getPARENTKEY());
                        marketList.add(marketsData);
                    }
                } else {
                    Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_SELECT_GEO, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForMarketMultiselectRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForCountryMultiselectRecyclerView() {

        try {
            if (countryList == null || countryList.size() == 0) {
                countryList = new ArrayList<COUNTRY>();
                if (regionsResponseModel != null) {
                    for (int i = 0; i < getCountryResponseModel.getCOUNTRY().length; i++) {
                        COUNTRY countryData = new COUNTRY();
                        countryData.setNAME(getCountryResponseModel.getCOUNTRY()[i].getNAME());
                        countryData.setKEY(getCountryResponseModel.getCOUNTRY()[i].getKEY());
                        countryData.setID(getCountryResponseModel.getCOUNTRY()[i].getID());
                        countryData.setPARENTKEY(getCountryResponseModel.getCOUNTRY()[i].getPARENTKEY());
                        countryList.add(countryData);
                    }
                } else {
                    Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.INITIATIVE_SELECT_MARKET, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForCountryMultiselectRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void showGeoRecyclerView() {

        try {
            createDataListForGeoMultiselectRecyclerView();
            geoChoiceMap = new HashMap<Integer, Boolean>();
            geoMultiSelectListAdapter = new GeoMultiSelectListAdapter(geoList, geoChoiceMap);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            dialog_recycleView.setLayoutManager(mLayoutManager);
            dialog_recycleView.setItemAnimator(new DefaultItemAnimator());
            dialog_recycleView.setAdapter(geoMultiSelectListAdapter);
            ISAP_Utils.dismissProgressDialog();
            geoMultiSelectListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showGeoRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void showMarketRecyclerView() {

        try {
            createDataListForMarketMultiselectRecyclerView();
            marketChoiceMap = new HashMap<Integer, Boolean>();
            marketMultiSelectListAdapter = new MarketsMultiSelectListAdapter(marketList, marketChoiceMap);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            dialog_recycleView.setLayoutManager(mLayoutManager);
            dialog_recycleView.setItemAnimator(new DefaultItemAnimator());
            dialog_recycleView.setAdapter(marketMultiSelectListAdapter);
            ISAP_Utils.dismissProgressDialog();
            marketMultiSelectListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showMarketRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void showCountryRecyclerView() {

        try {
            createDataListForCountryMultiselectRecyclerView();
            countryChoiceMap = new HashMap<Integer, Boolean>();
            countryMultiSelectListAdapter = new CountryMultiSelectListAdapter(countryList, countryChoiceMap);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            dialog_recycleView.setLayoutManager(mLayoutManager);
            dialog_recycleView.setItemAnimator(new DefaultItemAnimator());
            dialog_recycleView.setAdapter(countryMultiSelectListAdapter);
            ISAP_Utils.dismissProgressDialog();
            countryMultiSelectListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showCountryRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void closeActivity() {
        super.onBackPressed();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Get extra data included in the Intent
            try {
                int position = Integer.parseInt(intent.getStringExtra("position"));
                String adapterType = intent.getStringExtra("type");
                if (adapterType.equalsIgnoreCase("goals")) {
                    refreshGoalsList(position);
                } else if (adapterType.equalsIgnoreCase("geo")) {
                    refreshGeoList(position);
                } else if (adapterType.equalsIgnoreCase("market")) {
                    refreshMarketList(position);
                } else if (adapterType.equalsIgnoreCase("country")) {
                    refreshCountryList(position);
                } else if (adapterType.equalsIgnoreCase("strategicImperatives")) {
                    refreshStrategicImperativesList(position);
                } else if (adapterType.equalsIgnoreCase("contacts")) {
                    updateInitiativeLeadContact(position);
                } else if (adapterType.equalsIgnoreCase("relevantBU")) {
                    refreshSelectedRelBUList(position);
                } else if (adapterType.equalsIgnoreCase("linkedOppts")) {
                    updateLinkedOpptsFields(position);
                }
            } catch (Exception e) {
                LogUtils.printLog(NewInitiativeActivity.this,"onReceive",e.getMessage());
//                Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void updateLinkedOpptsFields(int position) {

        try {
            linkedOPPTSName.setText(linkedopptList.get(position).getOPPORTUNITY_NM());
            linkedOPPTSSalesStage.setText(linkedopptList.get(position).getSALES_STAGE());
            linkedOPPTSClosedDate.setText(linkedopptList.get(position).getCLOSE_DATE());
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"updateLinkedOpptsFields",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshSelectedRelBUList(int position) {


        try {
            for (int i = 0; i < relevantBUList.size(); i++) {
                if (selectedBUList.get(position).getKEY().equalsIgnoreCase(relevantBUList.get(i).getKEY())) {
                    relevantBUList.get(i).setisSelected(false);
                    relevantBUList.get(i).setPCT("");
                }
            }
            adapter.notifyDataSetChanged();
            selectedBUList.remove(position);
            selectedBrandUnitsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"refreshSelectedRelBUList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateInitiativeLeadContact(int position) {

        try {
            contactsLeadDialog.dismiss();
            if (contactList != null) {
                if (contactList.size() > 0) {
                    if (contactList.get(position) != null) {
                        contactsLeadEmail.setText(contactList.get(position).getEmailId());
                        contactsLeadRole.setText(contactList.get(position).getRole());
                        contactsLeadName.setText(contactList.get(position).getName());
                        initiateLeadNameTextView.setText(contactList.get(position).getName());
                        leadCnum = contactList.get(position).getCnum();
                    }
                }
            }
            editContactsLead.setText("");
            contactList.clear();
            initiativeLeadContactsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"updateInitiativeLeadContact",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshGoalsList(int position) {

        try {
            for (int i = 0; i < goalsList.size(); i++) {
                if (selectedGoalsArrayList.get(position).getKEY().equalsIgnoreCase(goalsList.get(i).getKEY())) {
                    goalsList.get(i).setisSelected(false);
                }
            }
            linkedGoalsMultiSelectAdapter.notifyDataSetChanged();
            selectedGoalsArrayList.remove(position);
            if (selectedGoalsArrayList.size() == 0)
                isItemSelected = true;
            else
                isItemSelected = false;
            linkedSelectedGoalsListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"refreshGoalsList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshStrategicImperativesList(int position) {

        try {
            for (int i = 0; i < strategicImperativesList.size(); i++) {
                if (selectedStrategicImperativesList.get(position).getKEY().equalsIgnoreCase(strategicImperativesList.get(i).getKEY())) {
                    strategicImperativesList.get(i).setisSelected(false);
                }
            }
            strategicImperativesMultiSelectAdapter.notifyDataSetChanged();
            selectedStrategicImperativesList.remove(position);
            if (selectedStrategicImperativesList.size() == 0)
                isStrategicImperativesSelected = true;
            else
                isStrategicImperativesSelected = false;
            selectedStrategicImperativesListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"refreshStrategicImperativesList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshGeoList(int position) {

        try {
            for (int i = 0; i < geoList.size(); i++) {
                if (selectedGeoList.get(position).getKEY().equalsIgnoreCase(geoList.get(i).getKEY())) {
                    geoList.get(i).setisSelected(false);
                }
            }
            geoMultiSelectListAdapter.notifyDataSetChanged();
            selectedGeoList.remove(position);
            selectedGeoListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"refreshGeoList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshMarketList(int position) {

        try {
            for (int i = 0; i < marketList.size(); i++) {
                if (selectedMarketList.get(position).getKEY().equalsIgnoreCase(marketList.get(i).getKEY())) {
                    marketList.get(i).setisSelected(false);
                }
            }
            marketMultiSelectListAdapter.notifyDataSetChanged();
            selectedMarketList.remove(position);
            selectedMarketListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"refreshMarketList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshCountryList(int position) {

        try {
            for (int i = 0; i < countryList.size(); i++) {
                if (selectedCountryList.get(position).getKEY().equalsIgnoreCase(countryList.get(i).getKEY())) {
                    countryList.get(i).setisSelected(false);
                }
            }
            countryMultiSelectListAdapter.notifyDataSetChanged();
            selectedCountryList.remove(position);
            selectedCountryListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"refreshCountryList",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedSpinnerString(String type) {

        String selectedSpinnerText = "";
        try {
            if (type.equalsIgnoreCase("industry")) {
                selectedSpinnerText = initiativeFields_model.getInitiativeDefault().getV2_INDUSTRYSOLUTIONVALUE();
            } else if (type.equalsIgnoreCase("progress")) {

                if (initiativeFields_model.getInitiativeDefault().getV1_PROGRESSKEY().equalsIgnoreCase("ASPT")) {
                    selectedSpinnerText = "Aspiration";
                } else if (initiativeFields_model.getInitiativeDefault().getV1_PROGRESSKEY().equalsIgnoreCase("PRGR")) {
                    selectedSpinnerText = "Progressing";
                } else if (initiativeFields_model.getInitiativeDefault().getV1_PROGRESSKEY().equalsIgnoreCase("DLVG")) {
                    selectedSpinnerText = "Delivering";
                } else if (initiativeFields_model.getInitiativeDefault().getV1_PROGRESSKEY().equalsIgnoreCase("CLSD")) {
                    selectedSpinnerText = "Closed";
                } else {
                    selectedSpinnerText = "";
                }
            } else if (type.equalsIgnoreCase("pRole")) {
                selectedSpinnerText = initiativeFields_model.getInitiativeDefault().getV3_PARTNERROLENAME();
            } else if (type.equalsIgnoreCase("closedDate")) {
                selectedSpinnerText = initiativeFields_model.getInitiativeDefault().getV1_CLOSEDATE();
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getSelectedSpinnerString",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return selectedSpinnerText;
    }

    public void showProgressSpinner(LinearLayout linearLayout, final List<String> spinnerArrayList, final Spinner spinner, final String type) {

        try {
            spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            spinner.setBackgroundColor(Color.TRANSPARENT);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout, spinnerArrayList);
            arrayAdapter.setDropDownViewResource(R.layout.custom_spinner_layout);
            spinner.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
            if (isEditingFlag) {
                int spinnerPosition = arrayAdapter.getPosition(getSelectedSpinnerString(type));
                spinner.setSelection(spinnerPosition);
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (type.equalsIgnoreCase("industry")) {
                        if (position == 0)
                            industrykey = "";
                        else
                            industrykey = initiativeFields_model.getInitiativeData().getINDUSTRYSOLUTIONS()[position - 1].getKEY();
                    } else if (type.equalsIgnoreCase("progress")) {
//                    System.out.println("postion:"+position+", element key:"+initiativeFields_model.getInitiativeData().getPROGRESS()[position-1].getKEY());
                        if (position == 0)
                            progressKey = "";
                        else
                            progressKey = initiativeFields_model.getInitiativeData().getPROGRESS()[position - 1].getKEY();
                    } else if (type.equalsIgnoreCase("pRole")) {
                        if (position == 0)
                            pRole = "";
                        else
                            pRole = initiativeFields_model.getInitiativeData().getPARTNERROLE()[position - 1].getKEY();
                    } else if (type.equalsIgnoreCase("closedDate")) {

                        if (position == 0) {
                            closeDate = "";

                        } else {
                            spinnerPosition = position;
//                            closeDate = initiativeFields_model.getInitiativeDefault().getV1_CLOSEDATEARRAY()[position].getNAME();
                            closeDate = spinnerArrayList.get(position);

                            enableSplitFields(position);
                            /*calculating current year, if current year and select year both are same hide split valuse*/
                            compareSplitCurrentYear(closeDate);
                        }
//


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if (linearLayout != null) {
                linearLayout.removeAllViews();
                linearLayout.addView(spinner);
            }

        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showProgressSpinner",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }


    private void enableSplitFields(int position) {
        if (position == 1) {
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            rlSplit1.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit2.setBackgroundResource(R.drawable.initiative_splite_bg);

            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit3.setBackgroundResource(R.drawable.initiative_splite_bg);


            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit4.setBackgroundResource(R.drawable.initiative_splite_bg);


        } else if (position == 2) {
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            rlSplit1.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear2.setTextColor(Color.BLACK);
            splitValue2.setTextColor(Color.BLACK);
            rlSplit2.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit3.setBackgroundResource(R.drawable.initiative_splite_bg);


            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit4.setBackgroundResource(R.drawable.initiative_splite_bg);

            if (splitValue3.getText().toString().length() > 0) {
                temparaySplit[3] = "0.00";
                getSubtractionRemaingValue();
                splitValue3.setText("");
            }
            if (splitValue4.getText().toString().length() > 0) {
                temparaySplit[4] = "0.00";
                getSubtractionRemaingValue();
                splitValue4.setText("");
            }
        } else if (position == 3) {
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            rlSplit1.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear2.setTextColor(Color.BLACK);
            splitValue2.setTextColor(Color.BLACK);
            rlSplit2.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear3.setTextColor(Color.BLACK);
            splitValue3.setTextColor(Color.BLACK);
            rlSplit3.setBackgroundResource(R.drawable.initiative_splite_bg_black);


            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit4.setBackgroundResource(R.drawable.initiative_splite_bg);

            if (splitValue4.getText().toString().length() > 0) {
                temparaySplit[4] = "0.00";
                getSubtractionRemaingValue();
                splitValue4.setText("");
            }
        } else if (position == 4) {
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            rlSplit1.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear2.setTextColor(Color.BLACK);
            splitValue2.setTextColor(Color.BLACK);
            rlSplit2.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear3.setTextColor(Color.BLACK);
            splitValue3.setTextColor(Color.BLACK);
            rlSplit3.setBackgroundResource(R.drawable.initiative_splite_bg_black);


            splitYear4.setTextColor(Color.BLACK);
            splitValue4.setTextColor(Color.BLACK);
            rlSplit4.setBackgroundResource(R.drawable.initiative_splite_bg_black);
        }
        /*if (position == 0) {
            splitValue1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            rlSplit1.setBackgroundResource(R.drawable.initiative_splite_bg_black);

            splitYear2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit2.setBackgroundResource(R.drawable.initiative_splite_bg);

            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit3.setBackgroundResource(R.drawable.initiative_splite_bg);


            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            rlSplit4.setBackgroundResource(R.drawable.initiative_splite_bg);


//            splitLabel1.setTextColor(Color.BLACK);
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            splitValue1.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg);
        } else if (position == 1) {
//            splitLabel1.setTextColor(Color.BLACK);
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            splitValue1.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel2.setTextColor(Color.BLACK);
            splitYear2.setTextColor(Color.BLACK);
            splitValue2.setTextColor(Color.BLACK);
            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg);
//            splitLabel4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg);
        } else if (position == 2) {
//            splitLabel1.setTextColor(Color.BLACK);
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            splitValue1.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel2.setTextColor(Color.BLACK);
            splitYear2.setTextColor(Color.BLACK);
            splitValue2.setTextColor(Color.BLACK);
            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel3.setTextColor(Color.BLACK);
            splitYear3.setTextColor(Color.BLACK);
            splitValue3.setTextColor(Color.BLACK);
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitYear4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setTextColor(getResources().getColor(R.color.ini_split_box_gray, null));
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg);
        } else if (position == 3) {
//            splitLabel1.setTextColor(Color.BLACK);
            splitYear1.setTextColor(Color.BLACK);
            splitValue1.setTextColor(Color.BLACK);
            splitValue1.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel2.setTextColor(Color.BLACK);
            splitYear2.setTextColor(Color.BLACK);
            splitValue2.setTextColor(Color.BLACK);
            splitValue2.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel3.setTextColor(Color.BLACK);
            splitYear3.setTextColor(Color.BLACK);
            splitValue3.setTextColor(Color.BLACK);
            splitValue3.setBackgroundResource(R.drawable.initiative_splite_bg_black);
//            splitLabel4.setTextColor(Color.BLACK);
            splitYear4.setTextColor(Color.BLACK);
            splitValue4.setTextColor(Color.BLACK);
            splitValue4.setBackgroundResource(R.drawable.initiative_splite_bg_black);
        }*/
    }

    private List<String> convertClosedDateObjectListToString() {

        List<String> stringList = new ArrayList<String>();
        try {
            stringList.add("");
            for (int i = 0; i < initiativeFields_model.getInitiativeDefault().getV1_CLOSEDATEARRAY().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeDefault().getV1_CLOSEDATEARRAY()[i].getNAME());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertClosedDateObjectListToString",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        return stringList;
    }

    private List<String> convertProgressObjectListToString() {

        List<String> stringList = new ArrayList<String>();
        try {
            stringList.add("Select Progress");
            for (int i = 0; i < initiativeFields_model.getInitiativeData().getPROGRESS().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeData().getPROGRESS()[i].getNAME());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertProgressObjectListToString",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private List<String> convertIndustrySolutionObjectListToString() {

        List<String> stringList = new ArrayList<String>();
        try {
            stringList.add("Select Industry Solutions");
            for (int i = 0; i < initiativeFields_model.getInitiativeData().getINDUSTRYSOLUTIONS().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeData().getINDUSTRYSOLUTIONS()[i].getNAME());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertIndustrySolutionObjectListToString",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private List<String> convertPartnerRoleObjectToString() {

        List<String> stringList = new ArrayList<String>();
        try {
            stringList.add("Select Partner Role");
            for (int i = 0; i < initiativeFields_model.getInitiativeData().getPARTNERROLE().length; i++) {
                stringList.add(initiativeFields_model.getInitiativeData().getPARTNERROLE()[i].getNAME());
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"convertPartnerRoleObjectToString",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return stringList;
    }

    private void createSelectedGoalsListUI(List<GOALS> selectedGoalsArrayList) {

        try {
            linkedSelectedGoalsListAdapter = new LinkedSelectedGoalsListAdapter(selectedGoalsArrayList);
            selectedGoalsMultiselectRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedGoalsMultiselectRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedGoalsMultiselectRecyclerView.setAdapter(linkedSelectedGoalsListAdapter);
            ISAP_Utils.dismissProgressDialog();
            linkedSelectedGoalsListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createSelectedGoalsListUI",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createSelectedStrategicImperativesListUI(List<STRATEGICIMPERATIVES> selectedStrategicImperativesList) {

        try {
            selectedStrategicImperativesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedStrategicImperativesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedStrategicImperativesRecyclerView.setAdapter(selectedStrategicImperativesListAdapter);
            ISAP_Utils.dismissProgressDialog();
            selectedStrategicImperativesListAdapter = new SelectedStrategicImperativesListAdapter(selectedStrategicImperativesList);
            selectedStrategicImperativesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedStrategicImperativesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedStrategicImperativesRecyclerView.setAdapter(selectedStrategicImperativesListAdapter);
            selectedStrategicImperativesListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createSelectedStrategicImperativesListUI",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createSelectedLinkedOpptsListUI(List<LINKEDOPPT> selectedLinkedOpptList) {

        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            selectedLinkedOpptsRecyclerView.setLayoutManager(mLayoutManager);
            selectedLinkedOpptsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedLinkedOpptsAdapter = new SelectedLinkedOpptsAdapter(selectedLinkedOpptList);
            selectedLinkedOpptsRecyclerView.setAdapter(selectedLinkedOpptsAdapter);
            selectedLinkedOpptsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
//      LogUtils.printLog(NewInitiativeActivity.this,"createSelectedLinkedOpptsListUI",e.getMessage());
//      Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createdSelectedBUnitsListUI(List<RELEVANTBRANDSORUNITS> selectedBUList) {

        try {
            selectedBURecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedBURecyclerView.setItemAnimator(new DefaultItemAnimator());
            ISAP_Utils.dismissProgressDialog();
            selectedBrandUnitsAdapter = new SelectedBrandUnitsAdapter(selectedBUList);
            selectedBURecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            selectedBURecyclerView.setItemAnimator(new DefaultItemAnimator());
            selectedBURecyclerView.setAdapter(selectedBrandUnitsAdapter);
            selectedBrandUnitsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createdSelectedBUnitsListUI",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchRegions(String[] dataListForRegionsDisplay) {
        try {
            ISAP_Utils.showISAPProgressDialog(NewInitiativeActivity.this, ISAP_Constants.FETCH_MARKET_DETAILS, false);
            IsapService = APiUtils.getUserService();
            GetRegionModel getRegionModel = new GetRegionModel();
            getRegionModel.setGeos(dataListForRegionsDisplay);
            Call call = IsapService.getRegions(getRegionModel);
            call.enqueue(new Callback<RegionsResponse>() {
                @Override
                public void onResponse(Call<RegionsResponse> call, Response<RegionsResponse> response) {
                    regionsResponseModel = response.body();
                    gson = new Gson();
                    String data = gson.toJson(regionsResponseModel);
                    LogUtils.printLog(NewInitiativeActivity.this, "fetchRegions", "Fetch region response :" + gson.toJson(regionsResponseModel).toString());
                    ISAP_Utils.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<RegionsResponse> call, Throwable t) {
                    ISAP_Utils.dismissProgressDialog();
                    LogUtils.printLog(NewInitiativeActivity.this, "fetchRegions", "Fetch Region failure message :" + t.getMessage());
                }
            });
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"fetchRegions",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCountries(String[] dataListForCountriesDisplayList) {

        try {
            ISAP_Utils.showISAPProgressDialog(NewInitiativeActivity.this, ISAP_Constants.FETCH_COUNTRY_DETAILS, false);
            IsapService = APiUtils.getUserService();
            GetCountryModel getCountryModel = new GetCountryModel();
            getCountryModel.setRegions(dataListForCountriesDisplayList);
            Call call = IsapService.getCountries(getCountryModel);
            call.enqueue(new Callback<GetCountryResponse>() {
                @Override
                public void onResponse(Call<GetCountryResponse> call, Response<GetCountryResponse> response) {
                    getCountryResponseModel = response.body();
                    gson = new Gson();
                    String data = gson.toJson(getCountryResponseModel);
                    LogUtils.printLog(NewInitiativeActivity.this, "fetchCountries", "Fetch country response :" + gson.toJson(getCountryResponseModel).toString());
                    ISAP_Utils.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<GetCountryResponse> call, Throwable t) {
                    ISAP_Utils.dismissProgressDialog();
                    LogUtils.printLog(NewInitiativeActivity.this, "fetchCountries", "Fetch country failure message :" + t.getMessage());

                }
            });
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"fetchCountries",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchInitiativeDetails(int clientId, int initiativeId) {

        try {
            ISAP_Utils.showISAPProgressDialog(NewInitiativeActivity.this, ISAP_Constants.POPULATE_INITIATIVE_FIELDS, false);
            IsapService = APiUtils.getUserService();
            Call call = IsapService.getInitiativeFields(clientId, initiativeId);
            call.enqueue(new Callback<InitiativeFields_Model>() {
                @Override
                public void onResponse(Call<InitiativeFields_Model> call, Response<InitiativeFields_Model> response) {
                    initiativeFields_model = response.body();
                    gson = new Gson();
                    String data = gson.toJson(initiativeFields_model.getInitiativeData().getLINKEDOPPT());
                    String data1 = gson.toJson(initiativeFields_model.getInitiativeData().getLOPT_INITIATIVES());
                    LogUtils.printLog(NewInitiativeActivity.this, "fetchInitiativeDetails", "Fetch Initiative response :" + gson.toJson(initiativeFields_model).toString());
                    ISAP_Utils.dismissProgressDialog();
                    if (isEditingFlag) {
                        updateUIFieldsforEditingInitiative();
                    } else populateDefaultFields();
                    showAddLinkedOpptsRecyclerView();
                    showProgressSpinner(progressLinearLayout, convertProgressObjectListToString(), progressSpinner, "progress");
                    showProgressSpinner(industrySolutionsLinearLayout, convertIndustrySolutionObjectListToString(), industrySolutionsSpinner, "industry");
                    showProgressSpinner(partnerRoleLinearLayout, convertPartnerRoleObjectToString(), partnerRoleSpinner, "pRole");
                    showProgressSpinner(yearSpinnerLinearLayout, convertClosedDateObjectListToString(), yearSpinner, "closedDate");
                }

                @Override
                public void onFailure(Call<InitiativeFields_Model> call, Throwable t) {
                    ISAP_Utils.dismissProgressDialog();
                    LogUtils.printLog(NewInitiativeActivity.this, "fetchInitiativeDetails", "Fetch initiative field failure message :" + t.getMessage());
                }
            });
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"fetchInitiativeDetails",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void populateDefaultFields() {

        try {
            createdDate.setText(initiativeFields_model.getInitiativeDefault().getV1_CREATEDATE());
            splitYear0.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[0].getYEAR());
            splitYear1.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[1].getYEAR());
            splitYear2.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[2].getYEAR());
            splitYear3.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[3].getYEAR());
            splitYear4.setText(initiativeFields_model.getInitiativeDefault().getV1_SPLITVALUES()[4].getYEAR());
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"populateDefaultFields",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForGoalsMultiselectRecyclerView() {

        try {
            if (goalsList == null || goalsList.size() == 0) {
                //if initiativesList (which is initiative only null, we need to preare data set from service else use
                //what is available
                goalsList = new ArrayList<GOALS>();
                for (int i = 0; i < initiativeFields_model.getInitiativeData().getGOALS().length; i++) {
                    GOALS goalData = new GOALS();
                    goalData.setVALUE(initiativeFields_model.getInitiativeData().getGOALS()[i].getNAME());
                    goalData.setKEY(initiativeFields_model.getInitiativeData().getGOALS()[i].getKEY());
                    goalsList.add(goalData);
                }
            }
            choiceMap = new HashMap<Integer, Boolean>();
            linkedGoalsMultiSelectAdapter = new LinkedGoalsMultiSelectAdapter(goalsList, choiceMap);
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForGoalsMultiselectRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void createDataListForAddedLinkedOpptsRecyclerView() {

        try {
            if (linkedopptList == null || linkedopptList.size() == 0) {
                //if initiativesList (which is initiative only null, we need to preare data set from service else use
                //what is available
                linkedopptList = new ArrayList<LINKEDOPPT>();
                for (int i = 0; i < initiativeFields_model.getInitiativeData().getLINKEDOPPT().length; i++) {
                    LINKEDOPPT linkedopptData = new LINKEDOPPT();
                    linkedopptData.setOPPORTUNITY_ID(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getOPPORTUNITY_ID());
                    linkedopptData.setINITIATIVE_KEY(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getINITIATIVE_KEY());
                    linkedopptData.setLINKED_INITIATIVE(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getLINKED_INITIATIVE());
                    linkedopptData.setCLOSE_DATE(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getCLOSE_DATE());
                    linkedopptData.setOPP_OWNER_CNUM(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getOPP_OWNER_CNUM());
                    linkedopptData.setSALES_STAGE(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getSALES_STAGE());
                    linkedopptData.setOPPORTUNITY_NM(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getOPPORTUNITY_NM());
                    linkedopptData.setTCV_OPPTY_VAL(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getTCV_OPPTY_VAL());
                    linkedopptData.setOWNER(initiativeFields_model.getInitiativeData().getLINKEDOPPT()[i].getOWNER());
                    linkedopptList.add(linkedopptData);
                }
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"createDataListForAddedLinkedOpptsRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddLinkedOpptsRecyclerView() {

        try {
            linkedOPPTsChoiceMap = new HashMap<Integer, Boolean>();
            createDataListForAddedLinkedOpptsRecyclerView();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            addLinkedOpptsRecyclerView.setLayoutManager(mLayoutManager);
            addLinkedOpptsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            addLinkedOpptAdapter = new AddLinkedOpptAdapter(linkedopptList, linkedOPPTsChoiceMap);
            addLinkedOpptsRecyclerView.setAdapter(addLinkedOpptAdapter);
            addLinkedOpptAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showAddLinkedOpptsRecyclerView",e.getMessage());
//            Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void showLinkedGoalsRecyclerView() {

        try {
            createDataListForGoalsMultiselectRecyclerView();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            dialog_recycleView.setLayoutManager(mLayoutManager);
            dialog_recycleView.setItemAnimator(new DefaultItemAnimator());
            dialog_recycleView.setAdapter(linkedGoalsMultiSelectAdapter);
            ISAP_Utils.dismissProgressDialog();
            linkedGoalsMultiSelectAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"showLinkedGoalsRecyclerView",e.getMessage());
//
//      Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void showForwardAnimation(View view1, View view2) {
        view1.animate().alpha(0.0f);
        view1.setVisibility(View.INVISIBLE);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        view2.startAnimation(AnimationUtils.loadAnimation(
                context, R.anim.slide_to_left
        ));
    }

    private void showBackwardAnimation(View view1, View view2) {
        view2.animate().alpha(0.0f);
        view2.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.GONE);
        view1.setVisibility(View.VISIBLE);
        view1.startAnimation(AnimationUtils.loadAnimation(
                context, R.anim.slide_to_right
        ));
    }

    private void animateBackwardViewLayouts() {
        if (initiativeLayoutPageNumber == 1) {
            previousLabelTextView.setTextColor(Color.GRAY);
            showBackwardAnimation(initiativeLayoutView1, initiativeLayoutView2);
        } else {
            nextLabelTextView.setTextColor(Color.parseColor("#65b0c1"));
            showBackwardAnimation(initiativeLayoutView2, initiativeLayoutView3);
        }
    }

    private void animateForwardViewLayouts() {
        if (initiativeLayoutPageNumber == 3) {
            nextLabelTextView.setTextColor(Color.GRAY);
            showForwardAnimation(initiativeLayoutView2, initiativeLayoutView3);
        } else {
            previousLabelTextView.setTextColor(Color.parseColor("#65b0c1"));
            showForwardAnimation(initiativeLayoutView1, initiativeLayoutView2);
        }
    }

    private String getBigDecimalCalculation(EditText total, int perchantageValue) {

        BigDecimal bd1 = new BigDecimal(total.getText().toString().replace(",", ""));
        BigDecimal bd2 = new BigDecimal(perchantageValue);
        BigDecimal result = bd1.divide(ONE_HUNDRED);
        result = result.multiply(bd2);
        return String.valueOf(result);
    }

    private String getBigDecimalToMillian(String value) {

//        BigDecimal bg1 = new BigDecimal(value);
//        bg1 = bg1.divide(ONE_MILLION);
//        String result = String.valueOf(bg1);
//        return result;
        double splitDouble = Double.parseDouble(value) / 1000000;
        splitDouble = Double.valueOf(new DecimalFormat("##.##").format(splitDouble));
        return String.valueOf(splitDouble);
    }

    /*calculating current year, if current year and select year both are same hide split valuse*/
    private void compareSplitCurrentYear(String closeDate) {
        String currentDate = createdDate.getText().toString().trim();
        if (currentDate != null && closeDate != null) {
            String[] split = currentDate.split("-");
            String currentYear = split[0];
            if (currentYear.equals(closeDate)) {
                mSplitValueByYear.setVisibility(View.GONE);
                mLinearSplit.setVisibility(View.GONE);
                temparaySplit[1] = ini_value.getText().toString().replace(",", "");
                temparaySplit[2] = "0.00";
                temparaySplit[3] = "0.00";
                temparaySplit[4] = "0.00";
                mRemaingValue.setVisibility(View.GONE);
            } else {
                mSplitValueByYear.setVisibility(View.VISIBLE);
                mLinearSplit.setVisibility(View.VISIBLE);
                isExpand = true;
                ((View) findViewById(R.id.show_collaps_minus)).setVisibility(View.VISIBLE);
                ((View) findViewById(R.id.show_collaps_plus)).setVisibility(View.GONE);
                mRemaingValue.setVisibility(View.VISIBLE);
            }

        }


    }

    private void getSubtractionRemaingValue() {


        try {
            BigDecimal total = new BigDecimal(ini_value.getText().toString().trim().replace(",", ""));
            BigDecimal bd1 = new BigDecimal(temparaySplit[1]);
            BigDecimal bd2 = new BigDecimal(temparaySplit[2]);
            BigDecimal bd3 = new BigDecimal(temparaySplit[3]);
            BigDecimal bd4 = new BigDecimal(temparaySplit[4]);

            BigDecimal result = total.subtract(bd1.add(bd2).add(bd3).add(bd4));

            if (result.compareTo(BigDecimal.ZERO) < 0) {
                Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.ERROR_REMAINING_VALUE, Toast.LENGTH_SHORT).show();

            } else {
                String remaingValue = String.valueOf(result).replace(".00", "");
                remaingValue=decim.format(Double.parseDouble(remaingValue));
                mRemaingValue.setText("$" + remaingValue + " remaining to assign.");
            }

        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getSubtractionRemaingValue",e.getMessage());

        }

    }

    private void getAdditionRemainingValue(int split, String editable) {

        try {

            BigDecimal splitValu = new BigDecimal(editable);
            // splitValu = splitValu.multiply(ONE_MILLIAN);
            if (split == 1) {
                temparaySplit[1] = String.valueOf(splitValu);
            } else if (split == 2) {
                temparaySplit[2] = String.valueOf(splitValu);
            } else if (split == 3) {
                temparaySplit[3] = String.valueOf(splitValu);
            } else if (split == 4) {
                temparaySplit[4] = String.valueOf(splitValu);
            }

            BigDecimal bd1 = new BigDecimal(temparaySplit[1]);
            BigDecimal bd2 = new BigDecimal(temparaySplit[2]);
            BigDecimal bd3 = new BigDecimal(temparaySplit[3]);
            BigDecimal bd4 = new BigDecimal(temparaySplit[4]);

            BigDecimal total = new BigDecimal(ini_value.getText().toString().replace(",", ""));
            BigDecimal result = total.subtract(bd1.add(bd2).add(bd3).add(bd4));
            if (result.compareTo(BigDecimal.ZERO) < 0) {
                Toast.makeText(NewInitiativeActivity.this, ISAP_Constants.ERROR_REMAINING_VALUE, Toast.LENGTH_SHORT).show();

            } else {
                String remaingValue = String.valueOf(result).replace(".00", "");
                remaingValue=decim.format(Double.parseDouble(remaingValue));
                mRemaingValue.setText("$" + remaingValue + " remaining to assign.");
            }
        } catch (Exception e) {
            LogUtils.printLog(NewInitiativeActivity.this,"getAdditionRemainingValue",e.getMessage());
        }


    }

    /*black color background for clicked splityear,rlsplit,splitValue*/
    private void blackBackGroundChange(TextView splitYear, RelativeLayout rlSplit, EditText splitValue) {
        splitYear.setTextColor(Color.BLACK);
        rlSplit.setBackgroundResource(R.drawable.initiative_splite_bg_black);
        splitValue.setTextColor(Color.BLACK);
    }

    /*gray color background for clicked splityear,rlsplit,splitValue*/
    private void grayBackGroundChange(TextView splitYear, RelativeLayout rlSplit, EditText splitValue) {
        splitYear.setTextColor(Color.GRAY);
        rlSplit.setBackgroundResource(R.drawable.initiative_splite_bg);
        splitValue.setTextColor(Color.GRAY);
    }
    private void emptySplitValues() {
        String value1,value2,value3,value4;
        value1=temparaySplit[1];
        value2=temparaySplit[2];
        value3=temparaySplit[3];
        value4=temparaySplit[4];
        if(value1.equals("0.00") && value2.equals("0.00")&& value3.equals("0.00") && value4.equals("0.00")){
            if(spinnerPosition==2){
                temparaySplit[0]="0.00";
                temparaySplit[1]="0.00";
                temparaySplit[2]=ini_value.getText().toString().replace(",","");
                temparaySplit[3]="0.00";
                temparaySplit[4]="0.00";
            }else if(spinnerPosition==3){
                temparaySplit[0]="0.00";
                temparaySplit[1]="0.00";
                temparaySplit[2]="0.00";
                temparaySplit[3]=ini_value.getText().toString().replace(",","");
                temparaySplit[4]="0.00";
            }else if(spinnerPosition==4 || spinnerPosition>4){
                temparaySplit[0]="0.00";
                temparaySplit[1]="0.00";
                temparaySplit[2]="0.00";
                temparaySplit[3]="0.00";
                temparaySplit[4]=ini_value.getText().toString().replace(",","");

            }
        }
    }

}