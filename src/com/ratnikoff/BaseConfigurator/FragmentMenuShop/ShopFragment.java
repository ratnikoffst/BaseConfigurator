package com.ratnikoff.BaseConfigurator.FragmentMenuShop;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ratnikoff.BaseConfigurator.R;

/**
 * Created by SM on 29.01.2016.
 */
public class ShopFragment extends Fragment {
    private View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.shop,container,false);
        return root;
    }
}
