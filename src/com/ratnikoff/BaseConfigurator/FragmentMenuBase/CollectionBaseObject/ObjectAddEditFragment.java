package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseObject;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.BaseSQLite.Object;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.ObjectBaseFragment;
import com.ratnikoff.BaseConfigurator.GPS.GpsAddress;
import com.ratnikoff.BaseConfigurator.R;

/**
 * Created by SM on 03.03.2016.
 */
public class ObjectAddEditFragment extends Fragment implements View.OnClickListener {
    private static final int Current_Operation_Edit = 1;
    private static final int Current_Operation_Add = 2;
    View root;
    private String currentTagFragment = "AddObject";
    private int id = -1;
    private int idOwner = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.objectaddedit, container, false);
        root.findViewById(R.id.ObjectAdd).setOnClickListener(this);
        root.findViewById(R.id.ObjectNo).setOnClickListener(this);

        root.findViewById(R.id.addressGps).setOnClickListener(this);

        idOwner = getArguments().getInt("ID_OWNER");

        currentTagFragment = getArguments().getString("TYPE"); // Присваиваен Tag fragment для
        if (currentTagFragment == "EditObject") {
            id = getArguments().getInt("ID");

            EditText temp = (EditText) root.findViewById(R.id.ObjectNameEditAdd);
            temp.setText(String.valueOf(getArguments().getString("NameObject")));

            temp = (EditText) root.findViewById(R.id.ObjectDogovor);
            temp.setText(getArguments().getString("DogovorObject"));

            temp = (EditText) root.findViewById(R.id.ObjectAddress);
            temp.setText(getArguments().getString("AddressObject"));

            temp = (EditText) root.findViewById(R.id.ObjectComment);
            temp.setText(getArguments().getString("CommentObject"));

            Button buttonTextOwner = (Button) root.findViewById(R.id.NameTextObject);
            buttonTextOwner.setText(R.string.EnterEditObject);
        }
        return root;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.ObjectAdd:
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                EditText temp = (EditText) root.findViewById(R.id.ObjectNameEditAdd);
                if (temp.getText().length() != 0) {
                    Object object = new Object();
                    object.setIdObject(id);
                    object.setIdOwwner(idOwner);
                    object.setNameObject(String.valueOf(temp.getText()));

                    imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);

                    temp = (EditText) root.findViewById(R.id.ObjectDogovor);
                    object.setDogovorObject(String.valueOf(temp.getText()));
                    imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);

                    temp = (EditText) root.findViewById(R.id.ObjectAddress);
                    object.setAddressObject(String.valueOf(temp.getText()));
                    imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);

                    temp = (EditText) root.findViewById(R.id.ObjectComment);
                    object.setCommentObject(String.valueOf(temp.getText()));

                    imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);
                    ObjectBaseFragment b = (ObjectBaseFragment) getFragmentManager().findFragmentByTag("OBJECT_FRAGMENT");
                    if (currentTagFragment == "AddObject") {
                        b.setNewItemObject(Current_Operation_Add, object);
                    } else {
                        b.setNewItemObject(Current_Operation_Edit, object);//(Current_Operation_Edit, id, NameOwner, Inn, NameAddress, CommentObject); // Установка нового значения
                    }
                    BaseConfigurator act = (BaseConfigurator) getActivity();
                    act.removePopFragment();
                } else {
                    temp.setError("Наименование Обьекта не може быть пустым !!!");
                    toastedit("Наименование Обьекта не може быть пустым !!!");
                }
                break;
            case R.id.ObjectNo:
                BaseConfigurator act = (BaseConfigurator) getActivity();
                act.removePopFragment();
                break;
            case R.id.addressGps:
                BaseConfigurator gpsact = (BaseConfigurator) getActivity();
                LocationManager lm = (LocationManager) gpsact.getSystemService(Context.LOCATION_SERVICE);

                //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, (LocationListener) this);
                GpsAddress g = new GpsAddress(lm);
                //gpsObject g2 = g.gpsLocation();


                int i;
                i = 1;
                break;
        }

    }

    private void toastedit(String text) {
        Toast toast = Toast.makeText(getActivity(),
                text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
