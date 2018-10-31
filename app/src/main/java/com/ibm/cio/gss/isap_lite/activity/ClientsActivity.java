


package com.ibm.cio.gss.isap_lite.activity;

/*
Class Name : "ClientsActivity"
Description :"Display the list of clients with search,typeahed and alpha index serach"
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.adapter.GridListAdapter;
import com.ibm.cio.gss.isap_lite.model.ClientsModel;
import com.ibm.cio.gss.isap_lite.model.Value;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;
import com.ibm.cio.gss.isap_lite.utility.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Handler;

public class ClientsActivity extends AppCompatActivity {

    public static ISAPService IsapService;
    public static List<ClientsModel> clients;
    private GridListAdapter adapter;
    private EditText filterText = null;
    private List<Value> data;
    public static ArrayList<Value> array_sort;
    int textlength = 0;
    ListView listView = null;
    private ProgressDialog progressDialog;
    private String[] sections;
    public ListView alphaList;
    private ArrayList<String> list;
    private ClientsModel clientsModel;
    private String userEmail="";
    public static int clientID=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        //hide phone status bar code start
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide phone status bar code end


        TextView selectClient = findViewById(R.id.selectMyClient);


        selectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 perform whatever you want on back arrow click
               if(ISAP_Utils.clientID!=0)
               {
                   ISAP_Utils.showISAPProgressDialog(ClientsActivity.this,ISAP_Constants.PROGRESSDIALOG_LOADING,false);
                   Intent intent=new Intent(ClientsActivity.this,IsapMenuActivity.class);
                   startActivity(intent);
                   ISAP_Utils.dismissProgressDialog();
               }
               else{
                   Toast.makeText(ClientsActivity.this, ISAP_Constants.CLIENT_NOT_SELECTED, Toast.LENGTH_SHORT).show();
               }


            }
        });
        userEmail= ISAP_Utils.LoggedInuserEmail;
        list = new ArrayList<String>();

        fetchClients(userEmail);
        listView = (ListView) findViewById(R.id.list_view);
        alphaList=(ListView) findViewById(R.id.listview);



    }

    private void fetchClients(String userName) {
        ISAP_Utils.showISAPProgressDialog(this, ISAP_Constants.PROGRESSDIALOG_FETCHING,false);
        clientsModel = new ClientsModel();
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getClients(userName);

        call.enqueue(new Callback<List<ClientsModel>>() {
            @Override
            public void onResponse(Call<List<ClientsModel>> call, Response<List<ClientsModel>> response) {

                clients = response.body();
                showClients(clients);
                LogUtils.printLog(ClientsActivity.this,"fetchClients","Number of clients section :"+clients.size());

            }

            @Override
            public void onFailure(Call<List<ClientsModel>> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
               // System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showClients(List<ClientsModel> clients) {

        try {
            data = new ArrayList<Value>();
            array_sort = new ArrayList<Value>();
            sections = new String[clients.size()];
            for (int i = 0; i < clients.size(); i++) {
                sections[i]=clients.get(i).getSection().toString();
                for (int j = 0; j < clients.get(i).getValue().length; j++) {

                    Value cName = new Value(clients.get(i).getValue()[j].getName(), clients.get(i).getSection(),clients.get(i).getValue()[j].getId());
                    data.add(cName);
                    array_sort.add(cName);

                }
            }
            filterText = (EditText) findViewById(R.id.filterText);
            filterText.addTextChangedListener(filterTextWatcher);
            filterText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        //performSearch();

                        if(ISAP_Utils.clientID!=0)
                        {
                            ISAP_Utils.showISAPProgressDialog(ClientsActivity.this,ISAP_Constants.PROGRESSDIALOG_LOADING,false);
                            Intent intent=new Intent(ClientsActivity.this,IsapMenuActivity.class);
                            startActivity(intent);
                            ISAP_Utils.dismissProgressDialog();
                        }
                        else{
                            Toast.makeText(ClientsActivity.this, ISAP_Constants.CLIENT_NOT_SELECTED, Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                    return false;
                }
            });


            data = sortAndAddSections(data);
            adapter = new GridListAdapter(getApplicationContext(), data, true);
            listView.setAdapter(adapter);

            //progressDialog.dismiss();


            ArrayAdapter adapteralpha = new ArrayAdapter<String>(getApplicationContext(), R.layout.client_alpha_index_layout, sections);
            alphaList.setAdapter(adapteralpha);
            ISAP_Utils.dismissProgressDialog();
            alphaList.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    String item = ((TextView)view).getText().toString();

                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                    array_sort.clear();
                    textlength=1;
                    for (int i = 0; i < data.size(); i++) {
                        if (textlength <= data.get(i).getName().length()) {

                            if (data.get(i).getName().toLowerCase().trim().substring(0, textlength).equals(item.toLowerCase().trim())) {
                                System.out.println("string matched for:" + filterText.getText().toString().toLowerCase().trim() + ",name:" + data.get(i).getName());
                                //set scroll on rolodex

                                adapter = new GridListAdapter(getApplicationContext(), data, true);
                                listView.setAdapter(adapter);
                                smoothScrollToPositionFromTop(listView,i);
                                break;

                            }
                        }
                    }


                }
            });

        }catch (Exception e){

            System.out.println("Exception Message:"+e.getMessage());
        }

    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            textlength = filterText.getText().length();
            if (textlength != 0) {
                try{
                    array_sort.clear();
                    for (int i = 0; i < data.size(); i++) {
                        if (textlength <= data.get(i).getName().length()) {

                            if (data.get(i).getName().toString().toLowerCase().trim().contains(filterText.getText().toString().toLowerCase().trim())) {
                                System.out.println("sring matched for:" + filterText.getText().toString().toLowerCase().trim() + ",name:" + data.get(i).getName());
                                Value item2 = new Value(data.get(i).getName(), data.get(i).getSection(),data.get(i).getId());
                                array_sort.add(item2);
                            }
                        }
                    }
                    array_sort = sortAndAddSections(array_sort);
                    adapter = new GridListAdapter(getApplicationContext(), array_sort, true);
                    listView.setAdapter(adapter);

                }catch (Exception e){
                    System.out.println("Exception Message:"+e.getMessage());
                }

            } else {
                adapter = new GridListAdapter(getApplicationContext(), data, true);
                listView.setAdapter(adapter);
            }
        }

    };


    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position) {


        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    view.setOnScrollListener(null);

                    // Fix for scrolling bug
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                           // view.setSelection(position);
                            view.setFastScrollEnabled(true);
                        }
                    });
                }
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) { }
        });

        // Perform scrolling to position
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                view.setSelection(position);
                //if you want smooth scroll use the below  and comment the above two lines
                // view.smoothScrollToPositionFromTop(position, 0);
            }
        });
    }


    private ArrayList sortAndAddSections(List<Value> itemList) {

        ArrayList tempList = new ArrayList();
        //First we sort the array
        try{
            Collections.sort(itemList);
            String header = "";
            for (int i = 0; i < itemList.size(); i++) {
                //If it is the start of a new section we create a new listcell and add it to our array

                if (header != itemList.get(i).getSection() && itemList.get(i).getSection() != null) {

                    Value sectionCell = new Value(itemList.get(i).getSection(), null,0);
                    sectionCell.setToSectionHeader();
                    tempList.add(sectionCell);
                    header = itemList.get(i).getSection();
                    i--;
                } else {
                    if (itemList.get(i).getSection() != null)
                        tempList.add(itemList.get(i));
                }
            }
        }catch (Exception e){
             System.out.println("Exception Message:"+e.getMessage());
        }

        return tempList;
    }

}
