package com.ratnikoff.BaseConfigurator.FragmentMenuConfig;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
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

        // root.findViewById(R.id.mercury).setOnClickListener(this);

        return root;
    }

    private void createFillTab() {
        TabHost tabs = (TabHost) root.findViewById(R.id.tabHost);

        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");

        spec.setContent(R.id.inkot);
        spec.setIndicator("ИНКОТЕКС");
        tabs.addTab(spec);

//        spec.setContent(R.id.inkotex);
//        spec.setIndicator("Инкотекс");
//        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.grpz);
        spec.setIndicator("ГРПЗ");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.test);
        spec.setIndicator("МЗИП");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.mercury:
//                LinearLayout l = (LinearLayout) root.findViewById(R.id.mercuryButton);
//                if (FLAG_OPEN_CLOSE_MERCURY == false) {
//                   // LinearLayout l = (LinearLayout) root.findViewById(R.id.mercuryButton);
//                    for (int i = 0; i < 5; i++) {
//
//                        Button b = new Button(this.getActivity());
//                        b.setText("" + i);
//                        l.addView(b);
//                        FLAG_OPEN_CLOSE_MERCURY=true;
//                    }
//                } else
//                {
//
//                    l.removeAllViews();
//                    FLAG_OPEN_CLOSE_MERCURY=false;
//                }
////
//                LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View rootView= layoutInflater.inflate(R.layout.main_activity_progress_element, this);
//
//                ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar_friend);
//                progressBar.setMax(100);
//                progressBar.setProgress(55);
//l.addView(mercurylinear);
//                Inflater inflater = null;
//                if (inflater != null) {
//                    root=inflater.inflate(mercurylinear,root.findViewById(R.id.mercuryButton),false);
//                }
//
//                LinearLayout l2 = (LinearLayout) getResources().getLayout(mercurylinear);
//                ///l.(l2);
        /// Button b=Button(this);

        //l=inflater.inflate(R.layout.configfragment, container, false);;//addView();

//                break;
//        }


    }
}
