package com.ratnikoff.BaseConfigurator.JsonParser;

import com.ratnikoff.BaseConfigurator.Base.Owner;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * Created by SM on 16.03.2016.
 */
public class OwnerData {
    // JSON Node names
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_ID = "id";
    private static final String TAG_INN = "inn";
    private static final String TAG_SHORT_NAME = "shortName";
    private static final String TAG_ADDRESS = "fullHouseAddress";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_PHONE = "phone";
    private static String url = "http://xn--c1aubj.xn--80asehdb/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/%D0%BA%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8/?%D0%B8%D0%BD%D0%BD=7704819021";

    // 7704819021
    private static String url2 = "http://xn--c1aubj.xn--80asehdb/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/%D0%BA%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8/";
    private static String url3 = "http://xn--c1aubj.xn--80asehdb/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/%D0%BA%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8/?%D0%B8%D0%BD%D0%BD=";
    //    private final int inn;
    private Owner owner = new Owner();
    private Long idi;


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
        url3 += inn;//inn;//"7704819021";
        JSONObject json = jParser.getJSONFromUrl(url3);
        // String inN;
        String name;

        /// String idit;//= Integer.parseInt(null);
        try {
            //  inN = json.getString(TAG_INN);
            name = json.getString(TAG_SHORT_NAME);

            owner.setName(name);
            idi = json.getLong("id");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONParserFull jParserF = new JSONParserFull();
        url2 += idi + "/";//inn;//"7704819021";
        /// jParser = new JSONParser();
        JSONObject json2 = jParserF.getJSONFromUrl(url2);
        String ss = json2.getString("fullHouseAddress");
        // String nam=json.getString(TAG_ADDRESS);
        owner.setAddress(ss);
        int i;
        i = 1;
        return owner;
    }

}
