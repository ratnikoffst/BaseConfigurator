package com.ratnikoff.BaseConfigurator.BaseSQLite;

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
    public static final String BASE_NAME = "test6.db";
    public static final int DATABASE_VERSION = 20;

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

    public static final String ADDRESS_PRIBOR = "ADRESS_PRIBOR";
    public static final String GADDRESS_PRIBOR = "GADRESS_PRIBOR";
    public static final String TARIFF_PRIBOR = "TARIFF_PRIBOR";
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
    SQLiteDatabase db;// = this.getWritableDatabase();

    public DataBaseHelper(Activity context) {

        super(context, BASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_OWNER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME_OWNER + " TEXT," +
                INN_OWNER + " TEXT," +
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
                ADDRESS_PRIBOR + " INTEGER," +
                BAUD_PRIBOR + " INTEGER," +
                PASSWORD_USER + " INTEGER," +
                PASSWORD_ADMIN + " INTEGER," +
                PU_PRIBOR + " INTEGER," +
                PI_PRIBOR + " INTEGER," +
                TYPE_CONNECT + " INTEGER," +
                TCP_IP + " TEXT," +
                PORT + " INTEGER," +
                GADDRESS_PRIBOR + " INTEGER," +
                TARIFF_PRIBOR + " INTEGER);");

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
    public void addOwner(String nameOwner, String innOwner, String addressOwner, String commentOwner) {
        ContentValues values = new ContentValues();
        values.put(NAME_OWNER, nameOwner);
        values.put(INN_OWNER, innOwner);
        values.put(ADDRESS_OWNER, addressOwner);
        values.put(COMMENT_OWNER, commentOwner);

        db = this.getWritableDatabase();
        db.insert(TABLE_OWNER, null, values);
        db.close();
    }

    // Класс удаления Owner
    // Добавить уничтожение субьобектов и приборов
    public void removeOwner(int id) {

        List<Object> object = getAllObjectOwner(id);
        for (int i = 0; i < object.size(); i++) {

            removeObject(object.get(i).getIdObject());
        }

        db = this.getWritableDatabase();
        db.delete(TABLE_OWNER, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Редактирование элемента первой таблицы
    public void editOwner(int id, String nameOwner, String nameInn, String nameAddress, String commentOwner) {
//   SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_OWNER, nameOwner);
        values.put(INN_OWNER, nameInn);
        values.put(ADDRESS_OWNER, nameAddress);
        values.put(COMMENT_OWNER, commentOwner);

        db = this.getWritableDatabase();
        db.update(TABLE_OWNER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Получение всего списка заказчиков
    public List<Owner> getAllOwner() {
        List<Owner> OwnerList = new ArrayList<Owner>();

        String selectQuery = "SELECT * FROM " + TABLE_OWNER;
        Cursor cursor;
        db = this.getReadableDatabase();//Database();
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Owner owner = new Owner(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                OwnerList.add(owner);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return OwnerList;
    }


    // Классы для работы с object


    public List<Object> getAllObjectOwner(int idOwner) {
        List<Object> ObjectList = new ArrayList<Object>();

        String selectQuery = "SELECT * FROM " + TABLE_OBJECT + " WHERE " + ID_OWNER + "=" + idOwner;

        //Cursor cursor;
        db = this.getWritableDatabase();
        //SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                int ido = Integer.parseInt(cursor.getString(0));
                int idow = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                String dog = cursor.getString(3);
                String ad = cursor.getString(4);
                String com = cursor.getString(5);
                Object object = new Object(ido, idow,
                        name, dog, ad, com);
                ObjectList.add(object);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
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

        db = this.getWritableDatabase();
        db.insert(TABLE_OBJECT, null, values);
        db.close();
    }

    // Редактирование элемента первой таблицы
    public void editObject(int id, int idOwner, String nameObject, String dogovor, String address, String comment) {


        ContentValues values = new ContentValues();

        values.put(ID_OWNER, idOwner);
        values.put(NAME_OBJECT, nameObject);
        values.put(DOGOVOR_OBJECT, dogovor);
        values.put(ADDRESS_OBJECT, address);
        values.put(COMMENT_OBJECT, comment);

        db = this.getWritableDatabase();
        db.update(TABLE_OBJECT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Удаление из таблицы обьектов добавить удаление приборов
    public void removeObject(int idObject) {
        List<Pribor> pribor = getAllObjectPribor(idObject);
        for (int i = 0; i < pribor.size(); i++) {
            removePribor(pribor.get(i).getIdPribor());
        }
        db = this.getWritableDatabase();
        db.delete(TABLE_OBJECT, KEY_ID + " = ?", new String[]{String.valueOf(idObject)});
        db.close();
    }
// Классы для работы с приборами

    public void addPribor(Pribor pribor) {//int idObject, String typePribor, int numberPribor, int address) {


        ContentValues values = new ContentValues();
        values.put(ID_OWNEROBJECT, pribor.idObject); // 1
        values.put(TYPE_PRIBOR, pribor.getTypePribor());  // 2
        values.put(NUMBER_PRIBOR, pribor.getNumberPribor()); //3
        values.put(ADDRESS_PRIBOR, pribor.getAddressPribor()); //4

        values.put(BAUD_PRIBOR, pribor.getBaudPribor()); //5
        values.put(PASSWORD_USER, pribor.getPasswordUser()); // 6
        values.put(PASSWORD_ADMIN, pribor.getPasswordAdmin()); //7
        values.put(PU_PRIBOR, pribor.getPuPribor()); // 8)
        values.put(PI_PRIBOR, pribor.getPiPribor()); // 9
        values.put(TYPE_CONNECT, pribor.getTypeConnectPribor());  // 10
        values.put(TCP_IP, pribor.getTcpIp()); // 11
        values.put(PORT, pribor.getPort()); // 12
        values.put(GADDRESS_PRIBOR, pribor.getGaddressPribor()); // 13
        values.put(TARIFF_PRIBOR, pribor.getTariff()); // 14
        // SQLiteDatabase db = this.getWritableDatabase();
        db = this.getWritableDatabase();
        db.insert(TABLE_PRIBOR, null, values);
        db.close();
    }

    public List<Pribor> getAllObjectPribor(int a) {
        List<Pribor> PriborList = new ArrayList<Pribor>();

        String selectQuery = "SELECT * FROM " + TABLE_PRIBOR + " WHERE " + ID_OWNEROBJECT + "=" + a;


        Cursor cursor;

        db = this.getReadableDatabase();//Database();

        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Pribor pribor = new Pribor();

                int id = Integer.parseInt(cursor.getString(0)); // ID прибора
                pribor.setIdPribor(id);
                int idow = Integer.parseInt(cursor.getString(1)); // ID objecta
                pribor.setIdObject(idow);
                String name = cursor.getString(2); // Type прибора
                pribor.setTypePribor(name);
                int num = Integer.parseInt(cursor.getString(3)); // НОМЕР прибора
                pribor.setNumberPribor(num);
                int ad = Integer.parseInt(cursor.getString(4)); // Сетевой адрес
                pribor.setAddressPribor(ad);
                int com = Integer.parseInt(cursor.getString(5)); // Скорость
                pribor.setBaudPribor(com);
                int a2 = Integer.parseInt(cursor.getString(6));// Пароль user
                pribor.setPasswordUser(a2);
                int a3 = Integer.parseInt(cursor.getString(7));// Пароль admin
                pribor.setPasswordAdmin(a3);
                int a4 = Integer.parseInt(cursor.getString(8));// Коэфициент трансформации Напряжения
                pribor.setPuPribor(a4);
                int a5 = Integer.parseInt(cursor.getString(9));// Коэфициент трансформации Тока
                pribor.setPiPribor(a5); // Тип конекат
                int a6 = Integer.parseInt(cursor.getString(10)); // Тип соединения
                pribor.setTypeConnectPribor(a6);
                String a7 = cursor.getString(11);  // TCp Ip адрес
                pribor.setTcpIp(a7);
                int a8 = Integer.parseInt(cursor.getString(12)); // Порт
                pribor.setPort(a8);
                int gAddress = Integer.parseInt(cursor.getString(13)); // Групповой адрес
                pribor.setGaddressPribor(gAddress);
                int tariff = Integer.parseInt(cursor.getString(14)); // Тарифф
                pribor.setTariff(tariff);
                PriborList.add(pribor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return PriborList;
    }

    public void removePribor(int idPribor) {

        db = this.getWritableDatabase();
        db.delete(TABLE_PRIBOR, KEY_ID + " = ?", new String[]{String.valueOf(idPribor)});
        db.close();
    }
}
