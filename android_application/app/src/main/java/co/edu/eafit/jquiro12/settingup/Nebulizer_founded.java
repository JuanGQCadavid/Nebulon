package co.edu.eafit.jquiro12.settingup;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Nebulizer_founded extends AppCompatActivity {

    public final String CODE = "co.edu.eafit.jquiro12.settingup.Nebulizer_founded.nebulonList";
    private int STANDAR_PORT;

    ListView listView;
    ArrayList<Datos_List_Nebulizer> lista;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nebulizer_founded);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        STANDAR_PORT = (Integer) getIntent().getExtras().getSerializable("STANDAR_PORT");

        //Generate the list
        lista = getH(); //new ArrayList<>();


        // Find the view list
        listView = (ListView) findViewById(R.id.nebulizers_list);


        //Create the adaptor which is gonna be used
        Adaptador adaptor  = new Adaptador(getApplicationContext(), lista);

        //Connect the Adapter to the list.
        listView.setAdapter(adaptor);

        //ClientesConnected p = new ClientesConnected(lista,listView);
        //p.start();



    }


    public void back(View view) {

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void next(View view) {
        ArrayList<Datos_List_Nebulizer>  nebulones_to_configure = localizeSelectedNebulones(listView);

        System.out.println("PENDIENTE");
        for (Datos_List_Nebulizer datoActual: nebulones_to_configure){
            System.out.println(datoActual.getIp());

        }



        Intent intent = new Intent(this, ConfigureNebulones.class);
        intent.putExtra("Neb_found-Config_neb", (Serializable) nebulones_to_configure);



        startActivity(intent);

    }




    public ArrayList<Datos_List_Nebulizer> localizeSelectedNebulones(ListView listImplemented ){

        ArrayList<Datos_List_Nebulizer> checked_nebulones = new ArrayList<>();

        // The number of elements stored in the list.
        int child_number = listImplemented.getCount();

        System.out.println(child_number);

        for(int i = 0; i < child_number; i ++){
            //Lets get the object storage in this position
            Datos_List_Nebulizer data_stored = (Datos_List_Nebulizer) listImplemented.getItemAtPosition(i);

            //Get the view of the child associated at the Object
            View view_child = listImplemented.getChildAt(i);


            Switch child_switch = (Switch) view_child.findViewById(R.id.switch_select);

            if(child_switch.isChecked()){
                checked_nebulones.add(data_stored);
            }



        }

        return checked_nebulones;
       // listImplemented.getChildAt()







    }

    /*
        This methos looks on the ip connected
        to the nebulon and check whetere then are
        Nebulones or not.

        List ones who are Nebulones.
     */
    public ArrayList<Datos_List_Nebulizer> getH() {


        System.out.println("***************************************************");
        BufferedReader br = null;
        ArrayList<Datos_List_Nebulizer> found = new ArrayList<>();

        String flushCmd = "sh ip -s -s neigh flush all";
        Runtime runtime = Runtime.getRuntime();
        try
        {
            runtime.exec(flushCmd,null,new File("/proc/net"));
        }catch (Exception e){

        }

        try {

            String ip, mac;
            int id;
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;

            int lineCount= 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount == 1)continue;
                System.out.println(line);
                String[] splitted = line.split(" +");
                ip = splitted[0];
                mac = splitted[3];
                System.out.println(ip);

                id = getId(ip);

                if(id != -1){
                    found.add(new Datos_List_Nebulizer(id, ip, mac));
                }


            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("***************************************************");
        return found;
    }

    private int getId(String ip){
        try {
            if(!Inet4Address.getByName(ip).isReachable(1500) == true)
                return -1;

            Socket connectionNebulizer = new Socket(ip,STANDAR_PORT);

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(connectionNebulizer.getInputStream()));
            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(connectionNebulizer.getOutputStream()), true);

            JSONObject post_dict = new JSONObject();
            try {
                post_dict.put("message_size", 47);
                post_dict.put("message_type", "id_request");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Send
            salida.println(post_dict.toString());

            //recive
            String response = entrada.readLine();
            System.out.println("----------------------------------");
            System.out.println(response);
            System.out.println("----------------------------------");



            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            connectionNebulizer.close();

            return Integer.parseInt(response);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return -1;
    }


}

/*

 public ArrayList<Datos_List_Nebulizer> getClientList() {


        System.out.println("***************************************************");
        BufferedReader br = null;
        ArrayList<Datos_List_Nebulizer> found = new ArrayList<>();

        String flushCmd = "sh ip -s -s neigh flush all";
        Runtime runtime = Runtime.getRuntime();
        try
        {
            runtime.exec(flushCmd,null,new File("/proc/net"));
        }catch (Exception e){

        }

        try {

            String ip, mac;
            int id = 0;
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;

            int lineCount= 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount == 1)continue;
                System.out.println(line);
                String[] splitted = line.split(" +");
                ip = splitted[0];
                mac = splitted[3];
                System.out.println(ip);

                if(Inet4Address.getByName(ip).isReachable(1500) == true){

                    found.add(new Datos_List_Nebulizer(id, ip, mac));

                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("***************************************************");
        return found;
    }

 */

