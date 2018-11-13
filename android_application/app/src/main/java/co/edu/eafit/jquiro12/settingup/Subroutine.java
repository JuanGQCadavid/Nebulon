package co.edu.eafit.jquiro12.settingup;

public class Subroutine {
    private int id;
    //Days
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean tursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    //Limits

    private int from_start;
    private int from_end;

    private int till_start;
    private int till_end;

    private int frequency;

    public Subroutine (){

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

    public boolean isTursday() {
        return tursday;
    }

    public void setTursday(boolean tursday) {
        this.tursday = tursday;
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

    public int getFrom_start() {
        return from_start;
    }

    public void setFrom_start(int from_start) {
        this.from_start = from_start;
    }

    public int getFrom_end() {
        return from_end;
    }

    public void setFrom_end(int from_end) {
        this.from_end = from_end;
    }

    public int getTill_start() {
        return till_start;
    }

    public void setTill_start(int till_start) {
        this.till_start = till_start;
    }

    public int getTill_end() {
        return till_end;
    }

    public void setTill_end(int till_end) {
        this.till_end = till_end;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
