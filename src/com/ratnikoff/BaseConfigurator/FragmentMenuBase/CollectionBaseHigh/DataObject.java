package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseHigh;

/**
 * Created by SM on 14.01.2016.
 * Класс являеться содержимым элементов коллекцией элементов основной базы
 */
public class DataObject {

    // private String nameObject; // Наименование Объекта
    private String nameOwner; // Наименование Заказчика
    private String InnObject;
    private String CommentObject;
    private String AddressObject;

    //    private BaseFragment onClickListener;

    public void setAddressObject(String addressObject) {
        this.AddressObject = addressObject;
    }

    public String getAddressObject() {
        return AddressObject;
    }

    public void setCommentObject(String commentObject) {
        this.CommentObject = commentObject;
    }

    public String getCommentObject() {
        return CommentObject;
    }

    public void setInnObject(String innObject) {
        this.InnObject = innObject;
    }

    public String getInnObject() {
        return InnObject;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

}
