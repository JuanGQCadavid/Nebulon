package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class routineMain extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    Data program_data;
    ArrayList<Routine> routinesList;
    Spinner spinner;
    Routine routine_selected;
    TextView desc_v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_main_2);

        program_data = (Data) getIntent().getSerializableExtra("global_data");

        if(program_data.getRoutines() == null){
            program_data.setRoutines(new ArrayList<Routine>());
        }

        routinesList = program_data.getRoutines();

        String [] titles = getTitles();

        spinner  = (Spinner) findViewById(R.id.spinner_v);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, titles);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        desc_v = (TextView)  findViewById(R.id.txt_desc);

    }

    public String [] getTitles(){
        String [] titles = new String [routinesList.size()];

        int count = 0;
        for(Routine routine : routinesList){
            titles[count] = routine.getName();
            count ++;
        }

        return titles;

    }

    public void createRoutine(View view) {
        program_data.setRoutine(null);
        Intent intent = new Intent(this, MakeRoutine.class);
        intent.putExtra("global_data", program_data);
        startActivity(intent);
    }

    public void back(View view) {
    }

    public void send(View view){

        //Comming soon.

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        routine_selected = routinesList.get(position);
        desc_v.setText(routine_selected.getDescription());
        Toast.makeText(getApplicationContext(), routine_selected.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
