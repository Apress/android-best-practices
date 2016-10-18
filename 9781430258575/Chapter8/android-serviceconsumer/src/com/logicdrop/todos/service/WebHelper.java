package com.logicdrop.todos.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.logicdrop.todos.activity.TodoActivity;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebHelper {

    public static boolean isOnline(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public WebResult executeHTTP(String url, String method, String input) throws IOException {

        OutputStream os = null;
        BufferedReader in = null;
        final WebResult result = new WebResult();

        try {
            final URL networkUrl = new URL(url);
            final HttpURLConnection conn = (HttpURLConnection) networkUrl.openConnection();
            conn.setRequestMethod(method);

            if (input !=null && !input.isEmpty()) {
                //Create HTTP Headers for the content length and type
                conn.setFixedLengthStreamingMode(input.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/json");
                //Place the input data into the connection
                conn.setDoOutput(true);
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(input.getBytes());
                //clean up
                os.flush();
            }

            final InputStream inputFromServer = conn.getInputStream();

            in = new BufferedReader(new InputStreamReader(inputFromServer));
            String inputLine;
            StringBuffer json = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }

            result.setHttpBody(json.toString());
            result.setHttpCode(conn.getResponseCode());

            return result;

        } catch (Exception ex) {
            Log.d(TodoActivity.APP_TAG, "HTTP error", ex);
            result.setHttpCode(500);
            return result;
        } finally {
            //clean up
            if (in != null) {
                in.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}
