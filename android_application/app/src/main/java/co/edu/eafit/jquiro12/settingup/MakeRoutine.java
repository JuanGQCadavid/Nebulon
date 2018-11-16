package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MakeRoutine extends AppCompatActivity {

    Routine routine;
    ListView routineList;


    EditText title_view;
    EditText description_view;

    Data program_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_routine);

        title_view = (EditText) findViewById(R.id.titulo_v);
        description_view =  (EditText) findViewById(R.id.desc_v);

        program_data = (Data) getIntent().getSerializableExtra("global_data");

        //subroutines = test();
        routine = program_data.getRoutine();

        if(routine == null){
            routine = new Routine();
            program_data.setRoutine(routine);
        }


        if(!routine.getName().equals(""))
            title_view.setText(routine.getName());

        if(!routine.getDescription().equals(""))
            description_view.setText(routine.getDescription());



        //Listview
        routineList = (ListView) findViewById(R.id.subrutinesList);

        RoutineAdapter rutineAdapter = new RoutineAdapter(getApplicationContext(), routine.getSubroutines());

        routineList.setAdapter(rutineAdapter);

    }

    public void next(View view) {

        program_data.addRoutineToRoutines(program_data.getRoutine());
        Intent intent = new Intent(this, routineMain.class);
        intent.putExtra("global_data",program_data);
        startActivity(intent);
    }
    /*
    public ArrayList<Subroutine> test(){
        ArrayList<Subroutine> test = new ArrayList<>();
            test.add(new Subroutine(1,true,true,true,true,true,true,true,15,00,23,30,5));
            test.add(new Subroutine(2,false,true,true,false,true,true,true,7,50,10,00,4));
            test.add(new Subroutine(3,true,true,false,true,true,false,true,13,45,14,30,3));
        return test;
    }
    */

    public void back(View view) {


    }

    public void addRoutine(View view) {
        if(!routine.getName().equals(title_view.getText().toString()))
            routine.setName(title_view.getText().toString());

        if(!routine.getDescription().equals(description_view.getText().toString()))
            routine.setDescription(description_view.getText().toString());

        program_data.setRoutine(routine);

        Intent intent = new Intent(this, RoutineDays.class);
        intent.putExtra("global_data", program_data);

        startActivity(intent);



    }



}
