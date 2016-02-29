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
    private ArrayList<OwnerData> RegistryOwner; // Список коллекции
    private ListView lvRegistryOwner;// ListView Коллекции
    private View root;//
    private final static int TYPE_EDIT = 1;
    private final static int TYPE_DELETE = 2;
    private final static int TYPE_CANCEL = 3;
    private final static int TYPE_ADD = 4;
    private final static String OWNER_OBJECT = "OBJECT_FRAGMENT";
    private int CurrentItem = -1;
    private DataBaseHelper db;//= new DataBaseHelper(getActivity());//=new DataBaseHelper(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ownerfragment, container, false);
        lvRegistryOwner = (ListView) root.findViewById(R.id.listOwner);
        root.findViewById(R.id.AddButton).setOnClickListener(this);  // Регистрация FAB
        db = new DataBaseHelper(getActivity());

        // CreateTestBase();
        CreateOwnerList();// Тестовое создание объектов
        return root;
    }

    // Временный класс для теста
    private void CreateTestBase() {

        int i;

        for (i = 1; i < 50; i++) {
            int inncreat = 772 * i * 15;

            db.addOwner("Заказчик " + i, inncreat, "Адрес" + i, "Комментарий " + i);
//
//            int j;
//            for (j = 1; j < 30; j++) {
//                db.addObject("Заказчик " + i, inncreat, "Адрес" + i, "Комментарий " + i);
//            }
        }
        List<Owner> ownerList = db.getAllOwner();
        for (int j = 0; j < ownerList.size(); j++) {
            /// OwnerData v = new OwnerData();

            ownerList.get(j).getID();

//            v.setIdOwner(String.valueOf());
//            v.setNameOwner(list.get(i).getName());
//            v.setAddressOwner(list.get(i).getAddress());
//            v.setCommentOwner(list.get(i).getComment());
//            v.setInnOwner(String.valueOf(list.get(i).getInn()));
//            RegistryOwner.add(v);
        }



    }

    // Заполнение адаптера основного вида
    private void CreateOwnerList() {

        List<Owner> list = db.getAllOwner();
        //  list.size();


        RegistryOwner = new ArrayList<OwnerData>();
        for (int i = 0; i < list.size(); i++) {
            OwnerData v = new OwnerData();
            v.setIdOwner(String.valueOf(list.get(i).getID()));
            v.setNameOwner(list.get(i).getName());
            v.setAddressOwner(list.get(i).getAddress());
            v.setCommentOwner(list.get(i).getComment());
            v.setInnOwner(String.valueOf(list.get(i).getInn()));
            RegistryOwner.add(v);
        }
        OwnerListAdapter adapter;
        adapter = new OwnerListAdapter(RegistryOwner, this);
        lvRegistryOwner.setAdapter(adapter);
        lvRegistryOwner.setOnItemClickListener(this);
        lvRegistryOwner.setOnItemLongClickListener(this);
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
        addBase = new ObjectBaseFragment();//AddEditFrag();
        object = (OwnerData) lvRegistryOwner.getAdapter().getItem(position);// Летить здесь !!!!
        bundle = new Bundle();
        // bundle.putString("TYPE", "EditOwner");
        bundle.putInt("ID", Integer.parseInt(object.getIdOwner()));
        bundle.putString("AddressOwner", object.getAddressOwner());
        bundle.putString("InnOwner", object.getInnOwner());
        bundle.putString("CommentOwner", object.getCommentOwner());
        bundle.putString("NameOwner", object.getNameOwner());
        addBase.setArguments(bundle);
        act.addFragment(OWNER_OBJECT, addBase, false);

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
                object = (OwnerData) lvRegistryOwner.getAdapter().getItem(CurrentItem);
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
                object = (OwnerData) lvRegistryOwner.getAdapter().getItem(CurrentItem);
                String id = object.getIdOwner();
                db.removeOwner(Integer.parseInt(id));
                RegistryOwner.remove(CurrentItem);

                CurrentItem = -1;
                ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
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
                CreateOwnerList();
                lvRegistryOwner.setSelection(id - 1);
                ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
                break;
            case 2:
                db.addOwner(nameOwner, Integer.valueOf(nameInn), nameAdress, commentObject);
                CreateOwnerList();
                ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
                lvRegistryOwner.setSelection(lvRegistryOwner.getCount());
                break;
        }
        CurrentItem = -1;
    }
}
