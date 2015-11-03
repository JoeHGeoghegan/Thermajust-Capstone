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
import android.widget.EditText;
import android.widget.Switch;

import capstone.thermajust.Model.Main_Model;

public class Device_Setup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //establish assets
        final Button findBluetooth = (Button)findViewById(R.id.button_device_Bluetooth);
        final EditText name = (EditText)findViewById(R.id.editText_device_DeviceName);
        final Switch microphone = (Switch)findViewById(R.id.switch_device_microphone);
        final Switch thermometer = (Switch)findViewById(R.id.switch_device_thermometer);
        final  Switch video = (Switch)findViewById(R.id.switch_device_video);
        final EditText wifiName = (EditText)findViewById(R.id.editText_device_wifi_field);
        final EditText wifiPassword = (EditText)findViewById(R.id.editText_device_wifi_password_field);
        final Button save = (Button)findViewById(R.id.button_device_save);

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
}
