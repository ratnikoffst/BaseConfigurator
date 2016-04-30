package com.ratnikoff.BaseConfigurator.UsbDriver;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import com.ratnikoff.BaseConfigurator.UsbDriver.USBDriver.FTDriver;

import java.util.Arrays;

import static java.lang.Integer.toHexString;

/**
 * Created by SM on 03.04.2016.
 */
public class Mercury23 {

    public static final int PAUSE = 200;
    public static final byte[] OPEN_CANAL_PASSWORD1 = {0x00, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x00, 0x00};
    public static final byte[] OPEN_CANAL_PASSWORD31 = {0x00, 0x01, 0x01, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x00, 0x00};

    public static final byte[] WRITE_SEARCH = {0x00, 0x00, 0x00, 0x00};
    public static final byte[] READ_SEARCH = {0x00, 0x00, 0x00, 0x00};

    private static final String ACTION_USB_PERMISSION = "";

    private static final byte[] WRITE_CLOSE_CANAL = {0x00, 0x02, 0x00, 0x00};
    private static final byte[] READ_CLOSE_CANAL = {0x00, 0x02, 0x00, 0x00};
    private static final byte[] POST_OPEN = {0x00, 0x00, 0x00, 0x00};

    private static final byte[] WRITE_PARAMETR_PRIBOR_ALL = {0x00, 0x08, 0x01, 0x00, 0x00, 0x00};
    private static final byte[] READ_PARAMETR_PRIBOR_ALL = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static final byte[] WRITE_NUMBER = {0x00, 0x08, 0x00, 0x00, 0x00};//, 0x01, 0x01, 0x01, 0x01, 0x00, 0x00};
    private static final byte[] READ_NUMBER = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static final byte[] WRITE_PUPI = {0x00, 0x08, 0x02, 0x00, 0x00};
    private static final byte[] READ_PUPI = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static final byte[] WRITE_NAME = {0x00, 0x08, 0x12, 0x00, 0x00};
    private static final byte[] READ_NAME = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static final byte[] WRITE_TARIF = {0x00, 0x08, 0x17, 0x00, 0x00};
    private static final byte[] READ_TARIF = {0x00, 0x00, 0x00, 0x00, 0x00};

    FTDriver ftd;
    Context c;
    FragmentManager fmm;

    public Mercury23(UsbManager manager, Context contex, FragmentManager fm) throws InterruptedException {
        ftd = new FTDriver(manager);
        c = contex;
        fmm = fm;
        if (openport()) {

        }
        //return openport();
    }

