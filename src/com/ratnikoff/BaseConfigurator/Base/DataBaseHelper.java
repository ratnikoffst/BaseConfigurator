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
    public static final String BASE_NAME = "test6.db";
    public static final int DATABASE_VERSION = 9;
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
                ADDRESS_PRIBOR + " INTEGER," +
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


    public List<Object> getAllObject() {
        List<Object> ObjectList = new ArrayList<Object>();

        String selectQuery = "SELECT * FROM " + TABLE_OBJECT;
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
                Object object = new Object(ido, idow,
                        name, dog, ad, com);
                ObjectList.add(object);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ObjectList;
    }

    public List<Object> getAllObjectOwner(int idOwner) {
        List<Object> ObjectList = new ArrayList<Object>();

        String selectQuery = "SELECT * FROM " + TABLE_OBJECT + " WHERE " + ID_OWNER + "=" + idOwner;

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

    // Редактирование элемента первой таблицы
    public void editObject(int id, int idOwner, String nameObject, String dogovor, String address, String comment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_OWNER, idOwner);
        values.put(NAME_OBJECT, nameObject);
        values.put(DOGOVOR_OBJECT, dogovor);
        values.put(ADDRESS_OBJECT, address);
        values.put(COMMENT_OBJECT, comment);

        db.update(TABLE_OBJECT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Удаление из таблицы обьектов добавить удаление приборов
    public void removeObject(int idObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OBJECT, KEY_ID + " = ?", new String[]{String.valueOf(idObject)});
        db.close();
    }

    public void addPribor(int idObject, String typePribor, int numberPribor, int address) {


        ContentValues values = new ContentValues();
        values.put(ID_OWNEROBJECT, idObject); // 1
        values.put(TYPE_PRIBOR, typePribor);  // 2
        values.put(NUMBER_PRIBOR, numberPribor); //3
        values.put(ADDRESS_PRIBOR, address); //4
        values.put(BAUD_PRIBOR, 9600); //5
        values.put(PASSWORD_USER, 111111); // 6
        values.put(PASSWORD_ADMIN, 222222); //7
        values.put(PU_PRIBOR, 1); // 8
        values.put(PI_PRIBOR, 1); // 9
        values.put(TYPE_CONNECT, 1);  // 10
        values.put(TCP_IP, "172.172.172.1 "); // 11
        values.put(PORT, 1010); // 12
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRIBOR, null, values);
        db.close();

//    public int idObject;// ID_OBJECT;/      / = "ID_OWNER_OBJECT";
//    public String typePribor;// = "TYPE_PRIBOR";
//    public int numberPribor;// = "NUMBER_PRIBOR"INTEGER;
//    public int addressPribor;// = "ADRESS_PRIBOR"; integer
//    public int baudPribor;// = "BAUD_PRIBOR";//integer,
//    public int passwordUser;// = "PASSWORD_USER";// integer,
//    public int passwordAdmin;// = "PASSWORD_ADMIN";// integer,
//    public int puPribor;// = "PU_PRIBOR";// integer,
//    public int piPribor;// = "PI_PRIBOR";// integer,
//    public int typeConnectPribor;// = "TYPE_CONNECT";// integer,
//    public String tcpIp;// = "TCP_IP";// text,
//    public int port;//T = "PORT";//integer
//    private int idPribor;

    }

    public List<Pribor> getAllObjectPribor(int a) {
        List<Pribor> PriborList = new ArrayList<Pribor>();

        String selectQuery = "SELECT * FROM " + TABLE_PRIBOR + " WHERE " + ID_OWNEROBJECT + "=" + a;

//        String selectQuery = "SELECT * FROM " + TABLE_OBJECT + " WHERE " + ID_OWNER + "=" + idOwner;

        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();

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
                String a7 = cursor.getString(11);
                pribor.setTcpIp(a7);
                int a8 = Integer.parseInt(cursor.getString(12));
                pribor.setPort(a8);
                //String a2=cursor.getString(6);
                PriborList.add(pribor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return PriborList;
    }
//    public int idObject;// ID_OBJECT;/      / = "ID_OWNER_OBJECT";
//    public String typePribor;// = "TYPE_PRIBOR";
//    public int numberPribor;// = "NUMBER_PRIBOR"INTEGER;
//    public int addressPribor;// = "ADRESS_PRIBOR"; integer
//    public int baudPribor;// = "BAUD_PRIBOR";//integer,
//    public int passwordUser;// = "PASSWORD_USER";// integer,
//    public int passwordAdmin;// = "PASSWORD_ADMIN";// integer,
//    public int puPribor;// = "PU_PRIBOR";// integer,
//    public int piPribor;// = "PI_PRIBOR";// integer,
//    public int typeConnectPribor;// = "TYPE_CONNECT";// integer,
//    public String tcpIp;// = "TCP_IP";// text,
//    public int port;//T = "PORT";//integer
//    private int idPribor;
//}
}
