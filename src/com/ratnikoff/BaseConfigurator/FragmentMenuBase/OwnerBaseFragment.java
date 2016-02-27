package com.ratnikoff.BaseConfigurator.FragmentMenuBase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.ratnikoff.BaseConfigurator.Base.DataBaseHelper;
import com.ratnikoff.BaseConfigurator.Base.Owner;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerAddEditFrag;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerData;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerListAdapter;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;
import java.util.List;


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
    private DataBaseHelper db;//=new DataBaseHelper(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.basefragmentowner, container, false);
        lvRegistryObject = (ListView) root.findViewById(R.id.listObject);
        root.findViewById(R.id.AddButton).setOnClickListener(this);  // Регистрация FAB
        db = new DataBaseHelper(getActivity());

        // CreateTestBase();
        CreateObjectList();// Тестовое создание объектов
        return root;
    }

    private void CreateTestBase() {

        int i;

        for (i = 1; i < 50; i++) {
            int inncreat = 772 * i * 15;

            db.addOwner("Заказчик " + i, inncreat, "Адрес" + i, "Комментарий " + i);
        }

    }

    // Заполнение адаптера основного вида здесь будет работа  SQlite
    private void CreateObjectList() {

        List<Owner> list = db.getAllOwner();
        list.size();


        RegistryObject = new ArrayList<OwnerData>();
        for (int i = 0; i < list.size(); i++) {
            OwnerData v = new OwnerData();
            v.setIdOwner(String.valueOf(list.get(i).getID()));
            v.setNameOwner(list.get(i).getName());
            v.setAddressOwner(list.get(i).getAddress());
            v.setCommentOwner(list.get(i).getComment());
            v.setInnOwner(String.valueOf(list.get(i).getInn()));
            RegistryObject.add(v);
        }
        OwnerListAdapter adapter;
        adapter = new OwnerListAdapter(RegistryObject, this);
        lvRegistryObject.setAdapter(adapter);
        lvRegistryObject.setOnItemClickListener(this);
        lvRegistryObject.setOnItemLongClickListener(this);
        registerForContextMenu(root); // Регистрация контекстного меню
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
                bundle.putInt("ID", Integer.parseInt(object.getIdOwner()));
                bundle.putString("AddressOwner", object.getAddressOwner());
                bundle.putString("InnOwner", object.getInnOwner());
                bundle.putString("CommentOwner", object.getCommentOwner());
                bundle.putString("NameOwner", object.getNameOwner());
                addBase.setArguments(bundle);
                act.addFragment("EditOwner", addBase, false);
                break;
            case TYPE_DELETE:
                object = (OwnerData) lvRegistryObject.getAdapter().getItem(CurrentItem);
                String id = object.getIdOwner();
                db.removeOwner(Integer.parseInt(id));
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

    /*
    * Первая переменная указывает редактирование=1 или добавление=2 !!!
    *
     */
    public void setNewDataItem(int operation, int id, String nameInn, String nameAdress, String nameOwner, String commentObject) {
        switch (operation) {
            case 1:
                db.editOwner(id, nameOwner, Integer.valueOf(nameInn), nameAdress, commentObject);
                CreateObjectList();
                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
                break;
            case 2:
                db.addOwner(nameOwner, Integer.valueOf(nameInn), nameAdress, commentObject);
                CreateObjectList();
                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
                lvRegistryObject.setSelection(lvRegistryObject.getCount());
                break;
        }
        CurrentItem = -1;
    }
}
