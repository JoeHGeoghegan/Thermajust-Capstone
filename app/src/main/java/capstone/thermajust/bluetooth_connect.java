package capstone.thermajust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.bluetooth. *;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class bluetooth_connect extends AppCompatActivity {

    TextView myLabel;
    TextView txt_bluetoothStatus;
    BluetoothAdapter btAdapter;
    private ListView listview_devices;
    public Set<BluetoothDevice> device;
    BluetoothDevice myDevice;
    BluetoothSocket Socket;
    OutputStream OutStream;
    InputStream InStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                if(device.getName().equals("Thermajust")){
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

    }

    void closeBT() throws IOException
    {
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
}
