package com.ibm.cio.gss.isap_lite.remote;

import android.webkit.CookieManager;

import com.ibm.cio.gss.isap_lite.utility.ISAP_Constants;
import com.ibm.cio.gss.isap_lite.utility.ISAP_Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
Class/Interface Name : "RetrofitClient"
Description :"RetrofitClient which helps to connect backend system ."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

public class RetrofitClient {
    private static Retrofit retrofit=null;
    static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder().header("Authorization", CookieManager.getInstance().getCookie("Header"));
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
             }
             }).build();
//        }
//        }).readTimeout(90, TimeUnit.SECONDS)
//            .connectTimeout(90, TimeUnit.SECONDS).build();

    public static Retrofit getClient(String url){
            retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }

}
