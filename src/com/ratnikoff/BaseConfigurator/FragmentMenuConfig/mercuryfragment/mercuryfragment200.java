package com.ratnikoff.BaseConfigurator.FragmentMenuConfig.mercuryfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
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
import com.ratnikoff.BaseConfigurator.UsbDriver.Mercury200;

import java.util.ArrayList;


/**
 * Created by SM on 21.03.2016.
 */
public class mercuryfragment200 extends Fragment implements View.OnClickListener {
    ListView lvRegistryPribor;
    int progress;


    //private View rootpribor;
    Mercury200 me;
    ProgressBar progressBar;
    String TagDeleteFrag;
    int TypePribor;
    int idObject;
    String type;
    private NumberPicker sAdr;
    private NumberPicker eAdr;
    private ArrayList<Pribor> SearchRegistryPribor;
    private View rootmerc200;

    private boolean naladchik = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootmerc200 = inflater.inflate(R.layout.mercuriy200, container, false);

        rootmerc200.findViewById(R.id.connect).setOnClickListener(this);
        rootmerc200.findViewById(R.id.savePribor).setOnClickListener(this);

        lvRegistryPribor = (ListView) rootmerc200.findViewById(R.id.AddSearch);

        type = getArguments().getString("TYPE");
        TagDeleteFrag = getArguments().getString("TAG");
        TypePribor = getArguments().getInt("Pribor");
        idObject = getArguments().getInt("ID_OWNER");


        CreateFillList();

        progressBar = (ProgressBar) rootmerc200.findViewById(R.id.progressBar);
        /// progressBar.setMax(240);

        sAdr = (NumberPicker) rootmerc200.findViewById(R.id.startAddress);
        sAdr.setMinValue(1);
        sAdr.setMaxValue(1000);
        sAdr.setValue(1);

        eAdr = (NumberPicker) rootmerc200.findViewById(R.id.endAddress);
        eAdr.setMinValue(1);
        eAdr.setMaxValue(1000);
        eAdr.setValue(40);//etDisplayedValues();
        Switch s = (Switch) rootmerc200.findViewById(R.id.switch1);

        s.setOnClickListener(this);

        return rootmerc200;
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
        BaseConfigurator act;

        switch (v.getId()) {
            case R.id.savePribor:
                if (SearchRegistryPribor.size() != 0) {
                    DataBaseHelper dbPribor = new DataBaseHelper(getActivity());
                    Pribor prib;
                    for (int i = 0; i < SearchRegistryPribor.size(); i++) {
                        prib = SearchRegistryPribor.get(i);
                        prib.setIdObject(idObject);
                        dbPribor.addPribor(prib);
                    }
                    act = (BaseConfigurator) getActivity();
                    act.removeFragment(TagDeleteFrag);
                    act.removePopFragment();
                } else {
                    Toast toast = Toast.makeText(getActivity(),
                            "Добавлять нечего !!!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
            case R.id.connect:
                SearchRegistryPribor.clear();
                ((BaseAdapter) lvRegistryPribor.getAdapter()).notifyDataSetChanged();
                act = (BaseConfigurator) getActivity();
                act.changeFragmennt = false;
                // Вставить проверку наличие порта
                UsbManager manager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
                FragmentManager fm = getActivity().getFragmentManager();


                Mercury200 mercury200 = null;
                try {
                    mercury200 = new Mercury200(manager, getActivity(), fm);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean i = mercury200.openport();
                if (i == true) {
                    searchPriborTask op = new searchPriborTask();
                    op.execute();
                } else {
                    Toast toast = Toast.makeText(getActivity(),
                            R.string.nocurrentusb, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.switch1:
                naladchik = naladchik != true;
                break;
            case R.id.writenumber:

                break;
        }
    }

    // Поток поиска приборов
    class searchPriborTask extends AsyncTask<Void, Pribor, Void> {
        int start;
        int end;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            start = sAdr.getValue();
            end = eAdr.getValue();
            progressBar.setMax(end - start);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            BaseConfigurator act = (BaseConfigurator) getActivity();
            act.changeFragmennt = true;
        }

        @Override
        protected Void doInBackground(Void... params) {

            byte[] write;
            byte[] read;
            Pribor prib;

            UsbManager manager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
            FragmentManager fm = getActivity().getFragmentManager();

            Mercury200 mercury200 = null;
            try {
                mercury200 = new Mercury200(manager, getActivity(), fm);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mercury200.openport();

            // Сделать задание наладчика++ отработать start и end;
            int i = 1;
            if (naladchik == true) {
                i = end - start;
                String s = "4194304011";
                i = Integer.parseInt(s);
                end = start + (i * 8);
                i = 8;
            }

            for (progress = start; progress <= end; progress = progress + i) {
                write = Mercury200.WRITE_VER_NUMBER;
                read = Mercury200.READ_VER_NUMBER;


                write = mercury200.getArrayAdress(write, progress);

                prib = new Pribor();
                prib.setAddressPribor(0);

                try {
                    read = mercury200.getReadSearch(write, read);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((write[0] == read[0]) &&
                        (write[1] == read[1]) &&
                        (write[2] == read[2]) &&
                        (write[3] == read[3]) &&
                        (write[4] == read[4])) {

                    prib.setAddressPribor(progress);
                    try {
                        prib.setNumberPribor(mercury200.getNumberPribor(progress));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        prib.setTariff(mercury200.getReadTarif(progress));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        prib.setGaddressPribor(mercury200.getGaddress(progress));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    prib.setBaudPribor(9600);
                    prib.setIdObject(idObject);
                    prib.setTypePribor("Меркурий 200");
                }
                publishProgress(prib);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Pribor... pribors) {
            super.onProgressUpdate(pribors);
            Pribor p = pribors[0];
            if (p.getAddressPribor() != 0) {
                SearchRegistryPribor.add(p);
                ((BaseAdapter) lvRegistryPribor.getAdapter()).notifyDataSetChanged();
                progressBar.setProgress(progress - start);
            } else {
                progressBar.setProgress(progress - start);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    // класс замены адресов наладчика плюс на номера квартир
    class reverseNaladchikTaskextends extends AsyncTask<Void, Pribor, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Pribor... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
