package co.edu.eafit.jquiro12.settingup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Nebulizer_founded extends AppCompatActivity {

    public final String CODE = "co.edu.eafit.jquiro12.settingup.Nebulizer_founded.nebulonList";
    ListView listView;
    ArrayList<Datos_List_Nebulizer> lista;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nebulizer_founded);

        //Generate the list
        lista = findNebulonesConneted();

        // Find the view list
        listView = (ListView) findViewById(R.id.nebulizers_list);

        //Create the adaptor which is gonna be used
        Adaptador adaptor  = new Adaptador(getApplicationContext(), lista);

        //Connect the Adapter to the list.
        listView.setAdapter(adaptor);
    }


    public void back(View view) {

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void next(View view) {
        ArrayList<Datos_List_Nebulizer>  nebulones_to_configure = localizeSelectedNebulones(listView);

        System.out.println("PENDIENTE");
        for (Datos_List_Nebulizer datoActual: nebulones_to_configure){
            System.out.println(datoActual.getId_title());

        }

        Intent intent = new Intent(this, ConfigureNebulones.class);
        intent.putExtra("sup", (Serializable) nebulones_to_configure);
        startActivity(intent);

    }

    /*
        This methos looks on the ip connected
        to the nebulon and check whetere then are
        Nebulones or not.

        List ones who are Nebulones.
     */
    public ArrayList<Datos_List_Nebulizer>  findNebulonesConneted(){
        ArrayList<Datos_List_Nebulizer> found = new ArrayList<>();


        found.add(new Datos_List_Nebulizer(1, "NEB_A_1", "00:0a:95:9d:68:16"));
        found.add(new Datos_List_Nebulizer(2, "NEB_B_45", "00:25:96:12:34:56"));
        found.add(new Datos_List_Nebulizer(2, "NEB_A_78", "b8:ee:65:4d:d1:e8"));
        found.add(new Datos_List_Nebulizer(1, "NEB_A_1", "00:0a:95:9d:68:16"));
        found.add(new Datos_List_Nebulizer(2, "NEB_B_45", "00:25:96:12:34:56"));


        return found;
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
}

