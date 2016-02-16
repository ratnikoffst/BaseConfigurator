package com.ratnikoff.BaseConfigurator.Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SM on 15.02.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String BASE_NAME = "test.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_OWNER = "BASE_OWNER"; // Таблица базы заказчика
    public static final String NAME_OWNER = "NAME_OWNER"; //Наименование заказчика
    public static final String INN_OWNER = "INN_OWNER"; //ИНН заказчика
    public static final String ADRESS_OWNER = "ADRESS_OWNER"; //Адрес заказчика
    public static final String COMMENT_OWNER = "COMMENT_OWNER"; //Комментарий о  заказчике


    public static final String TABLE_OBJECT = "BASE_OBJECT"; // Таблица обьектов заказчика
    public static final String ID_OWNER = "ID_OWNER"; // ID заказчика для связи с первой таблицей
    public static final String ID_OWNER_OBJECT = "ID_OWNER_OBJECT"; // ID OWNER  OBJECT ключ для связи с третьей таблицой
    public static final String NAME_OBJECT = "NAME_OWNER"; //ИНН заказчика
    public static final String ADRESS_OBJECT = "ADRESS_OBJECT"; //Адрес заказчика
    public static final String COMMENT_OBJECT = "COMMENT_OBJECT"; //Комментарий о  заказчике

    public static final String TABLE_PRIBOR = "BASE_PRIBOR";

    public static final String ID_OWNEROBJECT = "ID_OWNER_OBJECT";
    public static final String TYPE_PRIBOR = "TYPE_PRIBOR";
    public static final String NUMBER_PRIBOR = "NUMBER_PRIBOR";
    public static final String ADRESS_PRIBOR = "ADRESS_PRIBOR";
    public static final String BAUD_PRIBOR = "BAUD_PRIBOR";//жinteger,
    public static final String PASSWORD_USER = "PASSWORD_USER";// integer,
    public static final String PASSWORD_ADMIN = "PASSWORD_ADMIN";// blob,
    public static final String PU_PRIBOR = "PU_PRIBOR";// integer,
    public static final String PI_PRIBOR = "PI_PRIBOR";// integer,
    public static final String TYPE_CONNECT = "TYPE_CONNECT";// integer,
    public static final String TCP_IP = "TCP_IP";// text,
    public static final String PORT = "PORT";//integer

    public DataBaseHelper(Context context) {

        super(context, BASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE +BASE_OWNER Object _id;" +
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME_OWNER TEXT," +
                "INN_OWNER INTEGER," +
                "ADRESS_OWNER TEXT," +
                "COMMENT_OWNER TEXT);");

        db.execSQL("CREATE TABLE BASE_OBJECT (" +
                "_ID integer," +
                "ID_OWNER integer," +
                "ID_OWNER_OBJECT integer," +
                "DOGOVOR_OBJECT integer," +
                "ADRESS_OBJECT text," +
                "COMMENT_OBJECT text," +
                "NAME_OBJECT text);");

        db.execSQL("CREATE TABLE BASE_PRIBOR " +
                "(_ID integer," +
                "ID_OWNER_OBJECT integer," +
                "TYPE_PRIBOR text," +
                "NUMBER_PRIBOR integer," +
                "ADRESS_PRIBOR integer," +
                "BAUD_PRIBOR integer," +
                "PASSWORD_USER integer," +
                "PASSWORD_ADMIN integer," +
                "PU_PRIBOR integer," +
                "PI_PRIBOR integer," +
                "TYPE_CONNECT integer," +
                "TCP_IP text," +
                "PORT integer);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
