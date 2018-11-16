package co.edu.eafit.jquiro12.settingup;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    //Port for communication -> Welcome
    int port;

    //Nebulizer founde
    ArrayList<Datos_List_Nebulizer> list;

    //Neb config to load
    ArrayList<Datos_List_Nebulizer> config_load;


    //List of routines
    ArrayList<Routine> routines;

    //Config neb
    String [] WiFiData;

    //routine RoutineDays-> MakeDays
    Routine routine;



    public Data(){
    }

    public void addRoutineToRoutines(Routine routine){
        routines.add(routine);
    }
    public ArrayList<Datos_List_Nebulizer> getConfig_load() {
        return config_load;
    }

    public void setConfig_load(ArrayList<Datos_List_Nebulizer> config_load) {
        this.config_load = config_load;
    }

    public String[] getWiFiData() {
        return WiFiData;
    }

    public void setWiFiData(String[] wiFiData) {
        WiFiData = wiFiData;
    }

    public ArrayList<Datos_List_Nebulizer> getList() {
        return list;
    }

    public void setList(ArrayList<Datos_List_Nebulizer> list) {
        this.list = list;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ArrayList<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(ArrayList<Routine> routines) {
        this.routines = routines;
    }
}
