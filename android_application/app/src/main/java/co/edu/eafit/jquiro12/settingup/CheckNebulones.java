package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckNebulones extends AppCompatActivity {

    ArrayList<Datos_List_Nebulizer> nebulonesConnected;
    ArrayList<Datos_List_Nebulizer> nebulonesNoConnected;
    String [] wifiData;
    int STANDAR_PORT;

    //list views
    ListView connectedList;
    ListView noConnectedList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_nebulones);

        nebulonesConnected = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("neb_connected");
        nebulonesNoConnected = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("neb_no_connected");
        STANDAR_PORT = (Integer)getIntent().getExtras().getSerializable("STANDAR_PORT");
        wifiData = (String[]) getIntent().getExtras().getSerializable("Neb_found-wifiData");

        if(nebulonesConnected == null){
            nebulonesConnected = new ArrayList<>();
        }
        if(nebulonesNoConnected == null){
            nebulonesNoConnected = new ArrayList<>();
        }

        // Find the view list
        connectedList = (ListView) findViewById(R.id.nebulonesConnected);
        noConnectedList = (ListView) findViewById(R.id.nebulobesNoConnected);

        //Create the adaptor which is gonna be used for connected
        Adapter_Configure connecterAdaptor  = new Adapter_Configure(getApplicationContext(), nebulonesConnected);
        Adapter_Configure noConnecterAdaptor  = new Adapter_Configure(getApplicationContext(), nebulonesNoConnected);

        //Connect the Adapter to the list.
        connectedList.setAdapter(connecterAdaptor);
        noConnectedList.setAdapter(noConnecterAdaptor);
    }

    public void Next(View view) {
        Intent intent = new Intent(this, routineMain.class);
        intent.putExtra("neb_list", (Serializable) nebulonesConnected);
        intent.putExtra("STANDAR_PORT", (Serializable) STANDAR_PORT);

        startActivity(intent);

    }

    public void back(View view) {
        Intent intent = new Intent(this, LoadingNebulizer.class);
        intent.putExtra("neb_no_connected_C", (Serializable) nebulonesNoConnected);
        intent.putExtra("neb_connected_C", (Serializable) nebulonesConnected);
        intent.putExtra("STANDAR_PORT", (Serializable) STANDAR_PORT);
        intent.putExtra("Neb_found-wifiData",(Serializable) wifiData);

        startActivity(intent);

    }
}
