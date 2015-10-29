package capstone.thermajust.Model;

import java.util.ArrayList;

/**
 * Created by Joe Geoghegan on 10/27/2015.
 *
 * The Main_Model class is created to be used in conjunction with the rest of the Thermajust code files.
 *
 * The main purpose of this class is to hold an array for each type of object.
 *  Along with that it also handles their arrangements and operations that involve
 *      every object in an array.
 */
public class Main_Model {

    /* List Array Data and their management functions */
    ArrayList<Device> deviceList = new ArrayList<Device>();
    ArrayList<Group> groupList = new ArrayList<Group>();
//    ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
//    ArrayList<Power> powerList = new ArrayList<Power>();

    /* WiFi related data and its management functions */
    private String WiFiDefaultName;
    private String WiFiDefaultPassword;

    public void setWifiDefaults(String name, String password){
        WiFiDefaultName = name;
        WiFiDefaultPassword = password;
    }
    public String getWiFiDefaultName() { return WiFiDefaultName; }
    public String getWiFiDefaultPassword() { return WiFiDefaultPassword; }

    
}
