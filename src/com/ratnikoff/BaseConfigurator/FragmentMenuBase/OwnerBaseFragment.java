package com.ratnikoff.BaseConfigurator.FragmentMenuBase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.BaseSQLite.DataBaseHelper;
import com.ratnikoff.BaseConfigurator.BaseSQLite.Object;
import com.ratnikoff.BaseConfigurator.BaseSQLite.Owner;
import com.ratnikoff.BaseConfigurator.BaseSQLite.Pribor;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerAddEditFragment;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner.OwnerListAdapter;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SM on 13.01.2016.
 */
public class OwnerBaseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private final static int TYPE_EDIT = 1;
    private final static int TYPE_DELETE = 2;
    private final static int TYPE_CANCEL = 3;
    private final static int TYPE_ADD = 4;
    private final static String OWNER_OBJECT = "OBJECT_FRAGMENT";
    private ArrayList<Owner> RegistryOwner; // Список коллекции
    private ListView lvRegistryOwner;// ListView Коллекции
    private View root;//
    private int CurrentItem = -1;
    private DataBaseHelper db;//= new DataBaseHelper(getActivity());//=new DataBaseHelper(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ownerfragment, container, false);
        lvRegistryOwner = (ListView) root.findViewById(R.id.listOwner);
        root.findViewById(R.id.AddButton).setOnClickListener(this);  // Регистрация FAB

        db = new DataBaseHelper(getActivity());

        //      CreateTestBase();

        //      TestBase();

        CreateOwnerList();// Тестовое создание объектов

        return root;
    }

    private void TestBase() {
        List<Owner> ownerList = db.getAllOwner();
        for (int j = 0; j < ownerList.size(); j++) {

            List<Object> objectList = db.getAllObjectOwner(ownerList.get(j).getID());

            for (int i = 0; i < objectList.size(); i++) {
                List<Pribor> pri = db.getAllObjectPribor(objectList.get(j).getIdObject());
            }
        }
    }

    // Временный класс для теста
    private void CreateTestBase() {
        int i;
        for (i = 1; i < 10; i++) {
            int inncreat = 772 * i * 15;
            db.addOwner("Заказчик " + i, "" + inncreat, "Адрес " + i, "Комментарий " + i);
        }
        List<Owner> ownerList = db.getAllOwner();
        for (int j = 0; j < ownerList.size(); j++) {
            for (int k = 0; k < 10; k++) {
                db.addObject(ownerList.get(j).getID(), "Подстанция " + k, " " + i * j, "улица " + i + " дом " + j, "Охуеть " + i * 10 + j);
            }
        }
//
        List<Owner> ownerList1 = db.getAllOwner();
        for (int j = 0; j < ownerList1.size(); j++) {
            List<Object> objectList = db.getAllObjectOwner(ownerList1.get(j).getID());
            for (int a = 0; a < objectList.size(); a++) {
                for (i = 0; i < 10; i++) {
                    db.addPribor(objectList.get(a).getIdObject(), "Меркурий 200.02", a * a * i, a * a * a * i);
                }
            }
        }

        i = 1;
    }

    // Заполнение адаптера основного вида
    private void CreateOwnerList() {

        List<Owner> list = db.getAllOwner();

        RegistryOwner = new ArrayList<Owner>();

        for (int i = 0; i < list.size(); i++) {
            Owner v = list.get(i);
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
        Owner owner;

        BaseConfigurator act = (BaseConfigurator) getActivity();
        addBase = new ObjectBaseFragment();//AddEditFrag();
        owner = (Owner) lvRegistryOwner.getAdapter().getItem(position);// Летить здесь !!!!
        bundle = new Bundle();
        bundle.putInt("ID", owner.getID());
        bundle.putString("NameOwner", owner.getName());
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
        OwnerAddEditFragment addBase;
        Bundle bundle;
        Owner owner;
        BaseConfigurator act = (BaseConfigurator) getActivity();

        switch (type) {
            case TYPE_EDIT:
                addBase = new OwnerAddEditFragment();
                owner = (Owner) lvRegistryOwner.getAdapter().getItem(CurrentItem);
                bundle = new Bundle();
                bundle.putString("TYPE", "EditOwner");
                bundle.putInt("ID", owner.getID());
                bundle.putString("AddressOwner", owner.getAddress());
                bundle.putString("InnOwner", owner.getInn());
                bundle.putString("CommentOwner", owner.getComment());
                bundle.putString("NameOwner", owner.getName());
                addBase.setArguments(bundle);
                act.addFragment("EditOwner", addBase, false);
                break;
            case TYPE_DELETE:
                owner = (Owner) lvRegistryOwner.getAdapter().getItem(CurrentItem);
                db.removeOwner(owner.getID());
                RegistryOwner.remove(CurrentItem);

                CurrentItem = -1;
                ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
                break;
            case TYPE_CANCEL:
                break;
            case TYPE_ADD:
                addBase = new OwnerAddEditFragment();
                bundle = new Bundle();
                bundle.putString("TYPE", "AddOwner");
                addBase.setArguments(bundle);
                act.addFragment("AddOwner", addBase, false);
                break;
        }
    }

    /*
    * Первая переменная указывает редактирование=1 или добавление=2 !!!
    * Переделать класс
     */
    public void setNewDataItem(int operation, Owner owner) {
        switch (operation) {
            case 1:
                db.editOwner(owner.getID(), owner.getName(), owner.getInn(), owner.getAddress(), owner.getComment());
                CreateOwnerList();
                lvRegistryOwner.setSelection(CurrentItem - 2);
                ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
                break;
            case 2:
                db.addOwner(owner.getName(), owner.getInn(), owner.getAddress(), owner.getComment());
                CreateOwnerList();
                ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
                lvRegistryOwner.setSelection(lvRegistryOwner.getCount());
                break;
        }
        CurrentItem = -1;
    }


}
