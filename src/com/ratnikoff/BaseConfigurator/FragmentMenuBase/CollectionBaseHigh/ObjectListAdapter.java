package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseHigh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.BaseFragment;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;

/**
 * Created by SM on 14.01.2016.
 * Класс являеться адаптером для коллекции базы обьектов
 */
public class ObjectListAdapter extends BaseAdapter {
    private ArrayList<DataObject> objectOwner;
    private BaseFragment c;

    public ObjectListAdapter(ArrayList<DataObject> object, BaseFragment c) {
        this.objectOwner = object;
        this.c = c;
    }

    @Override
    public int getCount() {
        return objectOwner.size();
    }

    @Override
    public Object getItem(int position) {
        return objectOwner.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c.getActivity()).inflate(R.layout.listitem_object, null);

        }
        fillView(convertView, position);
        return convertView;
    }

    // Наполнение Вида позиции
    private void fillView(View convertView, int position) {
        final DataObject ObjectPosition = (DataObject) getItem(position); // Обьект по позиции .

//        TextView temp = (TextView) convertView.findViewById(R.id.NameObject); // Поиск соотвествующего объекта для заполнения
//        temp.setText(ObjectPosition.getNameObject()); // Установка Имени обьекта

        TextView temp = (TextView) convertView.findViewById(R.id.nameOwner); // Поиск соотвестmвующего объекта для заполнения
        temp.setText(ObjectPosition.getNameOwner()); // Установка имени заказчика

        temp = (TextView) convertView.findViewById(R.id.InnOwner); // Поиск соотвестmвующего объекта для заполнения
        temp.setText(ObjectPosition.getInnObject()); // Установка ИНН заказчика

        temp = (TextView) convertView.findViewById(R.id.AdressOwner); // Поиск соотвесттвующего объекта для заполнения
        temp.setText(ObjectPosition.getAddressObject()); // Установка Адреса заказчика

        temp = (TextView) convertView.findViewById(R.id.CommentOwner); // Поиск соотвестemвующего объекта для заполнения
        temp.setText(ObjectPosition.getCommentObject()); // Установка имени заказчика
    }

}
