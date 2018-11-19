package com.ibm.cio.gss.isap_lite.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.OPPTS;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rashmi on 5/18/18.
 */

public class LinkedOpportunityAdapter extends RecyclerView.Adapter<LinkedOpportunityAdapter.MyViewHolder> {
    private List<OPPTS> opptsList;
    private  AlertDialog.Builder alertDialogBuilder;
    private   AlertDialog alertDialog;
    private OPPTS oppsDetailsList;
    public Context ctx;
    public View customView;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView oppsIdLabel,oppsNameLabel,oppsTCVLabel;
        public MyViewHolder(View view) {
            super(view);
            oppsIdLabel = (TextView) view.findViewById(R.id.oppsId);
            oppsNameLabel = (TextView) view.findViewById(R.id.opportunityName);
            oppsTCVLabel = (TextView) view.findViewById(R.id.oppsTCV);
        }
    }
    public LinkedOpportunityAdapter(List<OPPTS> moviesList) {
        this.opptsList = moviesList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.link_opportunities_list_layout, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try{
        holder.setIsRecyclable(false);
        oppsDetailsList = opptsList.get(holder.getAdapterPosition());
        holder.oppsIdLabel.setText(oppsDetailsList.getOPPORTUNITY_ID());
        holder.oppsNameLabel.setText(oppsDetailsList.getOPPORTUNITY_NM());
        holder.oppsTCVLabel.setText(oppsDetailsList.getOPPTY_VALUE());
        }catch (Exception e){
            //Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if(opptsList!=null)
        return opptsList.size();
        else return 0;
    }
}
