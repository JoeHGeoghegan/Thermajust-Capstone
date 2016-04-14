package capstone.thermajust.Model;

/**
 * Created by Joe Geoghegan on 10/11/2015.
 * <p/>
 * The Device class is created to be used in conjunction with the rest of the Thermajust code files.
 * <p/>
 * USE: This class holds data which defines a class.
 */
public class Device {
    //Basic information about the groupCheck
    private String name;
    private String idNum;

    //main settings
    private boolean onoff;

    //peripheral information
    private boolean useTemp;
    private boolean useMic;
    private boolean useVid;

    //network information
    private String wifiName,
            wifiPassword;

    //peripheral classes
    private thermometer therm;
//    private microphone mic;
//    private video vid;

    private String ip;

    public Device(String name, String idNum, boolean onoff, boolean useTemp, boolean useMic,
                  boolean useVid, String wifiName, String wifiPassword, thermometer therm, String ip) {
        this.name = name;
        this.idNum = idNum;
        this.onoff = onoff;
        this.useTemp = useTemp;
        this.useMic = useMic;
        this.useVid = useVid;
        this.wifiName = wifiName;
        if (wifiPassword.compareTo("<!>end<!>") != 0) {
            this.wifiPassword = wifiPassword;
        } else {
            this.wifiPassword = null;
        }
        if (therm == null) {
            this.therm = new thermometer(70, false, 1);
        } else {
            this.therm = therm;
        }
        this.ip = ip;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdNum() {
        return idNum;
    }
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    public boolean getOnoff() {
        return onoff;
    }
    public void setOnoff(boolean onoff) {
        this.onoff = onoff;
    }
    public boolean getUseTemp() {
        return useTemp;
    }
    public void setUseTemp(boolean useTemp) {
        this.useTemp = useTemp;
    }
    public boolean getUseMic() {
        return useMic;
    }
    public void setUseMic(boolean useMic) {
        this.useMic = useMic;
    }
    public boolean getUseVid() {
        return useVid;
    }
    public void setUseVid(boolean useVid) {
        this.useVid = useVid;
    }
    public String getWifiPassword() {
        return wifiPassword;
    }
    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }
    public String getWifiName() {
        return wifiName;
    }
    public void setWifiName(String wifiName) { this.wifiName = wifiName; }
    public thermometer getTherm() { return therm; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    @Override
    public String toString() {
        String write = getName() + "," +
                getIdNum() + "," +
                getOnoff() + ",";
        if (therm != null) {
            if (getUseTemp()) {
                write = write + getUseTemp() + "," + therm.toString() + ",";
            } else {
                write = write + getUseTemp() + ",";
            }
        } else {
            write = write + getUseTemp() + ",70,false,1,";
        } //a thermometer exists but not set up, these are default values

        write = write + getUseMic() + "," + getUseVid() + ",";
//        if (getUseMic()) {
//            write = write + getUseMic() + "," + mic.toString() + ",";
//        }else { write = write + getUseMic() + ","; }
//        if (getUseVid()) {
//            write = write + getUseVid() + "," + vid.toString() + ",";
//        }else { write = write + getUseVid() + ","; }
        write = write + getIp();
        return write +
                getWifiName() + "," +
                getWifiPassword() + ",<!>end<!>\n";
    }
}
