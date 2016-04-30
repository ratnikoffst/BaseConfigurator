package com.ratnikoff.BaseConfigurator.UsbDriver;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import com.ratnikoff.BaseConfigurator.UsbDriver.USBDriver.FTDriver;

import java.nio.ByteBuffer;

/**
 * Created by SM on 03.04.2016.
 */
public class Mercury200 {

    public static final int PAUSE = 200;


    public static final byte[] WRITE_VER_NUMBER = {0x00, 0x00, 0x00, 0x00, 0x28, 0x00, 0x00};
    public static final byte[] READ_VER_NUMBER = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    public static final byte[] READ_NUMBER = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private static final byte[] WRITE_TARIF = {0x00, 0x00, 0x00, 0x00, 0x2e, 0x00, 0x00};
    private static final byte[] READ_TARIF = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private static final byte[] WRITE_GADDRESS = {0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00};
    private static final byte[] READ_GADDRESS = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private static final byte[] WRITE_NUMBER = {0x00, 0x00, 0x00, 0x00, 0x2f, 0x00, 0x00};
    private static final String ACTION_USB_PERMISSION = "";


    FTDriver ftd;
    Context c;
    FragmentManager fmm;

    public Mercury200(UsbManager manager, Context contex, FragmentManager fm) throws InterruptedException {
        ftd = new FTDriver(manager);
        c = contex;
        fmm = fm;

    }

    //
    public boolean openport() {
        PendingIntent permissionIntent = PendingIntent.getBroadcast(c, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        ftd.setPermissionIntent(permissionIntent);

        return ftd.begin(FTDriver.BAUD9600);

    }

    public int getReadTarif(int progress) throws InterruptedException {
        byte[] write = WRITE_TARIF;
        byte[] read = READ_TARIF;

        write = getArrayAdress(write, progress);

        ftd.write(write);
        Thread.sleep(PAUSE);
        ftd.read(read);

        return read[5];
    }


    // Основные классы работы

    public byte[] getReadSearch(byte[] write, byte[] read) throws InterruptedException {
        int i = 1;

        ftd.write(write);
        Thread.sleep(PAUSE);
        ftd.read(read);
        return read;
    }

    public int getGaddress(int progress) throws InterruptedException {
        byte[] write = WRITE_GADDRESS;
        byte[] read = READ_GADDRESS;


        write = getArrayAdress(write, progress);

        ftd.write(write);
        Thread.sleep(PAUSE);
        ftd.read(read);

        int number;
        byte[] b = {0, 0, 0, 0};

        b[0] = read[7];
        b[1] = read[8];
        b[2] = read[9];
        b[3] = read[10];

        //  ByteBuffer bb = ByteBuffer.allocate(4);

        ByteBuffer bb = ByteBuffer.wrap(b);

        number = bb.getInt();


        return number;
    }

    public int getNumberPribor(int adress) throws InterruptedException {
        byte[] write = WRITE_NUMBER;
        byte[] read = READ_NUMBER;


        write = getArrayAdress(write, adress);

        ftd.write(write);
        Thread.sleep(PAUSE);
        ftd.read(read);

        int number;
        byte[] b = {0, 0, 0, 0};

        b[0] = read[5];
        b[1] = read[6];
        b[2] = read[7];
        b[3] = read[8];

        //     ByteBuffer bb = ByteBuffer.allocate(4);

        ByteBuffer bb = ByteBuffer.wrap(b);

        number = bb.getInt();

        return number;
    }

    public byte[] getArrayAdress(byte[] write, int progress) {
        byte[] address = ByteBuffer.allocate(4).putInt(progress).array();

        write[0] = address[0];
        write[1] = address[1];
        write[2] = address[2];
        write[3] = address[3];

        write = crc16modbus(write);

        return write;
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
