package co.edu.eafit.jquiro12.settingup;

import java.io.Serializable;

public class Subroutine  implements Serializable {
    private int id;
    //Days
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    //Limits

    private String from_hour;
    private String from_minute;

    private String till_hour;
    private String till_minute;

    private int frequency;

    public Subroutine() {

    }

    public Subroutine(int id, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, String from_hour, String from_minute, String till_hour, String till_minute, int frequency) {
        this.id = id;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.from_hour = from_hour;
        this.from_minute = from_minute;
        this.till_hour = till_hour;
        this.till_minute = till_minute;
        this.frequency = frequency;
    }

    public Subroutine(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public String getFrom_hour() {
        return from_hour;
    }

    public void setFrom_hour(String from_hour) {
        this.from_hour = from_hour;
    }

    public String getFrom_minute() {
        return from_minute;
    }

    public void setFrom_minute(String from_minute) {
        this.from_minute = from_minute;
    }

    public String getTill_hour() {
        return till_hour;
    }

    public void setTill_hour(String till_hour) {
        this.till_hour = till_hour;
    }

    public String getTill_minute() {
        return till_minute;
    }

    public void setTill_minute(String till_minute) {
        this.till_minute = till_minute;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
