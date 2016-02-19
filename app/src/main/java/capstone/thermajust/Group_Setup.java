package capstone.thermajust;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import capstone.thermajust.Model.CA_group_checklist;
import capstone.thermajust.Model.Device;
import capstone.thermajust.Model.Group;
import capstone.thermajust.Model.node;

public class Group_Setup extends AppCompatActivity {

    private CA_group_checklist customAdapterGroup;
    private static ArrayList<Device> includedDevices;
    private int mode, groupPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        includedDevices = new ArrayList<Device>();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mode = 1; //defaults to new
            } else {
                mode = extras.getInt("mode");
                if (mode == 0) {
                    groupPos = extras.getInt("groupPosition");
                    includedDevices = Main_Tabbed_View.model.groupList.get(groupPos).devices;
                }
            }
        } else {
            //hopefully will not happen
        }

        ArrayList<node.groupCheck> devices = new ArrayList<node.groupCheck>();

        for (int i = 0; i < Main_Tabbed_View.model.deviceList.size() ; i++) { //for each existing device
            Device temp = Main_Tabbed_View.model.deviceList.get(i);

            if (mode == 0 && includedDevices != null) { //if there is a point to check contains
                if (includedDevices.contains(temp)) { //if devices are in group
                    devices.add(new node.groupCheck(temp.getName(), i, true));
                }
            }
            else { //if a new group or devices do not belong to group
                devices.add(new node.groupCheck(temp.getName(), i, false));
            }
        }

        customAdapterGroup = new CA_group_checklist(this,
                devices,
                getResources());

        final ListView deviceList = (ListView) findViewById(R.id.listView_group_devices);
        deviceList.setAdapter(customAdapterGroup);

        Button save = (Button) findViewById(R.id.button_group_save);
        Button delete = (Button) findViewById(R.id.button_group_delete);

        final EditText name = (EditText) findViewById(R.id.editText_group_name);

        //OnClicks
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if new or editing add into model the correct way
                switch (mode) {
                    case 0: //editing
                        Main_Tabbed_View.model.groupList.set(groupPos, new Group(name.getText().toString(), includedDevices));
                        break;
                    default: //new
                        Main_Tabbed_View.model.groupList.add(new Group(name.getText().toString(), includedDevices));
                        break;
                }

                //and save the model
                Main_Tabbed_View.model.saveGroups(getApplicationContext());
            }
        });
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

    public static Boolean deviceInGroup(Device device) {
        if(includedDevices != null) {
            if (includedDevices.contains(device)) {
                return true;
            }
        }
        return false; //not in array
    }


    //node helper methods
    public static void includedDevicesAdd(Device device) {
        includedDevices.add(device);
    }
    public static void includedDevicesRemove(Device device) {
        includedDevices.remove(device);
    }
}
