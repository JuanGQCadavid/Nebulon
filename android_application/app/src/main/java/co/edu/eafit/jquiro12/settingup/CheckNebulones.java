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

    Data program_data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_nebulones);

        nebulonesConnected = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("neb_connected");
        nebulonesNoConnected = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("neb_no_connected");
        program_data = (Data) getIntent().getSerializableExtra("global_data");

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
        program_data.setList(nebulonesConnected);

        Intent intent = new Intent(this, routineMain.class);
        intent.putExtra("global_data", program_data);
        startActivity(intent);

    }

    public void back(View view) {
        Intent intent = new Intent(this, LoadingNebulizer.class);
        intent.putExtra("neb_no_connected_C", (Serializable) nebulonesNoConnected);
        intent.putExtra("neb_connected_C", (Serializable) nebulonesConnected);
        intent.putExtra("global_data", program_data);;

        startActivity(intent);

    }
}
