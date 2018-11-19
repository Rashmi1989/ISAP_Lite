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
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.List;
import java.util.Map;

/**
 * Created by Kabuli on 7/4/2018.
 */

public class GeoAdapter extends RecyclerView.Adapter<GeoAdapter.MyViewHolder> {

    private List<GEO> geoList;
    private Map<Integer,Boolean> geoMap;
    private GEO geoData;
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
    public GeoAdapter(List<GEO> strategicimperativesList, Map<Integer,Boolean> choiceMap) {
        this.geoList = strategicimperativesList;
        this.geoMap = choiceMap;
    }
    @Override
    public GeoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_goal_intiative_list, parent, false);
        ctx=parent.getContext();
        return new GeoAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final GeoAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        try{
        geoData = geoList.get(position);
        holder.initiativeNameLabel.setText(geoData.getNAME());
        holder.addGoalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.addGoalCheckBox.isChecked()){
                    geoMap.put(holder.getAdapterPosition(),true);
                }else{
                    geoMap.put(holder.getAdapterPosition(),false);
                }
            }
        });
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(geoList!=null)
        return geoList.size();
        else return 0;
    }
}
