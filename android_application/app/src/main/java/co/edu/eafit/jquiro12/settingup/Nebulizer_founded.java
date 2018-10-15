package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Nebulizer_founded extends AppCompatActivity {
    ListView listView;
    ArrayList<Datos_List_Nebulizer> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nebulizer_founded);

        listView = (ListView) findViewById(R.id.nebulizers_list);
        lista = new ArrayList<>();

        lista.add(new Datos_List_Nebulizer(1, "NEB_A_1", "00:0a:95:9d:68:16", false));
        lista.add(new Datos_List_Nebulizer(2, "NEB_B_45", "00:25:96:12:34:56", false));
        lista.add(new Datos_List_Nebulizer(2, "NEB_A_78", "b8:ee:65:4d:d1:e8", false));
        lista.add(new Datos_List_Nebulizer(1, "NEB_A_1", "00:0a:95:9d:68:16", false));
        lista.add(new Datos_List_Nebulizer(2, "NEB_B_45", "00:25:96:12:34:56", false));
        lista.add(new Datos_List_Nebulizer(2, "NEB_A_78", "b8:ee:65:4d:d1:e8", false));

        //lista = new ArrayList<>()
        Adaptador adaptor  = new Adaptador(getApplicationContext(), lista);

        listView.setAdapter(adaptor);
    }

    public void Back(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void Next(View view) {

    }
}

/*


<Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Sig"/>
 */