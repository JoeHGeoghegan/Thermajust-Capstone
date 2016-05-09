package capstone.thermajust;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import capstone.thermajust.Model.Device;
import capstone.thermajust.Model.Main_Model;

public class Edit_Device extends AppCompatActivity {
    boolean microphoneBool, thermometerBool, videoBool;
    int deviceSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting and setting device
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                deviceSelected = Main_Tabbed_View.model.deviceList.size(); //puts at the end if you are somehow editing a non existent device
            } else {
                deviceSelected = extras.getInt("selectedDevice");
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //establish assets
        final Button findBluetooth = (Button) findViewById(R.id.button_deviceEdit_Bluetooth);
        final EditText name = (EditText) findViewById(R.id.editText_deviceEdit_DeviceName);
        final EditText deviceID = (EditText) findViewById(R.id.editText_deviceEdit_DeviceID_Field);
        final Switch microphone = (Switch) findViewById(R.id.switch_deviceEdit_microphone);
        final Switch thermometer = (Switch) findViewById(R.id.switch_deviceEdit_thermometer);
        final Switch video = (Switch) findViewById(R.id.switch_deviceEdit_video);
        final EditText wifiName = (EditText) findViewById(R.id.editText_deviceEdit_wifi_field);
        final EditText wifiPassword = (EditText) findViewById(R.id.editText_deviceEdit_wifi_password_field);
        final Button save = (Button) findViewById(R.id.button_deviceEdit_save);
        final Button delete = (Button) findViewById(R.id.button_deviceEdit_delete);

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

                Main_Tabbed_View.model.deviceList.set(deviceSelected, new Device(
                        name.getText().toString(),
                        deviceIDadjusted,
                        false, //devices start off
                        thermometerBool,
                        microphoneBool,
                        videoBool,
                        wifiName.getText().toString(),
                        wifiPassword.getText().toString(),
                        null,
                        null
                ));

                Main_Tabbed_View.model.saveDevices(getApplicationContext());

                Snackbar snackbar = Snackbar.make(view, "Device Saved", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Main_Tabbed_View.model.deviceList.remove(deviceSelected);
                Main_Tabbed_View.model.saveDevices(getApplicationContext());
                finish(); //closes device edit
            }
        });

        //set defaults
        Device temp = Main_Tabbed_View.model.deviceList.get(deviceSelected);
        name.setText(temp.getName());
        deviceID.setText(temp.getIdNum());
        microphone.setChecked(temp.getUseMic());
        thermometer.setChecked(temp.getUseTemp());
        video.setChecked(temp.getUseVid());
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
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void toBluetoothConnect() {
        Intent myIntent = new Intent(Edit_Device.this, bluetooth_connect.class);
        Edit_Device.this.startActivity(myIntent);
    }
}
