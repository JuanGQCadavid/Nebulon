package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class LoadingNebulizer extends AppCompatActivity {

    ArrayList<Datos_List_Nebulizer> nebulonList;

    ArrayList<Datos_List_Nebulizer> nebulonConnected;
    ArrayList<Datos_List_Nebulizer> nebulonNoConnected;

    String[] wifiData;
    int STANDAR_PORT;
    Handler mHandler;

    TextView txt_view_loading;

    Data program_data;

    //private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_nebulizer);

        program_data = (Data) getIntent().getSerializableExtra("global_data");

        nebulonList = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("Config_neb-Load_neb");
        if(nebulonList == null)
            nebulonList = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("neb_no_connected_C");


        wifiData = program_data.getWiFiData();
        STANDAR_PORT = program_data.getPort();


        nebulonConnected = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("neb_connected_C");;
        if(nebulonConnected == null)
            nebulonConnected = new ArrayList<>();


        nebulonNoConnected = new ArrayList<>();


        txt_view_loading = (TextView) findViewById(R.id.loading_status);

        new Thread(new Runnable() {
            public void run() {
                sendAllWiFi();
            }
        }).start();

    }

    public void next(){
        Intent intent = new Intent(this, CheckNebulones.class);


        intent.putExtra("global_data",program_data);
        intent.putExtra("neb_no_connected", (Serializable) nebulonNoConnected);
        intent.putExtra("neb_connected", (Serializable) nebulonConnected);
        startActivity(intent);
    }

    public void sendAllWiFi(){
        String type = wifiData[0];
        String ssid = wifiData[1];
        String password = wifiData[2];
        String user = wifiData[3];



        ArrayList<SendWiFiThread> threads = new ArrayList<>();
        System.out.println("Mandando datos");
        //txt_view_loading.setText("Mandando datos");
        for(Datos_List_Nebulizer nebulizer:nebulonList){

            //Paralelizar!!
            SendWiFiThread thread = new SendWiFiThread(STANDAR_PORT,nebulizer.getIp(),type,ssid,password,user);
            threads.add(thread);
            thread.start();


        }
        //txt_view_loading.setText("Validando envio.");
        System.out.println("Validando envio.");
        while(threads.size() > 0){
            for(SendWiFiThread thread :threads){
                if(thread.getState() ==  Thread.State.TERMINATED){
                    threads.remove(threads.indexOf(thread));
                }
            }

        }

        //txt_view_loading.setText("Esperando respuesta: ");
        System.out.println("Esperando respuesta: ");

        long start_time = System.currentTimeMillis(); // One Minute
        long waiting_time = System.currentTimeMillis() - start_time;
        int time_to_wait = 10000;

        while(waiting_time< time_to_wait){
            System.out.println("Esperando respuesta: " +  waiting_time / 1000 + " Segundos.");
            //txt_view_loading.setText("Esperando respuesta: " +  waiting_time / 1000 + " Segundos.");
            waiting_time = System.currentTimeMillis() - start_time;

        }
        System.out.println("Dude!");

        //Lets see wich nebulizers are still connected.
        for(int i = 0; i < nebulonList.size(); i ++){
            Datos_List_Nebulizer nebulon = nebulonList.get(i);
            String ip =nebulon.getIp();


            try {
                if(Inet4Address.getByName(ip).isReachable(1500) == true){
                    System.out.println(ip +" is not connected");
                    nebulonNoConnected.add(nebulon);
                }else{
                    nebulonConnected.add(nebulon);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        next();


    }
    public void back(View view){

    }




}
