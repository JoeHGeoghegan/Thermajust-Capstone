package capstone.thermajust;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import capstone.thermajust.clients.tcpClient;
import capstone.thermajust.Model.Device;

public class Joined_Controller extends AppCompatActivity {
    private ArrayList<String> chatLog = new ArrayList<>();


    ArrayList<Device> devices = new ArrayList<>();
    private ArrayList<tcpClient> clients = new ArrayList<>();

    static TextView currentTemp;
    boolean tog;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getting and setting device
        boolean usingTempLayout = false;

        // connect to the server
        new connectTask().execute("");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                devices = null;
            } else {
                if (extras.getBoolean("single")) {
                    devices.add(Main_Tabbed_View.model.deviceList.get(extras.getInt("selection")));
                }
                else {
                    devices = Main_Tabbed_View.model.groupList.get(extras.getInt("selection")).devices;
                }
                if (extras.getString("mode").compareTo("temp") != 0) {
                    setContentView(R.layout.activity_base_controller);
                    usingTempLayout = false;
                }
                else {
                    setContentView(R.layout.activity_therm_controller);
                    usingTempLayout = true;
                }
                String type = extras.getString("type");
                for (int i = 0; i < devices.size() ; i++) {
                        clients.add(new tcpClient(devices.get(i).getIp().split(":")[0],
                                Integer.parseInt(devices.get(i).getIp().split(":")[1]), new tcpClient.OnMessageReceived() {
                            @Override
                            //here the messageReceived method is implemented
                            public void messageReceived(String message) {
                                //this method calls the onProgressUpdate
//                                publishProgress(message);
                            }
                        }));
                }
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton_control_power);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    for (Device device: devices) { device.setOnoff(true); }
                    tog = true;
                    broadcast("LED_ON");
                } else {
                    // The toggle is disabled
                    for (Device device: devices) { device.setOnoff(false); }
                    tog = false;
                    broadcast("LED_OFF");
                }
                Main_Tabbed_View.model.saveDevices(getApplicationContext());
            }
        });
        toggle.setChecked(tog); //sets the current mode

        if (usingTempLayout) {
            final TextView setTemp = (TextView) findViewById(R.id.textView_thermControl_setTemp);
            setTemp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //send data
                    message = "TEMP_SET_";
                    message += setTemp.getText().toString();
                    broadcast(message);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            if (devices.size() == 1) {
                setTemp.setText("" + devices.get(0).getTherm().getSetTemp());
            }

            ImageButton upTemp = (ImageButton) findViewById(R.id.button_thermControl_temp_up);
            upTemp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int temp = Integer.parseInt(setTemp.getText().toString());
                    temp++;
                    for (Device device: devices) {
                        if (device.getUseTemp()) {
                            device.getTherm().setSetTemp(temp);
                        }
                    }
                    setTemp.setText("" + temp);
                    //send data, should send due to text watcher
                }
            });

            ImageButton downTemp = (ImageButton) findViewById(R.id.button_thermControl_temp_down);
            downTemp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int temp = Integer.parseInt(setTemp.getText().toString());
                    temp--;
                    for (Device device: devices) {
                        if (device.getUseTemp()) {
                            device.getTherm().setSetTemp(temp);
                        }
                    }
                    setTemp.setText("" + temp);
                    //send data, done by text watcher on change
                }
            });

            Button updateTemp = (Button) findViewById(R.id.button_thermControl_tempUpdate);
            updateTemp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
//                    updateCurrentTemperature();
                }
            });

            currentTemp = (TextView) findViewById(R.id.textView_thermControl_temp);
//            updateCurrentTemperature();

            RadioGroup mode = (RadioGroup) findViewById(R.id.radioGroup_thermControl_group_mode);
//        RadioButton cool = (RadioButton) findViewById(R.id.radioButton_thermControl_radio_cooling);
//        RadioButton heat = (RadioButton) findViewById(R.id.radioButton_thermControl_radio_heating);
//        RadioButton both = (RadioButton) findViewById(R.id.radioButton_thermControl_radio_coolheat);
            mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.radioButton_thermControl_radio_cooling) {
                        for (Device device: devices) {
                            if (device.getUseTemp()) {
                                device.getTherm().setMode("cool");
                            }
                        }
                    } else if (checkedId == R.id.radioButton_thermControl_radio_heating) {
                        for (Device device: devices) {
                            if (device.getUseTemp()) {
                                device.getTherm().setMode("heat");
                            }
                        }
                    }
                    Main_Tabbed_View.model.saveDevices(getApplicationContext());
                }
            });
            //set mode
            if (devices.size() == 1) {
                switch (devices.get(0).getTherm().getMode()) {
                    case 1: //cool
                        mode.check(R.id.radioButton_thermControl_radio_cooling);
                        break;
                    case 2: //heat
                        mode.check(R.id.radioButton_thermControl_radio_heating);
                        break;
                }
            }
        }
    }

    void broadcast(String msg) {
        Toast.makeText(getApplicationContext(), "Sent: " + message, Toast.LENGTH_LONG);
        for (int i = 0; i < clients.size() ; i++) {
            sendMsg(clients.get(i), msg);
        }
    }
    void sendMsg(tcpClient client, String msg) {
        client.send(msg);
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

    public class connectTask extends AsyncTask<String,String,tcpClient> {

        @Override
        protected tcpClient doInBackground(String... message) {
            for (int i = 0; i < devices.size(); i++) {
                //we create a TCPClient object and
                String[] ipTemp = devices.get(i).getIp().split(":");
                clients.add(new tcpClient(ipTemp[0], Integer.parseInt(ipTemp[1]),
                        new tcpClient.OnMessageReceived() {
                    @Override
                    //here the messageReceived method is implemented
                    public void messageReceived(String message) {
                        //this method calls the onProgressUpdate
                        publishProgress(message);
                        Toast.makeText(getApplicationContext(), "Received: " + message, Toast.LENGTH_LONG);
                    }
                }));
                clients.get(i).run();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            chatLog.add(values[0]);
        }
    }
}
