package com.ratnikoff.BaseConfigurator.BaseSQLite;

/**
 * Created by SM on 26.02.2016.
 */
public class Owner {
    private int ID = 0;
    private String Name = "";
    private String Inn = "";
    private String Address = "";
    private String Comment = "";

    //    private String ID;
//    private String nameOwner; // Наименование Заказчика
//    private String InnOwner;  // ИНН Заказчика
//    private String CommentOwner; // Комментарий Заказчику
//    private String AddressOwner; // Адресс Заказчика
    public Owner() {
        //  this.Name = name;
    }

    public Owner(int id, String name, String inn, String address, String comment) {
        this.ID = id;
        this.Name = name;
        this.Inn = inn;
        this.Address = address;
        this.Comment = comment;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {

        this.Name = name;
    }

    public String getInn() {
        return Inn;
    }

    public void setInn(String inn) {
        this.Inn = inn;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}