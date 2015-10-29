package capstone.thermajust;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
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

import android.widget.TextView;

public class Main_Tabbed_View extends AppCompatActivity {

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "The current tab is " + mSectionsPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                switch(tabLayout.getSelectedTabPosition()) {
                    case 0:
                        showDeviceGroupDialog();
//                        toDeviceSetup(view);
//                        toGroupSetup(view);
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
            return PlaceholderFragment.newInstance(position + 1);
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
                    return "Power Info";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main__tabbed__view, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
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
            args.putInt("title",title);
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
