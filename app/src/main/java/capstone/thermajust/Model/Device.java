package capstone.thermajust.Model;

/**
 * Created by Joe Geoghegan on 10/11/2015.
 *
 * The Device class is created to be used in conjunction with the rest of the Thermajust code files.
 *
 * USE: This class holds data which defines a class.
 */
public class Device {
    //Basic information about the device
    private String name;
    private long idNum;
    private boolean useTemp,
                    useMic,
                    useVid,
                    //init settings
                    notFirstLaunch;
    //todo Peripheral list
//    private thermometer therm;
//    private microphone mic;
//    private video vid;

    //Constructor
        //todo CURRENTLY DOES NOT ACCOUNT FOR PERIPHERALS
        //WILL BE MORE ADVANCED THAN THIS
    public Device(boolean notFirstLaunch, String name, long idNum, boolean useTemp, boolean useMic, boolean useVid) {
        this.notFirstLaunch = notFirstLaunch;
        this.name = name;
        this.idNum = idNum;
        this.useTemp = useTemp;
        this.useMic = useMic;
        this.useVid = useVid;
    }

    //toString
        //todo CURRENTLY DOES NOT ACCOUNT FOR PERIPHERALS
    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", idNum=" + idNum +
                ", useTemp=" + useTemp +
                ", useMic=" + useMic +
                ", useVid=" + useVid +
                ", notFirstLaunch=" + notFirstLaunch +
                '}';
    }

    //Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getIdNum() { return idNum; }
    public void setIdNum(long idNum) { this.idNum = idNum; }
    public boolean isUseTemp() { return useTemp; }
    public void setUseTemp(boolean useTemp) { this.useTemp = useTemp; }
    public boolean isUseMic() { return useMic; }
    public void setUseMic(boolean useMic) { this.useMic = useMic; }
    public boolean isUseVid() { return useVid; }
    public void setUseVid(boolean useVid) { this.useVid = useVid; }
    public boolean isNotFirstLaunch() { return notFirstLaunch; }
    public void setNotFirstLaunch(boolean notFirstLaunch) { this.notFirstLaunch = notFirstLaunch; }
}
