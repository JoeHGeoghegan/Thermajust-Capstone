package capstone.thermajust;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import capstone.thermajust.ListAdapterElements.CA_group_checklist;
import capstone.thermajust.ListAdapterElements.node;
import capstone.thermajust.Model.Device;
import capstone.thermajust.Model.Schedule;

public class Schedule_Setup extends AppCompatActivity {
    private CA_group_checklist customAdapterApplied;
    private int mode, schedPos;
    private static ArrayList<Device> appliedDevices;
    private Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mode = 1; //defaults to new
            } else {
                mode = extras.getInt("mode");
                if (mode == 0) {
                    schedPos = extras.getInt("schedPosition");
                    appliedDevices = Main_Tabbed_View.model.scheduleList.get(schedPos).getApplied().devices;
                }
            }
        } else {
            //hopefully will not happen
        }

        EditText name = (EditText) findViewById(R.id.editText_schedule_name);
        Button save = (Button) findViewById(R.id.button_schedule_save);

        final TextView sunS = (TextView) findViewById(R.id.textView_schedule_start_sun);
        final TextView monS = (TextView) findViewById(R.id.textView_schedule_start_mon);
        final TextView tueS = (TextView) findViewById(R.id.textView_schedule_start_tue);
        final TextView wedS = (TextView) findViewById(R.id.textView_schedule_start_wed);
        final TextView thuS = (TextView) findViewById(R.id.textView_schedule_start_thu);
        final TextView friS = (TextView) findViewById(R.id.textView_schedule_start_fri);
        final TextView satS = (TextView) findViewById(R.id.textView_schedule_start_sat);
        TextView timeS = (TextView) findViewById(R.id.textView_schedule_timeStart);
        ToggleButton powS = (ToggleButton) findViewById(R.id.toggle_schedule_start_power);
        EditText tempS = (EditText) findViewById(R.id.editText_schedule_start_temp);

        final TextView sunE = (TextView) findViewById(R.id.textView_schedule_end_sun);
        final TextView monE = (TextView) findViewById(R.id.textView_schedule_end_mon);
        final TextView tueE = (TextView) findViewById(R.id.textView_schedule_end_tue);
        final TextView wedE = (TextView) findViewById(R.id.textView_schedule_end_wed);
        final TextView thuE = (TextView) findViewById(R.id.textView_schedule_end_thu);
        final TextView friE = (TextView) findViewById(R.id.textView_schedule_end_fri);
        final TextView satE = (TextView) findViewById(R.id.textView_schedule_end_sat);
        TextView timeE = (TextView) findViewById(R.id.textView_schedule_timeEnd);
        ToggleButton powE = (ToggleButton) findViewById(R.id.toggle_schedule_end_power);
        EditText tempE = (EditText) findViewById(R.id.editText_schedule_end_temp);

        ListView list = (ListView) findViewById(R.id.listView_schedule_list);

        //Underlined Versions of Dates
            //Can set with text.setText(textU)
        final SpannableString SunU = new SpannableString("Sun");
        SunU.setSpan(new UnderlineSpan(), 0, SunU.length(), 0);
        final SpannableString MonU = new SpannableString("Mon");
        MonU.setSpan(new UnderlineSpan(), 0, MonU.length(), 0);
        final SpannableString TueU = new SpannableString("Tue");
        TueU.setSpan(new UnderlineSpan(), 0, TueU.length(), 0);
        final SpannableString WedU = new SpannableString("Wed");
        WedU.setSpan(new UnderlineSpan(), 0, WedU.length(), 0);
        final SpannableString ThuU = new SpannableString("Thu");
        ThuU.setSpan(new UnderlineSpan(), 0, ThuU.length(), 0);
        final SpannableString FriU = new SpannableString("Fri");
        FriU.setSpan(new UnderlineSpan(), 0, FriU.length(), 0);
        final SpannableString SatU = new SpannableString("Sat");
        SatU.setSpan(new UnderlineSpan(), 0, SatU.length(), 0);

        //set defaults
        if (mode != 1) {
            schedule = Main_Tabbed_View.model.scheduleList.get(schedPos);

            //non-binary
            name.setText(schedule.getName());
            timeS.setText(schedule.getStartHour() + ":" + schedule.getStartMinute());
            tempS.setText(schedule.getStartTempSet());
            timeE.setText(schedule.getEndHour() + ":" + schedule.getEndMinute());
            tempE.setText(schedule.getEndTempSet());
            //binary
            sunS.setText((schedule.isSunS()) ? SunU : "Sun ");
            monS.setText((schedule.isMonS()) ? MonU : "Mon ");
            tueS.setText((schedule.isTueS()) ? TueU : "Tue ");
            wedS.setText((schedule.isWedS()) ? WedU : "Wed ");
            thuS.setText((schedule.isThuS()) ? ThuU : "Thu ");
            friS.setText((schedule.isFriS()) ? FriU : "Fri ");
            satS.setText((schedule.isSatS()) ? SatU : "Sat ");
            powS.setChecked(schedule.isStartPower());
            sunE.setText((schedule.isSunE()) ? SunU : "Sun ");
            monE.setText((schedule.isMonE()) ? MonU : "Mon ");
            tueE.setText((schedule.isTueE()) ? TueU : "Tue ");
            wedE.setText((schedule.isWedE()) ? WedU : "Wed ");
            thuE.setText((schedule.isThuE()) ? ThuU : "Thu ");
            friE.setText((schedule.isFriE()) ? FriU : "Fri ");
            satE.setText((schedule.isSatE()) ? SatU : "Sat ");
            powE.setChecked(schedule.isEndPower());
        } else {
            //defaults (time values are already set to default and temps should be
            //blank)
            sunS.setText(SunU);
            monS.setText(MonU);
            tueS.setText(TueU);
            wedS.setText(WedU);
            thuS.setText(ThuU);
            friS.setText(FriU);
            satS.setText(SatU);
            powS.setChecked(false);
            sunE.setText(SunU);
            monE.setText(MonU);
            tueE.setText(TueU);
            wedE.setText(WedU);
            thuE.setText(ThuU);
            friE.setText(FriU);
            satE.setText(SatU);
            powE.setChecked(true);
        }

        //fill list nodes
        ArrayList<node.groupCheck> devices = new ArrayList<node.groupCheck>();
        for (int i = 0; i < Main_Tabbed_View.model.deviceList.size() ; i++) { //for each existing device
            Device temp = Main_Tabbed_View.model.deviceList.get(i);

            if (mode == 0 && appliedDevices != null) { //if there is a point to check contains
                if (appliedDevices.contains(temp)) { //if devices are in sched
                    devices.add(new node.groupCheck(temp.getName(), i, true));
                }
            }
            else { //if a new group or devices do not belong to the sched
                devices.add(new node.groupCheck(temp.getName(), i, false));
            }
        }
        customAdapterApplied = new CA_group_checklist(this,
                devices,
                getResources());
        list.setAdapter(customAdapterApplied);

        //onClicks
//        save
//        timeS
//        powS
//        timeE
//        powE

        sunS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sunS.toString().compareTo(SunU.toString())==0) {
                    schedule.setSunS(false);
                    sunS.setText("Sun ");
                } else {
                    schedule.setSunS(true);
                    sunS.setText(SunU);
                }
            }
        });
//        monS
//        tueS
//        wedS
//        thuS
//        friS
//        satS
//        sunE
//        monE
//        tueE
//        wedE
//        thuE
//        friE
//        satE
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
