package com.ratnikoff.BaseConfigurator.FragmentMenuBase.CollectionBaseObject;

/**
 * Created by SM on 14.01.2016.
 * Класс являеться содержимым элементов коллекцией элементов основной базы
 */
public class ObjectData extends DataSubObject {
    private int idObject;
    private int idOwwner;// -ключ заказчика integer
    private String NAME_OBJECT;// - Название Обьекта TEXT
    private String DOGOVOR_OBJECT;// - договор по обьекту Integer
    private String ADDRESS_OBJECT;// - адрес обьекта TEXT
    private String COMMENT_OBJECT;// - Комментарий к обьекту TEXT


    public int getIdObject() {
        return idObject;
    }

    public void setIdObject(int idObject) {
        this.idObject = idObject;
    }

    public int getIdOwwner() {
        return idOwwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwwner = idOwner;
    }

    public String getNAME_OBJECT() {
        return NAME_OBJECT;
    }

    public void setNAME_OBJECT(String NAME_OBJECT) {
        this.NAME_OBJECT = NAME_OBJECT;
    }

    public String getDOGOVOR_OBJECT() {
        return DOGOVOR_OBJECT;
    }

    public void setDOGOVOR_OBJECT(String DOGOVOR_OBJECT) {
        this.DOGOVOR_OBJECT = DOGOVOR_OBJECT;
    }

    public String getADDRESS_OBJECT() {
        return ADDRESS_OBJECT;
    }

    public void setADDRESS_OBJECT(String ADDRESS_OBJECT) {
        this.ADDRESS_OBJECT = ADDRESS_OBJECT;
    }

    public String getCOMMENT_OBJECT() {
        return COMMENT_OBJECT;
    }

    public void setCOMMENT_OBJECT(String COMMENT_OBJECT) {
        this.COMMENT_OBJECT = COMMENT_OBJECT;
    }
}
