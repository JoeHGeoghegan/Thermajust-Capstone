package capstone.thermajust.Model;

import java.util.ArrayList;

import capstone.thermajust.Main_Tabbed_View;

/**
 * Created by Joe Geoghegan on 2/23/2016.
 */
public class Schedule {
    String name;
    boolean enabled;
    boolean sunS, monS, tueS, wedS, thuS, friS, satS;
    boolean sunE, monE, tueE, wedE, thuE, friE, satE;
    int startHour, startMinute, endHour, endMinute; //military time
    boolean startPower, endPower, startTemp, endTemp;
    int startTempSet, endTempSet;

    ArrayList<Device> applied;

    public Schedule() {
        this.name = null;
        this.enabled = true;
        this.sunS = true;
        this.monS = true;
        this.tueS = true;
        this.wedS = true;
        this.thuS = true;
        this.friS = true;
        this.satS = true;
        this.sunE = true;
        this.monE = true;
        this.tueE = true;
        this.wedE = true;
        this.thuE = true;
        this.friE = true;
        this.satE = true;
        this.startHour = 12;
        this.startMinute = 0;
        this.endHour = 13;
        this.endMinute = 0;
        this.startPower = true;
        this.endPower = false;
        this.startTemp = false;
        this.endTemp = false;
        this.startTempSet = 70;
        this.endTempSet = 70;
        this.applied = new ArrayList<Device>();
    }

    public Schedule(java.lang.String[] tokens) {
        int num = 0;
        this.name = tokens[num++];;
        this.enabled = Boolean.parseBoolean(tokens[num++]);;
        this.sunS = Boolean.parseBoolean(tokens[num++]);
        this.monS = Boolean.parseBoolean(tokens[num++]);
        this.tueS = Boolean.parseBoolean(tokens[num++]);
        this.wedS = Boolean.parseBoolean(tokens[num++]);
        this.thuS = Boolean.parseBoolean(tokens[num++]);
        this.friS = Boolean.parseBoolean(tokens[num++]);
        this.satS = Boolean.parseBoolean(tokens[num++]);
        this.sunE = Boolean.parseBoolean(tokens[num++]);
        this.monE = Boolean.parseBoolean(tokens[num++]);
        this.tueE = Boolean.parseBoolean(tokens[num++]);
        this.wedE = Boolean.parseBoolean(tokens[num++]);
        this.thuE = Boolean.parseBoolean(tokens[num++]);
        this.friE = Boolean.parseBoolean(tokens[num++]);
        this.satE = Boolean.parseBoolean(tokens[num++]);
        this.startHour = Integer.parseInt(tokens[num++]);////
        this.startMinute = Integer.parseInt(tokens[num++]);
        this.endHour = Integer.parseInt(tokens[num++]);
        this.endMinute = Integer.parseInt(tokens[num++]);
        this.startPower = Boolean.parseBoolean(tokens[num++]);
        this.endPower = Boolean.parseBoolean(tokens[num++]);
        this.startTemp = Boolean.parseBoolean(tokens[num++]);
        this.endTemp = Boolean.parseBoolean(tokens[num++]);
        this.startTempSet = Integer.parseInt(tokens[num++]);
        this.endTempSet = Integer.parseInt(tokens[num++]);

        int numDev = Integer.parseInt(tokens[num++]);
        this.applied = new ArrayList<Device>();
        for (int i = 0; i < numDev ; i++) {
            Device temp = Main_Tabbed_View.model.getDeviceByID(tokens[num++]);
            if (temp != null){
                this.applied.add(temp);
            }
        }
    };
    public Schedule(String name, boolean enabled,
                    boolean sunS, boolean monS, boolean tueS, boolean wedS, boolean thuS, boolean friS, boolean satS,
                    boolean sunE, boolean monE, boolean tueE, boolean wedE, boolean thuE, boolean friE, boolean satE,
                    int startHour, int startMinute, int endHour, int endMinute,
                    boolean startPower, boolean endPower, boolean startTemp, boolean endTemp,
                    int startTempSet, int endTempSet,
                    ArrayList<Device> applied) {
        this.name = name;
        this.enabled = enabled;
        this.sunS = sunS;
        this.monS = monS;
        this.tueS = tueS;
        this.wedS = wedS;
        this.thuS = thuS;
        this.friS = friS;
        this.satS = satS;
        this.sunE = sunE;
        this.monE = monE;
        this.tueE = tueE;
        this.wedE = wedE;
        this.thuE = thuE;
        this.friE = friE;
        this.satE = satE;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.startPower = startPower;
        this.endPower = endPower;
        this.startTemp = startTemp;
        this.endTemp = endTemp;
        this.startTempSet = startTempSet;
        this.endTempSet = endTempSet;
        this.applied = applied;
    }

