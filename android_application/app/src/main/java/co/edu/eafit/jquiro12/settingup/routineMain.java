package co.edu.eafit.jquiro12.settingup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class routineMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_main_2);
    }
    public void Next(View view) {
        Intent intent = new Intent(this, MakeRoutine.class);
        startActivity(intent);
    }

    public void Back(View view) {
    }
}
