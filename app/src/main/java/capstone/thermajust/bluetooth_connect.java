package capstone.thermajust;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.bluetooth. *;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class bluetooth_connect extends AppCompatActivity {

    //UI elements
    TextView myLabel;
    TextView txt_bluetoothStatus;
    Button findBT;
    Button openBT;
    EditText myTextbox;
    Button send;
    private ListView listview_devices;

    //connection elements
    BluetoothAdapter btAdapter;
    public Set<BluetoothDevice> device;
    BluetoothDevice myDevice;
    BluetoothSocket Socket;
    OutputStream OutStream;
    InputStream InStream;
    volatile boolean stopWorker;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //all view elements that need to be edited
        myLabel = (TextView) findViewById(R.id.textView_bluetoothconnect_myLabel);
        txt_bluetoothStatus = (TextView) findViewById(R.id.textView_bluetoothconnect_bluetoothStatus);
        openBT = (Button) findViewById(R.id.button_bluetoothconnect_openBT);
        findBT = (Button) findViewById(R.id.button_bluetoothconnect_findBT);
        myTextbox = (EditText) findViewById(R.id.editText_bluetoothconnect_myTextbox);
        send = (Button) findViewById(R.id.button_bluetoothconnect_send);
        listview_devices = (ListView) findViewById(R.id.listView_bluetoothconnect_devices);

        //button listeners

        findBT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    findBT();
                }catch(Exception e){ //TODO NEED TO PROPERLY HANDLE THIS
                    e.printStackTrace();
                }
            }
        });

        openBT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    openBT();
                }catch(Exception e){ //TODO NEED TO PROPERLY HANDLE THIS
                    e.printStackTrace();
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    sendData();
                }catch(Exception e){ //TODO NEED TO PROPERLY HANDLE THIS
                    e.printStackTrace();
                }
            }
        });

        //list set
        ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Main_Tabbed_View.model.getDeviceNames());
        listview_devices.setAdapter(arrayAdapter);
        //list listener
        listview_devices.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openController(position);
            }
        });
    }

    void findBT(){
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter == null){
            myLabel.setText("Bluetooth not available");
        }

        if (!btAdapter.isEnabled() && btAdapter != null){
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        else{
            String mydeviceaddress = btAdapter.getAddress();
            String mydevicename = btAdapter.getName();

            String status = mydevicename + " : " + mydeviceaddress;
            txt_bluetoothStatus.setText(status);
        }

        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

        if (pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                String[] tokens = device.getName().split("-"); //Thermajust1-ID#
                if(tokens[0].equals("dragon")){
//                    tokens[1];
                    myDevice = device;
                    break;
                }
            }
        }
        myLabel.setText("Bluetooth Device Found");

    }

    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        Socket = myDevice.createRfcommSocketToServiceRecord(uuid);
        Socket.connect();
        OutStream = Socket.getOutputStream();
        InStream = Socket.getInputStream();

        ListenForData();

        myLabel.setText("Bluetooth Opened");
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
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = InStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            InStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPos];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPos = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            myLabel.setText(data);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPos++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    void sendData() throws IOException
    {
        String msg = myTextbox.getText().toString();
        msg += "\n";
        OutStream.write(msg.getBytes());
        myLabel.setText("Data is Sent");
    }

    void closeBT() throws IOException
    {
        stopWorker = true;
        OutStream.close();
        InStream.close();
        Socket.close();
        myLabel.setText("Bluetooth Closed");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void openController(int position) {
        Intent myIntent = new Intent(bluetooth_connect.this, Base_Controller.class);
        myIntent.putExtra("selection", position);
        bluetooth_connect.this.startActivity(myIntent);
    }
}
