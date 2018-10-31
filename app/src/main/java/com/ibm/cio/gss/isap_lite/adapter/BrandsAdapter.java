package com.ibm.cio.gss.isap_lite.adapter;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.activity.NewInitiativeActivity;
import com.ibm.cio.gss.isap_lite.model.RELEVANTBRANDSORUNITS;
import com.ibm.cio.gss.isap_lite.model.Value;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.List;
import java.util.Map;

/**
 * Created by Kabuli on 7/9/2018.
 */

public class BrandsAdapter extends BaseAdapter {
    private Context context;
    private List<RELEVANTBRANDSORUNITS> arrayList;
    private LayoutInflater inflater;
    private boolean isListView;
    private int selectedPosition = -1;
    public int clientId=0;
    public String clientName="";
    private Map<Integer,Boolean> choiceMap;
    int position = 0;

    public BrandsAdapter(Context context, Map<Integer, Boolean> choice_Map, List<RELEVANTBRANDSORUNITS> arrayList, boolean isListView) {
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        this.choiceMap=choice_Map;
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
        try{
        RELEVANTBRANDSORUNITS cell =(RELEVANTBRANDSORUNITS) getItem(i);
        final BrandsAdapter.ViewHolder viewHolder;
        position = i;
        if(cell.isSection())
        {
            view = inflater.inflate(R.layout.relevant_bu, null);
            view.setClickable(false);
            TextView header = (TextView) view.findViewById(R.id.relevatBuTitle);
            header.setText(cell.getBRAND_GROUP());
        }
        else
        {
            viewHolder = new BrandsAdapter.ViewHolder();
            if (isListView)
                view = inflater.inflate(R.layout.relevant_bu_data, viewGroup, false);
                viewHolder.brandname = (TextView) view.findViewById(R.id.relevantBuName);
                viewHolder.brandname.setText(cell.getBRAND());
                viewHolder.brandValue = (EditText) view.findViewById(R.id.relevantBuValueEditText);
          String  brandValue=cell.getPCT();
            if(brandValue!=null){
                brandValue=brandValue.split("\\.", 2)[0];
                viewHolder.brandValue.setText(brandValue+"%");
            }
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.relevantBuCheckbox);
            if(arrayList.get(i).getisSelected()){

               viewHolder.checkBox.setChecked(true);
            }
            view.setTag(viewHolder);
            viewHolder.brandValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    System.out.println("The edit text value is changed");
                }
                @Override
                public void afterTextChanged(Editable editable) {
//                    System.out.println("get pct:"+arrayList.get(i).getPCT()+" , set pct :>"+viewHolder.brandValue.getText().toString());if(viewHolder.brandValue.getText()!=null && viewHolder.brandValue.getText().length()>0 && !viewHolder.brandValue.getText().toString().equalsIgnoreCase("%"))
                    position = i;
                    if(viewHolder.brandValue.getText()!=null && viewHolder.brandValue.getText().length()>0 )
//                        arrayList.get(i).setPCT(viewHolder.brandValue.getText().toString().substring(0,viewHolder.brandValue.getText().toString().length()));
                        arrayList.get(i).setPCT(viewHolder.brandValue.getText().toString().replace("%",""));
                }
            });

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolder.checkBox.isChecked()){
                        choiceMap.put(i,true);
                    }else{
                        choiceMap.put(i,false);
                    }
                }
            });

            final View finalView = view;
            viewHolder.brandValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (position == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager) finalView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(viewHolder.brandValue.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });
        }
        }catch (Exception e){
//            Toast.makeText(context, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return view;
    }
    private void itemCheckChecked(View v) {
        System.out.println("item checked:"+v.getTag());
    }
    //On selecting any view set the current position to selectedPositon and notify adapter
    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }
    private class ViewHolder {
        private TextView brandTitle,brandname;
        private EditText brandValue;
        private CheckBox checkBox;
    }
    //Return the selectedPosition item
    public String getSelectedItem() {
        try{
        if (selectedPosition != -1) {
            Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition), Toast.LENGTH_SHORT).show();
            return arrayList.get(selectedPosition).getBRAND_GROUP();
        }
        }catch (Exception e){
//            Toast.makeText(context, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        return "";
    }
    //Delete the selected position from the arrayList
    public void deleteSelectedPosition() {
        try{
        if (selectedPosition != -1) {
            arrayList.remove(selectedPosition);
            selectedPosition = -1;//after removing selectedPosition set it back to -1
            notifyDataSetChanged();
        }
        }catch (Exception e){
//            Toast.makeText(context, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}