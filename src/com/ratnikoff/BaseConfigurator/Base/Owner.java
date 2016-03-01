package com.ratnikoff.BaseConfigurator.Base;

/**
 * Created by SM on 26.02.2016.
 */
public class Owner {
    private int ID;
    private String Name;
    private int Inn;
    private String Address;
    private String Comment;

//    private String ID;
//    private String nameOwner; // Наименование Заказчика
//    private String InnOwner;
//    private String CommentOwner;
//    private String AddressOwner;

    public Owner(int id, String name, int inn, String address, String comment) {
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
        Name = name;
    }

    public int getInn() {
        return Inn;
    }

    public void setInn(int inn) {
        Inn = inn;
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