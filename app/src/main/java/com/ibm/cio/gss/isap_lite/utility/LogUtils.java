package com.ibm.cio.gss.isap_lite.utility;

import android.app.Activity;
import android.util.Log;

import com.ibm.cio.gss.isap_lite.activity.LoginActivity;

public class LogUtils {
    /*Strip*/
    /*this type of condition called as strip*/
    private static boolean ERROR = false;
    private static boolean INFO = true;
    private static boolean DEBUG = false;
    private static boolean WARN = false;
    /*true-app display all types of logs in app,false-app is not showing any type of logs */
    /*NOTE:While releasing app please mark all false only*/


    public static void printLog(Activity activity, String methodName, String message) {
        if (ERROR) {
            Log.e(">>>" + activity.getClass().getSimpleName(), "METHOD:"+methodName + "---" +"MESSAGE:"+ message);
        }
        if (INFO) {
            Log.i(">>>" + activity.getClass().getSimpleName(), "METHOD:"+methodName + "---" +"MESSAGE:"+ message);
        }
        if (DEBUG) {
            Log.d(">>>" + activity.getClass().getSimpleName(), "METHOD:"+methodName + "---" +"MESSAGE:"+ message);
        }
        if (WARN) {
            Log.w(">>>" + activity.getClass().getSimpleName(), "METHOD:"+methodName + "---" +"MESSAGE:"+ message);
        }

    }


}
