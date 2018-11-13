package co.edu.eafit.jquiro12.settingup;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SendWiFiThread extends Thread {
    int STANDAR_PORT;
    String ip;
    String type;
    String ssid;
    String password;
    String user;


    public SendWiFiThread(int STANDAR_PORT, String ip, String type, String ssid, String password, String user){
        this.STANDAR_PORT = STANDAR_PORT;
        this.ip = ip;
        this.type = type;
        this.ssid = ssid;
        this.password = password;
        this.user = user;

    }

    public void run(){
        sendWiFiData(ip,getJson(type,ssid,password,user));

    }

    public void sendWiFiData(String ip, JSONObject json){
        try {
            Socket connectionNebulizer = new Socket(ip,STANDAR_PORT);

            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(connectionNebulizer.getOutputStream()), true);
            //Send
            salida.println(json.toString());
            connectionNebulizer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public JSONObject getJson(String type, String ssid, String password, String user){
        JSONObject jsonSend = new JSONObject();
        int length = 92; //{"message_size":"","message_type":"app_to_neb_net","network":{"security_type":"","ssid":""}}
        length += type.length() + ssid.length();
        try{

            jsonSend.put("message_type","app_to_neb_net");

            JSONObject variables = new JSONObject();
            variables.put("security_type",type);  //(0 or 1 or 2),

            variables.put("ssid", ssid);

            if(type.equals("2")){
                variables.put("user", user);
                length += 10 + user.length();//,"user":""
            }
            if(type.equals("2") || type.equals("1")){
                variables.put("password", password);
                length += 14 + password.length();//,"password":""
            }

            jsonSend.put("network", variables);

            length += (length+"").length();
            jsonSend.put("message_size", length);


        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonSend;
    }


}
