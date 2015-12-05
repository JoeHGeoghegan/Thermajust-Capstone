package capstone.thermajust;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import capstone.thermajust.Model.Device;

public class therm_controller extends AppCompatActivity {
    Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therm_controller);
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

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton_thermControl_power);
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

        final TextView setTemp = (TextView) findViewById(R.id.textView_thermControl_setTemp);
        setTemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //send data
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        setTemp.setText("" + device.getTherm().getSetTemp());

        Button upTemp = (Button) findViewById(R.id.button_thermControl_temp_up);
        upTemp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int temp = Integer.parseInt(setTemp.getText().toString());
                temp++;
                device.getTherm().setSetTemp(temp);
                setTemp.setText("" + temp);
                //send data, should send due to text watcher
            }
        });

        Button downTemp = (Button) findViewById(R.id.button_thermControl_temp_down);
        downTemp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int temp = Integer.parseInt(setTemp.getText().toString());
                temp--;
                device.getTherm().setSetTemp(temp);
                setTemp.setText("" + temp);
                //send data, should send due to text watcher
            }
        });

        Button updateTemp = (Button) findViewById(R.id.button_thermControl_tempUpdate);
        updateTemp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                updateCurrentTemperature();
            }
        });

        final TextView currentTemp = (TextView) findViewById(R.id.textView_thermControl_temp);
//        updateCurrentTemperature();
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
