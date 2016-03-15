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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import capstone.thermajust.Model.Device;

public class therm_controller extends AppCompatActivity {
    Device device;
    private boolean connected;

    static TextView currentTemp;

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
                connected = false;
            } else {
                device = Main_Tabbed_View.model.deviceList.get(extras.getInt("selection"));
                connected = extras.getBoolean("connected");
            }
        }

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton_thermControl_power);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    device.setOnoff(true);
                    sendMsg("LED ON");
                } else {
                    // The toggle is disabled
                    device.setOnoff(false);
                    sendMsg("LED OFF");
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
                String message = "TEMP SET ";
                message += setTemp.getText().toString();
                sendMsg(message);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        setTemp.setText("" + device.getTherm().getSetTemp());

        ImageButton upTemp = (ImageButton) findViewById(R.id.button_thermControl_temp_up);
        upTemp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int temp = Integer.parseInt(setTemp.getText().toString());
                temp++;
                device.getTherm().setSetTemp(temp);
                setTemp.setText("" + temp);
                //send data, should send due to text watcher
            }
        });

        ImageButton downTemp = (ImageButton) findViewById(R.id.button_thermControl_temp_down);
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
                updateCurrentTemperature();
            }
        });

        currentTemp = (TextView) findViewById(R.id.textView_thermControl_temp);
        updateCurrentTemperature();

        RadioGroup mode = (RadioGroup) findViewById(R.id.radioGroup_thermControl_group_mode);
//        RadioButton cool = (RadioButton) findViewById(R.id.radioButton_thermControl_radio_cooling);
//        RadioButton heat = (RadioButton) findViewById(R.id.radioButton_thermControl_radio_heating);
//        RadioButton both = (RadioButton) findViewById(R.id.radioButton_thermControl_radio_coolheat);
        mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton_thermControl_radio_cooling) {
                    device.getTherm().setMode("cool");
                } else if(checkedId == R.id.radioButton_thermControl_radio_heating) {
                    device.getTherm().setMode("heat");
                } else if(checkedId == R.id.radioButton_thermControl_radio_coolheat) {
                    device.getTherm().setMode("coolheat");
                }
                Main_Tabbed_View.model.saveDevices(getApplicationContext());
            }
        });
        //set mode
        switch (device.getTherm().getMode()) {
            case 1: //cool
                mode.check(R.id.radioButton_thermControl_radio_cooling);
                break;
            case 2: //heat
                mode.check(R.id.radioButton_thermControl_radio_heating);
                break;
            case 3: //coolheat
                mode.check(R.id.radioButton_thermControl_radio_coolheat);
                break;
        }
    }

    void sendMsg(String msg) {
        if (connected) {
            try {
                bluetooth_connect.sendDataOutActivity(msg);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    void updateCurrentTemperature() {
        if (connected) {
            sendMsg("GET TEMP");
            bluetooth_connect.listenForDataInController();
        }
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
