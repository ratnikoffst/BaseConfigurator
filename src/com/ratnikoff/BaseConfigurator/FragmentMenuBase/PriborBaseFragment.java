package com.ratnikoff.BaseConfigurator.FragmentMenuBase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ListView;
import com.ratnikoff.BaseConfigurator.Base.DataBaseHelper;
import com.ratnikoff.BaseConfigurator.Base.Object;
import com.ratnikoff.BaseConfigurator.Base.Pribor;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseObject.ObjectAddEditFragment;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBasePribor.PriborListAdapter;
import com.ratnikoff.BaseConfigurator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SM on 02.03.2016.
 */
public class PriborBaseFragment extends Fragment {
    // private final static String OWNER_OBJECT = "OBJECT_FRAGMENT";
    private final static int TYPE_EDIT = 1;
    private final static int TYPE_DELETE = 2;
    private final static int TYPE_CANCEL = 3;
    private final static int TYPE_ADD = 4;
    private ArrayList<Pribor> RegistryPribor; // Список коллекции
    private ListView lvRegistryPribor;// ListView Коллекции
    private View rootpribor;//
    private DataBaseHelper dbPribor;// = new DataBaseHelper(getActivity());
    private int idObject;
    private String NameObject;
    private String NameOwner;
    // Переменные контекстного меню и кнопки +(FAB)
    private int CurrentItem = -1;
    //public java.lang.Object setNewItem;
    // private DataBaseHelper db;
    //     private LayoutInflater menuInflater;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootpribor = inflater.inflate(R.layout.priborfragment, container, false);
        lvRegistryPribor = (ListView) rootpribor.findViewById(R.id.listPribor);
        dbPribor = new DataBaseHelper(getActivity());

        // rootpribor.findViewById(R.id.objectFAB).setOnClickListener((View.OnClickListener) this);  // Регистрация FAB

        registerForContextMenu(rootpribor); // Регистрация контекстного меню

        idObject = getArguments().getInt("ID");
        NameObject = getArguments().getString("NameObject");


        NameOwner = getArguments().getString("NameOwner");

        // Здесь не находить ресурс
        //   TextView ButtonOwnerObject;
        Button ButtonOwnerObject = (Button) rootpribor.findViewById(R.id.TextNameOwnerObject);

//        Button NameOwnerObject = (Button) rootobject.findViewById(R.id.NameOwnerObject);
//        NameOwnerObject.setText(NameOwner);

        String s = "Заказчик: " + NameOwner + " Обьект: " + NameObject;
        ButtonOwnerObject.setText(s);

        CreateFillList();
        return rootpribor;
    }

    private void CreateFillList() {

        List<Pribor> list = dbPribor.getAllObjectPribor(idObject);
        RegistryPribor = new ArrayList<Pribor>();
        for (int i = 0; i < list.size(); i++) {
            Pribor v = list.get(i);

            RegistryPribor.add(v);
        }
        PriborListAdapter adapter = new PriborListAdapter(RegistryPribor, this);//ListAdapter(RegistryObject, this);
        lvRegistryPribor.setAdapter(adapter);
//        List<Object> list = dbObject.getAllObjectOwner(idOwner);
//        RegistryObject = new ArrayList<Object>();
//        for (int i = 0; i < list.size(); i++) {
//            Object v = new Object(list.get(i).getIdObject()
//                    , list.get(i).getIdOwwner()
//                    , list.get(i).getNAME_OBJECT()
//                    , list.get(i).getDOGOVOR_OBJECT()
//                    , list.get(i).getADDRESS_OBJECT()
//                    , list.get(i).getCOMMENT_OBJECT());
//            RegistryObject.add(v);
//        }
//
//        ObjectListAdapter adapter = new ObjectListAdapter(RegistryObject, this);
//        lvRegistryObject.setAdapter(adapter);
//        lvRegistryObject.setOnItemClickListener(this);
//        lvRegistryObject.setOnItemLongClickListener(this);
    }
