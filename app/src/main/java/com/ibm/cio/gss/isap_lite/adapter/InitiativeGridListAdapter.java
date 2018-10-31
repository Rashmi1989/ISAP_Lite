package com.ibm.cio.gss.isap_lite.adapter;

/**
 * Created by Kabuli on 5/2/2018.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;

import com.ibm.cio.gss.isap_lite.model.INITIATIVES_TABLE;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;


public class InitiativeGridListAdapter extends RecyclerView.Adapter<InitiativeGridListAdapter.MyViewHolder> {

    private INITIATIVES_TABLE[] initiative_List;
    private  AlertDialog.Builder alertDialogBuilder;
    private   AlertDialog alertDialog;
    private INITIATIVES_TABLE ini_data;
    public  Context ctx;
    public View customView;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initiativeName, businessUnit,initiativeValue,closingDate;
        public MyViewHolder(View view) {
            super(view);
            initiativeName = (TextView) view.findViewById(R.id.initiative_name);
            businessUnit = (TextView) view.findViewById(R.id.initiative_bUnit);
            initiativeValue = (TextView) view.findViewById(R.id.initiative_value);
            closingDate = (TextView) view.findViewById(R.id.initiative_closeDate);
        }
    }
    public TextView changeLayoutParams(TextView textView,String heightWidthString,int value){
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
        if(heightWidthString.equalsIgnoreCase("height")){
            params.height = value;
        }else{
            params.width = value;
        }
        textView.setLayoutParams(params);
        return textView;
    }
    public InitiativeGridListAdapter(INITIATIVES_TABLE[] ini_List) {
        this.initiative_List = ini_List;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_initiative, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try{
        ini_data = initiative_List[position];
        holder.initiativeName = changeLayoutParams(holder.initiativeName,"width",400);
        holder.businessUnit = changeLayoutParams(holder.businessUnit,"width",350);
        holder.initiativeName.setText(ini_data.getINITIATIVE_KEY_NM());
        holder.initiativeValue.setText(ini_data.getINITIATIVES_VAL());
        holder.businessUnit.setText(ini_data.getBUSINESS_UNIT());
        holder.closingDate.setText(ini_data.getCLOSING_DATE());
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(initiative_List!=null)
        return initiative_List.length;
        else return 0;
    }
}


