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
import com.ibm.cio.gss.isap_lite.model.GEO;
import com.ibm.cio.gss.isap_lite.model.MARKETS;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.List;
import java.util.Map;

/**
 * Created by Kabuli on 7/4/2018.
 */

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MyViewHolder> {

    private List<MARKETS> marketList;
    private Map<Integer,Boolean> marketMap;
    private MARKETS marketData;
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
    public MarketAdapter(List<MARKETS> strategicimperativesList, Map<Integer,Boolean> choiceMap) {
        this.marketList = strategicimperativesList;
        this.marketMap = choiceMap;
    }
    @Override
    public MarketAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_goal_intiative_list, parent, false);
        ctx=parent.getContext();
        return new MarketAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MarketAdapter.MyViewHolder holder, final int position) {
        try{
        holder.setIsRecyclable(false);
        marketData = marketList.get(position);
        holder.initiativeNameLabel.setText(marketData.getNAME());
//        if(geoList.getisSelected()){
//            holder.addGoalCheckBox.setChecked(true);
//        }
        holder.addGoalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.addGoalCheckBox.isChecked()){
                    marketMap.put(holder.getAdapterPosition(),true);
                }else{
                    marketMap.put(holder.getAdapterPosition(),false);
                }
            }
        });
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(marketList!=null)
        return marketList.size();
        else return 0;
    }
}
