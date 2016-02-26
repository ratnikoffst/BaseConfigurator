package com.ratnikoff.BaseConfigurator.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SM on 15.02.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String BASE_NAME = "test.db";
    public static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "_id";

    public static final String TABLE_OWNER = "BASE_OWNER"; // Таблица базы заказчика
    public static final String NAME_OWNER = "NAME_OWNER"; //Наименование заказчика
    public static final String INN_OWNER = "INN_OWNER"; //ИНН заказчика
    public static final String ADDRESS_OWNER = "ADDRESS_OWNER"; //Адрес заказчика
    public static final String COMMENT_OWNER = "COMMENT_OWNER"; //Комментарий о  заказчике


    public static final String TABLE_OBJECT = "BASE_OBJECT"; // Таблица обьектов заказчика
    public static final String ID_OWNER = "ID_OWNER"; // ID заказчика для связи с первой таблицей
    public static final String ID_OWNER_OBJECT = "ID_OBJECT"; // ID OWNER  OBJECT ключ для связи с третьей таблицой
    public static final String DOGOVOR_OBJECT = "DOGOVOR_OBJECT"; //Номер договора со сбытом
    public static final String NAME_OBJECT = "NAME_OWNER"; //ИНН заказчика
    public static final String ADDRESS_OBJECT = "ADDRESS_OBJECT"; //Адрес заказчика
    public static final String COMMENT_OBJECT = "COMMENT_OBJECT"; //Комментарий о  заказчике

    public static final String TABLE_PRIBOR = "BASE_PRIBOR";

    public static final String ID_OWNEROBJECT = "ID_OWNER_OBJECT";
    public static final String TYPE_PRIBOR = "TYPE_PRIBOR";
    public static final String NUMBER_PRIBOR = "NUMBER_PRIBOR";
    public static final String ADRESS_PRIBOR = "ADRESS_PRIBOR";
    public static final String BAUD_PRIBOR = "BAUD_PRIBOR";//integer,
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

        db.execSQL("CREATE TABLE " + TABLE_OWNER + "(" + KEY_ID + "PRIMARY KEY AUTOINCREMENT," +
                NAME_OWNER + " TEXT," +
                INN_OWNER + " INTEGER," +
                ADDRESS_OWNER + " TEXT," +
                COMMENT_OWNER + " TEXT);");

        db.execSQL("CREATE TABLE" + TABLE_OBJECT + "(" + KEY_ID + "PRIMARY KEY AUTOINCREMENT," +
                ID_OWNER + " integer," +
                ID_OWNER_OBJECT + " integer," +
                DOGOVOR_OBJECT + " integer," +
                ADDRESS_OBJECT + " text," +
                COMMENT_OBJECT + " text," +
                NAME_OBJECT + " text);");

        db.execSQL("CREATE TABLE " + TABLE_PRIBOR +
                "(" + KEY_ID + "PRIMARY KEY AUTOINCREMENT," +
                ID_OWNER_OBJECT + " integer," +
                TYPE_PRIBOR + " text," +
                NUMBER_PRIBOR + " integer," +
                ADRESS_PRIBOR + " integer," +
                BAUD_PRIBOR + " integer," +
                PASSWORD_USER + " integer," +
                PASSWORD_ADMIN + " integer," +
                PU_PRIBOR + "integer," +
                PI_PRIBOR + " integer," +
                TYPE_CONNECT + " integer," +
                TCP_IP + "text," +
                PORT + " integer);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void addOwner(String nameOwner, Integer innOwner, String addressOwner, String commentOwner) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME_OWNER, nameOwner);
        values.put(INN_OWNER, innOwner);
        values.put(ADDRESS_OWNER, addressOwner);
        values.put(COMMENT_OWNER, commentOwner);

        db.insert(TABLE_OWNER, null, values);
        db.close();
    }

    void getOwner() {


    }
//    public int updateOwner(String nameOner, Integer innOwner, String addressOwner, String commentOwner) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(NAME_OWNER, nameOner);
//        values.put(INN_OWNER, innOwner);
//        values.put(ADDRESS_OWNER, addressOwner);
//        values.put(COMMENT_OWNER, commentOwner);
//
//        return db.update(TABLE_OWNER, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
}
