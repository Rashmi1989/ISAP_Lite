package com.ibm.cio.gss.isap_lite.utility;

/**
 * Created by Kabuli on 3/23/2018.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.DisplayMetrics;
import android.widget.Toast;
import android.content.Context;
import android.support.annotation.StringRes;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.ibm.cio.gss.isap_lite.R;

public class ISAP_Utils {
    private static final String TAG = ISAP_Utils.class.getSimpleName();
    public static int clientID=0;
    public static String LoggedInuserEmail="";
//    public static String LoggedInuserEmail="adamd@us.ibm.com";
    public static String userName="";
    public static String photoUrl="";
    public static String clientName="";
    public static Runnable progressRunnable;
    public static int currentPage=0;
    static ProgressDialog mProgressDialog;
    public static boolean isGoalTabSelected = false;
    public static boolean isInitiativeTabSelected = false;
    public static boolean isInitiativeActive=false;
    public static boolean isNewGoal=false;
    public static boolean userAccess=true;



    /**
     * Shows a long time duration toast message.
     *
     * @param msg Message to be show in the toast.
     * @return Toast object just shown
     **/
    public static Toast showToast(Context ctx, CharSequence msg) {
        return showToast(ctx, msg, Toast.LENGTH_LONG);
    }

    /**
     * Shows the message passed in the parameter in the Toast.
     *
     * @param msg      Message to be show in the toast.
     * @param duration Duration in milliseconds for which the toast should be shown
     * @return Toast object just shown
     **/
    public static Toast showToast(Context ctx, CharSequence msg, int duration) {
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.setDuration(duration);
        toast.show();
        return toast;
    }

    /**
     * Shows an alert dialog with the OK button. When the user presses OK button, the dialog
     * dismisses.
     **/
    public static void showAlertDialog(Context context, @StringRes int titleResId, @StringRes int bodyResId) {
        showAlertDialog(context, context.getString(titleResId),
                context.getString(bodyResId), null);
    }

    /**
     * Shows an alert dialog with the OK button. When the user presses OK button, the dialog
     * dismisses.
     **/
    public static void showAlertDialog(Context context, String title, String body) {
        showAlertDialog(context, title, body, null);
    }

    /**
     * Shows an alert dialog with OK button
     **/
    public static void showAlertDialog(Context context, String title, String body, DialogInterface.OnClickListener okListener) {

        if (okListener == null) {
            okListener = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(body).setPositiveButton("OK", okListener);

        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        builder.show();
    }



    /**
     * Shows a progress dialog with a spinning animation in it. This method must preferably called
     * from a UI thread.
     *
     * @param ctx           Activity context
     * @param title         Title of the progress dialog
     * @param isCancellable True if the dialog can be cancelled on back button press, false otherwise
     **/

    public static void showISAPProgressDialog(Context ctx, String title, boolean isCancellable) {

        if (ctx instanceof Activity) {
            if (!((Activity) ctx).isFinishing()) {
                mProgressDialog = new ProgressDialog(ctx , R.style.MyAlertDialogStyle);
                mProgressDialog.setMessage(title);
                mProgressDialog.setCancelable(isCancellable);
                mProgressDialog.show();
            }
        }
//         progressRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                mProgressDialog.cancel();
//            }
//        };
    }
    /**
     * Check if the {@link android.app.ProgressDialog} is visible in the UI.
     **/
    public static boolean isProgressDialogVisible() {
        return (mProgressDialog != null);
    }

    /**
     * Dismiss the progress dialog if it is visible.
     **/
    public static void dismissProgressDialog() {

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        mProgressDialog = null;
    }
    public static DisplayMetrics getDeviceMetrics(Context ctx ){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        ((Activity) ctx).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics;
    }
//    public static String showResponse(){
//        String response=null;
//        Gson gson = new Gson();
//        String data=gson.toJson();
//        return response;
//    }
}
