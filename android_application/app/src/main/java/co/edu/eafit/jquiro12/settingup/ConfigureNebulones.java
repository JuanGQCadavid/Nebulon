package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ConfigureNebulones extends AppCompatActivity {

    public final String CODE = "co.edu.eafit.jquiro12.settingup.ConfigureNebulones.nebulonList";

    ListView listView;
    ArrayList<Datos_List_Nebulizer> nebulonList;

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
    }

    public ArrayList<Datos_List_Nebulizer>  getNebulonesFound(){
        ArrayList<Datos_List_Nebulizer> list = (ArrayList<Datos_List_Nebulizer>) getIntent().getExtras().getSerializable("sup");

        System.out.println("PENDIENTE");
        for (Datos_List_Nebulizer datoActual: list){
            System.out.println(datoActual.getId_title());

        }

        return list;
    }

    public void back(View view) {
    }

    public void Next(View view) {
        Intent intent = new Intent(this, routineMain.class);
        startActivity(intent);
    }
}
