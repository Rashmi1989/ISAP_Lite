package com.ibm.cio.gss.isap_lite.fragment;

/*
Class Name : "InitiativeFragment"
Description :"This fragment is used to display list of Initiatives associted with goals."
Author      :"Kabuli Behera"
Date of Creation :"March 07 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.LoginActivity;
import com.ibm.cio.gss.isap_lite.activity.SelectclientActivity;
import com.ibm.cio.gss.isap_lite.adapter.InitiativeGridListAdapter;
import com.ibm.cio.gss.isap_lite.adapter.SalesConnectAdapter;
import com.ibm.cio.gss.isap_lite.model.GoalsModel;
import com.ibm.cio.gss.isap_lite.model.InitiativeModel;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.stylekitIcons.MyDividerItemDecoration;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitiativeFragment extends Fragment {
    int clientId=0;
    String intranetId="";
    private ISAPService IsapService;
    private InitiativeModel initiativeModel;
    private InitiativeGridListAdapter initiativeAdapter;
    private SalesConnectAdapter salesConnectAdapter;
    private RecyclerView recyclerView;
    private HorizontalBarChart barChart;
    private TextView firstText;
    private TextView secondtext;
    private TextView thirdText;
    private TextView initiativeName;
    private TextView businessUnit;
    private TextView initiativeValue;
    private TextView closingDate;
    private Button isapInititivesButton;
    private Button salesConnectButton;
    private TextView more;
    private View icon;
    private boolean isSalesconnect;
    private LinearLayout mLinearLayout;



    public InitiativeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getActivity().findViewById(R.id.geo_market);
        view.setVisibility(view.GONE);
        clientId= ISAP_Utils.clientID;
        intranetId=ISAP_Utils.LoggedInuserEmail;

        // Inflate the layout for this fragment
        fetchInitiativeSummary(clientId,intranetId);
        RelativeLayout relativeLayout=getActivity().findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        LinearLayout moreView=getActivity().findViewById(R.id.moreLayout);
        moreView.setVisibility(View.VISIBLE);
        LinearLayout saveView=getActivity().findViewById(R.id.saveLayout);
        saveView.setVisibility(View.GONE);
        return inflater.inflate(R.layout.fragment_initiative, container, false);

    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.initiative_grid);
        barChart = (HorizontalBarChart) getView().findViewById(R.id.barchartHorizontal);
        initiativeName = (TextView) getView().findViewById(R.id.InitiativeName);
        businessUnit = (TextView) getView().findViewById(R.id.BusinessUnitName);
        initiativeValue = (TextView) getView().findViewById(R.id.InitiativeValueName);
        closingDate = (TextView) getView().findViewById(R.id.ClosingDateName);
        isapInititivesButton = (Button) getView().findViewById(R.id.show_initiatives_tab);
        isapInititivesButton.setOnClickListener(showInitiatives);
        salesConnectButton = (Button) getView().findViewById(R.id.salesConnect_tab);
        salesConnectButton.setOnClickListener(showSalesConnect);
        mLinearLayout = getView().findViewById(R.id.v_chart);

        barChart.setExtraBottomOffset(20);

        //need to move code to utility file for device specific
        /*DisplayMetrics displayMetrics=ISAP_Utils.getDeviceMetrics((Activity) getContext());
        if(displayMetrics.heightPixels==2368){
            //samsung 7
            LinearLayout layout= (LinearLayout)view.findViewById(R.id.v_chart);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,530);
            layout.setLayoutParams(parms);
        }
        else if(displayMetrics.heightPixels==1776){
            //Red mi
            LinearLayout layout= (LinearLayout)view.findViewById(R.id.v_chart);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,400);
            layout.setLayoutParams(parms);
        }
        else if(displayMetrics.heightPixels==2768){
            //samsung galaxy 8
            LinearLayout.LayoutParams grid = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,600);
            recyclerView.setLayoutParams(grid);
            LinearLayout layout= (LinearLayout)view.findViewById(R.id.v_chart);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,750);
            layout.setLayoutParams(parms);
        }
        else if(displayMetrics.heightPixels==2392){
            //samsung nexus 6 p
            LinearLayout.LayoutParams grid = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,500);
            recyclerView.setLayoutParams(grid);
            LinearLayout layout= (LinearLayout)view.findViewById(R.id.v_chart);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,600);
            layout.setLayoutParams(parms);
        }*/

    }

    public TextView changeLayoutParams(TextView textView,String heightWidthString,int value){

        LayoutParams params = (LayoutParams) textView.getLayoutParams();
        if(heightWidthString.equalsIgnoreCase("height")){

            params.height = value;
        }else{
            params.width = value;
        }
        textView.setLayoutParams(params);
        return textView;
    }

    private View.OnClickListener showInitiatives = new View.OnClickListener() {
        public void onClick(View v) {

            initiativeName = changeLayoutParams(initiativeName,"width",400);
            businessUnit = changeLayoutParams(businessUnit,"width",350);
            isSalesconnect = false;
            initiativeAdapter = new InitiativeGridListAdapter(initiativeModel.getINITIATIVES_TABLE());

            isapInititivesButton.setBackground(getResources().getDrawable(R.drawable.goal_round_btn,null));
            isapInititivesButton.setTextColor(getResources().getColor(R.color.colorLogin,null));
            salesConnectButton.setBackground(getResources().getDrawable(R.drawable.initiative_round_btn,null));
            salesConnectButton.setTextColor(getResources().getColor(R.color.colorAccent,null));

            if (initiativeModel.getINITIATIVES_TABLE() != null)
            {
                if (initiativeModel.getINITIATIVES_TABLE().length == 0)
                {
                    initiativeName.setText("There are no top 4 initiatives for this client");
                    businessUnit.setText("");
                    initiativeValue.setText("");
                    closingDate.setText("");
                }
                else {
                    initiativeName.setText("Initiative Name");
                    businessUnit.setText("Initiative Lead Name");
                    initiativeValue.setText("Initiative value ($M)");
                    closingDate.setText("Closing Date");
                }

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                // recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));
                recyclerView.setAdapter(initiativeAdapter);


                ISAP_Utils.dismissProgressDialog();
                initiativeAdapter.notifyDataSetChanged();
            }

        }
    };

    private View.OnClickListener showSalesConnect = new View.OnClickListener() {
        public void onClick(View v) {

            initiativeName = changeLayoutParams(initiativeName,"width",350);
            businessUnit = changeLayoutParams(businessUnit,"width",300);

            isSalesconnect = true;
            salesConnectAdapter = new SalesConnectAdapter(initiativeModel.getSALES_OPT_TABLE());

            salesConnectButton.setBackground(getResources().getDrawable(R.drawable.initiative_tab_active,null));
            salesConnectButton.setTextColor(getResources().getColor(R.color.colorLogin,null));
            isapInititivesButton.setBackground(getResources().getDrawable(R.drawable.goal_tab_inactive,null));
            isapInititivesButton.setTextColor(getResources().getColor(R.color.colorAccent,null));

            if (initiativeModel.getSALES_OPT_TABLE() != null)
            {
                if (initiativeModel.getSALES_OPT_TABLE().length == 0)
                {
                    initiativeName.setText("There are no top 4 Sales Connect opportunities for this client!");
                    businessUnit.setText("");
                    initiativeValue.setText("");
                    closingDate.setText("");
                }
                else
                {
                    initiativeName.setText("Opportunity ID");
                    businessUnit.setText("Opportunity Name");
                    initiativeValue.setText("TCV($M)");
                    closingDate.setText("Closing Date");
                }

                //  initiativeAdapter = new InitiativeGridListAdapter(initiativeModel.getINITIATIVES_TABLE());

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                // recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));
                // recyclerView.setAdapter(initiativeAdapter);
                recyclerView.setAdapter(salesConnectAdapter);


                ISAP_Utils.dismissProgressDialog();
                // initiativeAdapter.notifyDataSetChanged();
                salesConnectAdapter.notifyDataSetChanged();
            }

        }
    };
    public void fetchInitiativeSummary(int clientId, String intranetId) {

        try {
            IsapService = APiUtils.getUserService();

            Call call = IsapService.getInitiativeSummary(clientId,"All",intranetId,"-1","-1");
            ISAP_Utils.showISAPProgressDialog(getActivity(), ISAP_Constants.FETCH_INITIATIVES,false);

//        Call call = IsapService.getMyPlan(SingletonSession.Instance());
            call.enqueue(new Callback<InitiativeModel>() {
                @Override
                public void onResponse(Call<InitiativeModel> call, Response<InitiativeModel> response) {

                    initiativeModel = response.body();
                    if  (initiativeModel.getINITIATIVES_SUMMARY() != null)
                    {
                        populateInitiativeTable();
                        setChartData();
                    }
//                    System.out.println("Initiative summary size:"+initiativeModel.getINITIATIVES_SUMMARY());
                    if(ISAP_Utils.isProgressDialogVisible())
                        ISAP_Utils.dismissProgressDialog();

                }

                @Override
                public void onFailure(Call<InitiativeModel> call, Throwable t) {
                    if(ISAP_Utils.isProgressDialogVisible())
                        ISAP_Utils.dismissProgressDialog();
                    System.out.println("data from clients failure:>>>>" + t.toString());
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){

        }

    }

    private void populateInitiativeTable() {

        try{
            firstText = (TextView) getView().findViewById(R.id.firstText);
            secondtext = (TextView) getView().findViewById(R.id.secondText);
            thirdText = (TextView) getView().findViewById(R.id.thirdText);
            // System.out.println("data from clients failure:>>>>" + initiativeModel.getINITIATIVES_SUMMARY().getINITIA_COUNT());

            if (initiativeModel.getINITIATIVES_SUMMARY().getOPPTY_COUNT() == null ||initiativeModel.getINITIATIVES_SUMMARY().getINITIA_COUNT() ==null ||
                    initiativeModel.getINITIATIVES_SUMMARY().getOPPTY_VAL() == null || initiativeModel.getINITIATIVES_SUMMARY().getINITIA_VAL() == null) {
                firstText.setText("There are no active ISAP initiatives linked to  SalesConnect opportunities");
                secondtext.setText("");
            }
            else
            {
                String S1 = initiativeModel.getINITIATIVES_SUMMARY().getOPPTY_COUNT();
                String S2 = initiativeModel.getINITIATIVES_SUMMARY().getINITIA_COUNT();
                String S3 = initiativeModel.getINITIATIVES_SUMMARY().getOPPTY_VAL();
                String S4 = initiativeModel.getINITIATIVES_SUMMARY().getINITIA_VAL();
                firstText.setText("There are " +S2 +" active ISAP initiatives linked to");
                secondtext.setText(" "+S1+" SalesConnect opportunities with a total opportunity ");
                thirdText.setText(Html.fromHtml("value of " + S3 + ", and a " + "<font color=\"#429386\">" +"total initiative value of " +S4+" "));

            }


            if (isSalesconnect == true)
            {
                salesConnectAdapter = new SalesConnectAdapter(initiativeModel.getSALES_OPT_TABLE());
                if (initiativeModel.getSALES_OPT_TABLE().length == 0)
                {
                    initiativeName.setText("There are no top 4 Sales Connect opportunities for this client!");
                    businessUnit.setText("");
                    initiativeValue.setText("");
                    closingDate.setText("");
                }
                else
                {
                    initiativeName.setText("Opportunity ID");
                    businessUnit.setText("Opportunity Name");
                    initiativeValue.setText("TCV($M)");
                    closingDate.setText("Closing Date");
                }

                //  initiativeAdapter = new InitiativeGridListAdapter(initiativeModel.getINITIATIVES_TABLE());

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //  recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));
                // recyclerView.setAdapter(initiativeAdapter);
                recyclerView.setAdapter(salesConnectAdapter);


                ISAP_Utils.dismissProgressDialog();
                // initiativeAdapter.notifyDataSetChanged();
                salesConnectAdapter.notifyDataSetChanged();
            }
            else
            {
                initiativeAdapter = new InitiativeGridListAdapter(initiativeModel.getINITIATIVES_TABLE());
                if (initiativeModel.getINITIATIVES_TABLE().length == 0)
                {
                    initiativeName.setText("");
                    businessUnit.setText("");
                    initiativeValue.setText("");
                    closingDate.setText("");
                }
                else {
                    initiativeName.setText("Initiative Name");
                    businessUnit.setText("Initiative Lead Name");
                    initiativeValue.setText("Initiative value ($M)");
                    closingDate.setText("Closing Date");
                }
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                // recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,16));
                recyclerView.setAdapter(initiativeAdapter);


                ISAP_Utils.dismissProgressDialog();
                initiativeAdapter.notifyDataSetChanged();
            }
        }catch(Exception e){


        }

    }

    private void setChartData(){

        try{
            final ArrayList<String> labels = new ArrayList<String>();
            ArrayList<BarEntry> points = new ArrayList<>();

            String s = "";
            int xAxisValue = 0;
            int maxValue = 0;
            for (int i = 0; i<initiativeModel.getINITIATIVES_GRAPH().getSALES_STAGE_ARRAY().length; i++)
            {
                labels.add(initiativeModel.getINITIATIVES_GRAPH().getSALES_STAGE_ARRAY()[i]);
                String S = initiativeModel.getINITIATIVES_GRAPH().getOPPTY_VALUE_ARRAY()[i][0];
                float f1 = Float.parseFloat(S);
                points.add(new BarEntry( f1, i));
                if(maxValue < (int)f1)
                {
                    maxValue = (int)f1;
                }
            }

            s = String.valueOf(maxValue);
            xAxisValue = s.length();

            int extractValue=maxValue/5;
            if(extractValue==0){
                extractValue=1;
            }

            double xAxisValueInChart=maxValue+extractValue;

            // Add bars to a bar set
            MyXAxisValueFormatter formatter = new MyXAxisValueFormatter(labels);
            XAxis axis1 = barChart.getXAxis();
            barChart.setNoDataText("No Data");
            barChart.setDescription(null);
            // barChart.setDrawGridBackground(false);
            barChart.getLegend().setEnabled(true);
            barChart.setAutoScaleMinMaxEnabled(false);
            barChart.setPinchZoom(false);
            barChart.setDoubleTapToZoomEnabled(false);
            barChart.animateY(10);
            barChart.getLegend().setEnabled(false);//#205 fixed



            BarDataSet barSet = new BarDataSet(points, "");
//        barSet.setColors(new int[]{ContextCompat.getColor(barChart.getContext(), R.color.),
//                ContextCompat.getColor(barChart.getContext(), R.color.orange),
//                ContextCompat.getColor(barChart.getContext(), R.color.red),barChart.getContext(), R.color.green});
            barSet.setColors(new int[] {Color.parseColor("#443B8E"),Color.parseColor("#AA9AEE"),Color.parseColor("#81A7F0"),Color.parseColor("#6FD2BC")});

            barSet.setValueTextColor(Color.parseColor("#274b60"));
//            barSet.setBarSpacePercent(20f);
            barSet.setValueTextSize(13);
            // Create a BarData object and assign it to the chart
            BarData barData = new BarData(labels,barSet);

            axis1.setValueFormatter(formatter);
            axis1.setTextSize(12);
            axis1.setDrawGridLines(false);

            axis1.setPosition(XAxis.XAxisPosition.BOTTOM);
            axis1.setDrawAxisLine(false);
            // axis1.setDrawGridLines(true);




            YAxis yl = barChart.getAxisLeft();
            yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yl.setDrawGridLines(true);
            yl.setEnabled(false);

            // yl.setAxisMinimum(0f);
            yl.setAxisMinValue(0f);
            yl.setDrawAxisLine(true);
            yl.setGranularity(1f);

            yl.setDrawLimitLinesBehindData(true);



            YAxis yr = barChart.getAxisRight();
            yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yr.setDrawGridLines(true);

            // yr.setAxisMaxValue(0f);
            // yr.setDrawAxisLine(true);
            yr.setEnabled(true);
            // yr.setGranularity(1f);
            yr.setAxisMaxValue((float) xAxisValueInChart);



            LimitLine myLimitLine = new LimitLine((float) xAxisValueInChart/2,"");
            myLimitLine.enableDashedLine(5.0f,10.0f,0);
            myLimitLine.setLineColor(Color.parseColor("#429386"));

            // barChart.getAxisRight().setEnabled(true);
            barChart.getAxisRight().setAxisMinValue(0);
            barChart.getAxisRight().setDrawAxisLine(false);
            barChart.getAxisRight().setTextColor(Color.parseColor("#274b60"));
            barChart.getAxisRight().addLimitLine(myLimitLine);
            barChart.getAxisLeft().setAxisMaxValue((float) xAxisValueInChart);

            barChart.setData(barData);
            if (labels != null && labels.size() > 0) {
                int size = labels.size();
                LayoutParams params = mLinearLayout.getLayoutParams();
                params.width = LayoutParams.MATCH_PARENT;

                if (size == 3) {
                    params.height = dpToPx(100);
                } else if (size == 4) {
                    params.height = dpToPx(150);
                } else if (size == 5) {
                    params.height = dpToPx(180);
                }


                mLinearLayout.setLayoutParams(params);
            }
        }catch (Exception e){

        }


    }
    private int dpToPx(int dp) {
        float density = getActivity().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
    public class MyXAxisValueFormatter implements XAxisValueFormatter {

        private ArrayList mValues;

        public MyXAxisValueFormatter(ArrayList values) {
            this.mValues = values;
            // return (String) mValues.get((int) value);
        }

        @Override
        public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
            // original is the original value to use, x-index is the index in your x-values array
            // implement your logic here ...
            return (String) mValues.get((int) index);
        }


    }

}
