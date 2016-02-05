package com.ratnikoff.BaseConfigurator.FragmentMenuBase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ratnikoff.BaseConfigurator.R;

/**
 * Created by SM on 27.01.2016.
 */
public class AddFrag extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addandeditobject, container, false);
        root.findViewById(R.id.innSearch).setOnClickListener(this);
        root.findViewById(R.id.yesAdd).setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag("AddObject"))
                .show(getFragmentManager().findFragmentByTag("Base"))
                .commit();
    }
}
