package com.ibm.cio.gss.isap_lite.fragment;
/*
Class Name : "My Plan Fragment"
Description :"This fragment is used to display My plan page with revenue and aspirations graphs and charts."
Author      :"Kabuli Behera"
Date of Creation :"March 07 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ibm.cio.gss.isap_lite.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.model.BUSINESS_UNIT;
import com.ibm.cio.gss.isap_lite.model.CircularImageView;
import com.ibm.cio.gss.isap_lite.model.GetImages;
import com.ibm.cio.gss.isap_lite.model.TEAM_MEMBER;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlanFragment extends Fragment  {
    private TextView tvX, tvY;
    public TextView ty, tz;
    public LinearLayout stack1;
    public LinearLayout stack2;
    public LinearLayout stack3;
    public LinearLayout stack4;
    public TextView year1_btn;
    public TextView year2_btn;
    public TextView year3_btn;
    public TextView year4_btn;
    private String[][] chartDataArrayList;
    public TableLayout tableLayout;
    private double [] totalArrayList;
    private ArrayList<String> team_member =null;
    private ArrayList<String> team_designation = null;
    private ArrayList<String> team_name=null;
    private static boolean isAllStackGray=false;
    private String firstYearString;
    private TextView gray_stack1,gray_stack2,gray_stack3,gray_stack4;
//    private  boolean gray1_status=false;

    BarChart barChart;
    ArrayList responseArray;
    HashMap<Integer, Boolean> buttonSelectDictionary;
    BarDataSet barDataSet1, barDataSet2, barDataSet3, barDataSet4, barDataSet5, barDataSet6, barDataSet7;
    //Declares Myplan Legends
    private  Button gbsLegend ;
    private  Button gtsLegend ;
    private  Button systemsLegend;
    private  Button cloudLegend ;
    private  Button solutionsLegend ;
    private  Button igfLegend ;
    private  Button totalLegend ;
    private static boolean isStack1On=false;
    private static boolean isStack2On=false;
    private static boolean isStack3On=false;
    private static boolean isStack4On=false;


    public MyPlanFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        View rootView = inflater.inflate(R.layout.fragment_my_plan, container,
                false);
        tableLayout = (TableLayout) rootView.findViewById(R.id.table_main);
        /*LinearLayout moreView=getActivity().findViewById(R.id.moreLayout);
        moreView.setVisibility(View.GONE);
        LinearLayout saveView=getActivity().findViewById(R.id.saveLayout);
        saveView.setVisibility(View.GONE);*/
        RelativeLayout relativeLayout=getActivity().findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.GONE);

        return rootView;
        // return inflater.inflate(R.layout.fragment_my_plan, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClickShowStackChart();
        onClickShowBarChart();
        onClickLegendIcons();
        onClickGrayedStack();


        barChart = (BarChart) getView().findViewById(R.id.barchart);

        View teamMember = getActivity().findViewById(R.id.geo_market);
        teamMember.setVisibility(teamMember.VISIBLE);
    /*formChartObjects();
    drawChartAfterFilterOption();
    drawChart(8);*/
    }



    public void populateMyPlanView(String[][] dataArray, BUSINESS_UNIT[] business_unit, TEAM_MEMBER[] team_member){


        if(dataArray!=null)
            chartDataArrayList = dataArray;
        if(dataArray!=null)
            formChartObjects(dataArray);
        drawChartAfterFilterOption();
        drawChart(8);
        if(dataArray!=null)
            populateStackChartData(dataArray);
        resetLegends();
        tableLayout.removeAllViews();
        if(business_unit!=null)
            displayGrid(business_unit);
        if(team_member!=null)
            displayTeamMembers(team_member);
//        ISAP_Utils.dismissProgressDialog();
    }
    private void resetLegends() {
        gbsLegend.getBackground().setAlpha(255);
        gtsLegend.getBackground().setAlpha(255);
        cloudLegend.getBackground().setAlpha(255);
        solutionsLegend.getBackground().setAlpha(255);
        systemsLegend.getBackground().setAlpha(255);
        igfLegend.getBackground().setAlpha(255);
        totalLegend.getBackground().setAlpha(255);
        if(getView(). findViewById(R.id.yr1_total).getVisibility()!=View.VISIBLE) {
            getView().findViewById(R.id.yr1_total).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr2_total).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr3_total).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr4_total).setVisibility(View.VISIBLE);
        }
        if(getView(). findViewById(R.id.yr1_gbs).getVisibility()!=View.VISIBLE) {
            getView().findViewById(R.id.yr1_gbs).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr2_gbs).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr3_gbs).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr4_gbs).setVisibility(View.VISIBLE);
        }
        if(getView(). findViewById(R.id.yr1_gts).getVisibility()!=View.VISIBLE) {
            getView().findViewById(R.id.yr1_gts).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr2_gts).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr3_gts).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr4_gts).setVisibility(View.VISIBLE);
        }
        if(getView(). findViewById(R.id.yr1_cloud).getVisibility()!=View.VISIBLE) {
            getView().findViewById(R.id.yr1_cloud).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr2_cloud).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr3_cloud).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr4_cloud).setVisibility(View.VISIBLE);
        }
        if(getView(). findViewById(R.id.yr1_systems).getVisibility()!=View.VISIBLE) {
            getView().findViewById(R.id.yr1_systems).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr2_systems).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr3_systems).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr4_systems).setVisibility(View.VISIBLE);
        }
        if(getView(). findViewById(R.id.yr1_solutions).getVisibility()!=View.VISIBLE) {
            getView().findViewById(R.id.yr1_solutions).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr2_solutions).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr3_solutions).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr4_solutions).setVisibility(View.VISIBLE);
        }
        if(getView(). findViewById(R.id.yr1_igf).getVisibility()!=View.VISIBLE) {
            getView().findViewById(R.id.yr1_igf).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr2_igf).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr3_igf).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.yr4_igf).setVisibility(View.VISIBLE);
        }

    }
    private void displayTeamMembers(TEAM_MEMBER[] team_member_array) {
        team_member = new ArrayList<String>();
        team_designation = new ArrayList<String>();
        team_name = new ArrayList<String>();

        try{
            for(int j=0;j<team_member_array.length;j++){
                team_member.add(ISAP_Constants.IMC_IMAGE_URL+team_member_array[j].getINTRANET_ID());
                team_designation.add(team_member_array[j].getRSPNSBLTY_NM());
                team_name.add(team_member_array[j].getEMP_NAME());
            }

            LinearLayout layout = (LinearLayout)getView().findViewById(R.id.Gallery);
            layout.removeAllViews();


            for (int j = 0; j < (team_member.size()); j++) {


                String url = team_member.get(j);

                LinearLayout LL = new LinearLayout(getActivity());
                LL.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                LL.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        140, 140);
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                        320, 70);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                        300, 70);


                CircularImageView image = new CircularImageView(getActivity().getApplicationContext(),null);
                // lp.setMargins(3, 3, 3, 3);
                image.setLayoutParams(lp);
                lp.setMarginStart(100);
                new GetImages(url, image, null).execute();
                LL.addView(image,lp);

                TextView designation = new TextView(getActivity());
                designation.setText(team_designation.get(j));
                designation.setMaxLines(1);
                designation.setEllipsize(TextUtils.TruncateAt.END);
                // lp1.setMargins(3, 3, 3, 3);
                designation.setLayoutParams(lp1);
                lp1.setMarginStart(60);
                LL.addView(designation,lp1);


                TextView name = new TextView(getActivity().getApplicationContext());
                name.setText(team_name.get(j));
                name.setMaxLines(1);
                name.setEllipsize(TextUtils.TruncateAt.END);
                name.setTypeface(Typeface.DEFAULT_BOLD);
                name.setPadding(10,0,10,0);
                //  lp2.setMargins(3, 3, 3, 3);
                designation.setLayoutParams(lp2);
                lp2.setMarginStart(60);
                LL.addView(name,lp2);

                layout.addView(LL);


            }

        }catch (Exception e){
            System.out.println("Exception message is:"+e.getMessage());
        }

    }

    public void populateStackChartData(String[][] dataArray){

        try{
            double[] gBSArray = parseData(dataArray[1]);
            double[] gTSArray = parseData(dataArray[2]);
            double[] gIGFArray = parseData(dataArray[6]);
            double[] gCloudArray = parseData(dataArray[4]);
            double[] gSystemsArray = parseData(dataArray[3]);
            double[] gSolutionArray = parseData(dataArray[5]);
            double[] gTotalArray = parseData(dataArray[7]);

            //Populate GBS Data
            TextView yr1_gbsData = (TextView) getView().findViewById(R.id.yr1_gbs);
            TextView yr2_gbsData = (TextView) getView().findViewById(R.id.yr2_gbs);
            TextView yr3_gbsData = (TextView) getView().findViewById(R.id.yr3_gbs);
            TextView yr4_gbsData = (TextView) getView().findViewById(R.id.yr4_gbs);
            yr1_gbsData.setText(String.valueOf(gBSArray[0]));
            yr2_gbsData.setText(String.valueOf(gBSArray[1]));
            yr3_gbsData.setText(String.valueOf(gBSArray[2]));
            yr4_gbsData.setText(String.valueOf(gBSArray[3]));
            //Populate GTS Data
            TextView yr1_gtsData = (TextView) getView().findViewById(R.id.yr1_gts);
            TextView yr2_gtsData = (TextView) getView().findViewById(R.id.yr2_gts);
            TextView yr3_gtsData = (TextView) getView().findViewById(R.id.yr3_gts);
            TextView yr4_gtsData = (TextView) getView().findViewById(R.id.yr4_gts);
            yr1_gtsData.setText(String.valueOf(gTSArray[0]));
            yr2_gtsData.setText(String.valueOf(gTSArray[1]));
            yr3_gtsData.setText(String.valueOf(gTSArray[2]));
            yr4_gtsData.setText(String.valueOf(gTSArray[3]));
            //Populate IGF Data
            TextView yr1_igfData = (TextView) getView().findViewById(R.id.yr1_igf);
            TextView yr2_igfData = (TextView) getView().findViewById(R.id.yr2_igf);
            TextView yr3_igfData = (TextView) getView().findViewById(R.id.yr3_igf);
            TextView yr4_igfData = (TextView) getView().findViewById(R.id.yr4_igf);
            yr1_igfData.setText(String.valueOf(gIGFArray[0]));
            yr2_igfData.setText(String.valueOf(gIGFArray[1]));
            yr3_igfData.setText(String.valueOf(gIGFArray[2]));
            yr4_igfData.setText(String.valueOf(gIGFArray[3]));
            //Populate Systems Data
            TextView yr1_systemsData = (TextView) getView().findViewById(R.id.yr1_systems);
            TextView yr2_systemsData = (TextView) getView().findViewById(R.id.yr2_systems);
            TextView yr3_systemsData = (TextView) getView().findViewById(R.id.yr3_systems);
            TextView yr4_systemsData = (TextView) getView().findViewById(R.id.yr4_systems);
            yr1_systemsData.setText(String.valueOf(gSystemsArray[0]));
            yr2_systemsData.setText(String.valueOf(gSystemsArray[1]));
            yr3_systemsData.setText(String.valueOf(gSystemsArray[2]));
            yr4_systemsData.setText(String.valueOf(gSystemsArray[3]));
            //Populate CLOUD Data
            TextView yr1_cloudData = (TextView) getView().findViewById(R.id.yr1_cloud);
            TextView yr2_cloudData = (TextView) getView().findViewById(R.id.yr2_cloud);
            TextView yr3_cloudData = (TextView) getView().findViewById(R.id.yr3_cloud);
            TextView yr4_cloudData = (TextView) getView().findViewById(R.id.yr4_cloud);
            yr1_cloudData.setText(String.valueOf(gCloudArray[0]));
            yr2_cloudData.setText(String.valueOf(gCloudArray[1]));
            yr3_cloudData.setText(String.valueOf(gCloudArray[2]));
            yr4_cloudData.setText(String.valueOf(gCloudArray[3]));
            //Populate total Data
            TextView yr1_totalData = (TextView) getView().findViewById(R.id.yr1_total);
            TextView yr2_totalData = (TextView) getView().findViewById(R.id.yr2_total);
            TextView yr3_totalData = (TextView) getView().findViewById(R.id.yr3_total);
            TextView yr4_totalData = (TextView) getView().findViewById(R.id.yr4_total);
            yr1_totalData.setText(String.valueOf(gTotalArray[0]));
            yr2_totalData.setText(String.valueOf(gTotalArray[1]));
            yr3_totalData.setText(String.valueOf(gTotalArray[2]));
            yr4_totalData.setText(String.valueOf(gTotalArray[3]));
            //Populate Solutions Data
            TextView yr1_solutionsData = (TextView) getView().findViewById(R.id.yr1_solutions);
            TextView yr2_solutionsData = (TextView) getView().findViewById(R.id.yr2_solutions);
            TextView yr3_solutionsData = (TextView) getView().findViewById(R.id.yr3_solutions);
            TextView yr4_solutionsData = (TextView) getView().findViewById(R.id.yr4_solutions);
            yr1_solutionsData.setText(String.valueOf(gSolutionArray[0]));
            yr2_solutionsData.setText(String.valueOf(gSolutionArray[1]));
            yr3_solutionsData.setText(String.valueOf(gSolutionArray[2]));
            yr4_solutionsData.setText(String.valueOf(gSolutionArray[3]));
        }catch (Exception e){
            System.out.println("Exception message:"+e.getMessage());
        }



    }
    private void onClickGrayedStack() {
        gray_stack1 = (TextView) getView().findViewById(R.id.gray_stack1);
        gray_stack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gray_stack1.setVisibility(view.INVISIBLE);
                isStack1On=false;
            }
        });
        gray_stack2 = (TextView) getView().findViewById(R.id.gray_stack2);
        gray_stack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gray_stack2.setVisibility(view.INVISIBLE);
                isStack2On=false;
            }
        });
        gray_stack3 = (TextView) getView().findViewById(R.id.gray_stack3);
        gray_stack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gray_stack3.setVisibility(view.INVISIBLE);
                isStack3On=false;
            }
        });
        gray_stack4 = (TextView) getView().findViewById(R.id.gray_stack4);
        gray_stack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gray_stack4.setVisibility(view.INVISIBLE);
                isStack4On=false;
            }
        });
    }
    private void onClickLegendIcons() {
        gbsLegend = (Button) getView().findViewById(R.id.gbs_legend);
        gbsLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Put the logic to filter Bar chart and stack chart on click of Legend.
                drawChart(0);
                filterStack(0);

            }
        });
        gtsLegend = (Button) getView().findViewById(R.id.gts_legend);
        gtsLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Put the logic to filter Bar chart and stack chart on click of Legend.
                drawChart(1);
                filterStack(1);

            }
        });
        systemsLegend = (Button) getView().findViewById(R.id.systems_legend);
        systemsLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Put the logic to filter Bar chart and stack chart on click of Legend.
                drawChart(2);
                filterStack(2);

            }
        });
        cloudLegend = (Button) getView().findViewById(R.id.cloud_legend);
        cloudLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Put the logic to filter Bar chart and stack chart on click of Legend.
                drawChart(3);
                filterStack(3);

            }
        });
        solutionsLegend = (Button) getView().findViewById(R.id.solutions_legend);
        solutionsLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Put the logic to filter Bar chart and stack chart on click of Legend.
                drawChart(4);
                filterStack(4);

            }
        });
        igfLegend = (Button) getView().findViewById(R.id.igf_legend);
        igfLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Put the logic to filter Bar chart and stack chart on click of Legend.
                drawChart(5);
                filterStack(5);

            }
        });
        totalLegend = (Button) getView().findViewById(R.id.totals_legend);
        totalLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Put the logic to filter Bar chart and stack chart on click of Legend.
                drawChart(6);
                filterStack(6);

            }
        });
    }

    private void onClickShowBarChart() {
        stack1 = (LinearLayout) getView().findViewById(R.id.Main_stack1);
        stack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // year1_btn= (TextView) getView().findViewById(R.id.Main_stack1);
                stack1.setVisibility(view.INVISIBLE);
//                stack1.getVisibility();
            }
        });

        stack2 = (LinearLayout) getView().findViewById(R.id.Main_stack2);
        stack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             year2_btn = (TextView) view.findViewById(R.id.Main_stack2);
                stack2.setVisibility(view.INVISIBLE);
            }
        });
        stack3 = (LinearLayout) getView().findViewById(R.id.Main_stack3);
        stack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             year2_btn = (TextView) view.findViewById(R.id.Main_stack2);
                stack3.setVisibility(view.INVISIBLE);
            }
        });
        stack4 = (LinearLayout) getView().findViewById(R.id.Main_stack4);
        stack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             year2_btn = (TextView) view.findViewById(R.id.Main_stack2);
                stack4.setVisibility(view.INVISIBLE);
            }
        });
    }
    private boolean isAllStackActive(){
        boolean status=false;
        if(isStack1On==false && isStack2On==false && isStack3On==false && isStack4On==false)
            status=false; else status=true;
        return status;
    }
    private void onClickShowStackChart() {


        year1_btn = (TextView) getView().findViewById(R.id.year1_stack);
        year1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllStackGray==false && isAllStackActive()==false)
                {
                    gray_stack2 = (TextView) getView().findViewById(R.id.gray_stack2);
                    gray_stack3 = (TextView) getView().findViewById(R.id.gray_stack3);
                    gray_stack4 = (TextView) getView().findViewById(R.id.gray_stack4);

                    gray_stack2.setVisibility(View.VISIBLE);
                    gray_stack3.setVisibility(View.VISIBLE);
                    gray_stack4.setVisibility(View.VISIBLE);
                    isAllStackGray=true;
                    isStack2On=true;
                    isStack3On=true;
                    isStack4On=true;
                }else {
                    stack1 = (LinearLayout) getView().findViewById(R.id.Main_stack1);
                    stack1.setVisibility(View.VISIBLE);
                    isAllStackGray=false;
                }

            }
        });
        year2_btn = (TextView) getView().findViewById(R.id.year2_stack);
        year2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllStackGray==false && isAllStackActive()==false)
                {
                    gray_stack1 = (TextView) getView().findViewById(R.id.gray_stack1);
                    gray_stack3 = (TextView) getView().findViewById(R.id.gray_stack3);
                    gray_stack4 = (TextView) getView().findViewById(R.id.gray_stack4);

                    gray_stack1.setVisibility(View.VISIBLE);
                    gray_stack3.setVisibility(View.VISIBLE);
                    gray_stack4.setVisibility(View.VISIBLE);
                    isAllStackGray=true;
                    isStack1On=true;
                    isStack3On=true;
                    isStack4On=true;
                }else {
                    stack2 = (LinearLayout) getView().findViewById(R.id.Main_stack2);
                    stack2.setVisibility(View.VISIBLE);
                    isAllStackGray=false;
                }

            }
        });
        year3_btn = (TextView) getView().findViewById(R.id.year3_stack);
        year3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllStackGray==false && isAllStackActive()==false)
                {
                    gray_stack2 = (TextView) getView().findViewById(R.id.gray_stack2);
                    gray_stack1 = (TextView) getView().findViewById(R.id.gray_stack1);
                    gray_stack4 = (TextView) getView().findViewById(R.id.gray_stack4);

                    gray_stack2.setVisibility(View.VISIBLE);
                    gray_stack1.setVisibility(View.VISIBLE);
                    gray_stack4.setVisibility(View.VISIBLE);
                    isAllStackGray=true;
                    isStack2On=true;
                    isStack1On=true;
                    isStack4On=true;
                }else {
                    stack3 = (LinearLayout) getView().findViewById(R.id.Main_stack3);
                    stack3.setVisibility(View.VISIBLE);
                    isAllStackGray=false;
                }

            }
        });
        year4_btn = (TextView) getView().findViewById(R.id.year4_stack);
        year4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllStackGray==false && isAllStackActive()==false)
                {
                    gray_stack2 = (TextView) getView().findViewById(R.id.gray_stack2);
                    gray_stack3 = (TextView) getView().findViewById(R.id.gray_stack3);
                    gray_stack1 = (TextView) getView().findViewById(R.id.gray_stack1);

                    gray_stack2.setVisibility(View.VISIBLE);
                    gray_stack3.setVisibility(View.VISIBLE);
                    gray_stack1.setVisibility(View.VISIBLE);
                    isAllStackGray=true;
                    isStack2On=true;
                    isStack3On=true;
                    isStack1On=true;
                }else {
                    stack4 = (LinearLayout) getView().findViewById(R.id.Main_stack4);
                    stack4.setVisibility(View.VISIBLE);
                    isAllStackGray=false;
                }

            }
        });



    }


    public void formChartObjects(String[][] dataArray){



        String[] yearsArray = dataArray[0];
        firstYearString = yearsArray[0];
        double[] gBSArray = parseData(dataArray[1]);//GBS
        double[] gTSArray = parseData(dataArray[2]);//GTS
        double[] gIGFArray = parseData(dataArray[3]);//System
        double[] gCloudArray = parseData(dataArray[4]);//Cloud
        double[] gSystemsArray = parseData(dataArray[5]);//Solutions
        double[] gSWGArray = parseData(dataArray[6]); //IGF
        double[] gTotalArray = parseData(dataArray[7]); //Total

        totalArrayList = gTotalArray;


        responseArray = new ArrayList();
        responseArray.add(yearsArray);
        responseArray.add(gBSArray);
        responseArray.add(gTSArray);
        responseArray.add(gIGFArray);
        responseArray.add(gCloudArray);
        responseArray.add(gSystemsArray);
        responseArray.add(gSWGArray);
        responseArray.add(gTotalArray);


        buttonSelectDictionary = new HashMap<>();
        buttonSelectDictionary.put(0,false);
        buttonSelectDictionary.put(1,false);
        buttonSelectDictionary.put(2,false);
        buttonSelectDictionary.put(3,false);
        buttonSelectDictionary.put(4,false);
        buttonSelectDictionary.put(5,false);
        buttonSelectDictionary.put(6,false);

    }

    private double[] parseData(String[] strings) {


        double[] nums = new double[strings.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Double.parseDouble(strings[i]);
        }
        return nums;
    }



    public ArrayList<BarEntry>  formBarGroup (double[] chartDataArr){

        ArrayList<BarEntry> bargroup = new ArrayList<>();
        if(chartDataArr.length >= 1){
            for(int i=0; i<chartDataArr.length; i++) {
                bargroup.add(new BarEntry((float) chartDataArr[i], i));
            }

        }

        return bargroup;
    }

    public void drawChartAfterFilterOption(){



        barChart.setDrawGridBackground(false);
        barChart.setDescription("");
        barChart.setDrawBarShadow(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();

        leftAxis.setEnabled(true);
        leftAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);

        // rightAxis.setDrawZeroLine(true);
        //leftAxis.setDrawZeroLine(true);
        leftAxis.setStartAtZero(true);

        //YAxis yAxis = barChart.getYA
        // create BarEntry for Bar Group 1
        ArrayList<ArrayList<BarEntry>> barGrpList = new ArrayList<ArrayList<BarEntry>>();

        try{

            for(int i=1;i<=7;i++){

//            System.out.println("The array is" + responseArray.get(i).toString());
                //double[] chartDataArr = (double[]) responseArray.get(i);

                barGrpList.add(formBarGroup((double[]) responseArray.get(i)));

            }


            // creating dataset for Bar Group1
            barDataSet1 = new BarDataSet(barGrpList.get(0), "GBS");

            // barDataSet1 = new BarDataSet(bargroup1, "GBS");
            barDataSet1.setColors(new int[] {Color.parseColor("#FF8ACBF9")});
            //barDataSet1.setColors(new int[] {Color.parseColor("#FF8ACBF9")});
            barDataSet1.setDrawValues(false);
            // barDataSet1.setBarSpacePercent(50.0f);
            // barDataSet1.setValueTextSize(15.0f);
            // barDataSet1.setStackLabels(new String[]{});
            // barDataSet1.
            // barDataSet1.
            //barDataSet1.set
            // barDataSet1.setLabel("");
            // barDataSet1.
            // creating dataset for Bar Group 2
            barDataSet2 = new BarDataSet(barGrpList.get(1), "GTS");
            // BarDataSet barDataSet2 = new BarDataSet(bargroup2, "GTS");
            //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet2.setColors(new int[] {Color.parseColor("#FF489EE1")});
            barDataSet2.setDrawValues(false);

            barDataSet3 = new BarDataSet(barGrpList.get(2), "Systems");
            //BarDataSet barDataSet3 = new BarDataSet(bargroup3, "Systems");
            // barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet3.setColors(new int[] {Color.parseColor("#f69531")});
            barDataSet3.setDrawValues(false);


            barDataSet4 = new BarDataSet(barGrpList.get(3), "Cloud");
            // BarDataSet barDataSet4 = new BarDataSet(bargroup4, "Cloud");
            // barDataSet4.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet4.setColors(new int[] {Color.parseColor("#FFFFE07C")});
            barDataSet4.setDrawValues(false);

            barDataSet5 = new BarDataSet(barGrpList.get(4), "Solutions");
            // BarDataSet barDataSet5 = new BarDataSet(bargroup5, "Solutions");
            //barDataSet5.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet5.setColors(new int[] {Color.parseColor("#FFB5C0C0")});
            barDataSet5.setDrawValues(false);


            barDataSet6 = new BarDataSet(barGrpList.get(5), "IGF");
            //BarDataSet barDataSet6 = new BarDataSet(bargroup6, "IGF");
            // barDataSet6.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet6.setColors(new int[] {Color.parseColor("#FFEA6231")});
            barDataSet6.setDrawValues(false);


            barDataSet7 = new BarDataSet(barGrpList.get(6), "Total");
            //BarDataSet barDataSet7 = new BarDataSet(bargroup7, "Total");
            //barDataSet7.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet7.setColors(new int[] {Color.parseColor("#74868e")});
            barDataSet7.setDrawValues(false);
        }catch (Exception e){

            System.out.println("Exception Message:"+e.getMessage());
        }



    }
    @SuppressLint("WrongConstant")
    private void filterStack(int tagValue) {
//        System.out.println("cliecked Legend with value :"+tagValue);
        String viewID="gbs";
        TextView year1_stack = null;
        TextView year2_stack= null;
        TextView year3_stack= null;
        TextView year4_stack= null;
        gbsLegend = (Button) getView().findViewById(R.id.gbs_legend);
        gtsLegend = (Button) getView().findViewById(R.id.gts_legend);
        systemsLegend = (Button) getView().findViewById(R.id.systems_legend);
        cloudLegend = (Button) getView().findViewById(R.id.cloud_legend);
        solutionsLegend = (Button) getView().findViewById(R.id.solutions_legend);
        igfLegend = (Button) getView().findViewById(R.id.igf_legend);
        totalLegend = (Button) getView().findViewById(R.id.totals_legend);
        if(tagValue==0)
        {
            year1_stack= (TextView)getView().findViewById(R.id.yr1_gbs);
            year2_stack= (TextView)getView(). findViewById(R.id.yr2_gbs);
            year3_stack= (TextView)getView(). findViewById(R.id.yr3_gbs);
            year4_stack= (TextView)getView(). findViewById(R.id.yr4_gbs);

            if(year1_stack.getVisibility()==0){
                gbsLegend.getBackground().setAlpha(50);
            }
            else {
                gbsLegend.getBackground().setAlpha(255);
            }
            //gbsLegend.getBackground().setAlpha(50);

        }
        else if(tagValue==1)
        {
            year1_stack= (TextView)getView(). findViewById(R.id.yr1_gts);
            year2_stack= (TextView)getView(). findViewById(R.id.yr2_gts);
            year3_stack= (TextView)getView(). findViewById(R.id.yr3_gts);
            year4_stack= (TextView)getView(). findViewById(R.id.yr4_gts);

            if(year2_stack.getVisibility()==0) {
                gtsLegend.getBackground().setAlpha(50);
            }else {
                gtsLegend.getBackground().setAlpha(255);
            }
        }
        else if(tagValue==2)
        {
            year1_stack= (TextView)getView(). findViewById(R.id.yr1_systems);
            year2_stack= (TextView)getView(). findViewById(R.id.yr2_systems);
            year3_stack= (TextView)getView(). findViewById(R.id.yr3_systems);
            year4_stack= (TextView)getView(). findViewById(R.id.yr4_systems);
            if(year3_stack.getVisibility()==0) {
                systemsLegend.getBackground().setAlpha(50);
            }else {
                systemsLegend.getBackground().setAlpha(255);
            }
        }
        else if(tagValue==3)
        {
            year1_stack= (TextView)getView(). findViewById(R.id.yr1_cloud);
            year2_stack= (TextView)getView(). findViewById(R.id.yr2_cloud);
            year3_stack= (TextView)getView(). findViewById(R.id.yr3_cloud);
            year4_stack= (TextView)getView(). findViewById(R.id.yr4_cloud);

            if(year4_stack.getVisibility()==0) {
                cloudLegend.getBackground().setAlpha(50);
            }else {
                cloudLegend.getBackground().setAlpha(255);
            }
        }
        else if(tagValue==4)
        {
            year1_stack= (TextView)getView(). findViewById(R.id.yr1_solutions);
            year2_stack= (TextView)getView(). findViewById(R.id.yr2_solutions);
            year3_stack= (TextView)getView(). findViewById(R.id.yr3_solutions);
            year4_stack= (TextView)getView(). findViewById(R.id.yr4_solutions);

            if(year4_stack.getVisibility()==0) {
                solutionsLegend.getBackground().setAlpha(50);
            }else {
                solutionsLegend.getBackground().setAlpha(255);
            }
        }
        else if(tagValue==5)
        {
            year1_stack= (TextView)getView(). findViewById(R.id.yr1_igf);
            year2_stack= (TextView)getView(). findViewById(R.id.yr2_igf);
            year3_stack= (TextView)getView(). findViewById(R.id.yr3_igf);
            year4_stack= (TextView)getView(). findViewById(R.id.yr4_igf);

            if(year4_stack.getVisibility()==0) {
                igfLegend.getBackground().setAlpha(50);
            }else {
                igfLegend.getBackground().setAlpha(255);
            }
        }
        else if(tagValue==6)
        {
            year1_stack= (TextView)getView(). findViewById(R.id.yr1_total);
            year2_stack= (TextView)getView(). findViewById(R.id.yr2_total);
            year3_stack= (TextView)getView(). findViewById(R.id.yr3_total);
            year4_stack= (TextView)getView(). findViewById(R.id.yr4_total);

            if(year4_stack.getVisibility()==0) {
                totalLegend.getBackground().setAlpha(50);
            }else {
                totalLegend.getBackground().setAlpha(255);
            }
        }

        if(year1_stack.getVisibility()==0)
        {
            year1_stack.setVisibility(View.GONE);
            year2_stack.setVisibility(View.GONE);
            year3_stack.setVisibility(View.GONE);
            year4_stack.setVisibility(View.GONE);


        }

        else{
            year1_stack.setVisibility(View.VISIBLE);
            year2_stack.setVisibility(View.VISIBLE);
            year3_stack.setVisibility(View.VISIBLE);
            year4_stack.setVisibility(View.VISIBLE);



        }

    }
    public void drawChart (int tagValue) {



        if (tagValue == 8) {

//            System.out.println("Tag value is"+tagValue);
        }else {
            if (!buttonSelectDictionary.get(tagValue)) {

                if(tagValue == 0){
                    barDataSet1.setColors(new int[] {Color.TRANSPARENT});
                } else if(tagValue == 1) {
                    barDataSet2.setColors(new int[] {Color.TRANSPARENT});
                }else if(tagValue == 2){
                    barDataSet3.setColors(new int[] {Color.TRANSPARENT});
                }else if(tagValue == 3){
                    barDataSet4.setColors(new int[] {Color.TRANSPARENT});
                }else if(tagValue == 4){
                    barDataSet5.setColors(new int[] {Color.TRANSPARENT});
                }else if(tagValue == 5) {
                    barDataSet6.setColors(new int[] {Color.TRANSPARENT});
                }else if(tagValue == 6) {
                    barDataSet7.setColors(new int[] {Color.TRANSPARENT});
                }else {
                    System.out.println("Normal bar graph"+tagValue);
                }


                buttonSelectDictionary.put(tagValue,true);

            }else {

                if(tagValue == 0){
                    barDataSet1.setColors(new int[] {Color.parseColor("#FF8ACBF9")});
                } else if(tagValue == 1) {
                    barDataSet2.setColors(new int[] {Color.parseColor("#FF489EE1")});
                }else if(tagValue == 2){
                    barDataSet3.setColors(new int[] {Color.parseColor("#f69531")});
                }else if(tagValue == 3){
                    barDataSet4.setColors(new int[] {Color.parseColor("#FFFFE07C")});
                }else if(tagValue == 4){
                    barDataSet5.setColors(new int[] {Color.parseColor("#FFB5C0C0")});
                }else if(tagValue == 5) {
                    barDataSet6.setColors(new int[] {Color.parseColor("#FFEA6231")});
                }else if(tagValue == 6) {
                    barDataSet7.setColors(new int[] {Color.parseColor("#74868e")});
                }else {
//                    System.out.println("Normal bar graph"+tagValue);
                }
                buttonSelectDictionary.put(tagValue,false);
            }

        }





        ArrayList<String> labels = new ArrayList<String>();
        /*labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");*/

        labels.add(chartDataArrayList[0][0]);
        labels.add(chartDataArrayList[0][1]);
        labels.add(chartDataArrayList[0][2]);
        labels.add(chartDataArrayList[0][3]);
        // labels.add("2012");
        //labels.add("2011");

        //  barDataSet1.
        ArrayList dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        dataSets.add(barDataSet4);
        dataSets.add(barDataSet5);
        dataSets.add(barDataSet6);
        dataSets.add(barDataSet7);
        //dataSets.add(barDataSet8);

        YAxis yaxis = barChart.getAxisLeft();
        double yMaxValue = 0.0;
        double yAxisMaxValueInChart;

        if(totalArrayList.length >= 1){
            for(int i=0;i<totalArrayList.length;i++){
                if(yMaxValue < totalArrayList[i]){
                    yMaxValue = totalArrayList[i];
                }
            }
            if(yMaxValue>5000.0){//any val above 5000 set 10000
                yAxisMaxValueInChart = 10000.0;
            }else if(yMaxValue>1000.0 && yMaxValue<=5000.0){// between 1000 to 5000 set 5000
                yAxisMaxValueInChart = 5000.0;
            }else if(yMaxValue>500.0 && yMaxValue<=1000.0){ // between 500 to 1000 set 1000
                yAxisMaxValueInChart = 1000.0;
            }else if(yMaxValue>100.0 && yMaxValue<=500.0) { // between 100 to 500 set 500
                yAxisMaxValueInChart = 500.0;
            }else if(yMaxValue>50.0 && yMaxValue<=100.0) { // between 50 to 100set 100
                yAxisMaxValueInChart = 100.0;
            }else if(yMaxValue>10.0 && yMaxValue<=50.0) { // between 10 to 50 set 50
                yAxisMaxValueInChart = 50.0;
            }else if(yMaxValue>5.0 && yMaxValue<=10.0) { // between 5 to 10 set 10
                yAxisMaxValueInChart = 10.0;
            }else{ //min set 5
                yAxisMaxValueInChart = 5.0;

            }

            yaxis.setAxisMaxValue((float)yAxisMaxValueInChart);
            yaxis.setGranularity((float)yAxisMaxValueInChart/5);

// initialize the Bardata with argument labels and dataSet
            BarData data = new BarData(labels, dataSets);
            barChart.setHighlightPerTapEnabled(false);
            barChart.setDoubleTapToZoomEnabled(false);
            //barChart.setNoDataText("Total");
            // barChart.setDrawValueAboveBar(false);
            //barChart.clearValues();
            // barChart.setExtraBottomOffset(20f);
            // barChart.setDrawBarShadow(false);
            //barChart.se
            Legend l = barChart.getLegend();
            // l.setFormSize(15f);
            l.setEnabled(false);

            barChart.notifyDataSetChanged();
            barChart.invalidate();
            barChart.setData(data);


        }





    }


    public void displayGrid(BUSINESS_UNIT[] business_unit){


        TableRow tableRow = new TableRow(getActivity());
        TextView tv0 = new TextView(getContext());
        tv0.setText("Business Unit");
        tv0.setTextColor(Color.GRAY);
        tv0.setLayoutParams(new TableRow.LayoutParams(1));
        tv0.setPadding(8,8,8,8);
        tv0.setTextSize(16);
        tv0.setTypeface(null, Typeface.BOLD);
        tableRow.addView(tv0);

        TextView tv1 = new TextView(getContext());
        tv1.setText(firstYearString+" "+"PY");
        tv1.setTextColor(Color.GRAY);
        tv1.setLayoutParams(new TableRow.LayoutParams(2));
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setTextSize(16);
        tv1.setGravity(Gravity.LEFT);
        tableRow.addView(tv1);

        TextView tv2 = new TextView(getContext());
        tv2.setText("CAGR");
        tv2.setTextColor(Color.GRAY);
        tv2.setLayoutParams(new TableRow.LayoutParams(3));
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setTextSize(16);
        tv2.setGravity(Gravity.RIGHT);
        tableRow.addView(tv2);


        tableLayout.addView(tableRow);
        tableLayout.addView(createSeparatorViewForTableLayout());

        try{
            for(int i =0;i<business_unit.length;i++){
                TableRow tbRow = new TableRow(getContext());
                TextView t1v = new TextView(getContext());
                t1v.setText(business_unit[i].getDO_TITLE());
                t1v.setLayoutParams(new TableRow.LayoutParams(1));
                t1v.setPadding(8,16,8,16);
                t1v.setTextColor(Color.GRAY);
                t1v.setTextSize(15);
                t1v.setGravity(Gravity.LEFT);
                tbRow.addView(t1v);

                TextView t2v = new TextView(getContext());
                t2v.setText(business_unit[i].getDO_TOTAL());
                t2v.setLayoutParams(new TableRow.LayoutParams(2));
                t2v.setTextColor(Color.GRAY);
                t2v.setTextSize(15);
                t2v.setGravity(Gravity.LEFT);
                tbRow.addView(t2v);

                TextView t3v = new TextView(getContext());
                t3v.setText(business_unit[i].getDO_YTY());
                if(String.valueOf(business_unit[i].getDO_YTY().charAt(0)).equalsIgnoreCase("-")){
                    t3v.setTextColor(Color.RED);
                }else{
                    t3v.setTextColor(Color.parseColor("#FF5AA700"));
                }
                t3v.setLayoutParams(new TableRow.LayoutParams(3));
                t3v.setTextSize(15);
                t3v.setGravity(Gravity.RIGHT);
                tbRow.addView(t3v);

                tableLayout.addView(tbRow);
                tableLayout.addView(createSeparatorViewForTableLayout());

            }
        }catch (Exception e){

        }




    }

    public View createSeparatorViewForTableLayout(){

        View lineView = new View(getContext());
        lineView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,1));
        lineView.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        lineView.getLayoutParams().height = 2;

        return lineView;


    }





}
