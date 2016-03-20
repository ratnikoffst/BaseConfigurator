package com.ratnikoff.BaseConfigurator.JsonParser;

import com.ratnikoff.BaseConfigurator.BaseSQLite.Owner;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * Created by SM on 16.03.2016.
 */
public class OwnerData {
    // JSON Node names

    private static final String TAG_SHORT_NAME = "shortName";
    private static final String TAG_ADDRESS = "fullHouseAddress";

    private static String urlAD = "http://xn--c1aubj.xn--80asehdb/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/%D0%BA%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8/";
    private static String urlID = "http://xn--c1aubj.xn--80asehdb/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/%D0%BA%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8/?%D0%B8%D0%BD%D0%BD=";

    private Owner owner = new Owner();
    private Long idi;
    private String Inn;


    public Owner getOwner(String inn) throws JSONException {
        try {
            Class strictModeClass = Class.forName("android.os.StrictMode");
            Class strictModeThreadPolicyClass = Class.forName("android.os.StrictMode$ThreadPolicy");
            Object laxPolicy = strictModeThreadPolicyClass.getField("LAX").get(null);
            Method method_setThreadPolicy = strictModeClass.getMethod("setThreadPolicy", strictModeThreadPolicyClass);
            method_setThreadPolicy.invoke(null, laxPolicy);
        } catch (Exception e) {

        }
        // Creating JSON Parser instance

        JSONParser jParser = new JSONParser();
        String url;// = "";
        url = urlID + inn;

        JSONObject json = jParser.getJSONFromUrl(url);
        if (json != null) {
            String name;

            try {

                name = json.getString(TAG_SHORT_NAME);

                owner.setName(name);
                idi = json.getLong("id");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONParserFull jParserF = new JSONParserFull();

            url = urlAD + idi + "/";
            JSONObject json2 = jParserF.getJSONFromUrl(url);
            String ss = json2.getString(TAG_ADDRESS);

            owner.setAddress(ss);
            return owner;
        } else {
            return null;
        }

    }
//
//    public int getInn() {
//        return inn;
//    }
}
