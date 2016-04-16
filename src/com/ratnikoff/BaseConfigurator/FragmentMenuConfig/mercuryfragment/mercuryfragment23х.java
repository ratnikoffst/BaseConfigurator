package com.ratnikoff.BaseConfigurator.FragmentMenuConfig.mercuryfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.ratnikoff.BaseConfigurator.BaseSQLite.Pribor;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBasePribor.PriborListAdapter;
import com.ratnikoff.BaseConfigurator.R;
import com.ratnikoff.BaseConfigurator.UsbDriver.Mercury23;

import java.util.ArrayList;

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
    ListView lvRegistryPribor;
    //private View rootpribor;
    Mercury23 me;
    private ArrayList<Pribor> SearchRegistryPribor;
    private View rootmerc23;
    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private UsbDeviceConnection mConnection;
    private UsbEndpoint mEndpointIntr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootmerc23 = inflater.inflate(R.layout.mercuriy23, container, false);

        rootmerc23.findViewById(R.id.connect).setOnClickListener(this);
        lvRegistryPribor = (ListView) rootmerc23.findViewById(R.id.AddSearch);
        CreateFillList();
        return rootmerc23;
    }

    private void CreateFillList() {

//        List<Pribor> list = dbPribor.getAllObjectPribor(idObject);
        SearchRegistryPribor = new ArrayList<Pribor>();
        //for (int i = 0; i < list.size(); i++) {
        //    Pribor v = list.get(i);

        //     RegistryPribor.add(v);
        // }
        PriborListAdapter adapter = new PriborListAdapter(SearchRegistryPribor, this); ///new PriborListAdapter(RegistryPribor, this); //ListAdapter(RegistryObject, this);
        lvRegistryPribor.setAdapter(adapter);

        // lvRegistryPribor.setOnItemClickListener(this);
        //lvRegistryPribor.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UsbManager manager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);

//        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
//        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

// Временно закрыто !!!
//        PowerManager mPowerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
//        int mWakeLockState = 0;
//        PowerManager.WakeLock mWakeLock = mPowerManager.newWakeLock(mWakeLockState, "UMSE PowerTest");
        //   byte[] b = new byte[]{0x02, 0x00, 0x00, (byte) 0xD0, 0x02, 0x00, 0x00, (byte) 0xD0, 0x02, 0x01, 0x02, 0x02, 0x02, 0x02};
//UsbManager usb;
//      ///  usb = new UsbManager();
//        UsbManager manager;
//        manager = new UsbManager(getActivity().getSystemService(Context.USB_SERVICE));

        //= null;

        FragmentManager fm = getActivity().getFragmentManager();//getFragmentManager();

        try {
            me = new Mercury23(manager, getActivity(), fm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            me.searchAll(1, 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addNewItem(Pribor prib) {

        SearchRegistryPribor.add(prib);
        //(/)lvRegistryPribor.getAdapter().notifyDataSetChanged();
        ((BaseAdapter) lvRegistryPribor.getAdapter()).notifyDataSetChanged();
    }
}

