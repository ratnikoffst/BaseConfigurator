package com.ratnikoff.BaseConfigurator.FragmentMenuConfig;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.BaseSQLite.Pribor;
import com.ratnikoff.BaseConfigurator.FragmentMenuConfig.mercuryfragment.mercuryfragment200;
import com.ratnikoff.BaseConfigurator.FragmentMenuConfig.mercuryfragment.mercuryfragment23х;
import com.ratnikoff.BaseConfigurator.R;

/**
 * Created by SM on 17.02.2016.
 */
public class ConfigFragment extends Fragment implements View.OnClickListener {
    private final static String CONFIG_PRIBOR = "MERCURY23";
    private static final int MERCURY230 = 0;

    private final static String CONFIG_PRIBOR200 = "MERCURY200";
    private static final int MERCURY200 = 0;


    int idObject;
    String TypeFragment;
    String TagFragment;
    BaseConfigurator act;//
    private View root;
    //  private Boolean FLAG_OPEN_CLOSE_MERCURY = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.configfragment, container, false);
        //      root.setOnClickListener(this);

        TypeFragment = getArguments().getString("TYPE");
        TagFragment = getArguments().getString("TAG_CONFIGFRAGMENT");
        int i;
        i = 1;
        act = (BaseConfigurator) getActivity();


        switch (TypeFragment) {
            case "AutoSearch":

                idObject = getArguments().getInt("ID_OBJECT");
                TagFragment = getArguments().getString("TAG_CONFIGFRAGMENT");//tring("TAG_CONFIGFRAGMENT", "AddConfigPriborAuto");
                break;
            case "OneConnect":

                break;
//        bundle.putString("TYPE", "AutoSearch");
//        bundle.putInt("ID_OBJECT", idObject);
//        bundle.putString("TAG_CONFIGFRAGMENT","AddConfigPriborAuto");
//

            // NameOwner = getArguments().getString("NameOwner");


//        bundle.putString("TYPE", "AutoSearch");
//        bundle.putInt("ID_OWNER", idObject);
//        addObject.setArguments(bundle);
//        BaseConfigurator act = (BaseConfigurator) getActivity();
//        act.addFragment("AddObject", addObject, false);

        }
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
        Bundle bundle;
        Pribor pribor;
        //      BaseConfigurator act = (BaseConfigurator) getActivity();

        switch (v.getId()) {
            case R.id.mercury200:
                mercuryfragment200 addconfigmerc200 = new mercuryfragment200();
                bundle = new Bundle();
                switch (TypeFragment) {
                    case "AutoSearch":
                        bundle.putString("TYPE", "AutoSearch");
                        bundle.putString("TAG", TagFragment);
                        bundle.putInt("ID_OWNER", idObject);
                        addconfigmerc200.setArguments(bundle);
                        act.addFragment(CONFIG_PRIBOR, addconfigmerc200, false);
                        break;
                    case "OneConnect":


                        break;
                }
                break;
            case R.id.mercury230:
                //  act = (BaseConfigurator) getActivity();

                switch (TypeFragment) {
                    case "AutoSearch":
                        // fragment и одиночное !!!
                        mercuryfragment23х addconfigmerc = new mercuryfragment23х();
                        bundle = new Bundle();
                        bundle.putString("TYPE", "AutoSearch");
                        bundle.putString("TAG", TagFragment);
                        bundle.putInt("Pribor", MERCURY230);
                        bundle.putInt("ID_OWNER", idObject);
                        addconfigmerc.setArguments(bundle);
                        act.addFragment(CONFIG_PRIBOR, addconfigmerc, false);
                        break;
                    case "OneConnect":
                        break;
                }

                break;
            case R.id.mercury234:
                break;
        }
    }

    private void vibor200() {

    }

    private void vibor230() {

    }

    private void vibor2334() {

    }
}
