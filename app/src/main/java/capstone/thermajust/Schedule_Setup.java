package capstone.thermajust;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

import capstone.thermajust.ListAdapterElements.CA_group_checklist;
import capstone.thermajust.ListAdapterElements.CA_schedule_checklist;
import capstone.thermajust.ListAdapterElements.node;
import capstone.thermajust.Model.Device;
import capstone.thermajust.Model.Schedule;

public class Schedule_Setup extends AppCompatActivity {
    private CA_schedule_checklist customAdapterApplied;
    private int mode, schedPos;
    private static ArrayList<Device> appliedDevices;
    private static Schedule schedule;

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
                    appliedDevices = Main_Tabbed_View.model.scheduleList.get(schedPos).getApplied();
                }
            }
        } else {
            //hopefully will not happen
        }

        final EditText name = (EditText) findViewById(R.id.editText_schedule_name);
        Button save = (Button) findViewById(R.id.button_schedule_save);

        final TextView sunS = (TextView) findViewById(R.id.textView_schedule_start_sun);
        final TextView monS = (TextView) findViewById(R.id.textView_schedule_start_mon);
        final TextView tueS = (TextView) findViewById(R.id.textView_schedule_start_tue);
        final TextView wedS = (TextView) findViewById(R.id.textView_schedule_start_wed);
        final TextView thuS = (TextView) findViewById(R.id.textView_schedule_start_thu);
        final TextView friS = (TextView) findViewById(R.id.textView_schedule_start_fri);
        final TextView satS = (TextView) findViewById(R.id.textView_schedule_start_sat);
        final TextView timeS = (TextView) findViewById(R.id.textView_schedule_timeStart);
        final ToggleButton powS = (ToggleButton) findViewById(R.id.toggle_schedule_start_power);
        final EditText tempS = (EditText) findViewById(R.id.editText_schedule_start_temp);

        final TextView sunE = (TextView) findViewById(R.id.textView_schedule_end_sun);
        final TextView monE = (TextView) findViewById(R.id.textView_schedule_end_mon);
        final TextView tueE = (TextView) findViewById(R.id.textView_schedule_end_tue);
        final TextView wedE = (TextView) findViewById(R.id.textView_schedule_end_wed);
        final TextView thuE = (TextView) findViewById(R.id.textView_schedule_end_thu);
        final TextView friE = (TextView) findViewById(R.id.textView_schedule_end_fri);
        final TextView satE = (TextView) findViewById(R.id.textView_schedule_end_sat);
        final TextView timeE = (TextView) findViewById(R.id.textView_schedule_timeEnd);
        final ToggleButton powE = (ToggleButton) findViewById(R.id.toggle_schedule_end_power);
        final EditText tempE = (EditText) findViewById(R.id.editText_schedule_end_temp);

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
            sunS.setText((schedule.isSunS()) ? SunU : "Sun");
            monS.setText((schedule.isMonS()) ? MonU : "Mon");
            tueS.setText((schedule.isTueS()) ? TueU : "Tue");
            wedS.setText((schedule.isWedS()) ? WedU : "Wed");
            thuS.setText((schedule.isThuS()) ? ThuU : "Thu");
            friS.setText((schedule.isFriS()) ? FriU : "Fri");
            satS.setText((schedule.isSatS()) ? SatU : "Sat");
            powS.setChecked(schedule.isStartPower());
            sunE.setText((schedule.isSunE()) ? SunU : "Sun");
            monE.setText((schedule.isMonE()) ? MonU : "Mon");
            tueE.setText((schedule.isTueE()) ? TueU : "Tue");
            wedE.setText((schedule.isWedE()) ? WedU : "Wed");
            thuE.setText((schedule.isThuE()) ? ThuU : "Thu");
            friE.setText((schedule.isFriE()) ? FriU : "Fri");
            satE.setText((schedule.isSatE()) ? SatU : "Sat");
            powE.setChecked(schedule.isEndPower());
        } else {
            schedule = new Schedule();
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
        ArrayList<node.schedCheck> devices = new ArrayList<node.schedCheck>();
        for (int i = 0; i < Main_Tabbed_View.model.deviceList.size() ; i++) { //for each existing device
            Device temp = Main_Tabbed_View.model.deviceList.get(i);

            if (mode == 0 && appliedDevices != null) { //if there is a point to check contains
                if (appliedDevices.contains(temp)) { //if devices are in sched
                    devices.add(new node.schedCheck(temp.getName(), i, true));
                }
            }
            else { //if a new group or devices do not belong to the sched
                devices.add(new node.schedCheck(temp.getName(), i, false));
            }
        }
        customAdapterApplied = new CA_schedule_checklist(this,
                devices,
                getResources());
        list.setAdapter(customAdapterApplied);

        //onClicks
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if new or editing add into model the correct way
                schedule.setName(name.getText().toString());
                schedule.setStartHour(Integer.parseInt(timeS.getText().toString().split(":")[0]));
                schedule.setStartMinute(Integer.parseInt(timeS.getText().toString().split(":")[1]));
                schedule.setStartTempSet(Integer.parseInt(tempS.getText().toString()));
                schedule.setEndHour(Integer.parseInt(timeE.getText().toString().split(":")[0]));
                schedule.setEndMinute(Integer.parseInt(timeE.getText().toString().split(":")[1]));
                schedule.setEndTempSet(Integer.parseInt(tempE.getText().toString()));
                switch (mode) {
                    case 0: //editing
                        Main_Tabbed_View.model.scheduleList.set(schedPos, schedule);
                        break;
                    default: //new
                        Main_Tabbed_View.model.scheduleList.add(schedule);
                        break;
                }

                //and save the model
                Main_Tabbed_View.model.saveSchedule(getApplicationContext());
            }
        });
