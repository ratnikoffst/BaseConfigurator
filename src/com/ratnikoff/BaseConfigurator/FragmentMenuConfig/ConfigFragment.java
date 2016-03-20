package com.ratnikoff.BaseConfigurator.FragmentMenuConfig;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import com.ratnikoff.BaseConfigurator.R;

/**
 * Created by SM on 17.02.2016.
 */
public class ConfigFragment extends Fragment implements View.OnClickListener {
    private View root;
    private Boolean FLAG_OPEN_CLOSE_MERCURY = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.configfragment, container, false);
        //       root.setOnClickListener(this);
        createFillTab();
        createSetOnclick();


        return root;
    }

    private void createSetOnclick() {
        root.findViewById(R.id.mercury200).setOnClickListener(this);
        root.findViewById(R.id.mercury202).setOnClickListener(this);
        root.findViewById(R.id.mercury230).setOnClickListener(this);
        root.findViewById(R.id.mercury234).setOnClickListener(this);
    }

    private void createFillTab() {
        TabHost tabs = (TabHost) root.findViewById(R.id.tabHost);

        tabs.setup();

        //   android:id="@+id/tabsLayout"

        View view = LayoutInflater.from(tabs.getContext()).inflate(R.layout.tab_mercury, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText("ИНКОТЕКС");

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.menuMercury); // R.layout.menu_mercuriy
        spec.setIndicator(view);
        tabs.addTab(spec);

        view = LayoutInflater.from(tabs.getContext()).inflate(R.layout.tab_grpz, null);
        tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText("ГРПЗ");

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.linearTab2);
        spec.setIndicator(view);
        tabs.addTab(spec);

        view = LayoutInflater.from(tabs.getContext()).inflate(R.layout.tab_nzif, null);
        tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText("НЗИФ");

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.linearTab33);
        spec.setIndicator(view);
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mercury200:
                break;
            case R.id.mercury230:
                break;
            case R.id.mercury234:
                break;
        }
    }
}
