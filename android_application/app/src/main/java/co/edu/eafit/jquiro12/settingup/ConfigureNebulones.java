package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ConfigureNebulones extends AppCompatActivity {

    public final String CODE = "co.edu.eafit.jquiro12.settingup.ConfigureNebulones.nebulonList";

    ListView listView;
    ArrayList<Datos_List_Nebulizer> nebulonList;

    Spinner spinner1;

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
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Spinner spinner = (Spinner) view.findViewById(R.id.spinner_1);
                TextView txt_ssid = (TextView) view.findViewById(R.id.ssid);
                TextView txt_pw = (TextView) view.findViewById(R.id.password);
                TextView txt_un = (TextView) view.findViewById(R.id.userName);

                String response = String.valueOf(spinner.getSelectedItem());

                if(response.equals("0")){
                    txt_ssid.setVisibility(View.VISIBLE);
                    txt_pw.setVisibility(View.GONE);
                    txt_un.setVisibility(View.GONE);

                }else if(response.equals("1")){
                    txt_ssid.setVisibility(View.VISIBLE);
                    txt_pw.setVisibility(View.VISIBLE);
                    txt_un.setVisibility(View.GONE);


                }else{
                    txt_ssid.setVisibility(View.VISIBLE);
                    txt_pw.setVisibility(View.VISIBLE);
                    txt_un.setVisibility(View.VISIBLE);

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
        startActivity(intent);
    }

    public String[] getWiFiData(){
        String [] data = new String[4];


        return data;
    }

}
