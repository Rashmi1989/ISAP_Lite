
package com.ibm.cio.gss.isap_lite.activity;
/*
Class Name : "LoginActivity"
Description :"Authenticate the user wrt Bluepage LDAP"
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cio.gss.isap_lite.R;
import com.ibm.cio.gss.isap_lite.model.BluepageAuth;
import com.ibm.cio.gss.isap_lite.model.authModel;
import com.ibm.cio.gss.isap_lite.remote.APiUtils;
import com.ibm.cio.gss.isap_lite.remote.ISAPService;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.LogUtils;
import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;


public class LoginActivity extends AppCompatActivity implements FingerPrintAuthCallback {
    private EditText edtUserName;
    private EditText edtPassword;
    private TextView TouchIdLogin, mversionNumber1,mversionNumber2;
    private View TouchIdImage;
    private Button btnLogin;
    private Button registerTouchId;
    private ISAPService IsapService;
    private BluepageAuth bluepageAuth;
    private FingerPrintAuthHelper mFingerPrintAuthHelper;
    private ISAP_Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTheme(R.style.login_theme);
//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this, LoginActivity.class));//App carsh will allow user to log in again.
        //hide phone status bar code start
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide phone status bar code end

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);






        //Touch Id buttons
        registerTouchId = (Button) findViewById(R.id.registerTouchId);
//        DisplayMetrics displayMetrics=ISAP_Utils.getDeviceMetrics(LoginActivity.this);
//        if(displayMetrics.heightPixels==2768) {
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) registerTouchId.getLayoutParams();
//            params.setMargins(0, 440, 0, 0); //left, top, right, bottom
//            registerTouchId.setLayoutParams(params);
//        }
        mversionNumber1 =findViewById(R.id.txt_verision_no1);
        mversionNumber2 =findViewById(R.id.txt_verision_no2);
        TouchIdLogin = (TextView) findViewById(R.id.TouchIdLogin);
        TouchIdImage = (View) findViewById(R.id.TouchIdImage);

        String uname= SecurePreferences.getStringValue("username","");;
        if(uname.toString().length()==0)
        {
            TouchIdImage.setVisibility(View.GONE);
            TouchIdLogin.setVisibility(View.GONE);

        }
        else
        {
            TouchIdImage.setVisibility(View.VISIBLE);
            TouchIdLogin.setVisibility(View.VISIBLE);
            registerTouchId.setVisibility(View.GONE);
            mversionNumber1.setVisibility(View.GONE);
            mversionNumber2.setVisibility(View.VISIBLE);
            ISAP_Utils.LoggedInuserEmail=uname;
        }
        registerTouchId.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                String username=edtUserName.getText().toString();
                String password=edtPassword.getText().toString();

                //Validate form
                if(validateLogin(username,password)){
                    doLogin(username,password,"touch");
                }
            }
        });


        edtUserName = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        IsapService = APiUtils.getUserService();

        //Event listener after clicking signin button .
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String username=edtUserName.getText().toString();
                String password=edtPassword.getText().toString();


                //Validate form
                if(validateLogin(username,password)){
                    doLogin(username,password,"");
                }
            }
        });
        /*getting app current verision*/
        getCurrentVerisionNumber();


    }


    //Validation goes here.
    private boolean validateLogin(String username, String password) {

        if(username == null || username.length() ==0) {
            Toast.makeText(this,"User name is required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.length() ==0) {
            Toast.makeText(this,"Password is required",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    };

    private void doLogin(final String username, final String password,String type) {

        if(type.equals("touch"))
            ISAP_Utils.showISAPProgressDialog(this,ISAP_Constants.PROGRESSDIALOG_REGISTER,false);
        else
            ISAP_Utils.showISAPProgressDialog(this,ISAP_Constants.PROGRESSDIALOG_AUTHENTICATING,false);


        final String loginType=type;
        bluepageAuth=new BluepageAuth(username,password);
        String base=username +":"+ password;
        CookieManager.getInstance().removeAllCookies(null);
        final String authHeader= "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        CookieManager.getInstance().setCookie("Header",authHeader);//Set the authHeader in Utility and later get in Retrofit client in order to add for all service call
        Call<authModel> call= IsapService.login(bluepageAuth);

        call.enqueue(new Callback<authModel>() {
            @Override
            public void onResponse(Call<authModel> call, Response<authModel> response) {
//                System.out.println("login response:"+response.raw());

                if(response.isSuccessful()){
                    authModel auth_model =response.body();


                    if(auth_model.isAuthenticated()==true){
                        ISAP_Utils.dismissProgressDialog();
                        //Storing credentials into Android Keychain.
                        ISAP_Utils.LoggedInuserEmail=username;
                        ISAP_Utils.userName=auth_model.getName();
                        ISAP_Utils.photoUrl=ISAP_Constants.IMC_IMAGE_URL.concat(username);

                        //On sucees rout to select cleint page
                        if(loginType.equals("touch")){
                            //save credentials in key store.
                            SecurePreferences.setValue("username", username);
                            SecurePreferences.setValue("password", password);
                            onSuccessfulRegistration();
                            registerTouchId.setVisibility(View.GONE);
                            TouchIdImage.setVisibility(View.VISIBLE);
                            TouchIdLogin.setVisibility(View.VISIBLE);
                            mversionNumber1.setVisibility(View.GONE);
                            mversionNumber2.setVisibility(View.VISIBLE);

                            //Display toast for registration successfully.
                        }else{
                            Intent intent=new Intent(LoginActivity.this,SelectclientActivity.class);
                            startActivity(intent);
                            SecurePreferences.setValue("user_email", username);
                        }


                    }else{
//                        Intent intent=new Intent(LoginActivity.this,SelectclientActivity.class);
//                        startActivity(intent);
                        ISAP_Utils.dismissProgressDialog();
                        boolean isWrongCredentials = false;
                        //lets check if there is any message from response. If we have any, then its a wrong credential passed else there might be
                        // any issue at db or anything else. According to that we need to show our error message.
                        if(auth_model.isAuthenticated() == false && auth_model.getMessage()!=null && auth_model.getMessage().length()>0){
                            isWrongCredentials = true;
                        }else{
                            isWrongCredentials = false;
                        }

                        if(loginType.equals("touchSuccess")) {
                            // Toast.makeText(LoginActivity.this, "Your w3id or password was entered incorrectly.Please register again for Touch Id", Toast.LENGTH_SHORT).show();
                            SecurePreferences.setValue("username", "");
                            SecurePreferences.setValue("password", "");
                            registerTouchId.setVisibility(View.VISIBLE);
                            TouchIdImage.setVisibility(View.GONE);
                            TouchIdLogin.setVisibility(View.GONE);
                            mversionNumber1.setVisibility(View.VISIBLE);
                            mversionNumber2.setVisibility(View.GONE);

                        }
                        Toast.makeText(LoginActivity.this, isWrongCredentials?ISAP_Constants.WRONG_CREDENTIALS:ISAP_Constants.ERROR_TRYAGAIN, Toast.LENGTH_SHORT).show();
                    }
                }

                else{
//                    startActivity(intent);
                    //System.out.println("Its error code-->>"+response.raw().code());
                   // System.out.println("Real code-->>"+response.code());
                    ISAP_Utils.dismissProgressDialog();
                    if(response.code()==401){
                        Toast.makeText(LoginActivity.this, ISAP_Constants.WRONG_CREDENTIALS, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, ISAP_Constants.SERVER_ISSUE, Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<authModel> call, Throwable t) {
                ISAP_Utils.dismissProgressDialog();
                LogUtils.printLog(LoginActivity.this,"onFailure",t.getMessage());

//                Intent intent=new Intent(LoginActivity.this,SelectclientActivity.class);
//                startActivity(intent);
//               Toast.makeText(LoginActivity.this, ISAP_Constants.ERROR_TRYAGAIN, Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Finger Print AUth goes here

    public void touchAuth(View view){

        Toast.makeText(LoginActivity.this, ISAP_Constants.FINGERPRINT_PLACE_FINGER, Toast.LENGTH_SHORT).show();

        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        mFingerPrintAuthHelper.startAuth();


    }
    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        //mFingerPrintAuthHelper.startAuth();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(this, ISAP_Constants.FINGERPRINT_NO_HARDWARE, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(this, ISAP_Constants.FINGERPRINT_NOT_REGISTER, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(this, ISAP_Constants.FINGERPRINT_BELOW_MARSHMELLOW, Toast.LENGTH_SHORT).show();
    }

    public  void onSuccessfulRegistration()
    {
        Toast.makeText(this, ISAP_Constants.FINGERPRINT_REGISTER_SUCCESS, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {

        String uname= SecurePreferences.getStringValue("username","");
        String pwd= SecurePreferences.getStringValue("password","");
        // Toast.makeText(this, "sucees with uname:"+uname.toString()+",pwd:"+pwd.toString(), Toast.LENGTH_SHORT).show();
        if(uname.toString().length()==0)
        {
//            progressDialog.dismiss();
            ISAP_Utils.dismissProgressDialog();
            onNoFingerPrintRegistered();
        }
        else{

            doLogin(uname.toString(),pwd.toString(),"touchSuccess");
        }

    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
//        progressDialog.dismiss();
        ISAP_Utils.dismissProgressDialog();
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                //Cannot recognize the fingerprint scanned.
                Toast.makeText(this, ISAP_Constants.FINGERPRINT_AUTH_FAILURE, Toast.LENGTH_SHORT).show();

                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                //Any recoverable error. Display message to the user.
                break;
        }
    }
    /*getting app current verision*/
    private void getCurrentVerisionNumber() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mversionNumber1.setText("Version "+version);
            mversionNumber2.setText("Version "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
