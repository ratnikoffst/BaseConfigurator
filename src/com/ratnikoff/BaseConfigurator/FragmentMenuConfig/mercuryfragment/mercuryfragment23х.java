package com.ratnikoff.BaseConfigurator.FragmentMenuConfig.mercuryfragment;

import android.app.Fragment;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ratnikoff.BaseConfigurator.R;
import com.ratnikoff.BaseConfigurator.UsbDriver.UsbDriver;

//        11
///12
//13
///14
//15
///.Context;


/**
 * Created by SM on 21.03.2016.
 */
public class mercuryfragment23х extends Fragment implements View.OnClickListener {
    private View rootmerc23;
    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private UsbDeviceConnection mConnection;
    private UsbEndpoint mEndpointIntr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootmerc23 = inflater.inflate(R.layout.mercuriy23, container, false);

        rootmerc23.findViewById(R.id.connect).setOnClickListener(this);


        return rootmerc23;
    }

    @Override
    public void onClick(View v) {

        int i;
        i = 1;

        UsbDriver usb = new UsbDriver((UsbManager) getActivity().getSystemService(Context.USB_SERVICE));
        String s = usb.getUsbInfo();
        TextView vinfo = (TextView) rootmerc23.findViewById(R.id.testUsb);
        vinfo.setText(s);
//        usb.openDevice();
        usb.testing();

    }


}

