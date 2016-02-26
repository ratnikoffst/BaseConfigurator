package com.ratnikoff.BaseConfigurator.FragmentMenuBase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.ratnikoff.BaseConfigurator.Base.DataBaseHelper;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerAddEditFrag;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerData;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerListAdapter;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;


/**
 * Created by SM on 13.01.2016.
 */
public class OwnerBaseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ArrayList<OwnerData> RegistryObject; // Список коллекции
    private ListView lvRegistryObject;// ListView Коллекции
    private View root;//
    private final static int TYPE_EDIT = 1;
    private final static int TYPE_DELETE = 2;
    private final static int TYPE_CANCEL = 3;
    private final static int TYPE_ADD = 4;
    private int CurrentItem = -1;
    private DataBaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.basefragmentowner, container, false);
        lvRegistryObject = (ListView) root.findViewById(R.id.listObject);
        root.findViewById(R.id.AddButton).setOnClickListener(this);  // Регистрация FAB
        CreateObjectList();// Тестовое создание объектов
        fillListObject();  // Заполнение коллекции
        registerForContextMenu(root); // Регистрация контекстного меню

        return root;
    }

    private void fillListObject() {
        OwnerListAdapter adapter;
        adapter = new OwnerListAdapter(RegistryObject, this);
        lvRegistryObject.setAdapter(adapter);
        lvRegistryObject.setOnItemClickListener(this);
        lvRegistryObject.setOnItemLongClickListener(this);
    }

    // Заполнение адаптера основного вида здесь будет работа с базой SQlite
    private void CreateObjectList() {
        RegistryObject = new ArrayList<OwnerData>();
        for (int i = 1; i < 30; i++) {
            OwnerData v = new OwnerData();

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
        enterFAB(TYPE_ADD);
    }


    // Слушатель выбранных item переход на список обьектов
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ObjectBaseFragment addBase;
        Bundle bundle;
        OwnerData object;
        BaseConfigurator act = (BaseConfigurator) getActivity();

        //  addBase = new OwnerAddEditFrag();
//        object = (OwnerData) lvRegistryObject.getAdapter().getItem(CurrentItem);
//        bundle = new Bundle();
//        bundle.putString("TYPE", "EditOwner");
//        bundle.putString("AddressOwner", object.getAddressObject());
//        bundle.putString("InnOwner", object.getInnObject());
//        bundle.putString("CommentOwner", object.getCommentObject());
//        bundle.putString("NameOwner", object.getNameOwner());
//        addBase.setArguments(bundle);
//        act.addFragment("EditOwner", addBase, false);




    }

    //Слушатель долгого нажатия item
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CurrentItem = position;
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, 101, Menu.NONE, R.string.edit);
        menu.add(Menu.NONE, 102, Menu.NONE, R.string.delete);
        menu.add(Menu.NONE, 103, Menu.NONE, R.string.Cancel);

    }

    // Прослушивание контекстного меню редактировать удалить отмена
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 101:
                enterFAB(TYPE_EDIT);
                break;
            case 102:
                enterFAB(TYPE_DELETE);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    // Установка изменений после применения AddEdit

    /*
    * Первая переменная указывает редактирование или добавление !!!
    *
     */
    public void setNewDataItem(int operation, String nameInn, String nameAdress, String nameOwner, String commentObject) {
        switch (operation) {
            case 1:
                OwnerData object = (OwnerData) lvRegistryObject.getAdapter().getItem(CurrentItem);
                object.setInnObject(nameInn);
                object.setAddressObject(nameAdress);
                object.setNameOwner(nameOwner);
                object.setCommentObject(commentObject);
                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
                break;
            case 2:
                OwnerData object2 = new OwnerData();
                object2.setInnObject(nameInn);
                object2.setAddressObject(nameAdress);
                object2.setNameOwner(nameOwner);
                object2.setCommentObject(commentObject);
                RegistryObject.add(object2);
                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
                lvRegistryObject.setSelection(lvRegistryObject.getCount());
                break;
        }
        CurrentItem = -1;
    }

    public void enterFAB(int type) {
        OwnerAddEditFrag addBase;
        Bundle bundle;
        OwnerData object;
        BaseConfigurator act = (BaseConfigurator) getActivity();

        switch (type) {
            case TYPE_EDIT:
                addBase = new OwnerAddEditFrag();
                object = (OwnerData) lvRegistryObject.getAdapter().getItem(CurrentItem);
                bundle = new Bundle();
                bundle.putString("TYPE", "EditOwner");
                bundle.putString("AddressOwner", object.getAddressObject());
                bundle.putString("InnOwner", object.getInnObject());
                bundle.putString("CommentOwner", object.getCommentObject());
                bundle.putString("NameOwner", object.getNameOwner());
                addBase.setArguments(bundle);
                act.addFragment("EditOwner", addBase, false);
                break;
            case TYPE_DELETE:
                RegistryObject.remove(CurrentItem);

                CurrentItem = -1;
                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
                break;
            case TYPE_CANCEL:
                break;
            case TYPE_ADD:
                addBase = new OwnerAddEditFrag();
                bundle = new Bundle();
                bundle.putString("TYPE", "AddOwner");
                addBase.setArguments(bundle);
                act.addFragment("AddOwner", addBase, false);
                break;
        }
    }
}
