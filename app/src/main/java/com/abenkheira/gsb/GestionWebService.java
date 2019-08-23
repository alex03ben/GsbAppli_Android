package com.abenkheira.gsb;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by abenkheira on 09/03/2018.
 */

public class GestionWebService extends AsyncTask {
    private static final String URLBASE  = "http://10380.sio.jbdelasalle.com/~abenkheira/webservice/webservice.php?";
    public  static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient okHttpClient;

    public GestionWebService() {
        if (okHttpClient == null){
            GestionWebService.okHttpClient = new OkHttpClient();
            CookieManager cm = new CookieManager();
            cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            okHttpClient.setCookieHandler(cm);
        }
    }

    public String post(String url, String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected String doInBackground(Object[] objects) {
        String url = URLBASE+objects[0];
        String result = null;
        try {
            result = post(url, "");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
