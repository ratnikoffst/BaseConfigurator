package com.ratnikoff.BaseConfigurator.GPS;

import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created by SM on 25.03.2016.
 */
public class GpsAddress {
    gpsObject address;

    public GpsAddress(LocationManager lm) {
        address = new gpsObject();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
    }

    public gpsObject GpsAddress(LocationManager lm) {
        address = new gpsObject();

        //   lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        //   LocationManager lm = (LocationManager) Activity.getSystemService(Context.LOCATION_SERVICE);

//lo`
        return address;
    }

    public gpsObject gpsLocation() {
        address = new gpsObject();

        // BaseConfigurator act = (BaseConfigurator) getActivity();
        //   LocationManager lm = (LocationManager) act.getSystemService();
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, (LocationListener) this);

        return address;
    }
}