//        public int idObject;// ID_OBJECT;// = "ID_OWNER_OBJECT";
//        public String TYPE_PRIBOR;// = "TYPE_PRIBOR";
//        public int NUMBER_PRIBOR;// = "NUMBER_PRIBOR"INTEGER;
//        public int ADRESS_PRIBOR;// = "ADRESS_PRIBOR"; integer
//        public int BAUD_PRIBOR;// = "BAUD_PRIBOR";//integer,
//        public int PASSWORD_USER;// = "PASSWORD_USER";// integer,
//        public int PASSWORD_ADMIN;// = "PASSWORD_ADMIN";// integer,
//        public int PU_PRIBOR;// = "PU_PRIBOR";// integer,
//        public int PI_PRIBOR;// = "PI_PRIBOR";// integer,
//        public int TYPE_CONNECT;// = "TYPE_CONNECT";// integer,
//        public String TCP_IP;// = "TCP_IP";// text,
//        public int PORT;//T = "PORT";//integer
//        private int idPribor;
//

//        lvRegistryObject.setOnItemClickListener(this);
//        lvRegistryObject.setOnItemLongClickListener(this);
//        registerForContextMenu(root2); // Регистрация контекстного меню
    //   }
//
//
//    @Override
//    public void onClick(View v) {
//        int i;
//        i = 1;
//        enterFAB(TYPE_ADD);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//        CurrentItem = position;
//        return false;
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, 101, Menu.NONE, R.string.edit);
        menu.add(Menu.NONE, 102, Menu.NONE, R.string.delete);
        menu.add(Menu.NONE, 103, Menu.NONE, R.string.Cancel);

    }

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

    public void enterFAB(int type) {
        ObjectAddEditFragment addObject;
        Bundle bundle;
        Object object;
        BaseConfigurator act = (BaseConfigurator) getActivity();

        switch (type) {
            case TYPE_EDIT:
//                addObject = new ObjectAddEditFragment();
//                object = (Object) lvRegistryObject.getAdapter().getItem(CurrentItem);
//                bundle = new Bundle();
//                bundle.putString("TYPE", "EditObject");
//                bundle.putInt("ID", object.getIdObject());
//                bundle.putInt("ID_OWNER", object.getIdOwwner());
//                bundle.putString("NameObject", object.getNAME_OBJECT());
//                bundle.putString("DogovorObject", object.getDOGOVOR_OBJECT());
//                bundle.putString("AddressObject", object.getADDRESS_OBJECT());
//                bundle.putString("CommentObject", object.getCOMMENT_OBJECT());
//                addObject.setArguments(bundle);
//                act.addFragment("EditObject", addObject, false);
                break;
            case TYPE_DELETE: // Отработано
//                object = (Object) lvRegistryObject.getAdapter().getItem(CurrentItem);
//                dbObject.removeObject(object.getIdObject());
//                RegistryObject.remove(CurrentItem);
//                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
//                CurrentItem = -1;
                break;
            case TYPE_CANCEL:
                break;
            case TYPE_ADD:
//                addObject = new ObjectAddEditFragment();
//                bundle = new Bundle();
//                bundle.putString("TYPE", "AddObject");
//                bundle.putInt("ID_OWNER",idOwner);
//                addObject.setArguments(bundle);
//                act.addFragment("AddObject", addObject, false);
//                CurrentItem = -1;
                break;
        }
    }

    /*
       * Первая переменная указывает редактирование=1 или добавление=2 !!!
       *
    */
//    public void setNewItemObject(int type, int id, int idOwner, String nameObject, String dogovor, String address, String comment) {
//        switch (type) {
//            case 1:
//                dbObject.editObject(id, idOwner, nameObject, dogovor, address, comment);
//                CreateFillList();//List();
//                lvRegistryObject.setSelection(CurrentItem - 2);
//                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
//                break;
//            case 2:
//                dbObject.addObject(idOwner, nameObject, dogovor, address, comment);//nameOwner, inn, address, commentObject);
//                CreateFillList();//List();
//                ((BaseAdapter) lvRegistryObject.getAdapter()).notifyDataSetChanged();
//                lvRegistryObject.setSelection(lvRegistryObject.getCount());
//                break;
//        }
//        CurrentItem = -1;
//
//    }
}
