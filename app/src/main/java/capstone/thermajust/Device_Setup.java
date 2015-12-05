package capstone.thermajust;

//import android.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import capstone.thermajust.Model.Device;
import capstone.thermajust.Model.Main_Model;

public class Device_Setup extends AppCompatActivity {
    boolean microphoneBool, thermometerBool, videoBool;

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

        final Button clear = (Button) findViewById(R.id.button_device_clear);

        //Switch onChecked
        microphone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    microphoneBool = true;
                } else {
                    microphoneBool = false;
                }
            }
        });
        thermometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    thermometerBool = true;
                } else {
                    thermometerBool = false;
                }
            }
        });
        video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    videoBool = true;
                } else {
                    videoBool = false;
                }
            }
        });

        //Button OnClicks
        findBluetooth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toBluetoothConnect();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String deviceIDadjusted;
                if (deviceID.getText().toString().isEmpty()) { //if there is no bluetooth established
                    deviceIDadjusted = "NoBluetoothSet" + Main_Tabbed_View.model.deviceList.size(); //generate an ID until
                } else {
                    deviceIDadjusted = deviceID.getText().toString();
                }//else put in devices ID
                Main_Tabbed_View.model.deviceList.add(new Device(
                        name.getText().toString(),
                        deviceIDadjusted,
                        false, //devices start off
                        thermometerBool,
                        microphoneBool,
                        videoBool,
                        wifiName.getText().toString(),
                        wifiPassword.getText().toString(),
                        null //TODO fix this? Maybe just create & save when settings for it are actually done
                ));

                Main_Tabbed_View.model.saveDevices(getApplicationContext());

                Snackbar snackbar = Snackbar.make(view, "Device Saved", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Main_Tabbed_View.model.deviceList.clear();

                Main_Tabbed_View.model.saveDevices(getApplicationContext());

                Snackbar snackbar = Snackbar.make(view, "Devices cleared!!!", Snackbar.LENGTH_LONG);
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

    public void toBluetoothConnect() {
        Intent myIntent = new Intent(Device_Setup.this, bluetooth_connect.class);
        Device_Setup.this.startActivity(myIntent);
    }
}
