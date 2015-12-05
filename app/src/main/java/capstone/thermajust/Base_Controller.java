package capstone.thermajust;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import capstone.thermajust.Model.Device;

public class Base_Controller extends AppCompatActivity {
    Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_controller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting and setting device
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                device = null;
            } else {
                device = Main_Tabbed_View.model.deviceList.get(extras.getInt("selection"));
            }
        } else {
            //hopefully will not happen
        }

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton_controller_power);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    device.setOnoff(true);
                } else {
                    // The toggle is disabled
                    device.setOnoff(false);
                }
                Main_Tabbed_View.model.saveDevices(getApplicationContext());
            }
        });
        toggle.setChecked(device.getOnoff()); //sets the current mode


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Main_Tabbed_View.model.saveDevices(this);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
