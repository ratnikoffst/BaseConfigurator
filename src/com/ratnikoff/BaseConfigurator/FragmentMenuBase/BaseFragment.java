package com.ratnikoff.BaseConfigurator.FragmentMenuBase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseHigh.DataObject;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseHigh.ObjectListAdapter;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;


/**
 * Created by SM on 13.01.2016.
 */
public class BaseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ArrayList<DataObject> RegistryObject; // Список коллекции
    private ListView lvRegistryObject;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.basefragment, container, false);
        lvRegistryObject = (ListView) root.findViewById(R.id.listObject);
        root.findViewById(R.id.AddButton).setOnClickListener(this);
        CreateObjectList();// Тестовое создание объектов
        fillListObject();  // Заполнение коллекции
        // root.setOnClickListener(this);
        return root;
    }

    private void fillListObject() {
        ObjectListAdapter adapter;
        adapter = new ObjectListAdapter(RegistryObject, this);
        lvRegistryObject.setAdapter(adapter);
        lvRegistryObject.setOnItemClickListener(this);
        lvRegistryObject.setOnItemLongClickListener(this);
    }

    // Заполнение адаптера основного вида здесь будет работа с базой SQlite
    private void CreateObjectList() {
        RegistryObject = new ArrayList<DataObject>();
        for (int i = 1; i < 30; i++) {
            DataObject v = new DataObject();
            v.setNameObject("Обьект №" + i);
            v.setNameOwner("Заказчик " + i);
            v.setAddressObject("Расположение " + i);
            v.setCommentObject("Комментарий " + i);
            v.setInnObject("" + i * 10203078);
            RegistryObject.add(v);

        }
    }

    // Слушатель кнопок
    @Override
    public void onClick(View v) {
        AddFrag addBase = new AddFrag();
        //Bundle p=;
        getFragmentManager().beginTransaction()
                .addToBackStack("Base")
                .hide(getFragmentManager().findFragmentByTag("Base"))
                .add(R.id.FragmentView, addBase, "AddObject")
                .show(addBase)
                .commit();
    }

    // Слушатель выбранных item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = 1;
        i++;
    }

    //Слушатель долгого нажатия item
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int i = 1;
        i++;
   //     DialogEditDelete();

        return false;
    }

    // Диалог изменения Item
//    private AlertDialog DialogEditDelete() {
//        AlertDialog.Builder df = new AlertDialog.Builder(getActivity());
//
//        df.setTitle("Ваши действия")
//                .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // The 'which' argument contains the index position
//                        // of the selected item
//                    }
//                });
//        return df.create();

  //  }
}