package com.ibm.cio.gss.isap_lite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.relationshipModel.IDENTIFY_INITIATIVE;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.List;

/**
 * Created by Kabuli on 7/25/2018.
 */

public class SelectedRelationshipIdentityAdapter extends RecyclerView.Adapter<SelectedRelationshipIdentityAdapter.MyViewHolder> {

    private List<IDENTIFY_INITIATIVE> selectedIdentifyList;

    private IDENTIFY_INITIATIVE selectedIdentifyDetailsList;
    public Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initiativeNameLabel;
        public View deleteButtonView;
        public MyViewHolder(View view) {
            super(view);
            initiativeNameLabel = (TextView) view.findViewById(R.id.selectedInitiativeName);
            deleteButtonView = (View) view.findViewById(R.id.deleteInitiativeButton);
            // addGoalCheckBox = (CheckBox) view.findViewById(R.id.addGoalcheckBox);
        }
    }

    public SelectedRelationshipIdentityAdapter(List<IDENTIFY_INITIATIVE> initiativesArrayList) {
        this.selectedIdentifyList = initiativesArrayList;
    }

    @Override
    public SelectedRelationshipIdentityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addgoal_initiative, parent, false);
        ctx=parent.getContext();
        return new SelectedRelationshipIdentityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectedRelationshipIdentityAdapter.MyViewHolder holder, final int position) {
      try{
        holder.setIsRecyclable(false);
       // holder.deleteButtonView.setVisibility(View.GONE);
        selectedIdentifyDetailsList = selectedIdentifyList.get(position);
        holder.initiativeNameLabel.setText(selectedIdentifyDetailsList.getNAME());

        holder.deleteButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("RelationshipDataRefresh");
                intent.putExtra("position",Integer.toString(holder.getAdapterPosition()));
                intent.putExtra("type","initiative");
                LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);

            }
        });
      }catch (Exception e){
//          Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
      }
    }
    @Override
    public int getItemCount() {
        if(selectedIdentifyList!=null)
        return selectedIdentifyList.size();
        else return 0;
    }
}
