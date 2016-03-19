package com.ratnikoff.BaseConfigurator.Base;

/**
 * Created by SM on 26.02.2016.
 */
public class Object {
    private int idObject;
    private int idOwner;// -ключ заказчика integer
    private String nameObject;// - Название Обьекта TEXT
    private String dogovorObject;// - договор по обьекту Integer
    private String addressObject;// - адрес обьекта TEXT
    private String commentObject;// - Комментарий к обьекту TEXT

    public Object(int idobject, int idowner, String name, String dog, String adress, String comment) {

        this.idObject = idobject;
        this.idOwner = idowner;
        this.nameObject = name;
        this.dogovorObject = dog;
        this.addressObject = adress;
        this.commentObject = comment;
    }

    public Object() {

    }

    public int getIdObject() {
        return idObject;
    }

    public void setIdObject(int idObject) {
        this.idObject = idObject;
    }

    public int getIdOwwner() {
        return idOwner;
    }

    public void setIdOwwner(int idOwwner) {
        this.idOwner = idOwwner;
    }

    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public String getDogovorObject() {
        return dogovorObject;
    }

    public void setDogovorObject(String dogovorObject) {
        this.dogovorObject = dogovorObject;
    }

    public String getAddressObject() {
        return addressObject;
    }

    public void setAddressObject(String addressObject) {
        this.addressObject = addressObject;
    }

    public String getCommentObject() {
        return commentObject;
    }

    public void setCommentObject(String commentObject) {
        this.commentObject = commentObject;
    }
}
