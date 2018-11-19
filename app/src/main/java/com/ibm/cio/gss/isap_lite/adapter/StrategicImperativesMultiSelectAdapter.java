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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.model.CITY_GROUP;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.model.STRATEGICIMPERATIVES;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class StrategicImperativesMultiSelectAdapter extends RecyclerView.Adapter<StrategicImperativesMultiSelectAdapter.MyViewHolder> {
    private List<STRATEGICIMPERATIVES> strategicimperativesList;
    private Map<Integer,Boolean> choiceMap;
    private STRATEGICIMPERATIVES strategicimperativesDetailList;
    public  Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initiativeNameLabel;
        public CheckBox addGoalCheckBox;
        public MyViewHolder(View view) {
            super(view);
            initiativeNameLabel = (TextView) view.findViewById(R.id.addGoalInitiativeName);
            addGoalCheckBox = (CheckBox) view.findViewById(R.id.addGoalcheckBox);
        }
    }

    public StrategicImperativesMultiSelectAdapter(List<STRATEGICIMPERATIVES> strategicimperativesList, Map<Integer,Boolean> choiceMap) {
        this.strategicimperativesList = strategicimperativesList;
        this.choiceMap = choiceMap;
    }
    @Override
      public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                 View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_goal_intiative_list, parent, false);
                ctx=parent.getContext();
                return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        try{
        holder.setIsRecyclable(false);
        strategicimperativesDetailList = strategicimperativesList.get(holder.getAdapterPosition());
        holder.initiativeNameLabel.setText(strategicimperativesDetailList.getNAME());
        if(strategicimperativesDetailList.getisSelected()){
            holder.addGoalCheckBox.setChecked(true);
        }
        holder.addGoalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.addGoalCheckBox.isChecked()){
//                    System.out.println("Checkbox is checked");
                    choiceMap.put(holder.getAdapterPosition(),true);
                }else{
                    choiceMap.put(holder.getAdapterPosition(),false);
                    // goalsList.get(position).setisSelected(false);
                    //sendValueBackToActivity(Integer.toString(position),"false",goalsList.get(position).getKEY(),goalsList.get(position).getNAME());
//                    System.out.println("Checkbox is unchecked");
                }
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