    public boolean openport() {
        PendingIntent permissionIntent = PendingIntent.getBroadcast(c, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        ftd.setPermissionIntent(permissionIntent);


        if (!ftd.begin(FTDriver.BAUD9600)) {
            return false;
        }

        return ftd.isConnected();

    }

//    public void searchAll(int address, int addressend) throws InterruptedException {
//        ArrayList<Pribor> pribors = new ArrayList<Pribor>();
//        String addressStr;
//        byte[] write;
//        byte[] read;
//
//        Pribor prib;// = new Pribor();
//        for (int i = 1; i <= 240; i++) {
//            write = WRITE_SEARCH;
//            read = READ_SEARCH;
//
//            addressStr = toHexString(i);
//            write[0] = (byte) Integer.parseInt(addressStr, 16);
//            write = crc16modbus(write);
//
//            ftd.write(write);
//            Thread.sleep(PAUSE);
//            ftd.read(read);
//
//            if (Arrays.equals(write, read)) {
//                prib = new Pribor();
//                prib.setAddressPribor(i);
//                prib.setNumberPribor(getNumber(i));
//                //   prib.setTypePribor(getName(i)); // Cделать метод
//                prib.setBaudPribor(9600); // Захордкодено
//                int pupi = getPuPi(i);//
//                int Pu = pupi / 10000;
//                pupi = pupi - Pu * 10000;
//                prib.setPiPribor(pupi);// ток
//                prib.setPuPribor(Pu);// напряжение
//                //  pribors.add(prib);
//
//                //mercuryfragment23х manager = getActivity().getSystemService(Context.USB_SERVICE);
//
//                mercuryfragment23х b = (mercuryfragment23х) fmm.findFragmentByTag("MERCURY23");
//                //  mercuryfragment23х b = (mercuryfragment23х) getActivity(Context).getFragmentManager.getFr.findFragmentByTag("MERCURY23");
//                b.addNewItem(prib);
//
//                // b.setNewDataItem(prib);
//
//                //if (currentTagFragment == "AddOwner") {
//                //  добавления в Basefragment
//                //} else {
//                //     b.setNewDataItem(Current_Operation_Edit, owner); // Установка нового значения
//            }
//
//        }

    //}


    public String getName(int address, int typePribor) throws InterruptedException {
        byte[] write = WRITE_NAME;
        byte[] read = READ_NAME;

        read = readPribor(address, write, read);

        return getCR(read, typePribor);
    }


    // Отработать команду
    public int getTarif(int address) throws InterruptedException {
        byte[] write = WRITE_TARIF;
        byte[] read = READ_TARIF;

        read = readPribor(address, write, read);

        int tarif = read[2] & 0xe;

        return tarif;
    }

    // 0 - Меркурий 230 А
    // 3-
    // 4- Меркурий 234 А
    private String getCR(byte[] read, int typePribor) {
        String s = "";
        switch (typePribor) {
            case 0:
                s = ocr230(read);
                break;
        }
        //  Switch (typePribor) {
        //    case 0:

        //  break;
        //}
        return s;
    }

    private String ocr230(byte[] parameter) {
        String s = "Меркурий 230 А";
        if ((parameter[3] & 0x30) == 0) {
            s = s + "R";
        }
        if ((parameter[3] & 0x40) != 0) {
            s = s + "Т";
        }
        if ((parameter[1] & 0x80) != 0) {
            s = s + "(1)";
        } else {
            s = s + "(2)";
        }
        switch (parameter[3] & 0x0f) {
            case 1:
                s = s + "-00 ";
                break;
            case 2:
                s = s + "-01 ";
                break;
            case 3:
                s = s + "-02 ";
                break;
            case 4:
                s = s + "-03 ";
                break;
        }
        if ((parameter[2] & 0x20) != 0) {
            s = s + "P";
        }
        if ((parameter[5] & 0x02) != 0) {
            s = s + "Q";
        }
        //byte b2= (byte) (read[3] & 0x0c);
        switch (parameter[4] & 0x0c) {
            case 0:
                s = s + "С";
                break;
            case 4:
                s = s + "R";
                break;

        }
        if ((parameter[5] & 0x04) != 0) {
            s = s + "S";
        }
        if ((parameter[4] & 0x10) != 0) {
            s = s + "I";
        }
        if ((parameter[6] & 0x10) != 0) {
            s = s + "L";
        }
        if ((parameter[4] & 0x20) != 0) {
            s = s + "G";
        }
        if ((parameter[4] & 0x02) != 0) {
            s = s + "D";
        }
        if ((parameter[4] & 0x01) != 0) {
            s = s + "N";
        }
        return s;

    }


    public int getPuPi(int address) throws InterruptedException {
        int pupi;// = 0;
        byte[] write = WRITE_PUPI;
        byte[] read = READ_PUPI;

        read = readPribor(address, write, read);
        String str = "" + read[1] + read[2];
        String str2 = "" + read[3] + read[4];
        pupi = Integer.parseInt(str) * 10000 + Integer.parseInt(str2);
        return pupi;
    }

    public int getNumber(int address) throws InterruptedException {

        byte[] write = WRITE_NUMBER;
        byte[] read = READ_NUMBER;

        read = readPribor(address, write, read);

        String minStr = "";// + read[1] + read[2] + read[3] + read[4];//..new String(number,"UTF8");
        for (int i = 1; i <= 4; i++) {
            if (read[i] < 10) {
                minStr = minStr + "0" + read[i];
            } else {
                minStr = minStr + read[i];
            }
        }


        return Integer.parseInt(minStr);
    }

    private String getParameter(int i) throws InterruptedException {

        byte[] write = WRITE_PARAMETR_PRIBOR_ALL;
        byte[] read = READ_PARAMETR_PRIBOR_ALL;
        read = readPribor(i, write, read);
        int ik;
        ik = 1;
        return null;
    }


    // Основные классы работы

    private byte[] readPribor(int address, byte[] write, byte[] read) throws InterruptedException {
        if (openPribor(address)) {
            Byte b, b2, b3, b4 = 0;
            //          do {
//                for (int i=0;i<read.length;i++)
//                {
//                    read[i]=0;
//                }
            String s = toHexString(address);
            write[0] = (byte) Integer.parseInt(s, 16);
            write = crc16modbus(write);

            ftd.write(write);
            Thread.sleep(PAUSE);
            ftd.read(read);

            b = read[read.length - 1];
            b2 = read[read.length - 2];

            read = crc16modbus(read);
            b3 = read[read.length - 1];
            b4 = read[read.length - 2];
//            } while ((b != b3) && (b2 != b4));
            //read.len
            // вставить проверку контрольной суммы
            closePribor(address);
        }

        return read;
    }

    public byte[] getReadSearch(byte[] write, byte[] read) throws InterruptedException {


        ftd.write(write);
        Thread.sleep(PAUSE);
        ftd.read(read);

        return read;
    }

    private void closePribor(int address) throws InterruptedException {
        byte[] write = WRITE_CLOSE_CANAL;
        byte[] read = READ_CLOSE_CANAL;


        String s = toHexString(address);
        write[0] = (byte) Integer.parseInt(s, 16);
        write = crc16modbus(write);

        ftd.write(write);
        Thread.sleep(PAUSE);
        ftd.read(read);
// Проверку crc сделать !!!

    }


    private boolean openPribor(int i) throws InterruptedException {
        byte[] b = OPEN_CANAL_PASSWORD1;

        byte[] b2 = POST_OPEN;
        byte[] b6 = POST_OPEN;

        String s = toHexString(i);
        b[0] = (byte) Integer.parseInt(s, 16);
        b6[0] = (byte) Integer.parseInt(s, 16);
        b = crc16modbus(b);
        b6 = crc16modbus(b6);

        ftd.write(b, b.length);
        Thread.sleep(PAUSE);
        ftd.read(b2);
        return Arrays.equals(b2, b6);

    }


    public byte[] crc16modbus(byte[] dataBuffer) {
        int Sum = 0xffff;
        byte[] arr = dataBuffer;

        for (int i = 0; i < arr.length - 2; i++) {
            Sum = (Sum ^ arr[i]);
            for (int j = 0; j < 8; j++) {
                if ((Sum & 0x1) == 1) {
                    Sum >>>= 1;
                    Sum = (Sum ^ 0xA001);
                } else {
                    Sum >>>= 1;
                }
            }
        }
        byte b3 = (byte) (Sum >> 8); // Из старшего  byte получаем младший
        Sum = (short) (Sum << 8);
        byte b5 = (byte) (Sum >> 8);
        dataBuffer[dataBuffer.length - 1] = b3;//(byte) (b2 >> 8);
        dataBuffer[dataBuffer.length - 2] = b5;
        return dataBuffer;
    }


}
