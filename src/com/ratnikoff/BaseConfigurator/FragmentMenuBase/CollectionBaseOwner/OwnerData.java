package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner;

/**
 * Created by SM on 14.01.2016.
 * Класс являеться содержимым элементов коллекцией элементов основной базы
 */
public class OwnerData {

    // private String nameObject; // Наименование Объекта
    private String ID;
    private String nameOwner; // Наименование Заказчика
    private String InnObject;
    private String CommentObject;
    private String AddressObject;

    //    private OwnerBaseFragment onClickListener;

    public void setAddressOwner(String addressObject) {
        this.AddressObject = addressObject;
    }

    public String getAddressOwner() {
        return AddressObject;
    }

    public void setCommentOwner(String commentObject) {
        this.CommentObject = commentObject;
    }

    public String getCommentOwner() {
        return CommentObject;
    }

    public void setInnOwner(String innObject) {
        this.InnObject = innObject;
    }

    public String getInnOwner() {
        return InnObject;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public String getIdOwner() {
        return ID;
    }

    public void setIdOwner(String ID) {

        this.ID = ID;
    }

}
