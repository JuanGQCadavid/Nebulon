package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.TimePickerDialog;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

public class RoutineDays extends AppCompatActivity implements View.OnClickListener {
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    Subroutine subroutine;

    String horas_from;
    String minutos_from;

    String horas_till;
    String minutos_till;


    //Widgets
    EditText etHora;
    ImageButton ibObtenerHora;

    EditText till_txt_v;
    ImageButton till_btn_v;

    EditText Frequency_txt_v;

    CheckBox c_l_v;
    CheckBox c_m_v;
    CheckBox c_w_v;
    CheckBox c_j_v;
    CheckBox c_v_v;
    CheckBox c_s_v;
    CheckBox c_d_v;

    Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_days);
        //Widget EditText donde se mostrara la hora obtenida

        routine = (Routine) getIntent().getSerializableExtra("routine-M-R");

        etHora = (EditText) findViewById(R.id.from_txt);
        ibObtenerHora= (ImageButton) findViewById(R.id.from_btn);
        ibObtenerHora.setOnClickListener(this);

        till_txt_v = (EditText) findViewById(R.id.till_txt);
        till_btn_v= (ImageButton) findViewById(R.id.till_btn);
        till_btn_v.setOnClickListener(this);

        Frequency_txt_v = (EditText) findViewById(R.id.Frequency_txt);


        //subroutine = (Subroutine) getIntent().getExtras().getSerializable("Subroutine_r-d");
        subroutine = new Subroutine();

        c_l_v = (CheckBox) findViewById(R.id.c_l);
        c_m_v = (CheckBox) findViewById(R.id.c_m);
        c_w_v = (CheckBox) findViewById(R.id.c_w);
        c_j_v = (CheckBox) findViewById(R.id.c_j);
        c_v_v = (CheckBox) findViewById(R.id.c_v);
        c_s_v = (CheckBox) findViewById(R.id.c_s);
        c_d_v = (CheckBox) findViewById(R.id.c_d);

    }


    private void obtenerHora_from(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado

                horas_from = horaFormateada;
                minutos_from = minutoFormateado;

                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);

            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);
        recogerHora.show();
    }

    private void obtenerHora_till(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado

                horas_till = horaFormateada;
                minutos_till = minutoFormateado;

                till_txt_v.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);

            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);
        recogerHora.show();
    }

    public void Next(View view) {

        if(Frequency_txt_v.getText().toString().equals("") || horas_from == null || horas_till == null){
            Toast.makeText(getApplicationContext(), "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int frec = Integer.parseInt(Frequency_txt_v.getText().toString());


        System.out.println("--------------------------------------------------");
        System.out.println("From ->" + horas_from + ":" + minutos_from);
        System.out.println("till ->" + horas_till + ":" + minutos_till);
        System.out.println("Lunes -> " + c_l_v.isChecked());
        System.out.println("--------------------------------------------------");

        //Days
        subroutine.setMonday(c_l_v.isChecked());
        subroutine.setTuesday(c_m_v.isChecked());
        subroutine.setWednesday(c_w_v.isChecked());
        subroutine.setTuesday(c_j_v.isChecked());
        subroutine.setFriday(c_v_v.isChecked());
        subroutine.setSaturday(c_s_v.isChecked());
        subroutine.setSunday(c_d_v.isChecked());

        subroutine.setFrom_hour(horas_from);
        subroutine.setFrom_minute(minutos_from);

        subroutine.setTill_hour(horas_till);
        subroutine.setTill_minute(minutos_till);

        subroutine.setFrequency(frec);

        routine.addSubroutine(subroutine);
        System.out.println("Hola?");

        Intent intent = new Intent(this,MakeRoutine.class);
        intent.putExtra("routine-R-M", routine);
        startActivity(intent);




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.from_btn:
                obtenerHora_from();
                break;
            case R.id.till_btn:
                obtenerHora_till();
                break;


        }

    }
}
