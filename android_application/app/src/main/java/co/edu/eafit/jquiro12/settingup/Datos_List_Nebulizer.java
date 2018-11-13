package co.edu.eafit.jquiro12.settingup;

import java.io.Serializable;


public class Datos_List_Nebulizer implements Serializable {
    private int id;
    private String ip;
    private String mac;


    public Datos_List_Nebulizer(int id, String ip, String mac){
        this.id = id;
        this.ip = ip;
        this.mac = mac;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac_title) {
        this.mac = mac;
    }

}
