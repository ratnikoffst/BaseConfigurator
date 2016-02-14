package com.ratnikoff.BaseConfigurator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.BaseFragment;
import com.ratnikoff.BaseConfigurator.FragmentMenuShop.ShopFragment;

import java.util.ArrayList;

public class BaseConfigurator extends Activity {
    private final static int Set_Base_Fragment = 1;        // Установлен fragment с базой данных
    private final static String Set_base_Tag = "Base";      // Тег Fragment базы данных
    private final static int Set_Config_Fragment = 2;      // Установлен fragment c конфигурацией
    private final static String Set_Config_Tag = "Config";  // Тег Fragment конфигуратор
    private final static int Set_Shop_Fragment = 3;        // Установлен fragment c магазином
    private final static String Set_Shop_Tag = "Shop";      // Тег Fragment Shop
    private final static int Set_HelpMy_Fragment = 4;      // Установлен fragment c помощью
    private final static String Set_HelpMy_Tag = "Help";  // Тег Fragment помощь

    private int current_fragment = 0; // Номер текущего fragment
    private ArrayList<String> fragmentTag = new ArrayList<String>();
    String currentTag = "";           // Текущий Тег fragment


    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Общий слушатель основных кнопок задан через layout
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.button_base:
                SetFragment(Set_Base_Fragment);
                break;
            case R.id.button_config:
                SetFragment(Set_Config_Fragment);
                break;
            case R.id.button_shop:
                SetFragment(Set_Shop_Fragment);
                break;
            case R.id.button_helpmy:
                SetFragment(Set_HelpMy_Fragment);
                break;
        }
    }

    // Установка fragment
    private void SetFragment(int set_fragment) {

        String stre = fragmentTag.get(fragmentTag.size());

        if (set_fragment != current_fragment) {
            if (currentTag != "") {
                getFragmentManager().popBackStackImmediate();
//                getFragmentManager().beginTransaction()
//                        .
//                        //.remove(getFragmentManager().findFragmentByTag(currentTag))
//                        .commit();
            }
            switch (set_fragment) {
                case Set_Base_Fragment:
                    SetLineBackground(R.color.red);
                    findViewById(R.id.button_base).setBackgroundResource(R.color.red);
                    currentTag = Set_base_Tag;
                    addFragment(Set_base_Tag, new BaseFragment, true);
                    break;
                case Set_Config_Fragment:
                    SetLineBackground(R.color.green);
                    findViewById(R.id.button_config).setBackgroundResource(R.color.green);
                    //addTagFragment();
                    currentTag = ""; //Set_Config_Tag
                    break;
                case Set_Shop_Fragment:
                    SetLineBackground(R.color.yellow);
                    findViewById(R.id.button_shop).setBackgroundResource(R.color.yellow);
                    currentTag = Set_Shop_Tag;
                    addFragment(Set_Shop_Tag, new ShopFragment(), true);
                    break;
                case Set_HelpMy_Fragment:
                    SetLineBackground(R.color.accent);
                    findViewById(R.id.button_helpmy).setBackgroundResource(R.color.accent);
                    currentTag = ""; //Set_HelpMy_Tag;
                    break;
            }
            setDrawableInUse(set_fragment);// Установка старого цвета и нового клавиши меню
        }
    }

    //Установка цвета вспомогательной линии
    private void SetLineBackground(int set_background_Line) {
        findViewById(R.id.line_base__color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_config_color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_shop_color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_helpmy_color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_free_color).setBackgroundResource(set_background_Line);
    }

    // Установка старого фона предыдущей используемой кнопки
    private void setDrawableInUse(int set_fragment) {
        switch (current_fragment) {
            case Set_Base_Fragment:
                findViewById(R.id.button_base).setBackgroundResource(R.drawable.drawable_gradient_base);
                break;
            case Set_Config_Fragment:
                findViewById(R.id.button_config).setBackgroundResource(R.drawable.drawable_gradient_config);
                break;
            case Set_Shop_Fragment:
                findViewById(R.id.button_shop).setBackgroundResource(R.drawable.drawable_gradient_shop);
                break;
            case Set_HelpMy_Fragment:
                findViewById(R.id.button_helpmy).setBackgroundResource(R.drawable.drawable_gradient_helpmy);
                break;
        }
        current_fragment = set_fragment;
    }

    // Перехват кнопки назад !!! C диалогом о желании выйти !!! Версия отработана !!!
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            DialogExit();
        } else {
            super.onBackPressed();
        }
    }

    // Диалог запроса подтверждения выхода из activity
    private void DialogExit() {
        AlertDialog.Builder df = new AlertDialog.Builder(this);
        df.setTitle("Вы хотите выйти ?");
        df.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        df.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }
        );
        df.setCancelable(true);
        df.show();
    }
// Методы управления fragment

    public void addFragment(String tag, Fragment frag, boolean delete) {

        int i;
        i = 1;
        getFragmentManager().beginTransaction()
                .add(R.id.FragmentView, CurrentFragment, Set_base_Tag)
                .commit();
        fragmentTag.add(tag);
    }

}
