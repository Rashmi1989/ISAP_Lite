package com.ibm.cio.gss.isap_lite.adapter;

/**
 * Created by Rashmi on 4/25/18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.ibm.cio.gss.isap_lite.model.GOAL_SUMMARY;
import com.ibm.cio.gss.isap_lite.model.GoalDeleteModel;
import com.ibm.cio.gss.isap_lite.model.GoalDetails;
import com.ibm.cio.gss.isap_lite.model.GoalResponseObj;
import com.ibm.cio.gss.isap_lite.model.INITIATIVES;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalRecyclerAdapter extends RecyclerView.Adapter<GoalRecyclerAdapter.MyViewHolder> {

    private List<GOAL_SUMMARY> goalsList;
    private  AlertDialog.Builder alertDialogBuilder;
    private   AlertDialog alertDialog;
    private GOAL_SUMMARY goalDetailsList;
    public  Context ctx;
    private ISAPService IsapService;
    public View customView;
    private GoalDeleteModel goalDeleteModel;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView goalNameLabel, goalDescriptionLabel, amountLabel,initiativeCountLabel,goalDevider;
        public SwipeLayout swipeLayout;
        public TextView Delete;
        public TextView Edit;
        private LinearLayout mLinearHead;
        public MyViewHolder(View view) {
            super(view);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            goalNameLabel = (TextView) view.findViewById(R.id.goalNameText);
            goalDescriptionLabel = (TextView) view.findViewById(R.id.goalDescriptionText);
            amountLabel = (TextView) view.findViewById(R.id.goalInitiativeValueText);
            goalDevider = (TextView) view.findViewById(R.id.goal_devider);
            initiativeCountLabel = (TextView) view.findViewById(R.id.goalInitiativeCountTextField);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
            Edit = (TextView) itemView.findViewById(R.id.Edit);
            mLinearHead=itemView.findViewById(R.id.ll_head);
        }
    }
    public void deleteGoal(String goalId, final Context v, final int adapterPosition){
         try{
            IsapService = APiUtils.getUserService();
            ISAP_Utils.showISAPProgressDialog(v, ISAP_Constants.DELETE_GOAL_INPROGRESS, false);
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
                        Toast.makeText(v, ISAP_Constants.DELETE_GOAL, Toast.LENGTH_SHORT).show();
                        goalsList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                        notifyItemRangeChanged(adapterPosition, goalsList.size());
                    } else
                        Toast.makeText(v, ISAP_Constants.DELETE_GOAL_FAILURE, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
                @Override
                public void onFailure(Call<GoalResponseObj> call, Throwable t) {
                    ISAP_Utils.dismissProgressDialog();
//            Toast.makeText("", t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            }catch (Exception e){
//                Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
            }
    }
    public GoalRecyclerAdapter(List<GOAL_SUMMARY> moviesList) {
        this.goalsList = moviesList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_goal_goalrecyclerview, parent, false);
        ctx=parent.getContext();
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
     try{
        holder.setIsRecyclable(true);
        goalDetailsList = goalsList.get(holder.getAdapterPosition());
        holder.goalNameLabel.setText(goalDetailsList.getSTRATEGIC_GOAL_NM());
        holder.goalDescriptionLabel.setText(goalDetailsList.getGOAL_DESC_TXT());
        if(goalDetailsList.getNO_OF_INTIATIVES().equalsIgnoreCase("0")){
            holder.initiativeCountLabel.setText("There are no initiatives.");
            holder.amountLabel.setText("");
            holder.goalDevider.setVisibility(View.GONE);
        }else{
            holder.initiativeCountLabel.setText(goalDetailsList.getNO_OF_INTIATIVES()+" initiatives");
            holder.amountLabel.setText(goalDetailsList.getESTIMATE_SIZE_AMT()+"  Total Initiative Value");
            holder.goalDevider.setVisibility(View.VISIBLE);
        }


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
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) { }
        });
        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsapMenuActivity activity = (IsapMenuActivity) v.getContext();
                GoalDetailFragment goalDetailFragment = new GoalDetailFragment();
                FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();
                t.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                ISAP_Utils.isInitiativeActive=false;
                Bundle bundle=new Bundle();
                bundle.putString("goalId", goalsList.get(holder.getAdapterPosition()).getSTRATEGIC_GOAL_KEY());
                goalDetailFragment.setArguments(bundle);
                t.replace(R.id.frame_layout_main,goalDetailFragment);
                t.addToBackStack("");
                t.commit();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage(ISAP_Constants.DELETE_GOAL_ALERT);
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteGoal(goalsList.get(holder.getAdapterPosition()).getSTRATEGIC_GOAL_KEY(),ctx,holder.getAdapterPosition());
                        //goalsList.remove(holder.getAdapterPosition());
                        //notifyItemRemoved(holder.getAdapterPosition());
                        //notifyItemRangeChanged(holder.getAdapterPosition(), goalsList.size());
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
//         Toast.makeText(ctx, ISAP_Constants.APP_EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
     }
    }
    @Override
    public int getItemCount() {
        if(goalsList!=null)
        return goalsList.size();
        else return 0;
    }
}
