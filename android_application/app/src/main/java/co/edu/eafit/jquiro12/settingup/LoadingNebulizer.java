package co.edu.eafit.jquiro12.settingup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;

public class LoadingNebulizer extends AppCompatActivity {

    ArrayList<Datos_List_Nebulizer> nebulonList;
    String[] wifiData;
    int STANDAR_PORT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_nebulizer);

        nebulonList = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("Neb_found-Config_neb");
        wifiData = (String[]) getIntent().getExtras().getSerializable("Neb_found-wifiData");
        STANDAR_PORT = (Integer) getIntent().getExtras().getSerializable("STANDAR_PORT");


    }

    public void sendAllWiFi(){
        String type = wifiData[0];
        String ssid = wifiData[1];
        String password = wifiData[2];
        String user = wifiData[3];

        for(Datos_List_Nebulizer nebulizer:nebulonList){

            //Paralelizar!!
            sendWiFiData(nebulizer.getIp(),getJson(type,ssid,password,user));

        }

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