    @Override
    public String toString() {
        return name +
                "," + enabled +
                "," + sunS +
                "," + monS +
                "," + tueS +
                "," + wedS +
                "," + thuS +
                "," + friS +
                "," + satS +
                "," + sunE +
                "," + monE +
                "," + tueE +
                "," + wedE +
                "," + thuE +
                "," + friE +
                "," + satE +
                "," + startHour +
                "," + startMinute +
                "," + endHour +
                "," + endMinute +
                "," + startPower +
                "," + endPower +
                "," + startTemp +
                "," + endTemp +
                "," + startTempSet +
                "," + endTempSet +
                "," + appliedToString() +
                "<!>end<!>\n";
    }

    public String appliedToString() {
        String write = applied.size() + ",";
        for (int i = 0; i < applied.size(); i++) {
            write = write + applied.get(i).getIdNum() + ",";
        }
        return write;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public boolean isSunS() { return sunS; }
    public void setSunS(boolean sunS) { this.sunS = sunS; }
    public boolean isMonS() { return monS; }
    public void setMonS(boolean monS) { this.monS = monS; }
    public boolean isTueS() { return tueS; }
    public void setTueS(boolean tueS) { this.tueS = tueS; }
    public boolean isWedS() { return wedS; }
    public void setWedS(boolean wedS) { this.wedS = wedS; }
    public boolean isThuS() { return thuS; }
    public void setThuS(boolean thuS) { this.thuS = thuS; }
    public boolean isFriS() { return friS; }
    public void setFriS(boolean friS) { this.friS = friS; }
    public boolean isSatS() { return satS; }
    public void setSatS(boolean satS) { this.satS = satS; }
    public boolean isSunE() { return sunE; }
    public void setSunE(boolean sunE) { this.sunE = sunE; }
    public boolean isMonE() { return monE; }
    public void setMonE(boolean monE) { this.monE = monE; }
    public boolean isTueE() { return tueE; }
    public void setTueE(boolean tueE) { this.tueE = tueE; }
    public boolean isWedE() { return wedE; }
    public void setWedE(boolean wedE) { this.wedE = wedE; }
    public boolean isThuE() { return thuE; }
    public void setThuE(boolean thuE) { this.thuE = thuE; }
    public boolean isFriE() { return friE; }
    public void setFriE(boolean friE) { this.friE = friE; }
    public boolean isSatE() { return satE; }
    public void setSatE(boolean satE) { this.satE = satE; }
    public int getStartHour() { return startHour; }
    public void setStartHour(int startHour) { this.startHour = startHour; }
    public int getStartMinute() { return startMinute; }
    public void setStartMinute(int startMinute) { this.startMinute = startMinute; }
    public int getEndHour() { return endHour; }
    public void setEndHour(int endHour) { this.endHour = endHour; }
    public int getEndMinute() { return endMinute; }
    public void setEndMinute(int endMinute) { this.endMinute = endMinute; }
    public boolean isStartPower() { return startPower; }
    public void setStartPower(boolean startPower) { this.startPower = startPower; }
    public boolean isEndPower() { return endPower; }
    public void setEndPower(boolean endPower) { this.endPower = endPower; }
    public boolean isStartTemp() { return startTemp; }
    public void setStartTemp(boolean startTemp) { this.startTemp = startTemp; }
    public boolean isEndTemp() { return endTemp; }
    public void setEndTemp(boolean endTemp) { this.endTemp = endTemp; }
    public int getStartTempSet() { return startTempSet; }
    public void setStartTempSet(int startTempSet) { this.startTempSet = startTempSet; }
    public int getEndTempSet() { return endTempSet; }
    public void setEndTempSet(int endTempSet) { this.endTempSet = endTempSet; }
    public ArrayList<Device> getApplied() { return applied; }
    public void setApplied(ArrayList<Device> applied) { this.applied = applied; }
}
