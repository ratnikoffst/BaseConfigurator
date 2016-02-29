package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseOwner;

import com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseObject.DataSubObject;

/**
 * Created by SM on 14.01.2016.
 * Класс являеться содержимым элементов коллекцией элементов основной базы
 */
public class OwnerData extends DataSubObject {

    // private String nameObject; // Наименование Объекта
    private String ID;
    private String nameOwner; // Наименование Заказчика
    private String InnOwner;
    private String CommentOwner;
    private String AddressOwner;

    //    private OwnerBaseFragment onClickListener;

    public void setAddressOwner(String addressObject) {
        this.AddressOwner = addressObject;
    }

    public String getAddressOwner() {
        return AddressOwner;
    }

    public void setCommentOwner(String commentObject) {
        this.CommentOwner = commentObject;
    }

    public String getCommentOwner() {
        return CommentOwner;
    }

    public void setInnOwner(String innObject) {
        this.InnOwner = innObject;
    }

    public String getInnOwner() {
        return InnOwner;
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
