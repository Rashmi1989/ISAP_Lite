package com.ibm.cio.gss.isap_lite.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.cio.gss.isap_lite.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestAccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_access);

        Toolbar mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Request Access");
        setSupportActionBar(mToolbar);
        mToolbar.setClickable(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                 perform whatever you want on back arrow click

//                Toast.makeText(getApplicationContext(), "Selected client:"+adapter.clientName,
//                        Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RequestAccessActivity.this,SelectclientActivity.class);
                startActivity(intent);

            }
        });

        TextView tv1 = (TextView) findViewById(R.id.textView3);
        tv1.setText("To set up a client plan, click the button below to initiate the client plan request process.\n\nIf you’ve already submitted a request, please check back later. You will receive an email notification once your request has been approved or declined by the plan owner.\n\nIf you require assistance please contact Help@IBM or your local Global Sales Coverage focal.");
        Pattern pattern = Pattern.compile("Help@IBM");
        Pattern pattern1 = Pattern.compile("Global Sales Coverage focal");

        String scheme = "https://w3.ibm.com/help/#/article/35468";
        String scheme1 = "https://w3-connections.ibm.com/wikis/home?lang=en-us#!/wiki/W6b2c08454fde_4b23_9a59_65738ef4233b/page/IBM%20Strategic%20Account%20Planning%20%28ISAP%29%20Stakeholders";
        addLink(tv1,"Help@IBM",scheme);
        addLink(tv1,"Global Sales Coverage focal",scheme1);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "https://w3.ibm.com/help/#/article/35468";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    public static void addLink(TextView textView, String patternToMatch,
                               final String link) {
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override public String transformUrl(Matcher match, String url) {
                return link;
            }
        };
        Linkify.addLinks(textView, Pattern.compile(patternToMatch), null, null,
                filter);
    }
}
