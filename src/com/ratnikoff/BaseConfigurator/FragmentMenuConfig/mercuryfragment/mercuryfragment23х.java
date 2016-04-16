package com.ratnikoff.BaseConfigurator.FragmentMenuConfig.mercuryfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import com.ratnikoff.BaseConfigurator.BaseSQLite.Pribor;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBasePribor.PriborListAdapter;
import com.ratnikoff.BaseConfigurator.R;
import com.ratnikoff.BaseConfigurator.UsbDriver.Mercury23;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.toHexString;


/**
 * Created by SM on 21.03.2016.
 */
public class mercuryfragment23х extends Fragment implements View.OnClickListener {
    ListView lvRegistryPribor;
    int progress;
    //private View rootpribor;
    Mercury23 me;
    NumberPicker sAdr;
    NumberPicker eAdr;
    ProgressBar progressBar;

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

        progressBar = (ProgressBar) rootmerc23.findViewById(R.id.progressBar);
        progressBar.setMax(240);

        sAdr = (NumberPicker) rootmerc23.findViewById(R.id.startAddress);
        sAdr.setMinValue(1);
        sAdr.setMaxValue(240);
        sAdr.setValue(1);

        eAdr = (NumberPicker) rootmerc23.findViewById(R.id.endAddress);
        eAdr.setMinValue(1);
        eAdr.setMaxValue(240);
        eAdr.setValue(240);//etDisplayedValues();
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


//        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
//        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

// Временно закрыто !!!
//        PowerManager mPowerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
//        int mWakeLockState = 0;
//        PowerManager.WakeLock mWakeLock = mPowerManager.newWakeLock(mWakeLockState, "UMSE PowerTest");
//        byte[] b = new byte[]{0x02, 0x00, 0x00, (byte) 0xD0, 0x02, 0x00, 0x00, (byte) 0xD0, 0x02, 0x01, 0x02, 0x02, 0x02, 0x02};
//        UsbManager usb;
//        usb = new UsbManager();
//        UsbManager manager;
//        manager = new UsbManager(getActivity().getSystemService(Context.USB_SERVICE));


        OpenTask op = new OpenTask();

        op.execute();

    }

    public void addNewItem(Pribor prib) {

        SearchRegistryPribor.add(prib);
        //(/)lvRegistryPribor.getAdapter().notifyDataSetChanged();
        ((BaseAdapter) lvRegistryPribor.getAdapter()).notifyDataSetChanged();
    }

//    private void addNewItem(Pribor[] pribors) {
//        int i;
//        i = 1;
//        SearchRegistryPribor.add(pribors[0]);
//        //(/)lvRegistryPribor.getAdapter().notifyDataSetChanged();
//        ((BaseAdapter) lvRegistryPribor.getAdapter()).notifyDataSetChanged();
//        //  progressBar.setProgress(progress);
//    }

    class OpenTask extends AsyncTask<Void, Pribor, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Integer doInBackground(Void... params) {

            ArrayList<Pribor> pribors = new ArrayList<Pribor>();
            String addressStr;
            byte[] write;
            byte[] read;
            Pribor prib;

            UsbManager manager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
            FragmentManager fm = getActivity().getFragmentManager();

            try {
                me = new Mercury23(manager, getActivity(), fm);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (progress = 1; progress <= 240; progress++) {
                write = Mercury23.WRITE_SEARCH;
                read = Mercury23.READ_SEARCH;

                addressStr = toHexString(progress);
                write[0] = (byte) Integer.parseInt(addressStr, 16);
                write = me.crc16modbus(write);

                try {
                    read = me.getReadSearch(write, read);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                prib = new Pribor();
                prib.setAddressPribor(254);
                if (Arrays.equals(write, read)) {

                    prib.setAddressPribor(progress);
                    try {
                        prib.setNumberPribor(me.getNumber(progress));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        prib.setTypePribor(me.getName(progress)); // Cделать метод
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    prib.setBaudPribor(9600); // Захордкодено
                    int pupi = 0;//
                    try {
                        pupi = me.getPuPi(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int Pu = pupi / 10000;
                    pupi = pupi - Pu * 10000;
                    prib.setPiPribor(pupi);// ток
                    prib.setPuPribor(Pu);// напряжение

                    ///   publishProgress(prib);

                }
                publishProgress(prib);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Pribor... pribors) {
            super.onProgressUpdate(pribors);
            Pribor p = pribors[0];
            if (p.getAddressPribor() != 254) {
                SearchRegistryPribor.add(p);

                ((BaseAdapter) lvRegistryPribor.getAdapter()).notifyDataSetChanged();

                progressBar.setProgress(progress);
            } else {
                progressBar.setProgress(progress);
            }
        }

    }
}

