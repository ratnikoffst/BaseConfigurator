package com.ratnikoff.BaseConfigurator.FragmentHelpMy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ratnikoff.BaseConfigurator.R;

/**
 * Created by SM on 17.02.2016.
 */
public class HelpMyFragment extends Fragment {
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.helpmyfragment, container, false);
        return root;
    }

}
