package com.ratnikoff.BaseConfigurator.GPS;

/**
 * Created by SM on 19.03.2016.
 */

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.math.BigDecimal;
import java.math.RoundingMode;
//import com.evos.gps.IGPSCommunicator;
//import com.google.inject.Singleton;

@Singleton
public class GPSCommunicator implements IGPSCommunicator {

    private final int RAUND_MOD = 4;
    private LocationManager _locationManager;
    private double _latitude = 0;
    private double _longitude = 0;
    private float _accuracy = 0;
    private long _time = 0;
    private float _speed = 0;
    private double _altitude = 0;
    private float _bearing = 0;

    public synchronized double getLatitude() {
        return new BigDecimal(_latitude).setScale(RAUND_MOD, RoundingMode.HALF_EVEN).doubleValue();
    }

    public synchronized double getLongitude() {
        return new BigDecimal(_longitude).setScale(RAUND_MOD, RoundingMode.HALF_EVEN).doubleValue();
    }

    public synchronized float getAccuracy() {
        return _accuracy;
    }

    public synchronized long getTime() {
        return _time;
    }

    public synchronized float getSpeed() {
        return _speed;
    }

    public synchronized double getAltitude() {
        return _altitude;
    }

    public synchronized float getBearing() {
        return _bearing;
    }

    private synchronized void setLatitudeAndLongitude(double latitude, double longitude, float accuracy, long time, float speed, double altitude, float bearing) {
        _longitude = longitude;
        _latitude = latitude;
        _accuracy = accuracy;
        _time = time;
        _speed = speed;
        _altitude = altitude;
        _bearing = bearing;
    }

    public void setContext(Context context) {
        initializationGPS(context);
    }

    private void initializationGPS(Context context) {
        _locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        _locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                new GPSListener());

    }

    private class GPSListener implements LocationListener {

        public void onLocationChanged(final Location loc) {
            if (loc != null)
                setLatitudeAndLongitude(loc.getLatitude(), loc.getLongitude(), loc.getAccuracy(), loc.getTime(), loc.getSpeed(), loc.getAltitude(), loc.getBearing());
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

}


