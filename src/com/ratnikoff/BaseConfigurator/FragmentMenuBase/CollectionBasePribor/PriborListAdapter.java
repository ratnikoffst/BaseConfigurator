package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBasePribor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ratnikoff.BaseConfigurator.Base.Pribor;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.PriborBaseFragment;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;

/**
 * Created by SM on 11.02.2016.
 */
public class PriborListAdapter extends BaseAdapter {
    private ArrayList<Pribor> Pribor;
    private PriborBaseFragment c;

    public PriborListAdapter(ArrayList<Pribor> pribor, PriborBaseFragment c) {
        this.Pribor = pribor;
        this.c = c;
    }


    @Override
    public int getCount() {
        return Pribor.size();
    }

    @Override
    public Pribor getItem(int position) {
        return Pribor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c.getActivity()).inflate(R.layout.list_pribor_item, null);

        }
        fillView(convertView, position);
        return convertView;
    }

    private void fillView(View convertView, int position) {
        final Pribor PribortPosition = getItem(position); // Обьект по позиции .

        TextView temp = (TextView) convertView.findViewById(R.id.typeNumber); // Поиск соотвестmвующего объекта для заполнения
        String s = PribortPosition.getTypePribor() + "    №" + PribortPosition.getNumberPribor();
        temp.setText(s); // Установка имени заказчика

        //temp = (TextView) convertView.findViewById(R.id.AdressObject); // Поиск соотвестmвующего объекта для заполнения
        //s = ObjectPosition.getIdOwner();
//        temp.setText(ObjectPosition.getADDRESS_OBJECT());

        //temp = (TextView) convertView.findViewById(R.id.DogObject); // Поиск соотвестmвующего объекта для заполнения
        //s = ObjectPosition.getIdOwner();
//        temp.setText(ObjectPosition.getCOMMENT_OBJECT());


    }
}
