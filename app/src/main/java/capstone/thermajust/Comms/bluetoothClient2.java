package capstone.thermajust.Comms;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Joe Geoghegan on 5/9/2016.
 */
public class bluetoothClient2 extends client {
    private static final int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter bluetoothAdapter;

    ArrayList<BluetoothDevice> pairedDeviceArrayList;

    ArrayAdapter<BluetoothDevice> pairedDeviceAdapter;
    private UUID myUUID;
    private final String UUID_STRING_WELL_KNOWN_SPP =
            "00001101-0000-1000-8000-00805F9B34FB";

    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;

    String name = "";

    @Override
    public boolean open() throws Exception {
        //using the well-known SPP UUID
        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return false;
        }

        //done, gets own devices name
//        String stInfo = bluetoothAdapter.getName() + "\n" +
//                bluetoothAdapter.getAddress();
//        textInfo.setText(stInfo);
//        name = bluetoothAdapter.getAddress();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            pairedDeviceArrayList = new ArrayList<BluetoothDevice>();

            //done, this is for making and choosing the device, need to make a specific
            //example, be sure to include setting name, and starting the threads
            BluetoothDevice connectedDevice = null;
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().compareTo("HC-06")==0) {
                    connectedDevice = device;
                }
            }
            if (connectedDevice != null) {
                myThreadConnectBTdevice = new ThreadConnectBTdevice(connectedDevice);
                myThreadConnectBTdevice.start();
            }
//            for (BluetoothDevice device : pairedDevices) {
//                pairedDeviceArrayList.add(device);
//            }
//            pairedDeviceAdapter = new ArrayAdapter<BluetoothDevice>(this,
//                    android.R.layout.simple_list_item_1, pairedDeviceArrayList);
//            listViewPairedDevice.setAdapter(pairedDeviceAdapter);
//
//            listViewPairedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    BluetoothDevice device =
//                            (BluetoothDevice) parent.getItemAtPosition(position);
//                    Toast.makeText(MainActivity.this,
//                            "Name: " + device.getName() + "\n"
//                                    + "Address: " + device.getAddress() + "\n"
//                                    + "BondState: " + device.getBondState() + "\n"
//                                    + "BluetoothClass: " + device.getBluetoothClass() + "\n"
//                                    + "Class: " + device.getClass(),
//                            Toast.LENGTH_LONG).show();
//
//                    textStatus.setText("start ThreadConnectBTdevice");
//                    myThreadConnectBTdevice = new ThreadConnectBTdevice(device);
//                    myThreadConnectBTdevice.start();
//                }
//            });
        }

        return false;
    }

    @Override
    public boolean close() {
        if(myThreadConnectBTdevice!=null){
            myThreadConnectBTdevice.cancel();
        }
        return false;
    }

    @Override
    public void send(String message) {
        if(myThreadConnected!=null){
            byte[] bytesToSend = message.getBytes();
            myThreadConnected.write(bytesToSend);
        }


    }

    @Override
    public void listen() {

    }

    @Override
    public String getName() {
        return name;
    }

    //Called in ThreadConnectBTdevice once connect successed
    //to start ThreadConnected
    private void startThreadConnected(BluetoothSocket socket){
        myThreadConnected = new ThreadConnected(socket);
        myThreadConnected.start();
    }

    /*
    ThreadConnectBTdevice:
    Background Thread to handle BlueTooth connecting
    */
    private class ThreadConnectBTdevice extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        private ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
//                textStatus.setText("bluetoothSocket: \n" + bluetoothSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean success = false;
            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                final String eMessage = e.getMessage();
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        textStatus.setText("something wrong bluetoothSocket.connect(): \n" + eMessage);
//                    }
//                });

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            if(success){
                //connect successful
                final String msgconnected = "connect successful:\n"
                        + "BluetoothSocket: " + bluetoothSocket + "\n"
                        + "BluetoothDevice: " + bluetoothDevice;

//                runOnUiThread(new Runnable(){
//
//                    @Override
//                    public void run() {
//                        textStatus.setText(msgconnected);
//
//                        listViewPairedDevice.setVisibility(View.GONE);
//                        inputPane.setVisibility(View.VISIBLE);
//                    }});

                startThreadConnected(bluetoothSocket);
            }else{
                //fail
            }
        }

        public void cancel() {

//            Toast.makeText(getApplicationContext(),
//                    "close bluetoothSocket",
//                    Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /*
    ThreadConnected:
    Background Thread to handle Bluetooth data communication
    after connected
     */
    private class ThreadConnected extends Thread {
        private final BluetoothSocket connectedBluetoothSocket;
        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;

        public ThreadConnected(BluetoothSocket socket) {
            connectedBluetoothSocket = socket;
            InputStream in = null;
            OutputStream out = null;

            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            connectedInputStream = in;
            connectedOutputStream = out;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = connectedInputStream.read(buffer);
                    String strReceived = new String(buffer, 0, bytes);
                    final String msgReceived = String.valueOf(bytes) +
                            " bytes received:\n"
                            + strReceived;

//                    runOnUiThread(new Runnable(){
//
//                        @Override
//                        public void run() {
//                            textStatus.setText(msgReceived);
//                        }});

                } catch (IOException e) {
                    e.printStackTrace();

                    final String msgConnectionLost = "Connection lost:\n"
                            + e.getMessage();
//                    runOnUiThread(new Runnable(){
//
//                        @Override
//                        public void run() {
//                            textStatus.setText(msgConnectionLost);
//                        }});
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                connectedBluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
