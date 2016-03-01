package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ratnikoff.BaseConfigurator.Base.Owner;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.OwnerBaseFragment;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;

/**
 * Created by SM on 14.01.2016.
 * Класс являеться адаптером для коллекции базы обьектов
 */
public class OwnerListAdapter extends BaseAdapter {
    private ArrayList<Owner> owner;
    private OwnerBaseFragment c;

    public OwnerListAdapter(ArrayList<Owner> owner, OwnerBaseFragment c) {
        this.owner = owner;
        this.c = c;
    }

    @Override
    public int getCount() {
        return owner.size();
    }

    @Override
    public Object getItem(int position) {
        return owner.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c.getActivity()).inflate(R.layout.list_item_owner, null);

        }
        fillView(convertView, position);
        return convertView;
    }

    // Наполнение Вида позиции
    private void fillView(View convertView, int position) {
        final Owner ownerPosition = (Owner) getItem(position); // Обьект по позиции .

        TextView temp = (TextView) convertView.findViewById(R.id.ownerID); // Поиск соотвестmвующего объекта для заполнения
        temp.setText(String.valueOf(ownerPosition.getID())); // Установка имени ID заказчика

        temp = (TextView) convertView.findViewById(R.id.nameOwner); // Поиск соотвестmвующего объекта для заполнения
        temp.setText(ownerPosition.getName()); // Установка имени заказчика

        temp = (TextView) convertView.findViewById(R.id.InnOwner); // Поиск соотвестmвующего объекта для заполнения
        temp.setText(String.valueOf(ownerPosition.getInn())); // Установка ИНН заказчика

        temp = (TextView) convertView.findViewById(R.id.AdressOwner); // Поиск соотвесттвующего объекта для заполнения
        temp.setText(ownerPosition.getAddress()); // Установка Адреса заказчика

        temp = (TextView) convertView.findViewById(R.id.CommentOwner); // Поиск соотвестemвующего объекта для заполнения
        temp.setText(ownerPosition.getComment()); // Установка Комментрия заказчика
    }

}
