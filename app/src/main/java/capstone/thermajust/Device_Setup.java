package capstone.thermajust;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import capstone.thermajust.Model.Device;

public class Device_Setup extends AppCompatActivity {
    boolean microphoneBool, thermometerBool, videoBool;
//    int nextDevice;
    String IP = null;
    static EditText deviceID;
    static TextView status;
//    client client;

    boolean connected = false;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<BluetoothDevice> pairedDeviceArrayList;
    ArrayAdapter<BluetoothDevice> pairedDeviceAdapter;
    private UUID myUUID;
    private final String UUID_STRING_WELL_KNOWN_SPP =
            "00001101-0000-1000-8000-00805F9B34FB";
    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;
    static boolean threadRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //establish assets
        final Button findBluetooth = (Button) findViewById(R.id.button_device_Bluetooth);
        status = (TextView) findViewById(R.id.textView_device_status);
        final EditText name = (EditText) findViewById(R.id.editText_device_DeviceName);
        deviceID = (EditText) findViewById(R.id.editText_device_DeviceID_Field);
        final Switch microphone = (Switch) findViewById(R.id.switch_device_microphone);
        final Switch thermometer = (Switch) findViewById(R.id.switch_device_thermometer);
        final Switch video = (Switch) findViewById(R.id.switch_device_video);
        final EditText wifiName = (EditText) findViewById(R.id.editText_device_wifi_field);
        final EditText wifiPassword = (EditText) findViewById(R.id.editText_device_wifi_password_field);
        final Button save = (Button) findViewById(R.id.button_device_save);

        //Switch onChecked
        microphone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // The toggle is enabled
                microphoneBool = isChecked;
            }
        });
        thermometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // The toggle is enabled
                thermometerBool = isChecked;
            }
        });
        video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // The toggle is enabled
                videoBool = isChecked;
            }
        });

        //Button OnClicks
        findBluetooth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    openBT();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                sendBT("LED_ON");
//                sendBT("WIFI_JOIN_UDel_Adamengelson1");
                if(!wifiName.getText().toString().isEmpty() && !wifiPassword.getText().toString().isEmpty()) {
                    sendBT("WIFI_JOIN_" + wifiName.getText().toString() + "_" + wifiPassword.getText().toString());
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String deviceIDadjusted;
                if (deviceID.getText().toString().isEmpty()) { //if there is no bluetooth established
                    deviceIDadjusted = "Debug" +
                            Main_Tabbed_View.model.getCurrentDebugID_increment(getApplicationContext()); //generate a debug ID
                    IP = "128.4.94.94:52007"; //A computer IP
                } else {
                    deviceIDadjusted = deviceID.getText().toString();//else put in devices ID
                }
                Main_Tabbed_View.model.deviceList.add(new Device(
                        name.getText().toString(),
                        deviceIDadjusted,
                        false, //devices start off
                        thermometerBool,
                        microphoneBool,
                        videoBool,
                        wifiName.getText().toString(),
                        wifiPassword.getText().toString(),
                        IP,
                        null //therm object
                ));

                Main_Tabbed_View.model.saveDevices(getApplicationContext());

                Snackbar snackbar = Snackbar.make(view, "Device Saved", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        //set wifi defaults
        String wifiDefaultName = Main_Tabbed_View.model.getWiFiDefaultName();
        String wifiDefaultPassword = Main_Tabbed_View.model.getWiFiDefaultPassword();
        if (wifiDefaultName != null) {
            wifiName.setText(wifiDefaultName);
            if (wifiDefaultPassword != null) { //shouldn't just fill in a password
                wifiPassword.setText(wifiDefaultPassword);
            }
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeBT();
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void openBT() throws Exception {
        //using the well-known SPP UUID
        threadRun = true;
        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            connected = false;
            return;
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
                deviceID.setText(connectedDevice.getAddress());
                myThreadConnectBTdevice = new ThreadConnectBTdevice(connectedDevice);
                myThreadConnectBTdevice.start();
                connected = true;
            }
        }

        return;
    }
    public boolean closeBT() {
        threadRun = false;
        if (myThreadConnected!=null) {
            myThreadConnected.cancel();
        }
        if(myThreadConnectBTdevice!=null){
            myThreadConnectBTdevice.cancel();
        }
        return false;
    }
    public void sendBT(String message) {
        if(myThreadConnected!=null){
            byte[] bytesToSend = message.getBytes();
            myThreadConnected.write(bytesToSend);
        }
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

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if(success){
                startThreadConnected(bluetoothSocket);
            }else{
                //fail
            }
        }

        public void cancel() {

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
    String strReceived = "";
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
                e.printStackTrace();
            }

            connectedInputStream = in;
            connectedOutputStream = out;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (threadRun) {
                try {

                    bytes = connectedInputStream.read(buffer);
                    strReceived = strReceived + new String(buffer, 0, bytes);
                    final String msgReceived = String.valueOf(bytes);

//                  try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            if (strReceived.contains("\n") && !strReceived.isEmpty()) {
                                try {
                                    strReceived = strReceived.split("GMT\r\n")[1];
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //do nothing, we did not receive time
                                }
                                status.setText(strReceived);

                                //Add response code here
                                if (strReceived.contains("Connected to ")) {
                                    sendBT("WIFI_INFO");
                                } else if (strReceived.contains("IP:")) {
                                    IP = strReceived.trim().split(":")[1] + ":80";
                                }

                                strReceived = "";
                            }
                        }});

                } catch (IOException e) {
                    e.printStackTrace();

//                    final String msgConnectionLost = "Connection lost:\n"
//                            + e.getMessage();
//                    runOnUiThread(new Runnable(){
//
//                        @Override
//                        public void run() {
//                            status.setText(msgConnectionLost);
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
