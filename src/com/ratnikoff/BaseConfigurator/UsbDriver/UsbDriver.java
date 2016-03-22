package com.ratnikoff.BaseConfigurator.UsbDriver;

import android.hardware.usb.*;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

import static android.R.id.message;

/**
 * Created by SM on 22.03.2016.
 */
public class UsbDriver {
    UsbManager manager;
    UsbDevice device;
    UsbDeviceConnection connection;
    UsbInterface usbInterface;
    int usbEndpoint;

    public UsbDriver(UsbManager systemService) {
        manager = systemService;
    }

    public String getUsbInfo() {

        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();


        String info = "";

        while (deviceIterator.hasNext()) {
            device = deviceIterator.next();
            info += "\n" + "DeviceID: " + device.getDeviceId() + "\n"
                    + "DeviceName: " + device.getDeviceName() + "\n"
                    + "DeviceClass: " + device.getDeviceClass() + " - "
                    + translateDeviceClass(device.getDeviceClass()) + "\n"
                    + "DeviceSubClass: " + device.getDeviceSubclass() + "\n"
                    + "VendorID: " + device.getVendorId() + "\n"
                    + "ProductID: " + device.getProductId() + "\n";
        }
        return info;

    }

    public UsbDeviceConnection openDevice() {
        usbInterface = device.getInterface(0);

        UsbEndpoint usbEndpoint2 = usbInterface.getEndpoint(0);

        connection = manager.openDevice(device);
        byte[] b = {44, 44, 32, 43, 43, 43, 43, 43, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 43, 43, 4, 3, 3, 4, 34, 34, 34, 34, 3, 4};
        connection.bulkTransfer(usbEndpoint2, b, b.length, 5000);

        String s = connection.getSerial();

        return connection;
    }

    public String translateDeviceClass(int deviceClass) {
        switch (deviceClass) {
            case UsbConstants.USB_CLASS_APP_SPEC:
                return "Application specific USB class";
            case UsbConstants.USB_CLASS_AUDIO:
                return "USB class for audio devices";
            case UsbConstants.USB_CLASS_CDC_DATA:
                return "USB class for CDC devices (communications device class)";
            case UsbConstants.USB_CLASS_COMM:
                return "USB class for communication devices";
            case UsbConstants.USB_CLASS_CONTENT_SEC:
                return "USB class for content security devices";
            case UsbConstants.USB_CLASS_CSCID:
                return "USB class for content smart card devices";
            case UsbConstants.USB_CLASS_HID:
                return "USB class for human interface devices (for example, mice and keyboards)";
            case UsbConstants.USB_CLASS_HUB:
                return "USB class for USB hubs";
            case UsbConstants.USB_CLASS_MASS_STORAGE:
                return "USB class for mass storage devices";
            case UsbConstants.USB_CLASS_MISC:
                return "USB class for wireless miscellaneous devices";
            case UsbConstants.USB_CLASS_PER_INTERFACE:
                return "USB class indicating that the class is determined on a per-interface basis";
            case UsbConstants.USB_CLASS_PHYSICA:
                return "USB class for physical devices";
            case UsbConstants.USB_CLASS_PRINTER:
                return "USB class for printers";
            case UsbConstants.USB_CLASS_STILL_IMAGE:
                return "USB class for still image devices (digital cameras)";
            case UsbConstants.USB_CLASS_VENDOR_SPEC:
                return "Vendor specific USB class";
            case UsbConstants.USB_CLASS_VIDEO:
                return "USB class for video devices";
            case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
                return "USB class for wireless controller devices";
            default:
                return "Unknown USB class!";
        }
    }

    public void testing() {

        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {
            device = deviceIterator.next();


        }

        UsbInterface intf = device.getInterface(0);

//        if (intf.getEndpointCount() == 0) {
//
//            lgView.setText( lgView.getText() + "\n" +  "could not find endpoint");
//            return;
//        } else {
//            lgView.setText( lgView.getText() + "\n" + "Endpoints Count: " + intf.getEndpointCount() );
//        }

        // Точка чтения и записи
        UsbEndpoint epIN = intf.getEndpoint(0);
        UsbEndpoint epOUT = intf.getEndpoint(1);

//        for (int i = 0; i < intf.getEndpointCount(); i++) {
//            if (intf.getEndpoint(i).getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
//                if (intf.getEndpoint(i).getDirection() == UsbConstants.USB_DIR_IN) {
//                    epIN = intf.getEndpoint(i);
//                    //           lgView.setText( lgView.getText() + "\n" + "IN endpoint: " + intf.getEndpoint(i) );
//                } else {
//                    epOUT = intf.getEndpoint(i);
//                    //         lgView.setText( lgView.getText() + "\n" + "OUT endpoint: " + intf.getEndpoint(i) );
//                }
//            } else {
//                //lgView.setText( lgView.getText() + "\n" + "no endpoints for INTERRUPT_TRANSFER"); }
//            }


        //    }
        UsbDevice mDevice = device;
        UsbEndpoint mEndpointIntr = epOUT;
        UsbDeviceConnection mConnection;

        if (device != null) {
            UsbDeviceConnection connection = manager.openDevice(device);
            if (connection != null && connection.claimInterface(intf, true)) {

                //lgView.setText( lgView.getText() + "\n" + "open device SUCCESS!");
                mConnection = connection;

            } else {

                // lgView.setText( lgView.getText() + "\n" + "open device FAIL!");
                mConnection = null;
            }

            int bufferDataLength = mEndpointIntr.getMaxPacketSize();
            //lgView.setText( lgView.getText() + "\n" + mEndpointIntr.getMaxPacketSize() );

            ByteBuffer buffer = ByteBuffer.allocate(bufferDataLength + 1);

            UsbRequest request = new UsbRequest();

            buffer.put((byte) message);

            request.initialize(mConnection, mEndpointIntr);
            for (int i = 1; i < 10000; i++) {
                request.queue(buffer, bufferDataLength);

                try

                {

                    if (request.equals(mConnection.requestWait()))

                    {

                        //отправка прошла успешно
                        //lgView.setText( lgView.getText() + "\n" +  "sending CLEAR!!!");

                    }

                } catch (Exception ex) {

                    int i2;
                    i2 = 1;
                    //что-то не так...
                    //lgView.setText( lgView.getText() + "\n" +  "sending not clear...");

                }

            }
        }
        int i3;
        i3 = 1;
    }
}