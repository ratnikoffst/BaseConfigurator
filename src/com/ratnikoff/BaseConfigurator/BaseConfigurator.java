package com.ratnikoff.BaseConfigurator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import com.ratnikoff.BaseConfigurator.FragmentHelpMy.HelpMyFragment;
import com.ratnikoff.BaseConfigurator.FragmentMenuBase.OwnerBaseFragment;
import com.ratnikoff.BaseConfigurator.FragmentMenuConfig.ConfigFragment;
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
    public boolean changeFragmennt = true; // Флаг разрешения переключений !!!
    String currentTag = "";           // Текущий Тег fragment
    Drawable c; // Тестовая переменная !!!
    private int current_fragment = 0; // Номер текущего fragment
    private ArrayList<String> fragmentTag = new ArrayList<String>();

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        c = findViewById(R.id.line_free_color).getBackground();
        if (current_fragment != 0) {
            removeAllFragment();
        }
    }


    // Общий слушатель основных кнопок задан через layout
    public void OnClick(View view) {
        if (changeFragmennt == true) {
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
    }

    // Установка fragment
    private void SetFragment(int set_fragment) {
        if (set_fragment != current_fragment) {
            switch (set_fragment) {
                case Set_Base_Fragment:
                    setLineBackground(R.color.red);
                    findViewById(R.id.button_base).setBackgroundResource(R.color.red);
                    currentTag = Set_base_Tag;
                    addFragment(Set_base_Tag, new OwnerBaseFragment(), true);
                    break;
                case Set_Config_Fragment:
                    setLineBackground(R.color.green);
                    findViewById(R.id.button_config).setBackgroundResource(R.color.green);
                    //addTagFragment();
                    currentTag = Set_Config_Tag; //Set_Config_Tag
                    addFragment(Set_Config_Tag, new ConfigFragment(), true);
                    break;
                case Set_Shop_Fragment:
                    setLineBackground(R.color.yellow);
                    findViewById(R.id.button_shop).setBackgroundResource(R.color.yellow);
                    currentTag = Set_Shop_Tag;
                    addFragment(Set_Shop_Tag, new ShopFragment(), true);
                    break;
                case Set_HelpMy_Fragment:
                    setLineBackground(R.color.accent);
                    findViewById(R.id.button_helpmy).setBackgroundResource(R.color.accent);
                    currentTag = Set_HelpMy_Tag; //Set_HelpMy_Tag;
                    addFragment(Set_HelpMy_Tag, new HelpMyFragment(), true);
                    break;
            }
            setDrawableInUse(set_fragment);// Установка старого цвета и нового клавиши меню
        }
    }

    // Методы работы с боковым меню
    //Установка цвета вспомогательной линии
    private void setLineBackground(int set_background_Line) {
        findViewById(R.id.line_base__color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_config_color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_shop_color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_helpmy_color).setBackgroundResource(set_background_Line);
        findViewById(R.id.line_free_color).setBackgroundResource(set_background_Line);
    }

    // Восстановление исходного вида меню
    private void setDefaultMenu() {
        int i = fragmentTag.size() - 1;
        switch (fragmentTag.get(i)) {
            case Set_base_Tag:
                setDrawableInUse(Set_Base_Fragment);
                break;
            case Set_Config_Tag:
                setDrawableInUse(Set_Config_Fragment);
                break;
            case Set_Shop_Tag:
                setDrawableInUse(Set_Shop_Fragment);
                break;
            case Set_HelpMy_Tag:
                setDrawableInUse(Set_HelpMy_Fragment);
                break;
        }
        findViewById(R.id.line_base__color).setBackgroundResource(R.color.red);
        findViewById(R.id.line_config_color).setBackgroundResource(R.color.green);
        findViewById(R.id.line_shop_color).setBackgroundResource(R.color.yellow);
        findViewById(R.id.line_helpmy_color).setBackgroundResource(R.color.accent);
        findViewById(R.id.line_free_color).setBackgroundDrawable(c);
        current_fragment = 0;
    }

    // Установка старого фона предыдущей используемой кнопки меню
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
        int i = fragmentTag.size() - 1;
        if (i == -1) {
            DialogExit();
        } else {
            //      super.onBackPressed(); Оставил на всякий случай !!!
            if (i >= 1) {
                removePopFragment();
            } else {
                setDefaultMenu();
                removeCurrentFragment();///Fragment();
            }
        }
    }

    // Диалог запроса подтверждения выхода из activity
    private void DialogExit() {
        AlertDialog.Builder df = new AlertDialog.Builder(this);
        df.setTitle(R.string.exitapplication);
        df.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            // Тупая заглушк
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

    /* Классы для управления fragment
       * tag - тэг вновь устанавливаемого обьекта
       * frag - вновь устанавливаемый фрагмент
       * флаг уничтожение предыдущего fragment true - да, false - нет
       */
    public void addFragment(String tag, Fragment frag, boolean del) {
        int i;
        i = fragmentTag.size();
        if (del == true && i != 0) {
            removeAllFragment();
        }
        if (fragmentTag.size() != 0) {
            i--;
            String currents = fragmentTag.get(i);
            getFragmentManager().beginTransaction()
                    .addToBackStack(currents)
                    .hide(getFragmentManager().findFragmentByTag(currents))
                    .add(R.id.FragmentView, frag, tag)
                    .show(frag)
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .add(R.id.FragmentView, frag, tag)
                    .show(frag)
                    .commit();
        }
        fragmentTag.add(tag);
    }

    // Удаление  всех fragment
    public void removeAllFragment() {
        int i = fragmentTag.size() - 1;

        do {
            removeFragment(i);
            i--;
        } while (i != -1);
    }

    // Удаление последнего fragmenta
    public void removeCurrentFragment() {
        int i = fragmentTag.size() - 1;
        String tag = fragmentTag.get(i);
        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag(tag))
                .commit();
        fragmentTag.remove(i);
    }

    // Удаление fragmenta по номеру
    public void removeFragment(int i) {
        String tag = fragmentTag.get(i);
        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag(tag))
                .commit();
        fragmentTag.remove(i);
    }

    // Удаление по TAG
    public void removeFragment(String tag) {
        //String tag = fragmentTag.get(i);
        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag(tag))
                .commit();
        fragmentTag.remove(tag);
    }
    // удаление текущего со всплытием
    public void removePopFragment() {
        int i = fragmentTag.size() - 1;
        String tag = fragmentTag.get(i);
        fragmentTag.remove(i);
        i--;
        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag(tag))
                .show(getFragmentManager().findFragmentByTag(fragmentTag.get(i)))
                .commit();
    }

//    public void notifyViewFragment() {
//        String tag = fragmentTag.get(fragmentTag.size());
//        //   getFragmentManager().findFragmentByTag(tag).notify();
//
//    }
}