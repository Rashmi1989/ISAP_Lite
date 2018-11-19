package com.ibm.cio.gss.isap_lite.adapter;

/**
 * Created by Rashmi on 4/25/18.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.activity.LoginActivity;
import com.ibm.cio.gss.isap_lite.activity.NewInitiativeActivity;
import com.ibm.cio.gss.isap_lite.fragment.LinkedOpportunitesFragment;
import com.ibm.cio.gss.isap_lite.model.DeleteInitiativeModel;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES_MORE;
import com.ibm.cio.gss.isap_lite.model.LinkedOpportunitiesModel;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.model.LinkedOpportunitiesModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InitiativesMoreAdapter extends RecyclerView.Adapter<InitiativesMoreAdapter.MyViewHolder> {

    private List<INITIATIVES_MORE> initiativesList;
    private  AlertDialog.Builder alertDialogBuilder;
    private   AlertDialog alertDialog;
    private INITIATIVES_MORE initiativesDetailList;
    public  Context ctx;
    public View customView;
    public View aspirationBar;
    public View progressingBar;
    public View deliveringBar;
    public View closedBar;
    public View linkedOpputunitiesView;
    private ISAPService IsapService;
    private LinkedOpportunitiesModel linkedOpportunitiesData;
    private IsapMenuActivity activity;
    private DeleteInitiativeModel deleteInitiativeModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initiativeNameLabel,goalListLabel,initiativesLeadLabel,business_UnitsLabel,industrySolutionLabel,
               initiativeValueLabel,progressValueLabel,linkedOpportunitiesLabel;
        public SwipeLayout swipeLayout;
        public TextView Delete;
        public TextView Edit;
        private LinearLayout mLinearHead;
        public MyViewHolder(View view) {
            super(view);
            initiativeNameLabel = (TextView) view.findViewById(R.id.initiativeNameLabel);
            goalListLabel = (TextView) view.findViewById(R.id.goalListLabel);
            initiativesLeadLabel = (TextView) view.findViewById(R.id.initiativesLeadLabel);
            business_UnitsLabel = (TextView) view.findViewById(R.id.business_UnitsLabel);
            customView = (View) view.findViewById(R.id.goalPopupButton);
            industrySolutionLabel = (TextView) view.findViewById(R.id.industrySolutionLabel);
            initiativeValueLabel = (TextView) view.findViewById(R.id.initiativeValueLabel);
            progressValueLabel = (TextView) view.findViewById(R.id.progressValueLabel);
            linkedOpputunitiesView = (View) view.findViewById(R.id.linkedOppurtunitiesButton);
            linkedOpportunitiesLabel = (TextView) view.findViewById(R.id.linkedOppTextView);
            aspirationBar = (View) view.findViewById(R.id.aspirationBar);
            progressingBar = (View) view.findViewById(R.id.progressingBar);
            deliveringBar = (View) view.findViewById(R.id.deliveringBar);
            closedBar = (View) view.findViewById(R.id.closedBar);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.InitiativeSwipe);
            Delete = (TextView) itemView.findViewById(R.id.InitiativeDeleteSwipeButton);
            Edit = (TextView) itemView.findViewById(R.id.InitiativeEditSwipeButton);
            mLinearHead=itemView.findViewById(R.id.ll_head);

            linkedOpportunitiesLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initiativesDetailList=initiativesList.get(getAdapterPosition());
                    activity = (IsapMenuActivity) v.getContext();
                    ISAP_Utils.showISAPProgressDialog(activity, ISAP_Constants.FETCH_LINKEDOPPORTUNITIES,false);
                    fetchLinkedOpportunities(initiativesDetailList.getINITIATIVE_KEY());
                }
            });
        }
    }

    public void fetchLinkedOpportunities(String initiativeKey){
        IsapService = APiUtils.getUserService();
        Call call = IsapService.getLinkedOpportunities(Integer.parseInt(initiativeKey) ,-1,-1);
        call.enqueue(new Callback<LinkedOpportunitiesModel>() {
            @Override
            public void onResponse(Call<LinkedOpportunitiesModel> call, Response<LinkedOpportunitiesModel> response) {
                try{
                linkedOpportunitiesData = response.body();
                ISAP_Utils.dismissProgressDialog();
                if (linkedOpportunitiesData.getOPPTS().length  > 0)
                {
                    LinkedOpportunitesFragment myFragment = new LinkedOpportunitesFragment();
                    FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();
                    bundle.putString("initiative_Key", initiativesDetailList.getINITIATIVE_KEY());
                    myFragment.setArguments(bundle);
                    t.replace(R.id.frame_layout_main,myFragment);
                    t.addToBackStack("");
                    t.commit();
                }
                else
                    Toast.makeText(activity,ISAP_Constants.NO_OPPTY, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
//                    Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LinkedOpportunitiesModel> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
            }
        });
    }
    public InitiativesMoreAdapter(List<INITIATIVES_MORE> moviesList) {
        this.initiativesList = moviesList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.initiative_list, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    public void deleteInitiative(String initiativeId, final Context v, final int adapterPosition){
        try{
        IsapService = APiUtils.getUserService();
        ISAP_Utils.showISAPProgressDialog(v, ISAP_Constants.DELETE_INITIATIVE_INPROGRESS, false);
        deleteInitiativeModel = new DeleteInitiativeModel();
        deleteInitiativeModel.setIntranetId(ISAP_Utils.LoggedInuserEmail);
        deleteInitiativeModel.setInitiativeId(initiativeId);

        Call call = IsapService.deleteInitiative(deleteInitiativeModel);
        call.enqueue(new Callback<GoalResponseObj>() {
            @Override
            public void onResponse(Call<GoalResponseObj> call, Response<GoalResponseObj> response) {
                GoalResponseObj resObj=  response.body();
                ISAP_Utils.dismissProgressDialog();
                try {
                    if(resObj.getFlag().equalsIgnoreCase("true")) {
                        Toast.makeText(v, ISAP_Constants.DELETE_INITIATIVE, Toast.LENGTH_SHORT).show();
                        initiativesList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                        notifyItemRangeChanged(adapterPosition, initiativesList.size());
                    } else
                        Toast.makeText(v, ISAP_Constants.DELETE_INITIATIVE_FAILURE, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GoalResponseObj> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
            }
        });
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
    try{
        holder.setIsRecyclable(true);
        initiativesDetailList = initiativesList.get(holder.getAdapterPosition());
        holder.initiativeNameLabel.setText(initiativesDetailList.getINITIATIVE_KEY_NM());
        if((initiativesDetailList.getGOALS()!=null) && initiativesDetailList.getGOALS().length > 0){
            holder.goalListLabel.setText((CharSequence) initiativesDetailList.getGOALS()[0].getVALUE());
        }
        holder.initiativesLeadLabel.setText(initiativesDetailList.getINITIATIVE_LEAD());
        holder.business_UnitsLabel.setText(initiativesDetailList.getBUSINESS_UNITS());
        holder.industrySolutionLabel.setText(initiativesDetailList.getINDUSTRY_SOLUTION());
        holder.initiativeValueLabel.setText(initiativesDetailList.getINITIATIVE_VAL());
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wraper));
        holder.swipeLayout.setLeftSwipeEnabled(false);
        if(ISAP_Utils.userAccess==false){
            holder.swipeLayout.setRightSwipeEnabled(false);
        }
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {}
            @Override
            public void onOpen(SwipeLayout layout) {holder.mLinearHead.setVisibility(View.INVISIBLE);}
            @Override
            public void onStartClose(SwipeLayout layout) { }
            @Override
            public void onClose(SwipeLayout layout) {holder.mLinearHead.setVisibility(View.VISIBLE); }
            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {}
            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {}
        });
     //reset progress bar
        aspirationBar.setVisibility(View.GONE);
        progressingBar.setVisibility(View.GONE);
        deliveringBar.setVisibility(View.GONE);
        closedBar.setVisibility(View.GONE);

         if(initiativesDetailList.getPROGRESSION_CODE().equalsIgnoreCase("ASPT")){
             holder.progressValueLabel.setText("Aspiration");
             aspirationBar.setVisibility(View.VISIBLE);
         }else if(initiativesDetailList.getPROGRESSION_CODE().equalsIgnoreCase("PRGR")){
             holder.progressValueLabel.setText("Progressing");
             progressingBar.setVisibility(View.VISIBLE);
         }else if (initiativesDetailList.getPROGRESSION_CODE().equalsIgnoreCase("DLVG")){
             holder.progressValueLabel.setText("Delivering");
             deliveringBar.setVisibility(View.VISIBLE);
         }else{
             holder.progressValueLabel.setText("Closed");
             closedBar.setVisibility(View.VISIBLE);
         }

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiativesDetailList=initiativesList.get(holder.getAdapterPosition());
                ArrayList<String> topList = new ArrayList<String>();
                for(int j=0;j<initiativesDetailList.getGOALS().length;j++){
                    topList.add(initiativesDetailList.getGOALS()[j].getVALUE());
                }
                System.out.println("data:"+topList);
                alertDialogBuilder = new AlertDialog.Builder(ctx);
                alertDialog = alertDialogBuilder.create();
                final String [] stringArray = topList.toArray(new String[topList.size()]);
                LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.citi_group, null);
                alertDialog.setView(convertView);
                ListView lv = (ListView) convertView.findViewById(R.id.citigroup);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,stringArray);
                lv.setAdapter(adapter);
                alertDialog.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        alertDialog.dismiss();
                        holder.goalListLabel.setText(initiativesDetailList.getGOALS()[position].getVALUE());
                    }
                });
            }
        });
        linkedOpputunitiesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiativesDetailList=initiativesList.get(holder.getAdapterPosition());
                activity = (IsapMenuActivity) view.getContext();
                ISAP_Utils.showISAPProgressDialog(activity, ISAP_Constants.FETCH_LINKEDOPPORTUNITIES,false);
                fetchLinkedOpportunities(initiativesDetailList.getINITIATIVE_KEY());
            }
        });
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ctx, NewInitiativeActivity.class);
                intent.putExtra("InitiativeId",initiativesList.get(holder.getAdapterPosition()).getINITIATIVE_KEY());
                ctx.startActivity(intent);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage(ISAP_Constants.DELETE_INITIATIVE_ALERT);
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteInitiative(initiativesList.get(holder.getAdapterPosition()).getINITIATIVE_KEY(),ctx,holder.getAdapterPosition());
                       // initiativesList.remove(holder.getAdapterPosition());
                       // notifyItemRemoved(holder.getAdapterPosition());
                       // notifyItemRangeChanged(holder.getAdapterPosition(), initiativesList.size());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }catch (Exception e){
//        Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
    }
    }
    @Override
    public int getItemCount() {
        if(initiativesList!=null)
        return initiativesList.size();
        else return 0;
    }
}
