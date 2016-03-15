package capstone.thermajust.Model;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.support.design.widget.Snackbar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import capstone.thermajust.Main_Tabbed_View;

/**
 * Created by Joe Geoghegan on 10/27/2015.
 * <p/>
 * The Main_Model class is created to be used in conjunction with the rest of the Thermajust code
 * files.
 * <p/>
 * The main purpose of this class is to hold an array for each type of object.
 * Along with that it also handles their arrangements and operations that involve
 * every object in an array.
 */
public class Main_Model {

    /*******************
     * ATTRIBUTES
     *******************/
    Boolean firstRun = true;

    /* List Array attributes */
    public ArrayList<Device> deviceList = new ArrayList<>();
    public ArrayList<Group> groupList = new ArrayList<>();
    public ArrayList<Schedule> scheduleList = new ArrayList<>();
//    public ArrayList<Power> powerList = new ArrayList<Power>();

    /* WiFi related attributes */
    private String WiFiDefaultName;
    private String WiFiDefaultPassword;

    /* System Settings */
    private int currentDebugID;

    /*******************
     * MANAGEMENT FUNCTIONS
     *******************/
    //if given an id will return the corresponding groupCheck (the first one if not unique)
    public Device getDeviceByID (String id) {
        for (int i = 0; i < deviceList.size() ; i++) {
            if (deviceList.get(i).getIdNum().compareTo(id) == 0) {
                return deviceList.get(i);
            }
        }
        return null;
    }


    /* ArrayList management functions */
    /* loadAll will load and process everything on application launch
            The functions are all at the bottom to make main components cleaner
     */
    public void loadAll(Context context) {
        if (firstRun) {
            loadOptions(context);
            loadDevice(context);
            loadGroup(context);
//            loadSchedule(context);
//            loadPower(context);
        }
        firstRun = false;
    }

    //returns the list of names of all devices, used to list them in UI
    public ArrayList<String> getDeviceNames() {
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < deviceList.size(); i++) {
            nameList.add(deviceList.get(i).getName());
        }
        return nameList;
    }

    /* WiFi  management functions */
    public void setWifiDefaults(String name, String password) {
        WiFiDefaultName = name;
        WiFiDefaultPassword = password;
    }

    public void setWiFiDefaultName(String wiFiDefaultName) {
        WiFiDefaultName = wiFiDefaultName;
    }

    public void setWiFiDefaultPassword(String wiFiDefaultPassword) {
        WiFiDefaultPassword = wiFiDefaultPassword;
    }

    public String getWiFiDefaultName() {
        return WiFiDefaultName;
    }

    public String getWiFiDefaultPassword() {
        return WiFiDefaultPassword;
    }

    public int getCurrentDebugID_increment(Context context) {
        currentDebugID++;
        saveOptions(context);
        return currentDebugID - 1;
    }
    public int getCurrentDebugID() {return currentDebugID;}
    public void setCurrentDebugID(int currentDebugID) { this.currentDebugID = currentDebugID; }

