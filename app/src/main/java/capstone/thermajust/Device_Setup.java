package capstone.thermajust;

//import android.app.ActionBar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import capstone.thermajust.Comms.bluetoothClient;
import capstone.thermajust.Comms.bluetoothClient2;
import capstone.thermajust.Comms.client;
import capstone.thermajust.Model.Device;
import capstone.thermajust.Model.Main_Model;

public class Device_Setup extends AppCompatActivity {
    boolean microphoneBool, thermometerBool, videoBool;
//    int nextDevice;
    String IP = null;
    static String wifiInfo = "Scan not run yet";
    client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //establish assets
        final Button findBluetooth = (Button) findViewById(R.id.button_device_Bluetooth);
        final EditText name = (EditText) findViewById(R.id.editText_device_DeviceName);
        final EditText deviceID = (EditText) findViewById(R.id.editText_device_DeviceID_Field);
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
                if (client == null) {
                    client = new bluetoothClient2(getBaseContext());
                }
                if (client.connected) {
                    deviceID.setText(client.getName());
                    client.send("LED_ON");

//                    Snackbar.make(view, client.txt, Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                    client.send("WIFI_JOIN_UDel_Adamengelson1");
//                    String msg;
//                    client.listen();
//                    boolean established = false;
//                    if (client.txt.compareTo("Connected to " + wifiName.getText().toString())==0) {
//                        established = true;
//                    }
//                    if (established) {
//                        Snackbar.make(view, "Established Connection!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                        msg = "WIFI_INFO";
//                        client.send(msg);
//                        client.listen();
//                        IP = client.txt;
//                        IP = IP + ":80";
//                    }
//                    else {
//                        Snackbar.make(view, "Failed to Establish Connection!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                        msg = "WIFI_SCAN";
//                        client.send(msg);
//                        client.listen();
//                        openWifiHelp(client.txt);
//                    }
//                    String msg = "WIFI_SCAN";
//                    client.send(msg);
//                    client.txt="buffering";
//                    client.listen();
//                    while (client.txt.compareTo("buffering") == 0);
//                    if (client.txt.compareTo(wifiName.getText().toString())!=0) {
//                        openWifiHelp(client.txt);
//                    }
//                    Boolean established = false;
//                    while (!established) {
//                        msg = "WIFI_JOIN_"+
//                                wifiName.getText().toString()+"_"+
//                                wifiPassword.getText().toString();
//                        client.send(msg);
//                        client.listen();
//                        if (client.txt.compareTo("Connected to " + wifiName.getText().toString())==0) {
//                            established = true;
//                        }
//                    }
//                    if (established) {
//                        Snackbar.make(view, "Established Connection!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                        msg = "WIFI_INFO";
//                        client.send(msg);
//                        client.listen();
//                        IP = client.txt;
//                        IP = IP + ":80";
//                    }
//                    else {
//                        Snackbar.make(view, "Failed to Establish Connection!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                        msg = "WIFI_SCAN";
//                        client.send(msg);
//                        client.listen();
//                        openWifiHelp(client.txt);
//                    }
                }
                else {
                    Snackbar.make(view, "A device not connected correctly", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String deviceIDadjusted;
                if (deviceID.getText().toString().isEmpty()) { //if there is no bluetooth established
                    deviceIDadjusted = "Debug" +
                            Main_Tabbed_View.model.getCurrentDebugID_increment(getApplicationContext()); //generate a debug ID
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
                        null, //therm object
                        IP
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
                client.close();
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void openWifiHelp(String info) {
        wifiInfo = info;
        DialogFragment newFragment = helpWifi.newInstance(R.string.wifiInfo);
        newFragment.show(getFragmentManager(), "helpWifi");
    }
    public static class helpWifi extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(wifiInfo)
                    .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Cancel
                        }
                    });
            return builder.create();
        }

        public static helpWifi newInstance(int title) {
            helpWifi frag = new helpWifi();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }
    }
}
