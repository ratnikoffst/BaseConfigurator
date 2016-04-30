package com.ratnikoff.BaseConfigurator.BaseSQLite;

/**
 * Created by SM on 02.03.2016.
 */
public class Pribor {
    public int idObject;// ID_OBJECT;/      / = "ID_OWNER_OBJECT";
    public String typePribor;// = "TYPE_PRIBOR";
    public int numberPribor;// = "NUMBER_PRIBOR"INTEGER;
    public int addressPribor;// = "ADRESS_PRIBOR"; integer
    public int gaddressPribor;// = "ADRESS_PRIBOR"; integer
    public int baudPribor;// = "BAUD_PRIBOR";//integer,
    public int passwordUser;// = "PASSWORD_USER";// integer,
    public int passwordAdmin;// = "PASSWORD_ADMIN";// integer,
    public int puPribor;// = "PU_PRIBOR";// integer,
    public int piPribor;// = "PI_PRIBOR";// integer,
    public int typeConnectPribor;// = "TYPE_CONNECT";// integer,
    public int Tariff;
    public String tcpIp;// = "TCP_IP";// text,
    public int port;//T = "PORT";//integer
    private int idPribor;

    // Класс для 230,234
    public Pribor() {
    }

    // Класс для 200
//    public Pribor(int idObject, int idPribor, String typePribor,
//                  int numberPribor, int AddressPribor){ // , int baudPribor
//        this.idObject = idObject;
//        this.idPribor = idPribor;
//        this.typePribor = typePribor;
//        this.numberPribor = numberPribor;
//        this.addressPribor = AddressPribor;
////        this.baudPribor = baudPribor;
//    }

    public int getIdPribor() {
        return idPribor;
    }

    public void setIdPribor(int idPribor) {
        this.idPribor = idPribor;
    }

    public int getIdObject() {
        return idObject;
    }

    public void setIdObject(int idObject) {
        this.idObject = idObject;
    }

    public String getTypePribor() {
        return typePribor;
    }

    public void setTypePribor(String typePribor) {
        this.typePribor = typePribor;
    }

    public int getAddressPribor() {
        return addressPribor;
    }

    public void setAddressPribor(int addressPribor) {
        this.addressPribor = addressPribor;
    }

    public int getGaddressPribor() {
        return gaddressPribor;
    }

    public void setGaddressPribor(int gaddressPribor) {
        this.gaddressPribor = gaddressPribor;
    }

    public int getTariff() {
        return Tariff;
    }

    public void setTariff(int tariff) {
        Tariff = tariff;
    }

    public int getBaudPribor() {
        return baudPribor;
    }

    public void setBaudPribor(int baudPribor) {
        this.baudPribor = baudPribor;
    }

    public int getNumberPribor() {
        return numberPribor;
    }

    public void setNumberPribor(int numberPribor) {
        this.numberPribor = numberPribor;
    }

    public int getPasswordAdmin() {
        return passwordAdmin;
    }

    public void setPasswordAdmin(int passwordAdmin) {
        this.passwordAdmin = passwordAdmin;
    }

    public int getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(int passwordUser) {
        this.passwordUser = passwordUser;
    }

    public int getPiPribor() {
        return piPribor;
    }

    public void setPiPribor(int piPribor) {
        this.piPribor = piPribor;
    }

    public int getPuPribor() {
        return puPribor;
    }

    public void setPuPribor(int puPribor) {
        this.puPribor = puPribor;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTypeConnectPribor() {
        return typeConnectPribor;
    }

    public void setTypeConnectPribor(int typeConnectPribor) {
        this.typeConnectPribor = typeConnectPribor;
    }

    public String getTcpIp() {
        return tcpIp;
    }

    public void setTcpIp(String tcpIp) {
        this.tcpIp = tcpIp;
    }


}
