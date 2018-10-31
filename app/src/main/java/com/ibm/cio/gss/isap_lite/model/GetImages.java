package com.ibm.cio.gss.isap_lite.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by abinash on 10/04/18.
 */

public class GetImages extends AsyncTask<Object, Object, Object> {
    private String requestUrl, imagename_;
    private CircularImageView view;
    private Bitmap bitmap ;
    private FileOutputStream fos;
    public GetImages(String requestUrl, CircularImageView view, String _imagename_) {
        this.requestUrl = requestUrl;
        this.view = view;
        // this.imagename_ = _imagename_ ;
    }

    @Override
    protected  Object doInBackground(Object... objects) {
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty ("Authorization", android.webkit.CookieManager.getInstance().getCookie("Header"));
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    protected  void onPostExecute(Object o) {
//        if(!ImageStorage.checkifImageExists(imagename_))
//        {
        view.setImageBitmap(bitmap);
//            ImageStorage.saveToSdCard(bitmap, imagename_);
//        }
    }
}