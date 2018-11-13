package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ConfigureNebulones extends AppCompatActivity {

    public final String CODE = "co.edu.eafit.jquiro12.settingup.ConfigureNebulones.nebulonList";

    ListView listView;
    EditText txt_ssid, txt_pw, txt_un;
    String response;

    ArrayList<Datos_List_Nebulizer> nebulonList;

    Spinner spinner1;

    private int STANDAR_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_nebulones);

        //Generate the list
        nebulonList = getNebulonesFound();

        // Find the view list
        listView = (ListView) findViewById(R.id.toConfigureList);

        //Create the adaptor which is gonna be used
        Adapter_Configure adaptor  = new Adapter_Configure(getApplicationContext(), nebulonList);

        //Connect the Adapter to the list.
        listView.setAdapter(adaptor);

        spinner1 = (Spinner) findViewById(R.id.spinner_1);
        txt_ssid = (EditText) findViewById(R.id.ssid);
        txt_pw = (EditText) findViewById(R.id.password);
        txt_un = (EditText) findViewById(R.id.userName);

        STANDAR_PORT = (Integer)getIntent().getExtras().getSerializable("STANDAR_PORT");


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {



                TextView txt_no = (TextView) findViewById(R.id.estaNo);

                response = (String)adapterView.getItemAtPosition(pos);

                if(response.equals("0")){
                    txt_ssid.setVisibility(View.VISIBLE);
                    txt_pw.setVisibility(View.GONE);
                    txt_un.setVisibility(View.GONE);
                    txt_no.setVisibility(View.GONE);

                }else if(response.equals("1")){
                    txt_ssid.setVisibility(View.VISIBLE);
                    txt_pw.setVisibility(View.VISIBLE);
                    txt_un.setVisibility(View.GONE);
                    txt_no.setVisibility(View.GONE);


                }else if(response.equals("2")){
                    txt_ssid.setVisibility(View.VISIBLE);
                    txt_pw.setVisibility(View.VISIBLE);
                    txt_un.setVisibility(View.VISIBLE);
                    txt_no.setVisibility(View.GONE);


                }else{
                    txt_ssid.setVisibility(View.GONE);
                    txt_pw.setVisibility(View.GONE);
                    txt_un.setVisibility(View.GONE);
                    txt_no.setVisibility(View.VISIBLE);

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
    }

    public ArrayList<Datos_List_Nebulizer>  getNebulonesFound(){
        ArrayList<Datos_List_Nebulizer> list = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("Neb_found-Config_neb");

        System.out.println("PENDIENTE");
        for (Datos_List_Nebulizer datoActual: list){
            System.out.println(datoActual.getIp());

        }

        return list;
    }

    public void back(View view) {

    }

    public void Next(View view) {
        //send Wifi


        String[] WiFiData = getWiFiData();

        Intent intent = new Intent(this, LoadingNebulizer.class);
        intent.putExtra("Config_neb-Load_neb",(Serializable)nebulonList);
        intent.putExtra("Neb_found-wifiData",(Serializable)WiFiData);
        intent.putExtra("STANDAR_PORT",(Serializable)STANDAR_PORT);

        startActivity(intent);
    }

    public String[] getWiFiData(){

        String [] data = new String[4];
        data[0] = response;
        data[1] = txt_ssid.getText().toString();
        data[2] = txt_pw.getText().toString();
        data[3] = txt_un.getText().toString();

        return data;
    }

}
