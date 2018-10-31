package com.ibm.cio.gss.isap_lite.exceptionHandling;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;


public class MyExceptionHandler implements Thread.UncaughtExceptionHandler{
    private final Context myContext;
    private final Class<?> myActivityClass;

    public MyExceptionHandler(Context context, Class<?> c) {
        myContext = context;
        myActivityClass = c;
    }
    public void uncaughtException(Thread thread, Throwable exception) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(myContext, ISAP_Constants.APP_CRASH_MESSAGE, Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(myContext, myActivityClass);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        myContext.startActivity(intent);
                        System.exit(0);

                    }
                },2000);

                Looper.loop();
            }
        }.start();

        try
        {
            Thread.sleep(4000); // Let the Toast display before app will get shutdown
        }
        catch (InterruptedException e)
        {
            // Ignored.
        }
    }
//    private void showDialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(myContext);
//        builder1.setTitle("Title");
//        builder1.setMessage("my message");
//        builder1.setCancelable(true);
//        builder1.setNeutralButton(android.R.string.ok,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }
//

}
