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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.BaseSQLite.DataBaseHelper;
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
    BaseConfigurator act;
    ProgressBar progressBar;
    String TagDeleteFrag;
    int TypePribor;
    int idObject;
    SearchPriborTask op;

    String type;
    private NumberPicker sAdr;
    private NumberPicker eAdr;
    private ArrayList<Pribor> SearchRegistryPribor;
    private View rootmerc23;
    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private UsbDeviceConnection mConnection;
    private UsbEndpoint mEndpointIntr;
    private boolean taskSearch = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootmerc23 = inflater.inflate(R.layout.mercuriy23, container, false);

        rootmerc23.findViewById(R.id.connect).setOnClickListener(this);
        rootmerc23.findViewById(R.id.savePribor).setOnClickListener(this);

        lvRegistryPribor = (ListView) rootmerc23.findViewById(R.id.AddSearch);

        type = getArguments().getString("TYPE");
        TagDeleteFrag = getArguments().getString("TAG");
        TypePribor = getArguments().getInt("Pribor");
        idObject = getArguments().getInt("ID_OWNER");

        act = (BaseConfigurator) getActivity();
        CreateFillList();

        progressBar = (ProgressBar) rootmerc23.findViewById(R.id.progressBar);
        progressBar.setMax(240);

        sAdr = (NumberPicker) rootmerc23.findViewById(R.id.startAddress);
        sAdr.setMinValue(1);
        sAdr.setMaxValue(240);
        sAdr.setValue(1);

        eAdr = (NumberPicker) rootmerc23.findViewById(R.id.endAddress);
        eAdr.setMinValue(1);
        eAdr.setMaxValue(253);
        eAdr.setValue(253);//etDisplayedValues();
        return rootmerc23;
    }

    private void CreateFillList() {


        SearchRegistryPribor = new ArrayList<Pribor>();

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
        //   PowerManager mPowerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        // int mWakeLockState = 0;
        // PowerManager.WakeLock mWakeLock = mPowerManager.newWakeLock(mWakeLockState, "UMSE PowerTest");
//        byte[] b = new byte[]{0x02, 0x00, 0x00, (byte) 0xD0, 0x02, 0x00, 0x00, (byte) 0xD0, 0x02, 0x01, 0x02, 0x02, 0x02, 0x02};
//        UsbManager usb;
//        usb = new UsbManager();
//        UsbManager manager;
//        manager = new UsbManager(getActivity().getSystemService(Context.USB_SERVICE));
        //     BaseConfigurator act;

        switch (v.getId()) {
            case R.id.savePribor:
                if (taskSearch == true) {
                    if (SearchRegistryPribor.size() != 0) {
                        DataBaseHelper dbPribor = new DataBaseHelper(getActivity());
                        Pribor prib;
                        for (int i = 0; i < SearchRegistryPribor.size(); i++) {
                            prib = SearchRegistryPribor.get(i);
                            prib.setIdObject(idObject);
                            dbPribor.addPribor(prib);
                        }

                        act.removeFragment(TagDeleteFrag);
                        act.removePopFragment();

                    } else {

                        toastDisplay(act.getString(R.string.nosave));
                    }
                } else {
                    toastDisplay(act.getString(R.string.curentstop));
                }
                break;
            case R.id.connect:
                if (taskSearch == true) {
                    SearchRegistryPribor.clear();
                    ((BaseAdapter) lvRegistryPribor.getAdapter()).notifyDataSetChanged();
                    op = new SearchPriborTask();
                    op.execute();

                } else {
                    op.cancel(true);
                }
                break;
        }
    }

    private void toastDisplay(String text) {
        Toast toast = Toast.makeText(getActivity(),
                text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    class SearchPriborTask extends AsyncTask<Void, Pribor, Void> {
        int start;
        int end;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            start = sAdr.getValue();
            end = eAdr.getValue();
            progressBar.setMax(end - start);

            ImageButton im = (ImageButton) rootmerc23.findViewById(R.id.connect);
            im.setImageResource(R.drawable.stop);
            taskSearch = false;
            //        act = (BaseConfigurator) getActivity();
            act.changeFragmennt = false;

        }


        @Override
        protected Void doInBackground(Void... params) {

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
            if (me.openport() == true) {
                for (progress = start; progress <= end; progress++) {
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
                            prib.setTypePribor(me.getName(progress, TypePribor)); // Cделать метод
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

//                    int tariff=0;
//                    try {
//                        tariff=me.getTarif(progress);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    prib.setTariff(tariff);

                        //    int gAdress;


                    }
                    publishProgress(prib);
                    if (isCancelled()) {
                        return null;
                    }
                }
            } else {
                prib = new Pribor();
                prib.setAddressPribor(254);

                prib.setTariff(200);
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
                progressBar.setProgress(progress - start);
            } else {
                if ((p.getTariff() == 200)) {
                    toastDisplay(act.getString(R.string.noport));
                } else {
                    progressBar.setProgress(progress - start);
                }

            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            BaseConfigurator act = (BaseConfigurator) getActivity();
            //   act.changeFragmennt = true;
            finish();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            finish();
        }

        private void finish() {
            //      act = (BaseConfigurator) getActivity();
            ImageButton im = (ImageButton) rootmerc23.findViewById(R.id.connect);
            im.setImageResource(R.drawable.find);
            act.changeFragmennt = true;
            taskSearch = true;
        }
    }
}

