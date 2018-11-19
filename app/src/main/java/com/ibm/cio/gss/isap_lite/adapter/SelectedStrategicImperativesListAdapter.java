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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.model.CITY_GROUP;
import com.ibm.cio.gss.isap_lite.model.GOALS;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.model.STRATEGICIMPERATIVES;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
public class SelectedStrategicImperativesListAdapter extends RecyclerView.Adapter<SelectedStrategicImperativesListAdapter.MyViewHolder> {
    private List<STRATEGICIMPERATIVES> strategicimperativesList;
    private STRATEGICIMPERATIVES strategicimperativesDetailList;
    public  Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initiativeNameLabel;
        public View deleteButtonView;
        public MyViewHolder(View view) {
            super(view);
            initiativeNameLabel = (TextView) view.findViewById(R.id.selectedInitiativeName);
            deleteButtonView = (View) view.findViewById(R.id.deleteInitiativeButton);
        }
    }
    public SelectedStrategicImperativesListAdapter(List<STRATEGICIMPERATIVES> strategicimperativesList) {
        this.strategicimperativesList = strategicimperativesList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addgoal_initiative, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(20);
            holder.itemView.setLayoutParams(params);
            holder.setIsRecyclable(false);
            strategicimperativesDetailList = strategicimperativesList.get(holder.getAdapterPosition());
            holder.initiativeNameLabel.setText(strategicimperativesDetailList.getNAME());
            holder.deleteButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("InitiativeDataRefresh");
                    intent.putExtra("position", Integer.toString(holder.getAdapterPosition()));
                    intent.putExtra("type", "strategicImperatives");
                    LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
                }
            });
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(strategicimperativesList!=null)
        return strategicimperativesList.size();
        else return 0;
    }
}
