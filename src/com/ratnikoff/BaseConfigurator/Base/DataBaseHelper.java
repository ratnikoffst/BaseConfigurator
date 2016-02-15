package com.ratnikoff.BaseConfigurator.Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SM on 15.02.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "BASECONFIGURATOR";
    public static final String NAME_OWNER = "NAME_OWNER"; //Наименование заказчика
    public static final String NAME_OBJECT = "NAME_OBJECT"; //Наименование заказчика

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE + TABLE_NAME
//        Object _id;
//        (_id INTEGER PRIMARY KEY AUTOINCREMENT,
//        COLUMN_NAME_OWNER TEXT,
//        COLUMN_PHONE TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
