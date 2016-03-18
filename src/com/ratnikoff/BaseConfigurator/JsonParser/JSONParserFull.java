package com.ratnikoff.BaseConfigurator.JsonParser;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class JSONParserFull {

    private static final String TAG_ADDRESS = "fullHouseAddress";
    private static final String ADDRESS = "address";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String jsonString = "";
    JSONArray address = null;

    // constructor
    public JSONParserFull() {

    }

    public JSONObject getJSONFromUrl(String url) {
        // Making HTTP request
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);//
            StringBuilder sb = new StringBuilder();
            String line = null;

            //  String s=new String("UTF-8",line);


            while ((line = reader.readLine()) != null) {
                line = new String(line.getBytes(), "UTF-8");
                sb.append(line + "\n"); //
            }
            is.close();
            jsonString = sb.toString();
            //  jsonString=jsonString.substring(2,jsonString.length()-2)+"";
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        try {
            jObj = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        try {
            JSONObject address2;//=new JSONArray("address");
            address2 = jObj.getJSONObject("address");
            //String ss = address2.getString("fullHouseAddress");
            jObj = address2;
            int i;
            i = 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jObj;
    }
}
