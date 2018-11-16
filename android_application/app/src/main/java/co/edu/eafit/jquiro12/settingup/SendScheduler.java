package co.edu.eafit.jquiro12.settingup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.ArrayList;

public class SendScheduler extends AppCompatActivity {

    Data program_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_scheduler);
        program_data = (Data) getIntent().getSerializableExtra("global_data");



        getIp(jsonGenerator(program_data.getRoutines()));

    }

    public JSONObject jsonGenerator(ArrayList<Routine> routines){

        return null;
    }

    public ArrayList<String> getIp(JSONObject json){

        return null;

    }
}
