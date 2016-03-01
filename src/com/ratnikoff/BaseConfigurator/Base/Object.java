package com.ratnikoff.BaseConfigurator.Base;

/**
 * Created by SM on 26.02.2016.
 */
public class Object {
    private int ID_OBJECT;
    private int ID_OWNER;// -ключ заказчика integer
    private String NAME_OBJECT;// - Название Обьекта TEXT
    private String DOGOVOR_OBJECT;// - договор по обьекту Integer
    private String ADDRESS_OBJECT;// - адрес обьекта TEXT
    private String COMMENT_OBJECT;// - Комментарий к обьекту TEXT

    public Object(int idobject, int idowner, String name, String dog, String adress, String comment) {

        this.ID_OBJECT = idobject;
        this.ID_OWNER = idowner;
        this.NAME_OBJECT = name;
        this.DOGOVOR_OBJECT = dog;
        this.ADDRESS_OBJECT = adress;
        this.COMMENT_OBJECT = comment;
    }

    public int getIdObject() {
        return ID_OBJECT;
    }

    public void setIdObject(int idObject) {
        this.ID_OBJECT = idObject;
    }

    public int getIdOwwner() {
        return ID_OWNER;
    }

    public void setIdOwwner(int idOwwner) {
        this.ID_OWNER = idOwwner;
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
