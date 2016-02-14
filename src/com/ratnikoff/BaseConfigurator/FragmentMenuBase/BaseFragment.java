package com.ratnikoff.BaseConfigurator.FragmentMenuBase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseHigh.AddEditFrag;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseHigh.DataObject;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseHigh.ObjectListAdapter;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;


/**
 * Created by SM on 13.01.2016.
 */
public class BaseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ArrayList<DataObject> RegistryObject; // Список коллекции
    private ListView lvRegistryObject;// ListView Коллекции
    private View root;//
    private final static int TYPE_EDIT = 1;
    private final static int TYPE_DELETE = 2;
    private final static int TYPE_CANCEL = 3;
    private final static int TYPE_ADD = 4;
    private int CurrentItem = -1;
//    private LayoutInflater menuInflater;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.basefragment, container, false);
        lvRegistryObject = (ListView) root.findViewById(R.id.listObject);
        root.findViewById(R.id.AddButton).setOnClickListener(this);  // Регистрация FAB
        CreateObjectList();// Тестовое создание объектов
        fillListObject();  // Заполнение коллекции
        registerForContextMenu(root); // Регистрация контекстного меню
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


        enterFAB(TYPE_ADD);
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

    // Класс временный переделать под
    public ArrayList<DataObject> getArray() {
        return RegistryObject;
    }

    // Установка изменений после применения AddEdit

    /*
    * Первая переменная указывает редактирование или добавление !!!
    *
     */
    public void setNewDataItem(int operation, String nameInn, String nameObject, String nameAdress, String nameOwner, String commentObject) {
        switch (operation) {
            case 1:
                DataObject object = (DataObject) lvRegistryObject.getAdapter().getItem(CurrentItem);
                object.setInnObject(nameInn);
                object.setNameObject(nameObject);
                object.setAddressObject(nameAdress);
                object.setNameOwner(nameOwner);
                object.setCommentObject(commentObject);

                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
                break;
            case 2:
                DataObject object2 = new DataObject();//(DataObject) lvRegistryObject.getAdapter().getItem(CurrentItem);
                object2.setInnObject(nameInn);
                object2.setNameObject(nameObject);
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
        AddEditFrag addBase;
        Bundle bundle;
        DataObject object;
        BaseConfigurator act = (BaseConfigurator) getActivity();

        switch (type) {
            case TYPE_EDIT:
                addBase = new AddEditFrag();
                object = (DataObject) lvRegistryObject.getAdapter().getItem(CurrentItem);
                bundle = new Bundle();
                bundle.putString("TYPE", "EditObject");
                bundle.putString("AddressObject", object.getAddressObject());
                bundle.putString("InnObject", object.getInnObject());
                bundle.putString("CommentObject", object.getCommentObject());
                bundle.putString("NameObject", object.getNameObject());
                bundle.putString("NameOwner", object.getNameOwner());
                addBase.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .addToBackStack("Base")
                        .hide(getFragmentManager().findFragmentByTag("Base"))
                        .add(R.id.FragmentView, addBase, "EditObject")
                        .show(addBase)
                        .commit();
                //act = (BaseConfigurator) getActivity();
                act.addTagFragment("EditObject");
                break;
            case TYPE_DELETE:
                RegistryObject.remove(CurrentItem);
                //  RegistryObject.
                CurrentItem = -1;
                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
                break;
            case TYPE_CANCEL:
                break;
            case TYPE_ADD:
                addBase = new AddEditFrag();
                bundle = new Bundle();
                bundle.putString("TYPE", "AddObject");
                addBase.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .addToBackStack("Base")
                        .hide(getFragmentManager().findFragmentByTag("Base"))
                        .add(R.id.FragmentView, addBase, "AddObject")
                        .show(addBase)
                        .commit();
                act.addTagFragment("AddObject");
                break;
        }
        //getFragmentManager();
//        BaseConfigurator act = (BaseConfigurator) getActivity();
//        act.onAttachTestFragment();
        /// act.

    }
//    public interface FragmentListener {
//        public void onAttachTestFragment();
//    }
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        FragmentListener mListener;
//
//       act   mListener = (FragmentListener) activity;
//
//        mListener.onAttachTestFragment();
//    }
}
