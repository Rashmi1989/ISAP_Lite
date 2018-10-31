package com.ibm.cio.gss.isap_lite.adapter;
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
import com.ibm.cio.gss.isap_lite.model.LINKEDOPPT;
import com.ibm.cio.gss.isap_lite.model.OPPTS;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Rashmi on 5/18/18.
 */
public class SelectedLinkedOpptsAdapter extends RecyclerView.Adapter<SelectedLinkedOpptsAdapter.MyViewHolder> {
    private List<LINKEDOPPT> linkedopptList;
    private LINKEDOPPT linkedOpptsDetailsList;
    public Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView oppsIdLabel,oppsNameLabel,oppsTCVLabel;
        public MyViewHolder(View view) {
            super(view);
            oppsIdLabel = (TextView) view.findViewById(R.id.oppsId);
            oppsNameLabel = (TextView) view.findViewById(R.id.opportunityName);
            oppsTCVLabel = (TextView) view.findViewById(R.id.oppsTCV);
        }
    }
    public SelectedLinkedOpptsAdapter(List<LINKEDOPPT> linkedopptList) {
        this.linkedopptList = linkedopptList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.link_opportunities_list_layout, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try{
        holder.setIsRecyclable(false);
        linkedOpptsDetailsList = linkedopptList.get(position);
        holder.oppsIdLabel.setText(linkedOpptsDetailsList.getOPPORTUNITY_ID());
        holder.oppsNameLabel.setText(linkedOpptsDetailsList.getOPPORTUNITY_NM());
        holder.oppsTCVLabel.setText(linkedOpptsDetailsList.getTCV_OPPTY_VAL());
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(linkedopptList!=null)
        return linkedopptList.size();
        else return 0;
    }
}