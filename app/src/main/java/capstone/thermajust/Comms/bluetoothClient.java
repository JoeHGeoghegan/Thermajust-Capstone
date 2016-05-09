package capstone.thermajust.Comms;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe Geoghegan on 3/22/2016.
 */
public class bluetoothClient extends client{
    //connection elements
    static BluetoothAdapter btAdapter;
    static public Set<BluetoothDevice> device;
    static BluetoothDevice myDevice;
    static BluetoothSocket Socket;
    static OutputStream OutStream;
    static InputStream InStream;
    static volatile boolean stopWorker;
    static Thread workerThread;
    static byte[] readBuffer;
    static int readBufferPos;

    public bluetoothClient() {
        connected = open();
    }

    public boolean open() {
//        findBT
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter == null){
            return false;
        }

        if (btAdapter != null){
            if (!btAdapter.isEnabled()) {
                return false;
            }
        }

        else{
//            String mydeviceaddress = btAdapter.getAddress();
//            String mydevicename = btAdapter.getName();

//            String status = mydevicename + " : " + mydeviceaddress;
//            txt_bluetoothStatus.setText(status);
        }

        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

        if (pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                String[] tokens = device.getName().split("-"); //Thermajust1-ID#
                if(tokens[0].equals("HC")){
                    myDevice = device;
                    break;
                }
            }
        }
//        openBT
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
            Socket = myDevice.createRfcommSocketToServiceRecord(uuid);
            Socket.connect();
            OutStream = Socket.getOutputStream();
            InStream = Socket.getInputStream();

            ListenForData();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean close() {
        if (connected) {
            try {
                stopWorker = true;
                OutStream.close();
                InStream.close();
                Socket.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        else {
            return true;
        }
    }
    public void send(String message) {
        if (connected) {
            try {
                OutStream.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void listen() {
        if (connected) {
            ListenForData();
        }
    }

    void ListenForData(){
        final Handler handler = new Handler();
        final byte delimiter = 10; //ASCII code for a newline character

        stopWorker = false;
        readBufferPos = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
            while(!Thread.currentThread().isInterrupted() && !stopWorker) {
                try {
                    int bytesAvailable = InStream.available();
                    if(bytesAvailable > 0) {
                        byte[] packetBytes = new byte[bytesAvailable];
                        InStream.read(packetBytes);
                        for(int i=0;i<bytesAvailable;i++) {
                            byte b = packetBytes[i];
                            if(b == delimiter) {
                                byte[] encodedBytes = new byte[readBufferPos];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");
                                readBufferPos = 0;

                                handler.post(new Runnable()
                                {
                                    public void run()
                                    {
                                        txt = data;
                                    }
                                });
                            }
                            else {
                                readBuffer[readBufferPos++] = b;
                            }
                        }
                    }
                }
                catch (IOException ex) {
                    stopWorker = true;
                }
            }
            }
        });
        workerThread.start();
    }

    public String getName() {
        String name;
        name = myDevice.getName();
        name = name.split("-")[1];
        return name;
    }
}
