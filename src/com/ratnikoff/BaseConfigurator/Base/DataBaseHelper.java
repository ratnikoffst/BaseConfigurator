package com.ratnikoff.BaseConfigurator.Base;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SM on 15.02.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String BASE_NAME = "test5.db";
    public static final int DATABASE_VERSION = 16;
    public static final String TABLE_OWNER = "BASE_OWNER"; // Таблица базы заказчика
    public static final String NAME_OWNER = "NAME_OWNER"; //Наименование заказчика
    public static final String INN_OWNER = "INN_OWNER"; //ИНН заказчика
    public static final String ADDRESS_OWNER = "ADDRESS_OWNER"; //Адрес заказчика
    public static final String COMMENT_OWNER = "COMMENT_OWNER"; //Комментарий о  заказчике

    public static final String TABLE_OBJECT = "BASE_OBJECT"; // Таблица обьектов заказчика
    public static final String ID_OWNER = "ID_OWNER"; // ID заказчика для связи с первой таблицей
    public static final String DOGOVOR_OBJECT = "DOGOVOR_OBJECT"; //Номер договора со сбытом
    public static final String NAME_OBJECT = "NAME_OWNER"; //ИНН заказчика
    public static final String ADDRESS_OBJECT = "ADDRESS_OBJECT"; //Адрес заказчика
    public static final String COMMENT_OBJECT = "COMMENT_OBJECT"; //Комментарий о Обьекте

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
    private static final String KEY_ID = "_id";
    //private List<Object> allObject;
    SQLiteDatabase db = this.getWritableDatabase();

    public DataBaseHelper(Activity context) {

        super(context, BASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_OWNER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME_OWNER + " TEXT," +
                INN_OWNER + " INTEGER," +
                ADDRESS_OWNER + " TEXT," +
                COMMENT_OWNER + " TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_OBJECT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_OWNER + " INTEGER," +
                NAME_OBJECT + " TEXT," +
                DOGOVOR_OBJECT + " TEXT," +
                ADDRESS_OBJECT + " TEXT," +
                COMMENT_OBJECT + " TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_PRIBOR +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID_OWNEROBJECT + " INTEGER," +
                TYPE_PRIBOR + " TEXT," +
                NUMBER_PRIBOR + " INTEGER," +
                ADRESS_PRIBOR + " INTEGER," +
                BAUD_PRIBOR + " INTEGER," +
                PASSWORD_USER + " INTEGER," +
                PASSWORD_ADMIN + " INTEGER," +
                PU_PRIBOR + " INTEGER," +
                PI_PRIBOR + " INTEGER," +
                TYPE_CONNECT + " INTEGER," +
                TCP_IP + " TEXT," +
                PORT + " INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIBOR);
        // и создаём их снова
        onCreate(db);
    }

    // Классы для работы с таблицей заказчиков
    // Класс для добавления заказчика
    public void addOwner(String nameOwner, Integer innOwner, String addressOwner, String commentOwner) {
        ContentValues values = new ContentValues();
        values.put(NAME_OWNER, nameOwner);
        values.put(INN_OWNER, innOwner);
        values.put(ADDRESS_OWNER, addressOwner);
        values.put(COMMENT_OWNER, commentOwner);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_OWNER, null, values);
        db.close();
    }

    // получение данных о одном заказчике
    public Owner getOwner(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_OWNER, new String[]{KEY_ID,
                        NAME_OWNER, INN_OWNER, ADDRESS_OWNER, COMMENT_OWNER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Owner owner = new Owner(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4));
        return owner;
    }

    // Класс удаления Owner
    // Добавить уничтожение субьобектов и приборов
    public void removeOwner(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OWNER, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Редактирование элемента первой таблицы
    public void editOwner(int id, String nameOwner, int nameInn, String nameAddress, String commentOwner) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME_OWNER, nameOwner);
        values.put(INN_OWNER, nameInn);
        values.put(ADDRESS_OWNER, nameAddress);
        values.put(COMMENT_OWNER, commentOwner);

        db.update(TABLE_OWNER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Получение всего списка заказчиков
    public List<Owner> getAllOwner() {
        List<Owner> OwnerList = new ArrayList<Owner>();

        String selectQuery = "SELECT * FROM " + TABLE_OWNER;
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Owner owner = new Owner(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4));
                OwnerList.add(owner);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return OwnerList;
    }


    // Классы для работы с object

    public List<Object> getAllObjectOwner(int idOwner) {
        List<Object> ObjectList = new ArrayList<Object>();

        String selectQuery = "SELECT * FROM " + TABLE_OBJECT + " WHERE " + ID_OWNER + "=" + idOwner;
//  SELECT * FROM tbl_info WHERE age < 15;

        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();


        cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                int ido = Integer.parseInt(cursor.getString(0));
                int idow = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                String dog = cursor.getString(3);
                String ad = cursor.getString(4);
                String com = cursor.getString(5);
//
//                ID_OWNER + " INTEGER," +
//                        //   ID_OWNER_OBJECT + " INTEGER," +
//                        DOGOVOR_OBJECT + " TEXT," +
//                ADDRESS_OBJECT + " TEXT," +
//                COMMENT_OBJECT + " TEXT," +
//                        NAME_OBJECT + " TEXT);");

                Object object = new Object(ido, idow,
                        name, dog, ad, com);
                ObjectList.add(object);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ObjectList;

    }

    /* idOwner -ключ заказчика integer
    *   NAME_OBJECT - Название Обьекта TEXT
    *  DOGOVOR_OBJECT - договор по обьекту Integer
    *   ADDRESS_OBJECT - адрес обьекта TEXT
    *   COMMENT_OBJECT - Комментарий к обьекту TEXT
    */
    public void addObject(int idOwner, String NameObject, String dogovor, String address, String comment) {

        ContentValues values = new ContentValues();
        values.put(ID_OWNER, idOwner);
        values.put(NAME_OBJECT, NameObject);
        values.put(DOGOVOR_OBJECT, dogovor);
        values.put(ADDRESS_OBJECT, address);
        values.put(COMMENT_OBJECT, comment);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }
}