/* LOAD FUNCTIONS */

    /**
     * Besides loadOptions, these all work the same way;
     * Open file, if it exists, read it line by line
     * each line is put into their respective classes constructor which is then appended to the
     * main model's ArrayLists
     * If it does not exist it create the file to be empty
     * <p/>
     * load Options only holds a few details, each are stored on separate lines, Order is the same
     * as class file attributes (excluding the arrayLists of classes of course)
     **/
    public void loadOptions(Context context) {
        try {
            FileInputStream fis = context.openFileInput("ThermajustOptionSave.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int num = 0;
                //process line
                String[] tokens = line.split(":"); //will split each line into identifier and value
                String identifier = tokens[0];
                String value = tokens[1];
                if (!(value.compareTo("null")==0)) {
                    switch (identifier) {
                        case "WiFiDefaultName":
                            setWiFiDefaultName(tokens[1]);
                            break;
                        case "WiFiDefaultPassword":
                            setWiFiDefaultPassword(tokens[1]);
                            break;
                        case "CurrentDebugID":
                            setCurrentDebugID(Integer.parseInt(tokens[1]));
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e0) {
            e0.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void loadDevice(Context context) {
        try {
            FileInputStream fis = context.openFileInput("ThermajustDeviceSave.txt");
            String delim = ",";
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //process line
                String[] tokens = line.split(delim);

                int num = 0;
                String name = tokens[num++];
                String idNum = tokens[num++];
                Boolean onoff = Boolean.parseBoolean(tokens[num++]);
                boolean useTemp = Boolean.parseBoolean(tokens[num++]);
                thermometer therm = null;
                if (useTemp) {
                    therm = new thermometer(Integer.parseInt(tokens[num++]), //setTemp
                            Boolean.parseBoolean(tokens[num++]), //override
                            Integer.parseInt(tokens[num++]) //mode
                    );
                }
                boolean useMic = Boolean.parseBoolean(tokens[num++]);
//                microphone mic = null;
//                if (useMic) {
//                    mic = new microphone();
//                }
                boolean useVid = Boolean.parseBoolean(tokens[num++]);
//                video vid = null;
//                if (useVid) {
//                    vid = new video();
//                }
                String wifiName = tokens[num++];
                String wifiPassword = tokens[num++];

                deviceList.add(new Device(name, idNum, onoff, useTemp, useMic, useVid, wifiName, wifiPassword, therm
//                        , mic
//                        , vid
                ));
            }
        } catch (FileNotFoundException e0) {
            e0.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e3) {
            deviceList.clear();
            saveDevices(context);
        }
    }

    public void loadGroup(Context context) {
        try {
            FileInputStream fis = context.openFileInput("ThermajustGroupSave.txt");
            String delim = ",";
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //process line
                String[] tokens = line.split(delim);

                //initialize groupCheck array
                ArrayList<Device> devices = new ArrayList<Device>();

                int num = 0;
                String name = tokens[num++];
                int numDevices = Integer.parseInt(tokens[num++]);
                for (int i = 0; i < numDevices ; i++) {
                    Device temp = getDeviceByID(tokens[num++]);
                    if (temp != null){
                        devices.add(temp);
                    }
                }

                groupList.add(new Group(name, devices));
            }
        } catch (FileNotFoundException e0) {
            e0.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e3) {
            groupList.clear();
            saveGroups(context);
        }
    }

    public void loadSchedule(Context context) {
        try {
            FileInputStream fis = context.openFileInput("ThermajustScheduleSave.txt");
            String delim = ",";
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //process line
                String[] tokens = line.split(delim);

                //initialize groupCheck array
                ArrayList<Device> devices = new ArrayList<Device>();

                Schedule schedule = new Schedule(tokens);

                scheduleList.add(schedule);
            }
        } catch (FileNotFoundException e0) {
            e0.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e3) {
            scheduleList.clear();
            saveSchedule(context);
        }
    }

    public void loadPower(Context context) {
//        "ThermajustPowerSave.txt";
    }

    /**
     * Save Functions, for arrays combines the toString of all members of the array and writes that
     * to file
     * For non-array attributes, saves a string version of the attributes
     */
    public void saveOptions(Context context) {
        String saveWrite = "";
        String fileName = "ThermajustOptionSave.txt";
        FileOutputStream outputStream;

        saveWrite =
                "WiFiDefaultName:" + getWiFiDefaultName() + "\n" +
                "WiFiDefaultPassword:" + getWiFiDefaultPassword() + "\n" +
                "CurrentDebugID:" + getCurrentDebugID();

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(saveWrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDevices(Context context) {
        String saveWrite = "";
        String fileName = "ThermajustDeviceSave.txt";
        FileOutputStream outputStream;

        for (int i = 0; i < deviceList.size(); i++) {
            saveWrite = saveWrite + deviceList.get(i).toString();
        }

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(saveWrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGroups(Context context) {
        String saveWrite = "";
        String fileName = "ThermajustGroupSave.txt";
        FileOutputStream outputStream;

        for (int i = 0; i < groupList.size(); i++) {
            saveWrite = saveWrite + groupList.get(i).toString();
        }

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(saveWrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSchedule(Context context) {
        String saveWrite = "";
        String fileName = "ThermajustScheduleSave.txt";
        FileOutputStream outputStream;

        for (int i = 0; i < scheduleList.size(); i++) {
            saveWrite = saveWrite + scheduleList.get(i).toString();
        }

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(saveWrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePower(Context context) {
        String saveWrite = "";
        String fileName = "ThermajustPowerSave.txt";
        FileOutputStream outputStream;


        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(saveWrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
