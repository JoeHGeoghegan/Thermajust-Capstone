package capstone.thermajust.Model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.File;

/**
 * Created by Joe Geoghegan on 10/27/2015.
 *
 * The Main_Model class is created to be used in conjunction with the rest of the Thermajust code
 *  files.
 *
 * The main purpose of this class is to hold an array for each type of object.
 *      Along with that it also handles their arrangements and operations that involve
 *       every object in an array.
 */
public class Main_Model {
    /*******************
     * ATTRIBUTES
     *******************/

    /* List Array attributes */
    ArrayList<Device> deviceList = new ArrayList<>();
    ArrayList<Group> groupList = new ArrayList<>();
//    ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
//    ArrayList<Power> powerList = new ArrayList<Power>();

    /* WiFi related attributes */
    private String WiFiDefaultName;
    private String WiFiDefaultPassword;

    /*******************
     *MANAGEMENT FUNCTIONS
     *******************/

    /* ArrayList management functions */
    /* loadAll will load and process everything on application launch
            The functions are all at the bottom to make main components cleaner
     */
    public void loadAll(Context content) {
        loadOptions(content);
        loadDevice(content);
        loadGroup(content);
//        loadSchedule(content);
//        loadPower(content);
    }

    /* WiFi  management functions */
    public void setWifiDefaults(String name, String password){
        WiFiDefaultName = name;
        WiFiDefaultPassword = password;
    }
    public String getWiFiDefaultName() { return WiFiDefaultName; }
    public String getWiFiDefaultPassword() { return WiFiDefaultPassword; }


    /* LOAD FUNCTIONS */
    /**Besides loadOptions, these all work the same way;
     * Open file, if it exists, read it line by line
     *      each line is put into their respective classes constructor which is then appended to the
     *       main model's ArrayLists
     * If it does not exist it create the file to be empty
     *
     * load Options only holds a few details, each are stored on separate lines, Order is the same
     *      as class file attributes (excluding the arrayLists of classes of course)
     **/
    public void loadOptions(Context context) {
        String file = "ThermajustOptionsSave.txt";
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(assetManager.open(file)));
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                //process line



            }
        } catch (FileNotFoundException e) {
            //Create File, first use
            try {
                FileOutputStream fileOutputStream =
                        context.openFileOutput(file, Context.MODE_PRIVATE);
                fileOutputStream.write("".getBytes());
                fileOutputStream.close();
            }catch (IOException ex) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            //log this somehow
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch(IOException e) {
                    //log this somehow
                }
            }
        }
    }
    public void loadDevice(Context context) {
        String file = "ThermajustDeviceSave.txt";
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(assetManager.open(file)));
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                //process line



            }
        } catch (FileNotFoundException e) {
            //Create File, first use
            try {
                FileOutputStream fileOutputStream =
                        context.openFileOutput(file, Context.MODE_PRIVATE);
                fileOutputStream.write("".getBytes());
                fileOutputStream.close();
            }catch (IOException ex) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            //log this somehow
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch(IOException e) {
                    //log this somehow
                }
            }
        }
    }
    public void loadGroup(Context context) {
        String file = "ThermajustGroupSave.txt";
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(assetManager.open(file)));
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                //process line



            }
        } catch (FileNotFoundException e) {
            //Create File, first use
            try {
                FileOutputStream fileOutputStream =
                        context.openFileOutput(file, Context.MODE_PRIVATE);
                fileOutputStream.write("".getBytes());
                fileOutputStream.close();
            }catch (IOException ex) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            //log this somehow
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch(IOException e) {
                    //log this somehow
                }
            }
        }
    }
    public void loadSchedule(Context context) {
        String file = "ThermajustScheduleSave.txt";
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(assetManager.open(file)));
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                //process line



            }
        } catch (FileNotFoundException e) {
            //Create File, first use
            try {
                FileOutputStream fileOutputStream =
                        context.openFileOutput(file, Context.MODE_PRIVATE);
                fileOutputStream.write("".getBytes());
                fileOutputStream.close();
            }catch (IOException ex) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            //log this somehow
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch(IOException e) {
                    //log this somehow
                }
            }
        }
    }
    public void loadPower(Context context) {
        String file = "ThermajustPowerSave.txt";
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(assetManager.open(file)));
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                //process line



            }
        } catch (FileNotFoundException e) {
            //Create File, first use
            try {
                FileOutputStream fileOutputStream =
                        context.openFileOutput(file, Context.MODE_PRIVATE);
                fileOutputStream.write("".getBytes());
                fileOutputStream.close();
            }catch (IOException ex) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            //log this somehow
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch(IOException e) {
                    //log this somehow
                }
            }
        }
    }
}
