package com.ibm.cio.gss.isap_lite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.COUNTRY;
import com.ibm.cio.gss.isap_lite.model.MARKETS;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.List;
import java.util.Map;

/**
 * Created by Kabuli on 7/4/2018.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {

    private List<COUNTRY> countryList;
    private Map<Integer,Boolean> countryMap;
    private COUNTRY countryData;
    public Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initiativeNameLabel;
        public CheckBox addGoalCheckBox;
        public MyViewHolder(View view) {
            super(view);
            initiativeNameLabel = (TextView) view.findViewById(R.id.addGoalInitiativeName);
            addGoalCheckBox = (CheckBox) view.findViewById(R.id.addGoalcheckBox);
        }
    }
    public CountryAdapter(List<COUNTRY> strategicimperativesList, Map<Integer,Boolean> choiceMap) {
        this.countryList = strategicimperativesList;
        this.countryMap = choiceMap;
    }
    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_goal_intiative_list, parent, false);
        ctx=parent.getContext();
        return new CountryAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final CountryAdapter.MyViewHolder holder, final int position) {
        try{
        holder.setIsRecyclable(false);
        countryData = countryList.get(position);
        holder.initiativeNameLabel.setText(countryData.getNAME());
        holder.addGoalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.addGoalCheckBox.isChecked()){
                    countryMap.put(holder.getAdapterPosition(),true);
                }else{
                    countryMap.put(holder.getAdapterPosition(),false);
                    System.out.println("Checkbox is unchecked");
                }
            }
        });
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(countryList!=null)
        return countryList.size();
        else return 0;
    }
}
