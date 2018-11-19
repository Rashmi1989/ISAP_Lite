
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
import com.ibm.cio.gss.isap_lite.model.COUNTRY;
import com.ibm.cio.gss.isap_lite.model.GEO;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.model.LINKEDOPPT;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AddLinkedOpptAdapter extends RecyclerView.Adapter<AddLinkedOpptAdapter.MyViewHolder> {

    private List<LINKEDOPPT> linkedopptList;
    private Map<Integer,Boolean> choiceMap;
    private LINKEDOPPT linkedopptDetailList;
    public  Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {



        public TextView oppurtunityId;
        public TextView oppurtunityTCV;
        public CheckBox addGoalCheckBox;

        public MyViewHolder(View view) {
            super(view);

            oppurtunityId = (TextView) view.findViewById(R.id.addMoreOpptId);
            addGoalCheckBox = (CheckBox) view.findViewById(R.id.addMoreOpptCheckBox);
            oppurtunityTCV = (TextView) view.findViewById(R.id.addOpptTcvValue);

        }
    }



    public AddLinkedOpptAdapter(List<LINKEDOPPT> linkedopptList,Map<Integer,Boolean> choiceMap) {
        this.linkedopptList = linkedopptList;
        this.choiceMap = choiceMap;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_more_oppt_list, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        try{
        holder.setIsRecyclable(false);
        linkedopptDetailList = linkedopptList.get(position);
        holder.oppurtunityId.setText(linkedopptDetailList.getOPPORTUNITY_ID());
        holder.oppurtunityTCV.setText(linkedopptDetailList.getTCV_OPPTY_VAL());

        if(linkedopptDetailList.getisSelected()){
            holder.addGoalCheckBox.setChecked(true);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("This item is clicked");

                Intent intent = new Intent("InitiativeDataRefresh");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("position",Integer.toString(holder.getAdapterPosition()));
                intent.putExtra("type","linkedOppts");

                LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);

            }
        });
        holder.addGoalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.addGoalCheckBox.isChecked()){

                    System.out.println("Checkbox is checked");
                   // linkedopptList.get(holder.getAdapterPosition()).setisSelected(true);
                    choiceMap.put(holder.getAdapterPosition(),true);
                    // goalsList.get(position).setisSelected(true);

                    //sendValueBackToActivity(Integer.toString(position),"true",goalsList.get(position).getKEY(),goalsList.get(position).getNAME());

                }else{

                   // linkedopptList.get(holder.getAdapterPosition()).setisSelected(false);
                    // goalsList.get(position).setisSelected(false);
                    choiceMap.put(holder.getAdapterPosition(),false);
                    //sendValueBackToActivity(Integer.toString(position),"false",goalsList.get(position).getKEY(),goalsList.get(position).getNAME());
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
        if(linkedopptList!=null)
        return linkedopptList.size();
        else return 0;
    }
}
