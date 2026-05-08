package com.example.heathwellnessassistant.Api;

import android.util.Log;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class ApiClient {

    private static final String TAG = "ApiClient";
    private static final String BASE_URL = "https://heathwellnessassistant-emotion-api.onrender.com/api/";
    private static final String API_KEY = "rdc243KBG789";
    private static final int TIME_OUT = 10000;

    // ─── POST ─────────────────────────────────────────────────────────────────
    public static JSONObject post(String endpoint, JSONObject body){
        HttpsURLConnection conn = null;
        try{
            conn = openConnection(BASE_URL + endpoint, "POST");

            byte[] data = body.toString().getBytes(StandardCharsets.UTF_8);
            conn.setRequestProperty("content-length", String.valueOf(data.length));
            try(OutputStream os = conn.getOutputStream()){
                os.write(data);
                os.flush();
            }

            return readResponse(conn);

        }catch (Exception e){
            Log.d(TAG, "put: "+ endpoint+ " failed"+ e.getMessage());
            //silent fail
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }
    // ─── Helpers ──────────────────────────────────────────────────────────────
    private static HttpsURLConnection openConnection(String urlStr, String method)
            throws Exception {
        HttpsURLConnection conn = (HttpsURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod(method);

        //telling the server input and output value
        conn.setRequestProperty("Content-Type",  "application/json");
        conn.setRequestProperty("x-api-key",     API_KEY);
        conn.setRequestProperty("Accept", "application/json");

        // setting time to connect to the server
        conn.setConnectTimeout(TIME_OUT);
        conn.setReadTimeout(TIME_OUT);
        return conn;
    }
    private static JSONObject readResponse(HttpURLConnection conn) throws Exception {
        //http status code
        int code = conn.getResponseCode();
        //read the response line by line
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                code >= 400 ? conn.getErrorStream() : conn.getInputStream(),
                StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();
        return new JSONObject(sb.toString());
    }
}
