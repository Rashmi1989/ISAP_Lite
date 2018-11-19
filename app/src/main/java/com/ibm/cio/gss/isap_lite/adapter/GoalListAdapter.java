package com.ibm.cio.gss.isap_lite.adapter;

/**
 * Created by Rashmi on 4/25/18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.activity.IsapMenuActivity;
import com.ibm.cio.gss.isap_lite.activity.NewGoalActivity;
import com.ibm.cio.gss.isap_lite.fragment.GoalDetailFragment;
import com.ibm.cio.gss.isap_lite.model.CITY_GROUP;
import com.ibm.cio.gss.isap_lite.model.GOAL_MORE;
import com.ibm.cio.gss.isap_lite.model.GoalDeleteModel;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.MyViewHolder> {

    private List<GOAL_MORE> goalsList;
    private  AlertDialog.Builder alertDialogBuilder;
    private   AlertDialog alertDialog;
    private GOAL_MORE goalDetailsList;
    public  Context ctx;
    public View customView;
    private ISAPService IsapService;
    private GoalDeleteModel goalDeleteModel;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView goalNameLabel, businessUnitLabel, amountLabel,initiativeLabel,goalLabel,initiativeTextLabel;
        public SwipeLayout swipeLayout;
        public TextView Delete;
        public TextView Edit;
        public View initiativeLabelGreyLine;
        private LinearLayout mLinearHead;
        public MyViewHolder(View view) {
            super(view);
            goalLabel = (TextView) view.findViewById(R.id.goal_detail);
            goalNameLabel = (TextView) view.findViewById(R.id.goal_detail_data);
            businessUnitLabel = (TextView) view.findViewById(R.id.goal_detail_data2);
            amountLabel = (TextView) view.findViewById(R.id.goalAmount);
            customView = (View) view.findViewById(R.id.intiativePopupButton);
            initiativeLabel = (TextView) view.findViewById(R.id.goal_detail_data4);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
            Edit = (TextView) itemView.findViewById(R.id.Edit);
            mLinearHead=itemView.findViewById(R.id.ll_head);
            initiativeLabelGreyLine = itemView.findViewById(R.id.initiativeLabelGreyLine);
            initiativeTextLabel = itemView.findViewById(R.id.goal_detail_data3);
            //goal_detail_data3
        }
    }
    public GoalListAdapter(List<GOAL_MORE> moviesList) {
        this.goalsList = moviesList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_detal_list, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }


    public void deleteGoal(String goalId, final View v, final int adapterPosition){
        IsapService = APiUtils.getUserService();
        ISAP_Utils.showISAPProgressDialog(v.getContext(), "Delete Goal", false);
        goalDeleteModel = new GoalDeleteModel();
        goalDeleteModel.setIntranetId(ISAP_Utils.LoggedInuserEmail);
        goalDeleteModel.setGoalId(goalId);

        Call call = IsapService.deleteGoal(goalDeleteModel);
        call.enqueue(new Callback<GoalResponseObj>() {
            @Override
            public void onResponse(Call<GoalResponseObj> call, Response<GoalResponseObj> response) {
                GoalResponseObj resObj=  response.body();
                ISAP_Utils.dismissProgressDialog();
                try {
                    if(resObj.getFlag().equalsIgnoreCase("true")) {
                        Toast.makeText(v.getContext(), ISAP_Constants.DELETE_GOAL, Toast.LENGTH_SHORT).show();
                        goalsList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                        notifyItemRangeChanged(adapterPosition, goalsList.size());

                    } else
                        Toast.makeText(v.getContext(), ISAP_Constants.DELETE_GOAL_FAILURE, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GoalResponseObj> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
            }
        });
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        try{
        holder.setIsRecyclable(true);
        goalDetailsList = goalsList.get(position);
        holder.goalLabel.setText(goalDetailsList.getGoal_label());
        holder.goalNameLabel.setText(goalDetailsList.getSTRATEGIC_GOAL_NM());
        holder.businessUnitLabel.setText(goalDetailsList.getCLIENT_BUSINESS_AREA());
        holder.amountLabel.setText(goalDetailsList.getESTIMATE_SIZE_AMT());
        customView.setTag(position);
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
            public void onStartClose(SwipeLayout layout) {}
            @Override
            public void onClose(SwipeLayout layout) {holder.mLinearHead.setVisibility(View.VISIBLE); }
            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {}
            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) { }
        });
        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("Each item is clicked");
            }
        });
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ctx, NewGoalActivity.class);
                intent.putExtra("goalId",goalsList.get(holder.getAdapterPosition()).getSTRATEGIC_GOAL_KEY());
                ctx.startActivity(intent);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGoal(goalsList.get(holder.getAdapterPosition()).getSTRATEGIC_GOAL_KEY(),v,holder.getAdapterPosition());
               // goalsList.remove(holder.getAdapterPosition());
               // notifyItemRemoved(holder.getAdapterPosition());
               // notifyItemRangeChanged(holder.getAdapterPosition(), goalsList.size());
            }
        });

        if(goalDetailsList.getINITIATIVES().length == 1 && goalDetailsList.getINITIATIVES()[0].getVALUE().toLowerCase().equalsIgnoreCase("none assigned")) {

            holder.initiativeLabel.setText("There are no initiatives.");
            customView.setVisibility(View.GONE);
            //holder.initiativeLabelGreyLine.setVisibility(View.GONE);
            //holder.initiativeTextLabel.setVisibility(View.GONE);

        }else {
            holder.initiativeLabel.setText(goalDetailsList.getINITIATIVES()[0].getVALUE());
        }

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               System.out.println("clicked item:"+holder.getAdapterPosition());
                 goalDetailsList=goalsList.get(holder.getAdapterPosition());
                ArrayList<String> topList = new ArrayList<String>();
                for(int j=0;j<goalDetailsList.getINITIATIVES().length;j++){
                    topList.add(goalDetailsList.getINITIATIVES()[j].getVALUE());
                }
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
                        holder.initiativeLabel.setText(goalDetailsList.getINITIATIVES()[position].getVALUE());
                    }
                });
            }
           });
        }catch (Exception e){
//            Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        if(goalsList!=null)
        return goalsList.size();
        else return 0;
    }
}
