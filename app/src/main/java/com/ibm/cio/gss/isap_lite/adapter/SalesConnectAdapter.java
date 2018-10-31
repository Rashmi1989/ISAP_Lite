package com.ibm.cio.gss.isap_lite.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.SALES_OPT_TABLE;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

/**
 * Created by abinash on 09/05/18.
 */
public class SalesConnectAdapter extends RecyclerView.Adapter<SalesConnectAdapter.MyViewHolder> {
    private SALES_OPT_TABLE[] initiative_List;
    private SALES_OPT_TABLE ini_data;
    public Context ctx;
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
    public SalesConnectAdapter(SALES_OPT_TABLE[] ini_List) {
        this.initiative_List = ini_List;
    }
    @Override
    public SalesConnectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_initiative, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try{
        ini_data = initiative_List[position];
        holder.initiativeName = changeLayoutParams(holder.initiativeName,"width",350);
        holder.businessUnit = changeLayoutParams(holder.businessUnit,"width",300);
        holder.initiativeName.setText(ini_data.getOPPORTUNITY_ID());
        holder.businessUnit.setText(ini_data.getOPPORTUNITY_NM());
        holder.initiativeValue.setText(ini_data.getOPPTY_VALUE());
        holder.closingDate.setText(ini_data.getOPP_CLOSE_DT());
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
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
    @Override
    public int getItemCount() {
        return initiative_List.length;
    }
}
