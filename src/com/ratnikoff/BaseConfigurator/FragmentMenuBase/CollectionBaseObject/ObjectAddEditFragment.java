package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseObject;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.ratnikoff.BaseConfigurator.BaseConfigurator;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.ObjectBaseFragment;
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
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        EditText temp = (EditText) root.findViewById(R.id.ObjectNameEditAdd);

        String nameObject = String.valueOf(temp.getText());

        ///int Inn = Integer.parseInt(inn);

        imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);

        temp = (EditText) root.findViewById(R.id.ObjectDogovor);
        String objectDogovor = String.valueOf(temp.getText());
        imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);

        temp = (EditText) root.findViewById(R.id.ObjectAddress);
        String objectAdress = String.valueOf(temp.getText());
        imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);

        temp = (EditText) root.findViewById(R.id.ObjectComment);
        String CommentObject = String.valueOf(temp.getText());

        imm.hideSoftInputFromWindow(temp.getWindowToken(), 0);

        switch (v.getId()) {
            case R.id.ObjectAdd:
                ObjectBaseFragment b = (ObjectBaseFragment) getFragmentManager().findFragmentByTag("OBJECT_FRAGMENT");
                if (currentTagFragment == "AddObject") {
                    b.setNewItemObject(Current_Operation_Add,
                            0,
                            idOwner,
                            nameObject,
                            objectDogovor,
                            objectAdress,
                            CommentObject);
                    // b.setNewItemObject(Current_Operation_Add,);
                    // b.setNewDataItem(Current_Operation_Add, 0, NameOwner, Inn, NameAddress, CommentObject);//  добавления в Basefragment
                } else {
                    b.setNewItemObject(Current_Operation_Edit,
                            id,
                            idOwner,
                            nameObject,
                            objectDogovor,
                            objectAdress,
                            CommentObject);//(Current_Operation_Edit, id, NameOwner, Inn, NameAddress, CommentObject); // Установка нового значения
                }
                break;
        }
        BaseConfigurator act = (BaseConfigurator) getActivity();
        act.removePopFragment();


    }
}
