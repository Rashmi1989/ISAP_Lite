package com.ibm.cio.gss.isap_lite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.RELEVANTBRANDSORUNITS;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.List;
import java.util.Map;

/**
 * Created by Kabuli on 7/5/2018.
 */

public class InitiativeBrandUnitAdapter extends RecyclerView.Adapter<InitiativeBrandUnitAdapter.MyViewHolder>{
    private List<RELEVANTBRANDSORUNITS> brandList;
    private Map<Integer,Boolean> geoMap;
    private RELEVANTBRANDSORUNITS brandData;
    public boolean section=false;
    public Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView relevantBUTitle,relevantBuName;
        private EditText relevantBuValueEditText;
        private boolean section=false;
        public MyViewHolder(View view) {
            super(view);
            relevantBUTitle = (TextView) view.findViewById(R.id.relevatBuTitle);
            relevantBuName = (TextView) view.findViewById(R.id.relevantBuName);
            relevantBuValueEditText = (EditText) view.findViewById(R.id.relevantBuValueEditText);
        }
    }
    public InitiativeBrandUnitAdapter(List<RELEVANTBRANDSORUNITS> relevantBUList, Map<Integer,Boolean> choiceMap,boolean sectionStatus) {
        this.brandList = relevantBUList;
        this.geoMap = choiceMap;
        this.section=sectionStatus;
    }
    @Override
    public InitiativeBrandUnitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(this.section){
             itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.relevant_bu, parent, false);
        }else{
             itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.relevant_bu_data, parent, false);
        }
        ctx=parent.getContext();
        return new InitiativeBrandUnitAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final InitiativeBrandUnitAdapter.MyViewHolder holder,int position) {
      try{
        holder.setIsRecyclable(true);
        final int rowIndex = position;  
        brandData=brandList.get(rowIndex);
        if(brandData.isSection())
        holder.relevantBUTitle.setText(brandData.getBRAND_GROUP());
        else{
            holder.relevantBuName.setText(brandData.getBRAND());
            holder.relevantBuValueEditText.setText(brandData.getPCT());
        }
        holder.relevantBuValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                holder.relevantBuValueEditText.setText(s.toString());
                brandList.get(rowIndex).setPCT(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
      }catch (Exception e){
//          Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public int getItemCount() {
        if(brandList!=null)
        return brandList.size();
        else return 0;
    }
}
