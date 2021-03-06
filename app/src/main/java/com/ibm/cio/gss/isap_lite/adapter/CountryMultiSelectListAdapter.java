
package com.ibm.cio.gss.isap_lite.adapter;

/**
 * Created by Rashmi on 4/25/18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.activity.NewInitiativeActivity;
import com.ibm.cio.gss.isap_lite.model.CITY_GROUP;
import com.ibm.cio.gss.isap_lite.model.COUNTRY;
import com.ibm.cio.gss.isap_lite.model.GEO;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CountryMultiSelectListAdapter extends RecyclerView.Adapter<CountryMultiSelectListAdapter.MyViewHolder> {

    private List<COUNTRY> countryList;
    private Map<Integer,Boolean> choiceMap;
    private COUNTRY countryDetailList;
    public  Context ctx;
    private boolean[] listCheckbox;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initiativeNameLabel;
        public CheckBox addGoalCheckBox;
        public MyViewHolder(View view) {
            super(view);
            initiativeNameLabel = (TextView) view.findViewById(R.id.addGoalInitiativeName);
            addGoalCheckBox = (CheckBox) view.findViewById(R.id.addGoalcheckBox);
        }
    }
    public CountryMultiSelectListAdapter(List<COUNTRY> countryList, Map<Integer,Boolean> choiceMap) {
        this.countryList = countryList;
        this.choiceMap = choiceMap;
        listCheckbox = new boolean[countryList.size()];
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_goal_intiative_list, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,  int position) {
        holder.setIsRecyclable(false);
        countryDetailList = countryList.get(position);
        holder.initiativeNameLabel.setText(countryDetailList.getNAME());
        if(countryDetailList.getisSelected()){
            holder.addGoalCheckBox.setChecked(true);
        }
       /* holder.addGoalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.addGoalCheckBox.isChecked()){
                    choiceMap.put(holder.getAdapterPosition(),true);
                }else{
                    choiceMap.put(holder.getAdapterPosition(),false);
                }
            }
        });*/

        boolean isFirst=getStatusOfBooleanArray();
        if(isFirst){
            boolean isChecked=listCheckbox[position];
            if(isChecked){
                holder.addGoalCheckBox.setChecked(true);
            }else {
                holder.addGoalCheckBox.setChecked(false);
            }

        }
        holder.addGoalCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int y = holder.getAdapterPosition();
                if (isChecked) {
                    if (y == 0) {
                        for (int i = 0; i < listCheckbox.length; i++) {
                            if (i == 0) {
                                listCheckbox[0] = true;
                                choiceMap.put(0, true);
                            } else {
                                listCheckbox[i] = false;
                                choiceMap.put(i, false);
                            }
                        }
                    } else {
                        for (int i = 0; i < listCheckbox.length; i++) {
                            if (i == 0) {
                                listCheckbox[0] = false;
                                choiceMap.put(0, false);
                            } else {
                                listCheckbox[y] = true;
                                choiceMap.put(y, true);
                            }
                        }
                    }
                    notifyDataSetChanged();
                }else {
                    choiceMap.put(holder.getAdapterPosition(), false);
                }

            }
        });

    }
    @Override
    public int getItemCount() {
        if(countryList!=null)
        return countryList.size();
        else return 0;
    }
    private boolean getStatusOfBooleanArray() {
        boolean isStatus = false;
        if (listCheckbox != null && listCheckbox.length > 0) {
            for (int i = 0; i < listCheckbox.length; i++) {
                if (listCheckbox[i] == true) {
                    isStatus = true;
                    break;
                } else {
                    isStatus = false;
                }
            }
        }
        return isStatus;
    }
}
