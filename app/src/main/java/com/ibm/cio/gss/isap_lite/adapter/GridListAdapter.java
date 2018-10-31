/*
Class Name : "GridListAdapter"
Description :"Adapter to create the UI view with radio buttons and alpha index with the help of LIST data."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/
package com.ibm.cio.gss.isap_lite.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.ClientsActivity;
import com.ibm.cio.gss.isap_lite.model.Value;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.List;

/**
 * Created by sonu on 08/02/17.
 */

public class GridListAdapter extends BaseAdapter  {
    private Context context;
    private List<Value> arrayList;
    private LayoutInflater inflater;
    private boolean isListView;
    private int selectedPosition = -1;
    public int clientId=0;
    public String clientName="";

    public GridListAdapter(Context context, List<Value> arrayList, boolean isListView) {
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Value cell =(Value) getItem(i);
        ViewHolder viewHolder;
       try{
        if(cell.isSectionHeader())
        {
            view = inflater.inflate(R.layout.section_header, null);
            view.setClickable(false);
            TextView header = (TextView) view.findViewById(R.id.section_header);
            header.setText(cell.getName());
            header.setTextColor(Color.parseColor("#000000"));
        }
        else
        {
            viewHolder = new ViewHolder();
            if (isListView)
                view = inflater.inflate(R.layout.list_custom_row_layout, viewGroup, false);
            viewHolder.label = (TextView) view.findViewById(R.id.label);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            view.setTag(viewHolder);
            viewHolder.label.setText(arrayList.get(i).getName());
            //check the radio button if both position and selectedPosition matches
            viewHolder.radioButton.setChecked(i == selectedPosition);
            //Set the position tag to both radio button and label
            viewHolder.radioButton.setTag(i);
            viewHolder.label.setTag(i);
            viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCheckChanged(v);
                }
            });
            viewHolder.label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCheckChanged(v);
                }


            });

        }
        }catch (Exception e){
//           Toast.makeText(context, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
       }
        return view;
    }
    //On selecting any view set the current position to selectedPositon and notify adapter
    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        try{
        ISAP_Utils.clientID=arrayList.get(selectedPosition).getId();
        ISAP_Utils.clientName=arrayList.get(selectedPosition).getName();
        }catch (Exception e){
//            Toast.makeText(context, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView label;
        private RadioButton radioButton;
    }
    //Return the selectedPosition item
    public String getSelectedItem() {
        if (selectedPosition != -1) {
            try{
            Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition), Toast.LENGTH_SHORT).show();
            return arrayList.get(selectedPosition).getName();
            }catch (Exception e){
//                Toast.makeText(context, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        }
        return "";
    }
    //Delete the selected position from the arrayList
    public void deleteSelectedPosition() {
        if (selectedPosition != -1) {
            arrayList.remove(selectedPosition);
            selectedPosition = -1;//after removing selectedPosition set it back to -1
            notifyDataSetChanged();
        }
    }
}