//        timeS
//        timeE

        //Toggles onClicks
        powS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (powS.isChecked()) {
                    schedule.setStartPower(true);
                } else {
                    schedule.setStartPower(false);
                }
            }
        });
        powE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (powS.isChecked()) {
                    schedule.setEndPower(true);
                } else {
                    schedule.setEndPower(false);
                }
            }
        });
        //Date Toggle onClicks
        sunS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isSunS()) {
                    schedule.setSunS(false);
                    sunS.setText("Sun");
                } else {
                    schedule.setSunS(true);
                    sunS.setText(SunU);
                }
            }
        });
        monS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isMonS()) {
                    schedule.setMonS(false);
                    monS.setText("Mon");
                } else {
                    schedule.setMonS(true);
                    monS.setText(MonU);
                }
            }
        });
        tueS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isTueS()) {
                    schedule.setTueS(false);
                    tueS.setText("Tue");
                } else {
                    schedule.setTueS(true);
                    tueS.setText(TueU);
                }
            }
        });
        wedS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isWedS()) {
                    schedule.setWedS(false);
                    wedS.setText("Wed");
                } else {
                    schedule.setWedS(true);
                    wedS.setText(WedU);
                }
            }
        });
        thuS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isThuS()) {
                    schedule.setThuS(false);
                    thuS.setText("Thu");
                } else {
                    schedule.setThuS(true);
                    thuS.setText(ThuU);
                }
            }
        });
        friS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isFriS()) {
                    schedule.setFriS(false);
                    friS.setText("Fri");
                } else {
                    schedule.setFriS(true);
                    friS.setText(FriU);
                }
            }
        });
        satS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isSatS()) {
                    schedule.setSatS(false);
                    satS.setText("Sat");
                } else {
                    schedule.setSatS(true);
                    satS.setText(SatU);
                }
            }
        });
        sunE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isSunE()) {
                    schedule.setSunE(false);
                    sunE.setText("Sun");
                } else {
                    schedule.setSunE(true);
                    sunE.setText(SunU);
                }
            }
        });
        monE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isMonE()) {
                    schedule.setMonE(false);
                    monE.setText("Mon");
                } else {
                    schedule.setMonE(true);
                    monE.setText(MonU);
                }
            }
        });
        tueE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isTueE()) {
                    schedule.setTueE(false);
                    tueE.setText("Tue");
                } else {
                    schedule.setTueE(true);
                    tueE.setText(TueU);
                }
            }
        });
        wedE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isWedE()) {
                    schedule.setWedE(false);
                    wedE.setText("Wed");
                } else {
                    schedule.setWedE(true);
                    wedE.setText(WedU);
                }
            }
        });
        thuE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isThuE()) {
                    schedule.setThuE(false);
                    thuE.setText("Thu");
                } else {
                    schedule.setThuE(true);
                    thuE.setText(ThuU);
                }
            }
        });
        friE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isFriE()) {
                    schedule.setFriE(false);
                    friE.setText("Fri");
                } else {
                    schedule.setFriE(true);
                    friE.setText(FriU);
                }
            }
        });
        satE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.isSatE()) {
                    schedule.setSatE(false);
                    satE.setText("Sat");
                } else {
                    schedule.setSatE(true);
                    satE.setText(SatU);
                }
            }
        });
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

    //node helper methods
    public static void devicesAdd(Device device) {
        schedule.getApplied().add(device);
    }
    public static void devicesRemove(Device device) {
        schedule.getApplied().remove(device);
    }

    public void showTimePickerDialogS(View v) {
        DialogFragment newFragment = new TimePickerFragmentS();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    public void showTimePickerDialogE(View v) {
        DialogFragment newFragment = new TimePickerFragmentE();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class TimePickerFragmentS extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView timeS = (TextView) getActivity().findViewById(R.id.textView_schedule_timeStart);
            timeS.setText(hourOfDay + ":" + minute);
        }
    }
    public static class TimePickerFragmentE extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView timeE = (TextView) getActivity().findViewById(R.id.textView_schedule_timeEnd);
            timeE.setText(hourOfDay + ":" + minute);
        }
    }
}
