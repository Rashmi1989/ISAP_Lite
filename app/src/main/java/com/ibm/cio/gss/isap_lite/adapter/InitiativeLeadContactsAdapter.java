package com.ibm.cio.gss.isap_lite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.InitiativeContactModel;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.stylekitIcons.CircleTransform;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;

import java.util.List;

/**
 * Created by Kabuli on 7/5/2018.
 */

public class InitiativeLeadContactsAdapter extends RecyclerView.Adapter<InitiativeLeadContactsAdapter.MyViewHolder> {

    private List<InitiativeContactModel> contactList;
    private InitiativeContactModel contactInfo;
    public Context ctx;
    private ISAPService IsapService;
    private Gson gson;
    private String type;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView leadName;
        private ImageView leadImage;
        private TextView leadEmail;
        private TextView leadRole;
        private LinearLayout contactLayout;
        private EditText searchBy;
        public MyViewHolder(View view) {
            super(view);
            leadImage=(ImageView)view.findViewById(R.id.leadImage) ;
            leadName = (TextView) view.findViewById(R.id.leadName);
            leadRole = (TextView) view.findViewById(R.id.leadRole);
            leadEmail =(TextView) view.findViewById(R.id.leadEmail);
            contactLayout=(LinearLayout)view.findViewById(R.id.contactList);
        }
    }
    public InitiativeLeadContactsAdapter(List<InitiativeContactModel> contactList, String type) {
        try {
            this.contactList = contactList;
            this.type = type;
        }catch(Exception e){

        }

    }
    @Override
    public InitiativeLeadContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list, parent, false);
        ctx=parent.getContext();
        return new InitiativeLeadContactsAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final InitiativeLeadContactsAdapter.MyViewHolder holder, final int position) {
        try{
        holder.setIsRecyclable(false);
        contactInfo = contactList.get(position);
        Glide.with(ctx)
                .load(ISAP_Constants.IMC_IMAGE_URL+contactInfo.getEmailId())
                .asBitmap()
                .transform(new CircleTransform(ctx))
                .placeholder(R.drawable.avatar_image)
                .into(holder.leadImage);
        holder.leadName.setText(contactInfo.getName());
        holder.leadEmail.setText(contactInfo.getEmailId());
        holder.leadRole.setText(contactInfo.getRole());
        holder.contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
             if(type.equalsIgnoreCase("relationships")){
                 intent.setAction("RelationshipDataRefresh");
             }else if(type.equalsIgnoreCase("initiatives")){
                 intent.setAction("InitiativeDataRefresh");
             }else if(type.equalsIgnoreCase("actionPlan")){
                 intent.setAction("ActionPlanRefresh");
             }
                intent.putExtra("position",Integer.toString(holder.getAdapterPosition()));
                intent.putExtra("type","contacts");
                LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
            }
        });
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(contactList!=null)
        return contactList.size();
        else return 0;
    }

}

