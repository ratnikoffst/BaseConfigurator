package com.ratnikoff.BaseConfigurator.GPS;

import android.content.Context;

/**
 * Created by SM on 19.03.2016.
 */
public interface IGPSCommunicator {
    double getLatitude();

    double getLongitude();

    float getAccuracy();

    long getTime();

    float getSpeed();

    double getAltitude();

    float getBearing();

    void setContext(Context context);
}
