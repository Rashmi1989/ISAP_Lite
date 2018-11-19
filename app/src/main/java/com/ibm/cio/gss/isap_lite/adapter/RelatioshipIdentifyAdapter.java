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
import com.ibm.cio.gss.isap_lite.model.relationshipModel.IDENTIFY_INITIATIVE;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.List;
import java.util.Map;

/**
 * Created by Kabuli on 7/25/2018.
 */

public class RelatioshipIdentifyAdapter extends RecyclerView.Adapter<RelatioshipIdentifyAdapter.MyViewHolder> {

    private List<IDENTIFY_INITIATIVE> identifyList;
    private Map<Integer,Boolean> choiceMap;
    private IDENTIFY_INITIATIVE identifyDetailsList;
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
    public RelatioshipIdentifyAdapter(List<IDENTIFY_INITIATIVE> initiativesArrayList, Map<Integer,Boolean> choiceMap) {
        this.identifyList = initiativesArrayList;
        this.choiceMap = choiceMap;
    }
    @Override
    public RelatioshipIdentifyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_goal_intiative_list, parent, false);
        ctx=parent.getContext();
        return new RelatioshipIdentifyAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final RelatioshipIdentifyAdapter.MyViewHolder holder,int position) {
      try{
        holder.setIsRecyclable(false);
        identifyDetailsList = identifyList.get(holder.getAdapterPosition());
        holder.initiativeNameLabel.setText(identifyDetailsList.getNAME());

        if(identifyDetailsList.getisSelected()){
            holder.addGoalCheckBox.setChecked(true);
        }
        holder.addGoalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.addGoalCheckBox.isChecked()){

                    System.out.println("Checkbox is checked");
                    choiceMap.put(holder.getAdapterPosition(),true);

                }else{
                    choiceMap.put(holder.getAdapterPosition(),false);
                    System.out.println("Checkbox is unchecked");
                }
            }
        });
      }catch (Exception e){
//          Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
      }
    }
    @Override
    public int getItemCount() {
        if(identifyList!=null)
        return identifyList.size();
        else return 0;
    }
}
