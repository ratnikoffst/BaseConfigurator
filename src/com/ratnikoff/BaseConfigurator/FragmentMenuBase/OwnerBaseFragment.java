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

        // CreateTestBase();
        CreateOwnerList();// Тестовое создание объектов
        return root;
    }

    // Временный класс для теста
    private void CreateTestBase() {

        int i;

        for (i = 1; i < 50; i++) {
            int inncreat = 772 * i * 15;

            db.addOwner("Заказчик " + i, inncreat, "Адрес " + i, "Комментарий " + i);

        }
        List<Owner> ownerList = db.getAllOwner();
        for (int j = 0; j < ownerList.size(); j++) {

            for (int k = 0; k < 10; k++) {
                db.addObject(ownerList.get(j).getID(), "Подстанция " + j, " " + i * j, "улица " + i + " дом " + j, "Охуеть " + k);
            }

        //  ownerList.get(j).getID();
//            private int idObject;+++
//            private int idOwwner;// -ключ заказчика integer
//            private String NAME_OBJECT;// - Название Обьекта TEXT
//            private String DOGOVOR_OBJECT;// - договор по обьекту Integer
//            private String ADDRESS_OBJECT;// - адрес обьекта TEXT
//            private String COMMENT_OBJECT;// - Комментарий к обьекту TEXT
        }
    }

    // Заполнение адаптера основного вида
    private void CreateOwnerList() {

        List<Owner> list = db.getAllOwner();

        RegistryOwner = new ArrayList<Owner>();

        for (int i = 0; i < list.size(); i++) {
            Owner v = new Owner(list.get(i).getID(),
                    list.get(i).getName(),
                    list.get(i).getInn(),
                    list.get(i).getAddress(),
                    list.get(i).getComment());
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
        // bundle.putString("TYPE", "EditOwner");
        bundle.putInt("ID", owner.getID());
        //   bundle.putString("AddressOwner", owner.getAddress());
        //   bundle.putInt("InnOwner", owner.getInn());
        //    bundle.putString("CommentOwner", owner.getComment());
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
        OwnerAddEditFrag addBase;
        Bundle bundle;
        Owner owner;
        BaseConfigurator act = (BaseConfigurator) getActivity();

        switch (type) {
            case TYPE_EDIT:
                addBase = new OwnerAddEditFrag();
                owner = (Owner) lvRegistryOwner.getAdapter().getItem(CurrentItem);
                bundle = new Bundle();
                bundle.putString("TYPE", "EditOwner");
                bundle.putInt("ID", owner.getID());
                bundle.putString("AddressOwner", owner.getAddress());
                bundle.putInt("InnOwner", owner.getInn());
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
    public void setNewDataItem(int operation, int id, String nameOwner, int inn, String address, String commentObject) {
        switch (operation) {
            case 1:
                db.editOwner(id, nameOwner, inn, address, commentObject);
                CreateOwnerList();
                lvRegistryOwner.setSelection(id - 1);
                ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
                break;
            case 2:
                db.addOwner(nameOwner, inn, address, commentObject);
                CreateOwnerList();
                //       ((BaseAdapter) lvRegistryOwner.getAdapter()).notifyDataSetChanged();
                lvRegistryOwner.setSelection(lvRegistryOwner.getCount());
                break;
        }
        CurrentItem = -1;
    }
}
