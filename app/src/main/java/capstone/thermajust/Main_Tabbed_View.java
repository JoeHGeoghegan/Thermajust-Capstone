package capstone.thermajust;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.thermajust.ListAdapterElements.CA_device_control;
import capstone.thermajust.ListAdapterElements.CA_group_control;
import capstone.thermajust.ListAdapterElements.CA_power_read;
import capstone.thermajust.ListAdapterElements.CA_schedule_control;
import capstone.thermajust.Model.Device;
import capstone.thermajust.Model.Group;
import capstone.thermajust.Model.Main_Model;
import capstone.thermajust.ListAdapterElements.node;
import capstone.thermajust.Model.Schedule;

public class Main_Tabbed_View extends AppCompatActivity {
    //Data structure attribute
    public static Main_Model model = new Main_Model(); //this will load all the data on creation
//    public Main_Model getModel() { return model; }
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__tabbed__view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "The current tab is " + mSectionsPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        showDeviceGroupDialog();
                        break;
                    case 1:
                        toScheduleSetup(view);
                        break;
                    case 2:
                        toPowerSetup(view);
                        break;
                }
            }
        });

        model.loadAll(getApplicationContext()); //This will load all data from file into the model
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main__tabbed__view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ListholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Devices";
                case 1:
                    return "Automation";
                case 2:
                    return "Power Usage";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ListholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ListholderFragment newInstance(int sectionNumber) {
            ListholderFragment fragment = new ListholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ListholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView;

            //layout Setup
            if (model.groupList.size() > 0 && sectionNumber - 1 == 0) {                             //groups do exist, and the tab is devices/group need two lists
                //define view to have the two list layout
                rootView = inflater.inflate(R.layout.fragment_mtabview_twolist, container, false);
                CA_device_control arrayAdapter1; //and the array adapters for the list
                CA_group_control arrayAdapter2;

                //define all elements needed to be edited
                TextView text1 = (TextView) rootView.findViewById(R.id.textView_twolist_header1);
                ListView list1 = (ListView) rootView.findViewById(R.id.listView_twolist_list1);
                TextView text2 = (TextView) rootView.findViewById(R.id.textView_twolist_header2);
                ListView list2 = (ListView) rootView.findViewById(R.id.listView_twolist_list2);

                //edit text
                text1.setText(getString(R.string.device));
                text2.setText(getString(R.string.group));

                //fill content list 1
                ArrayList<node.deviceControl> deviceControlNodes = new ArrayList<node.deviceControl>();
                for (int i = 0; i < model.deviceList.size(); i++) {
                    Device temp = model.deviceList.get(i);
                    deviceControlNodes.add(new node.deviceControl(temp.getName(), temp.getIdNum(), i));
                }
                arrayAdapter1 = new CA_device_control(getActivity(),
                        deviceControlNodes,
                        getResources());
                //fill content list 2
                ArrayList<node.groupControl> groupControlNodes = new ArrayList<node.groupControl>();
                for (int i = 0; i < model.groupList.size(); i++) {
                    Group temp = model.groupList.get(i);
                    groupControlNodes.add(new node.groupControl(temp.getName(), i));
                }
                arrayAdapter2 = new CA_group_control(getActivity(),
                        groupControlNodes,
                        getResources());

                //init list 1
                list1.setAdapter(arrayAdapter1);

                //init list 2
                list2.setAdapter(arrayAdapter2);

            } else {                                                                                //we only require one list
                //define view to have the one list layout
                rootView = inflater.inflate(R.layout.fragment_mtabview_onelist, container, false);

                //define all elements needed to be edited
                TextView text1 = (TextView) rootView.findViewById(R.id.textView_onelist_header1);
                ListView list1 = (ListView) rootView.findViewById(R.id.listView_onelist_list);
                if (sectionNumber - 1 == 0) { //Device tab
                    CA_device_control arrayAdapter; //and the array adapters for the list
                    //edit text
                    text1.setText(getString(R.string.device));

                    //fill content list 1
                    ArrayList<node.deviceControl> deviceControlNodes = new ArrayList<node.deviceControl>();
                    for (int i = 0; i < model.deviceList.size(); i++) {
                        Device temp = model.deviceList.get(i);
                        deviceControlNodes.add(new node.deviceControl(temp.getName(), temp.getIdNum(), i));
                    }
                    arrayAdapter = new CA_device_control(getActivity(),
                            deviceControlNodes,
                            getResources());

                    //init list
                    list1.setAdapter(arrayAdapter);
                }
                else if (sectionNumber - 1 == 1) {                                                //schedule tab
                    CA_schedule_control arrayAdapter; //and the array adapter for the list
                    //edit text
                    text1.setText(getString(R.string.schedule));
                    //edit list 1
                    ArrayList<node.scheduleControl> scheduleControlNodes = new ArrayList<node.scheduleControl>();
                    for (int i = 0; i < model.scheduleList.size() ; i++) {
                        Schedule temp = model.scheduleList.get(i);
                        scheduleControlNodes.add(new node.scheduleControl(temp.getName(), temp.isEnabled(), i));
                    }
                    arrayAdapter = new CA_schedule_control(getActivity(),
                            scheduleControlNodes,
                            getResources());

                    //init list
                    list1.setAdapter(arrayAdapter);
                }
                else if (sectionNumber - 1 == 2){                                                   //power tab
                    CA_power_read arrayAdapter; //and the array adapter for the list
                    //edit text
                    text1.setText(getString(R.string.power_meter));
                    //edit list 1
                    ArrayList<node.powerRead> powerReadNodes = new ArrayList<node.powerRead>();
                    for (int i = 0; i < model.deviceList.size() ; i++) {
                        powerReadNodes.add(new node.powerRead(model.deviceList.get(i).getName(), "0", "W", i));
                    }
                    arrayAdapter = new CA_power_read(getActivity(),
                            powerReadNodes,
                            getResources());

                    //init list
                    list1.setAdapter(arrayAdapter);
                }
            }
            return rootView;
        }
    }

    public static class deviceGroupDiologFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.deviceGroupCreate)
                    .setNegativeButton(R.string.device, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //toDevice
                            toDeviceSetup();
                        }
                    })
                    .setPositiveButton(R.string.group, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //toGroup
                            toGroupSetup();
                        }
                    })
                    .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Cancel
                        }
                    });
            return builder.create();
        }

        public static deviceGroupDiologFragment newInstance(int title) {
            deviceGroupDiologFragment frag = new deviceGroupDiologFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        //Dialog Switching
        public void toDeviceSetup() {
            Intent myIntent = new Intent(getActivity(), Device_Setup.class);
            getActivity().startActivity(myIntent);
        }

        public void toGroupSetup() {
            Intent myIntent = new Intent(getActivity(), Group_Setup.class);
            myIntent.putExtra("mode", 1); //creating a new group
            getActivity().startActivity(myIntent);
        }
    }

    void showDeviceGroupDialog() {
        DialogFragment newFragment = deviceGroupDiologFragment.newInstance(R.string.deviceGroupCreate);
        newFragment.show(getFragmentManager(), "deviceGroupDiologFragment");
    }

    //Menu Switching
    public void toSettings(MenuItem menuItem) {
        Intent myIntent = new Intent(Main_Tabbed_View.this, Main_Settings_Page.class);
        Main_Tabbed_View.this.startActivity(myIntent);
    }

    public void toDefaultWifi(MenuItem menuItem) {
        Intent myIntent = new Intent(Main_Tabbed_View.this, WiFi_Default.class);
        Main_Tabbed_View.this.startActivity(myIntent);
    }

    //FAB Switching
    public void toScheduleSetup(View view) {
        Intent myIntent = new Intent(Main_Tabbed_View.this, Schedule_Setup.class);
        Main_Tabbed_View.this.startActivity(myIntent);
    }

    public void toPowerSetup(View view) {
        Intent myIntent = new Intent(Main_Tabbed_View.this, Power_Setup.class);
        Main_Tabbed_View.this.startActivity(myIntent);
    }
}
