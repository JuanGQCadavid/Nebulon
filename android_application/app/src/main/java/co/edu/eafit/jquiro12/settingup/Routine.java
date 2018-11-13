package co.edu.eafit.jquiro12.settingup;

import java.util.ArrayList;

public class Routine {
    private int id;
    private String name;
    private String description;
    private ArrayList<Subroutine> subroutines;

    public Routine(){

    }

    public Routine(int id, String name, String description, ArrayList<Subroutine> subroutines) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subroutines = subroutines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Subroutine> getSubroutines() {
        return subroutines;
    }

    public void setSubroutines(ArrayList<Subroutine> subroutines) {
        this.subroutines = subroutines;
    }
}
