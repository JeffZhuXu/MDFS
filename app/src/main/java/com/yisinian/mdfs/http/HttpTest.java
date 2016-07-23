package com.yisinian.mdfs.http;

import android.os.AsyncTask;

/**
 * Created by mac on 16/1/13.
 */
public class HttpTest {


    public static void httpGetTest(String url) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String urlSring = params[0];


                return null;
            }
        }.execute(url);

    }

    public static void httpPost() {

    }
}
