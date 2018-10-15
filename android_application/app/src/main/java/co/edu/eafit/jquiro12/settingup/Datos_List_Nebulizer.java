package co.edu.eafit.jquiro12.settingup;

public class Datos_List_Nebulizer {
    private int id;
    private String id_title;
    private String mac_title;
    private boolean selected_option;

    public Datos_List_Nebulizer(int id, String id_title, String mac_title, boolean selected_option){
        this.id = id;
        this.id_title = id_title;
        this.mac_title = mac_title;
        this.selected_option = selected_option;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_title() {
        return id_title;
    }

    public void setId_title(String id_title) {
        this.id_title = id_title;
    }

    public String getMac_title() {
        return mac_title;
    }

    public void setMac_title(String mac_title) {
        this.mac_title = mac_title;
    }

    public boolean isSelected_option() {
        return selected_option;
    }

    public void setSelected_option(boolean selected_option) {
        this.selected_option = selected_option;
    }
}
